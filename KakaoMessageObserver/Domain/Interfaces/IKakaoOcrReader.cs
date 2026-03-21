using System;
using System.Collections.Generic;
using System.Text;

namespace KakaoMessageObserver.Domain.Interfaces
{
    /// <summary>
    /// OCR 결과를 담는 데이터 모델
    /// </summary>
    public struct OcrResult
    {
        public string Text;
        public int X;
        public int PageWidth;
    }

    public interface IKakaoOcrReader
    {
        List<OcrResult> ReadDetailedText(IntPtr chatWindowHwnd);
        string ReadFromWindow(IntPtr chatWindowHwnd);
    }
}