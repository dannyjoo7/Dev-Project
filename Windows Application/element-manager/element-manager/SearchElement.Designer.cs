namespace element_manager
{
    partial class SearchElement
    {
        /// <summary> 
        /// 필수 디자이너 변수입니다.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// 사용 중인 모든 리소스를 정리합니다.
        /// </summary>
        /// <param name="disposing">관리되는 리소스를 삭제해야 하면 true이고, 그렇지 않으면 false입니다.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region 구성 요소 디자이너에서 생성한 코드

        /// <summary> 
        /// 디자이너 지원에 필요한 메서드입니다. 
        /// 이 메서드의 내용을 코드 편집기로 수정하지 마세요.
        /// </summary>
        private void InitializeComponent()
        {
            this.lblTag = new System.Windows.Forms.Label();
            this.lblDescription = new System.Windows.Forms.Label();
            this.lblName = new System.Windows.Forms.Label();
            this.pic = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.pic)).BeginInit();
            this.SuspendLayout();
            // 
            // lblTag
            // 
            this.lblTag.AutoSize = true;
            this.lblTag.Font = new System.Drawing.Font("나눔고딕", 8.999999F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.lblTag.Location = new System.Drawing.Point(137, 106);
            this.lblTag.Name = "lblTag";
            this.lblTag.Size = new System.Drawing.Size(128, 14);
            this.lblTag.TabIndex = 7;
            this.lblTag.Text = "tag1, tag2, tag3, tag4";
            this.lblTag.Click += new System.EventHandler(this.lblTag_Click);
            // 
            // lblDescription
            // 
            this.lblDescription.Font = new System.Drawing.Font("나눔고딕", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.lblDescription.Location = new System.Drawing.Point(134, 33);
            this.lblDescription.Name = "lblDescription";
            this.lblDescription.Size = new System.Drawing.Size(624, 64);
            this.lblDescription.TabIndex = 6;
            this.lblDescription.Text = "This is element introduction example.";
            this.lblDescription.Click += new System.EventHandler(this.lblDescription_Click);
            // 
            // lblName
            // 
            this.lblName.Font = new System.Drawing.Font("나눔고딕 ExtraBold", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.lblName.Location = new System.Drawing.Point(132, 7);
            this.lblName.Name = "lblName";
            this.lblName.Size = new System.Drawing.Size(625, 23);
            this.lblName.TabIndex = 5;
            this.lblName.Text = "Element";
            this.lblName.Click += new System.EventHandler(this.lblName_Click);
            // 
            // pic
            // 
            this.pic.BackColor = System.Drawing.Color.White;
            this.pic.Location = new System.Drawing.Point(5, 4);
            this.pic.Name = "pic";
            this.pic.Size = new System.Drawing.Size(120, 120);
            this.pic.TabIndex = 4;
            this.pic.TabStop = false;
            this.pic.Click += new System.EventHandler(this.pic_Click);
            // 
            // SearchElement
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Gainsboro;
            this.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.Controls.Add(this.lblTag);
            this.Controls.Add(this.lblDescription);
            this.Controls.Add(this.lblName);
            this.Controls.Add(this.pic);
            this.Name = "SearchElement";
            this.Size = new System.Drawing.Size(758, 128);
            ((System.ComponentModel.ISupportInitialize)(this.pic)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private Label lblTag;
        private Label lblDescription;
        private Label lblName;
        private PictureBox pic;
    }
}
