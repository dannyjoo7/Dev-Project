using KakaoMessageObserver.Domain.Interfaces;
using KakaoMessageObserver.Infrastructure.Automation;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Media;

namespace KakaoMessageObserver.Presentation.ViewModels
{
    /// <summary>
    /// 메인 상태 ViewModel
    /// </summary>
    public class MainStatusViewModel : INotifyPropertyChanged
    {
        #region ===== Dependencies =====

        private readonly IKakaoWindowFinder _windowFinder;
        private readonly IKakaoOcrReader _ocrReader;

        #endregion

        #region ===== Monitoring =====

        private CancellationTokenSource? _monitorCts;

        #endregion

        #region ===== Status Fields =====

        private bool _isKakaoConnected;
        private bool _isMonitoring;
        private bool _isDbConnected;

        #endregion

        #region ===== Runtime Cache =====

        /// <summary>
        /// 채팅방별 메시지 중복 방지 캐시 (Key: 방이름, Value: [방향:메시지] 형태의 HashSet)
        /// </summary>
        private readonly Dictionary<string, HashSet<string>> _chatMessageCache = new();

        /// <summary>
        /// UI 로그 표시용 중복 방지 캐시
        /// </summary>
        private readonly HashSet<string> _logCache = new();

        #endregion

        #region ===== Status Properties =====

        public bool IsKakaoConnected
        {
            get => _isKakaoConnected;
            set { _isKakaoConnected = value; OnNotifyStatus(); }
        }

        public bool IsMonitoring
        {
            get => _isMonitoring;
            private set { _isMonitoring = value; OnNotifyStatus(); }
        }

        public bool IsDbConnected
        {
            get => _isDbConnected;
            set { _isDbConnected = value; OnNotifyStatus(); }
        }

        private void OnNotifyStatus()
        {
            OnPropertyChanged(nameof(IsKakaoConnected));
            OnPropertyChanged(nameof(IsMonitoring));
            OnPropertyChanged(nameof(IsDbConnected));
            OnPropertyChanged(nameof(KakaoStatusText));
            OnPropertyChanged(nameof(KakaoStatusColor));
            OnPropertyChanged(nameof(KakaoCheckIcon));
            OnPropertyChanged(nameof(MonitoringStatusText));
            OnPropertyChanged(nameof(MonitoringStatusColor));
            OnPropertyChanged(nameof(MonitoringCheckIcon));
            OnPropertyChanged(nameof(DbStatusText));
            OnPropertyChanged(nameof(DbStatusColor));
            OnPropertyChanged(nameof(DbCheckIcon));
        }

        #endregion

        #region ===== Status UI Computed =====

        public string KakaoStatusText => IsKakaoConnected ? "Found" : "Not Found";
        public Brush KakaoStatusColor => IsKakaoConnected ? Brushes.Green : Brushes.Red;
        public string KakaoCheckIcon => IsKakaoConnected ? "✔ " : "✖ ";

        public string MonitoringStatusText => IsMonitoring ? "Running" : "Stopped";
        public Brush MonitoringStatusColor => IsMonitoring ? Brushes.Green : Brushes.Red;
        public string MonitoringCheckIcon => IsMonitoring ? "✔ " : "✖ ";

        public string DbStatusText => IsDbConnected ? "Connected" : "Disconnected";
        public Brush DbStatusColor => IsDbConnected ? Brushes.Green : Brushes.Red;
        public string DbCheckIcon => IsDbConnected ? "✔ " : "✖ ";

        #endregion

        #region ===== Collections =====

        public ObservableCollection<string> ChatRoomList { get; }
        public ObservableCollection<string> MessageLog { get; }

        #endregion

        #region ===== Constructor =====

        public MainStatusViewModel(
            IKakaoWindowFinder? windowFinder = null,
            IKakaoOcrReader? ocrReader = null)
        {
            _windowFinder = windowFinder ?? new KakaoWindowFinder();
            _ocrReader = ocrReader ?? new KakaoOcrReader();

            MessageLog = new ObservableCollection<string>();
            ChatRoomList = new ObservableCollection<string>();
        }

        #endregion

        #region ===== Public Logic =====

        /// <summary>
        /// 메시지 모니터링 루프를 백그라운드 태스크로 시작합니다.
        /// </summary>
        public void StartMonitoring()
        {
            try
            {
                if (IsMonitoring) return;
                IsMonitoring = true;
                _monitorCts = new CancellationTokenSource();
                Task.Run(() => MonitoringLoop(_monitorCts.Token));
            }
            catch (Exception ex)
            {
                AddLog($"[Critical] Start Error: {ex.Message}");
                IsMonitoring = false;
            }
        }

        /// <summary>
        /// 실행 중인 모니터링 루프를 안전하게 중단합니다.
        /// </summary>
        public void StopMonitoring()
        {
            try
            {
                if (!IsMonitoring) return;
                _monitorCts?.Cancel();
                _monitorCts = null;
                IsMonitoring = false;
            }
            catch (Exception ex)
            {
                AddLog($"Stop Error: {ex.Message}");
            }
        }

        /// <summary>
        /// 2초 간격으로 카카오톡 상태를 점검하고 데이터를 추출하는 무한 루프입니다.
        /// </summary>
        /// <param name="token">작업 취소를 감지하기 위한 토큰</param>
        private async Task MonitoringLoop(CancellationToken token)
        {
            AddLog("Monitoring Started");
            while (!token.IsCancellationRequested)
            {
                try
                {
                    CheckKakao();
                    await Task.Delay(2000, token);
                }
                catch (TaskCanceledException) { break; }
                catch (Exception ex)
                {
                    // 개별 루프에서 에러 발생 시 로그만 남기고 다음 루프 진행
                    AddLog($"Loop Error: {ex.Message}");
                    await Task.Delay(5000, token); // 에러 반복 시 부하 방지를 위해 조금 더 대기
                }
            }
            AddLog("Monitoring Stopped");
        }

        /// <summary>
        /// 현재 활성화된 카카오톡 채팅창 목록을 확인하고 메시지 처리를 시작합니다.
        /// </summary>
        public void CheckKakao()
        {
            try
            {
                var chatWindows = _windowFinder.GetKakaoChatWindows();

                if (chatWindows.Count == 0)
                {
                    IsKakaoConnected = false;
                    RemoveAllChatRooms();
                    return;
                }

                IsKakaoConnected = true;
                var currentRooms = new HashSet<string>();

                foreach (var win in chatWindows)
                {
                    currentRooms.Add(win.title);
                    AddChatRoom(win.title);
                    ProcessChatWindow(win.title, win.hwnd);
                }

                RemoveClosedChatRooms(currentRooms);
            }
            catch (Exception ex)
            {
                AddLog($"Check Window Error: {ex.Message}");
            }
        }

        #endregion

        #region ===== Chat Processing (The Core) =====

        /// <summary>
        /// 특정 채팅창의 OCR 분석을 수행하고 새 메시지를 판별하여 기록합니다.
        /// </summary>
        /// <param name="chatName">채팅방 제목</param>
        /// <param name="hwnd">채팅창 윈도우 핸들</param>
        private void ProcessChatWindow(string chatName, IntPtr hwnd)
        {
            try
            {
                // OCR 엔진 실행
                var recognizedItems = _ocrReader.ReadDetailedText(hwnd);

                foreach (var item in recognizedItems)
                {
                    string cleanedMsg = RefineOcrText(item.Text);
                    if (string.IsNullOrWhiteSpace(cleanedMsg)) continue;

                    // X 좌표 기반 송/수신 판별
                    string direction = item.X < (item.PageWidth * 0.35) ? "RECV" : "SEND";

                    string cacheKey = $"{direction}:{cleanedMsg}";
                    if (IsNewMessage(chatName, cacheKey))
                    {
                        AddLog($"[{direction}][{chatName}] {cleanedMsg}");
                    }
                }
            }
            catch (Exception ex)
            {
                // 특정 창 분석 실패 시 해당 창만 건너뜀
                System.Diagnostics.Debug.WriteLine($"Process Window [{chatName}] Error: {ex.Message}");
            }
        }

        /// <summary>
        /// OCR로 추출된 원본 문자열에서 시간 정보 및 노이즈를 제거합니다.
        /// </summary>
        /// <param name="input">OCR 원본 텍스트</param>
        /// <returns>정제된 순수 메시지 텍스트</returns>
        private string RefineOcrText(string input)
        {
            try
            {
                if (string.IsNullOrWhiteSpace(input)) return string.Empty;

                string result = Regex.Replace(input, @"(오전|오후)\s?\d{1,2}:\d{2}", "");
                result = Regex.Replace(result, @"\d{1,2}:\d{2}", "");
                result = Regex.Replace(result, @"[^가-힣a-zA-Z0-9\s\?\!\.]", "");

                return result.Trim();
            }
            catch { return input.Trim(); }
        }

        #endregion

        #region ===== Helpers =====

        private void AddChatRoom(string roomName)
        {
            Application.Current.Dispatcher.Invoke(() =>
            {
                if (!ChatRoomList.Contains(roomName))
                    ChatRoomList.Add(roomName);
            });
        }

        private void RemoveClosedChatRooms(HashSet<string> currentRooms)
        {
            Application.Current.Dispatcher.Invoke(() =>
            {
                for (int i = ChatRoomList.Count - 1; i >= 0; i--)
                {
                    if (!currentRooms.Contains(ChatRoomList[i]))
                    {
                        string room = ChatRoomList[i];
                        ChatRoomList.RemoveAt(i);
                        _chatMessageCache.Remove(room);
                        AddLog($"Chat Closed: {room}");
                    }
                }
            });
        }

        private void RemoveAllChatRooms()
        {
            Application.Current.Dispatcher.Invoke(() =>
            {
                ChatRoomList.Clear();
                _chatMessageCache.Clear();
            });
        }

        private void AddLog(string text)
        {
            if (string.IsNullOrWhiteSpace(text)) return;

            string logEntry = $"[{DateTime.Now:HH:mm:ss}] {text}";

            Application.Current.Dispatcher.Invoke(() =>
            {
                MessageLog.Add(logEntry);

                if (MessageLog.Count > 500)
                {
                    MessageLog.RemoveAt(0);
                }
            });
        }

        private bool IsNewMessage(string chatRoom, string cacheKey)
        {
            if (!_chatMessageCache.TryGetValue(chatRoom, out var set))
            {
                set = new HashSet<string>();
                _chatMessageCache[chatRoom] = set;
            }
            return set.Add(cacheKey);
        }

        #endregion

        #region ===== INotifyPropertyChanged =====

        public event PropertyChangedEventHandler? PropertyChanged;
        protected void OnPropertyChanged(string name) => PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(name));

        #endregion
    }
}