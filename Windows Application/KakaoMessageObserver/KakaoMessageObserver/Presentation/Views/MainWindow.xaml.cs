using KakaoMessageObserver.Presentation.ViewModels;
using System.Collections.Specialized;
using System.Windows;

namespace KakaoMessageObserver
{
    public partial class MainWindow : Window
    {
        public MainStatusViewModel VM { get; }

        public MainWindow()
        {
            InitializeComponent();

            VM = new MainStatusViewModel();
            DataContext = VM;

            InitializeState();
            InitializeEvents();
        }

        #region ===== 초기화 =====

        private void InitializeState()
        {
            // 테스트 초기 상태
            VM.IsKakaoConnected = false;
            VM.IsDbConnected = true;
        }

        private void InitializeEvents()
        {
            VM.MessageLog.CollectionChanged += OnMessageLogChanged;
        }

        #endregion

        #region ===== Monitoring Fields =====

        private CancellationTokenSource? _monitorCts;

        #endregion

        #region ===== UI Event Handlers =====

        private void OnMessageLogChanged(object? sender, NotifyCollectionChangedEventArgs e)
        {
            Dispatcher.BeginInvoke(() =>
            {
                if (MessageListBox.Items.Count > 0)
                {
                    MessageListBox.ScrollIntoView(
                        MessageListBox.Items[MessageListBox.Items.Count - 1]);
                }
            });
        }


        #endregion


        #region ===== Button Clicks =====

        private void StartMonitoringButton_Click(object sender, RoutedEventArgs e)
        {
            VM.StartMonitoring();
        }

        private void StopMonitoringButton_Click(object sender, RoutedEventArgs e)
        {
            VM.StopMonitoring();
        }

        #endregion


        #region ===== Window Lifecycle =====

        protected override void OnClosed(System.EventArgs e)
        {
            // 프로그램 종료 시 Monitoring 중지 (중요)
            VM.StopMonitoring();

            base.OnClosed(e);
        }

        #endregion
    }
}
