namespace element_manager
{
    partial class ElementPublisher
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.Button btnPrev;
            Syncfusion.Windows.Forms.Tools.CustomImageCollection customImageCollection1 = new Syncfusion.Windows.Forms.Tools.CustomImageCollection();
            Syncfusion.Windows.Forms.Tools.ResetButton resetButton1 = new Syncfusion.Windows.Forms.Tools.ResetButton();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.labelElementName = new System.Windows.Forms.Label();
            this.panel2 = new System.Windows.Forms.Panel();
            this.label1 = new System.Windows.Forms.Label();
            this.labelPublisher = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.labelId = new System.Windows.Forms.Label();
            this.labelTag = new System.Windows.Forms.Label();
            this.linkSite = new System.Windows.Forms.LinkLabel();
            this.mySqlDataAdapter1 = new MySql.Data.MySqlClient.MySqlDataAdapter();
            this.rating = new Syncfusion.Windows.Forms.Tools.RatingControl();
            this.labelInfo = new System.Windows.Forms.Label();
            this.textMemo = new System.Windows.Forms.TextBox();
            this.btnConfirm = new System.Windows.Forms.Button();
            btnPrev = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // btnPrev
            // 
            btnPrev.Font = new System.Drawing.Font("맑은 고딕", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            btnPrev.Location = new System.Drawing.Point(724, 13);
            btnPrev.Name = "btnPrev";
            btnPrev.Size = new System.Drawing.Size(70, 45);
            btnPrev.TabIndex = 34;
            btnPrev.Text = "<Prev";
            btnPrev.UseVisualStyleBackColor = true;
            btnPrev.Click += new System.EventHandler(this.button1_Click);
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackColor = System.Drawing.SystemColors.ControlDark;
            this.pictureBox1.Location = new System.Drawing.Point(12, 12);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(170, 170);
            this.pictureBox1.TabIndex = 0;
            this.pictureBox1.TabStop = false;
            this.pictureBox1.Click += new System.EventHandler(this.pictureBox1_Click);
            // 
            // labelElementName
            // 
            this.labelElementName.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.labelElementName.ForeColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.labelElementName.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.labelElementName.Location = new System.Drawing.Point(190, 12);
            this.labelElementName.Name = "labelElementName";
            this.labelElementName.Size = new System.Drawing.Size(320, 46);
            this.labelElementName.TabIndex = 6;
            this.labelElementName.Text = "Element Name";
            this.labelElementName.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.labelElementName.Click += new System.EventHandler(this.label4_Click);
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.Color.DarkGray;
            this.panel2.Location = new System.Drawing.Point(12, 188);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(780, 2);
            this.panel2.TabIndex = 29;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("맑은 고딕", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.label1.ForeColor = System.Drawing.SystemColors.ControlDarkDark;
            this.label1.Location = new System.Drawing.Point(199, 58);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(60, 17);
            this.label1.TabIndex = 30;
            this.label1.Text = "등록자 : ";
            this.label1.Click += new System.EventHandler(this.label_Click);
            // 
            // labelPublisher
            // 
            this.labelPublisher.AutoSize = true;
            this.labelPublisher.Font = new System.Drawing.Font("맑은 고딕", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.labelPublisher.ForeColor = System.Drawing.SystemColors.ControlDarkDark;
            this.labelPublisher.Location = new System.Drawing.Point(253, 58);
            this.labelPublisher.Name = "labelPublisher";
            this.labelPublisher.Size = new System.Drawing.Size(63, 17);
            this.labelPublisher.TabIndex = 31;
            this.labelPublisher.Text = "Publisher";
            // 
            // label3
            // 
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label3.ForeColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.label3.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.label3.Location = new System.Drawing.Point(571, 12);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(70, 46);
            this.label3.TabIndex = 32;
            this.label3.Text = "ID : ";
            this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.label3.Click += new System.EventHandler(this.labelId_Click);
            // 
            // labelId
            // 
            this.labelId.BackColor = System.Drawing.Color.White;
            this.labelId.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.labelId.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point);
            this.labelId.ForeColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.labelId.Location = new System.Drawing.Point(636, 12);
            this.labelId.Name = "labelId";
            this.labelId.Size = new System.Drawing.Size(80, 45);
            this.labelId.TabIndex = 33;
            this.labelId.Text = "1234";
            this.labelId.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.labelId.Click += new System.EventHandler(this.label2_Click);
            // 
            // labelTag
            // 
            this.labelTag.AutoSize = true;
            this.labelTag.Font = new System.Drawing.Font("맑은 고딕", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.labelTag.ForeColor = System.Drawing.Color.DodgerBlue;
            this.labelTag.Location = new System.Drawing.Point(199, 86);
            this.labelTag.Name = "labelTag";
            this.labelTag.Size = new System.Drawing.Size(111, 17);
            this.labelTag.TabIndex = 35;
            this.labelTag.Text = "Tag1, Tag2, Tag3";
            this.labelTag.Click += new System.EventHandler(this.label2_Click_1);
            // 
            // linkSite
            // 
            this.linkSite.AutoSize = true;
            this.linkSite.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.linkSite.Location = new System.Drawing.Point(199, 166);
            this.linkSite.Name = "linkSite";
            this.linkSite.Size = new System.Drawing.Size(114, 16);
            this.linkSite.TabIndex = 36;
            this.linkSite.TabStop = true;
            this.linkSite.Text = "www.element.com";
            // 
            // mySqlDataAdapter1
            // 
            this.mySqlDataAdapter1.DeleteCommand = null;
            this.mySqlDataAdapter1.InsertCommand = null;
            this.mySqlDataAdapter1.SelectCommand = null;
            this.mySqlDataAdapter1.UpdateCommand = null;
            // 
            // rating
            // 
            this.rating.Images = customImageCollection1;
            this.rating.Location = new System.Drawing.Point(636, 154);
            this.rating.Name = "rating";
            this.rating.ResetButton = resetButton1;
            this.rating.Size = new System.Drawing.Size(145, 28);
            this.rating.TabIndex = 38;
            this.rating.Text = "ratingControl1";
            this.rating.Click += new System.EventHandler(this.rating_Click);
            // 
            // labelInfo
            // 
            this.labelInfo.AutoSize = true;
            this.labelInfo.Font = new System.Drawing.Font("맑은 고딕", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.labelInfo.ForeColor = System.Drawing.SystemColors.ControlDarkDark;
            this.labelInfo.Location = new System.Drawing.Point(15, 199);
            this.labelInfo.MaximumSize = new System.Drawing.Size(400, 400);
            this.labelInfo.Name = "labelInfo";
            this.labelInfo.Size = new System.Drawing.Size(396, 68);
            this.labelInfo.TabIndex = 39;
            this.labelInfo.Text = "커넥츠 프랩 공시관은 관리형 독학관 답게 학습 수준 및 시기에 따른 맞춤형 커리큘럼을 제공한다. 공시를 시작하는 수험생을 위한 개념 학습부터 이미" +
    " 기초 학습이 완료된 수험생을 위한 심화/핵심 이론 커리큘럼을 제공한다.";
            this.labelInfo.Click += new System.EventHandler(this.label2_Click_2);
            // 
            // textMemo
            // 
            this.textMemo.Font = new System.Drawing.Font("맑은 고딕", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.textMemo.Location = new System.Drawing.Point(437, 198);
            this.textMemo.Multiline = true;
            this.textMemo.Name = "textMemo";
            this.textMemo.Size = new System.Drawing.Size(350, 314);
            this.textMemo.TabIndex = 40;
            this.textMemo.Text = "Memo";
            this.textMemo.TextChanged += new System.EventHandler(this.textIntro_TextChanged);
            // 
            // btnConfirm
            // 
            this.btnConfirm.BackColor = System.Drawing.Color.Transparent;
            this.btnConfirm.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.btnConfirm.Location = new System.Drawing.Point(706, 518);
            this.btnConfirm.Name = "btnConfirm";
            this.btnConfirm.Size = new System.Drawing.Size(82, 23);
            this.btnConfirm.TabIndex = 41;
            this.btnConfirm.Text = "메모 수정";
            this.btnConfirm.UseVisualStyleBackColor = false;
            // 
            // ElementPublisher
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 550);
            this.Controls.Add(this.btnConfirm);
            this.Controls.Add(this.textMemo);
            this.Controls.Add(this.labelInfo);
            this.Controls.Add(this.rating);
            this.Controls.Add(this.linkSite);
            this.Controls.Add(this.labelTag);
            this.Controls.Add(btnPrev);
            this.Controls.Add(this.labelId);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.labelPublisher);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.labelElementName);
            this.Controls.Add(this.pictureBox1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "ElementPublisher";
            this.Text = "Element";
            this.Load += new System.EventHandler(this.Element_Load);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private PictureBox pictureBox1;
        private Label labelElementName;
        private Panel panel2;
        private Label label1;
        private Label labelPublisher;
        private Label label3;
        private Label labelId;
        private Button btnPrev;
        private Label labelTag;
        private LinkLabel linkSite;
        private MySql.Data.MySqlClient.MySqlDataAdapter mySqlDataAdapter1;
        private Syncfusion.Windows.Forms.Tools.RatingControl rating;
        private Label labelInfo;
        private TextBox textMemo;
        private Button btnConfirm;
    }
}