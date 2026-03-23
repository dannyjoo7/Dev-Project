namespace element_manager
{
    partial class Main
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
            this.panel1 = new System.Windows.Forms.Panel();
            this.iconPictureBox2 = new FontAwesome.Sharp.IconPictureBox();
            this.minButton = new FontAwesome.Sharp.IconButton();
            this.closeButton = new FontAwesome.Sharp.IconButton();
            this.SignUplabel = new System.Windows.Forms.Label();
            this.maxButton = new FontAwesome.Sharp.IconButton();
            this.panel2 = new System.Windows.Forms.Panel();
            this.btnAddTag = new FontAwesome.Sharp.IconButton();
            this.btnPermit = new FontAwesome.Sharp.IconButton();
            this.panel3 = new System.Windows.Forms.Panel();
            this.iconPictureBox1 = new FontAwesome.Sharp.IconPictureBox();
            this.signUpLink = new System.Windows.Forms.LinkLabel();
            this.label2 = new System.Windows.Forms.Label();
            this.Setting_Btn = new FontAwesome.Sharp.IconButton();
            this.UesrMod_Btn = new FontAwesome.Sharp.IconButton();
            this.SearchCategory_Btn = new FontAwesome.Sharp.IconButton();
            this.Search_Btn = new FontAwesome.Sharp.IconButton();
            this.panelInf = new System.Windows.Forms.Panel();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.iconPictureBox2)).BeginInit();
            this.panel2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.iconPictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(55)))), ((int)(((byte)(60)))), ((int)(((byte)(70)))));
            this.panel1.Controls.Add(this.iconPictureBox2);
            this.panel1.Controls.Add(this.minButton);
            this.panel1.Controls.Add(this.closeButton);
            this.panel1.Controls.Add(this.SignUplabel);
            this.panel1.Controls.Add(this.maxButton);
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(1000, 50);
            this.panel1.TabIndex = 3;
            this.panel1.Paint += new System.Windows.Forms.PaintEventHandler(this.panel1_Paint);
            this.panel1.MouseDown += new System.Windows.Forms.MouseEventHandler(this.panel1_MouseDown);
            // 
            // iconPictureBox2
            // 
            this.iconPictureBox2.BackColor = System.Drawing.Color.Transparent;
            this.iconPictureBox2.ForeColor = System.Drawing.SystemColors.Window;
            this.iconPictureBox2.IconChar = FontAwesome.Sharp.IconChar.Box;
            this.iconPictureBox2.IconColor = System.Drawing.SystemColors.Window;
            this.iconPictureBox2.IconFont = FontAwesome.Sharp.IconFont.Solid;
            this.iconPictureBox2.IconSize = 31;
            this.iconPictureBox2.Location = new System.Drawing.Point(10, 10);
            this.iconPictureBox2.Name = "iconPictureBox2";
            this.iconPictureBox2.Size = new System.Drawing.Size(32, 31);
            this.iconPictureBox2.TabIndex = 9;
            this.iconPictureBox2.TabStop = false;
            this.iconPictureBox2.MouseDown += new System.Windows.Forms.MouseEventHandler(this.iconPictureBox2_MouseDown);
            // 
            // minButton
            // 
            this.minButton.FlatAppearance.BorderSize = 0;
            this.minButton.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.minButton.IconChar = FontAwesome.Sharp.IconChar.WindowMinimize;
            this.minButton.IconColor = System.Drawing.Color.White;
            this.minButton.IconFont = FontAwesome.Sharp.IconFont.Solid;
            this.minButton.IconSize = 20;
            this.minButton.Location = new System.Drawing.Point(907, 12);
            this.minButton.Name = "minButton";
            this.minButton.Size = new System.Drawing.Size(25, 25);
            this.minButton.TabIndex = 4;
            this.minButton.UseVisualStyleBackColor = true;
            this.minButton.Click += new System.EventHandler(this.minButton_Click);
            // 
            // closeButton
            // 
            this.closeButton.FlatAppearance.BorderSize = 0;
            this.closeButton.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.closeButton.IconChar = FontAwesome.Sharp.IconChar.X;
            this.closeButton.IconColor = System.Drawing.Color.White;
            this.closeButton.IconFont = FontAwesome.Sharp.IconFont.Solid;
            this.closeButton.IconSize = 20;
            this.closeButton.Location = new System.Drawing.Point(963, 12);
            this.closeButton.Name = "closeButton";
            this.closeButton.Size = new System.Drawing.Size(25, 25);
            this.closeButton.TabIndex = 2;
            this.closeButton.UseVisualStyleBackColor = true;
            this.closeButton.Click += new System.EventHandler(this.closeButton_Click);
            // 
            // SignUplabel
            // 
            this.SignUplabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.SignUplabel.ForeColor = System.Drawing.SystemColors.Control;
            this.SignUplabel.Location = new System.Drawing.Point(25, 0);
            this.SignUplabel.Name = "SignUplabel";
            this.SignUplabel.Size = new System.Drawing.Size(197, 50);
            this.SignUplabel.TabIndex = 0;
            this.SignUplabel.Text = "Element Manager";
            this.SignUplabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            this.SignUplabel.MouseDown += new System.Windows.Forms.MouseEventHandler(this.SignUplabel_MouseDown);
            // 
            // maxButton
            // 
            this.maxButton.FlatAppearance.BorderSize = 0;
            this.maxButton.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.maxButton.IconChar = FontAwesome.Sharp.IconChar.Square;
            this.maxButton.IconColor = System.Drawing.Color.White;
            this.maxButton.IconFont = FontAwesome.Sharp.IconFont.Regular;
            this.maxButton.IconSize = 20;
            this.maxButton.Location = new System.Drawing.Point(935, 12);
            this.maxButton.Name = "maxButton";
            this.maxButton.Size = new System.Drawing.Size(25, 25);
            this.maxButton.TabIndex = 3;
            this.maxButton.UseVisualStyleBackColor = true;
            this.maxButton.Click += new System.EventHandler(this.maxButton_Click);
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.Color.Silver;
            this.panel2.Controls.Add(this.btnAddTag);
            this.panel2.Controls.Add(this.btnPermit);
            this.panel2.Controls.Add(this.panel3);
            this.panel2.Controls.Add(this.iconPictureBox1);
            this.panel2.Controls.Add(this.signUpLink);
            this.panel2.Controls.Add(this.label2);
            this.panel2.Controls.Add(this.Setting_Btn);
            this.panel2.Controls.Add(this.UesrMod_Btn);
            this.panel2.Controls.Add(this.SearchCategory_Btn);
            this.panel2.Controls.Add(this.Search_Btn);
            this.panel2.Location = new System.Drawing.Point(0, 50);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(200, 550);
            this.panel2.TabIndex = 4;
            this.panel2.Paint += new System.Windows.Forms.PaintEventHandler(this.panel2_Paint);
            // 
            // btnAddTag
            // 
            this.btnAddTag.BackColor = System.Drawing.Color.DarkGray;
            this.btnAddTag.FlatAppearance.BorderColor = System.Drawing.Color.DarkGray;
            this.btnAddTag.FlatAppearance.MouseDownBackColor = System.Drawing.Color.White;
            this.btnAddTag.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Silver;
            this.btnAddTag.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnAddTag.Font = new System.Drawing.Font("맑은 고딕", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.btnAddTag.IconChar = FontAwesome.Sharp.IconChar.Tags;
            this.btnAddTag.IconColor = System.Drawing.Color.Black;
            this.btnAddTag.IconFont = FontAwesome.Sharp.IconFont.Auto;
            this.btnAddTag.IconSize = 35;
            this.btnAddTag.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnAddTag.Location = new System.Drawing.Point(10, 295);
            this.btnAddTag.Name = "btnAddTag";
            this.btnAddTag.Padding = new System.Windows.Forms.Padding(5, 5, 0, 0);
            this.btnAddTag.Size = new System.Drawing.Size(180, 50);
            this.btnAddTag.TabIndex = 31;
            this.btnAddTag.Text = "   타입 추가 요청";
            this.btnAddTag.UseVisualStyleBackColor = false;
            this.btnAddTag.Click += new System.EventHandler(this.btnAddTag_Click);
            // 
            // btnPermit
            // 
            this.btnPermit.BackColor = System.Drawing.Color.DarkGray;
            this.btnPermit.FlatAppearance.BorderColor = System.Drawing.Color.DarkGray;
            this.btnPermit.FlatAppearance.MouseDownBackColor = System.Drawing.Color.White;
            this.btnPermit.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Silver;
            this.btnPermit.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnPermit.Font = new System.Drawing.Font("맑은 고딕", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.btnPermit.IconChar = FontAwesome.Sharp.IconChar.Check;
            this.btnPermit.IconColor = System.Drawing.Color.Black;
            this.btnPermit.IconFont = FontAwesome.Sharp.IconFont.Auto;
            this.btnPermit.IconSize = 35;
            this.btnPermit.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnPermit.Location = new System.Drawing.Point(10, 239);
            this.btnPermit.Name = "btnPermit";
            this.btnPermit.Padding = new System.Windows.Forms.Padding(5, 5, 0, 0);
            this.btnPermit.Size = new System.Drawing.Size(180, 50);
            this.btnPermit.TabIndex = 30;
            this.btnPermit.Text = "   운용자 가입 승인";
            this.btnPermit.UseVisualStyleBackColor = false;
            this.btnPermit.Click += new System.EventHandler(this.btnPermit_Click);
            // 
            // panel3
            // 
            this.panel3.BackColor = System.Drawing.Color.DarkGray;
            this.panel3.Location = new System.Drawing.Point(4, 231);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(192, 2);
            this.panel3.TabIndex = 29;
            // 
            // iconPictureBox1
            // 
            this.iconPictureBox1.BackColor = System.Drawing.Color.Transparent;
            this.iconPictureBox1.ForeColor = System.Drawing.SystemColors.ControlText;
            this.iconPictureBox1.IconChar = FontAwesome.Sharp.IconChar.MailBulk;
            this.iconPictureBox1.IconColor = System.Drawing.SystemColors.ControlText;
            this.iconPictureBox1.IconFont = FontAwesome.Sharp.IconFont.Auto;
            this.iconPictureBox1.IconSize = 25;
            this.iconPictureBox1.Location = new System.Drawing.Point(171, 524);
            this.iconPictureBox1.Name = "iconPictureBox1";
            this.iconPictureBox1.Size = new System.Drawing.Size(25, 25);
            this.iconPictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.CenterImage;
            this.iconPictureBox1.TabIndex = 16;
            this.iconPictureBox1.TabStop = false;
            this.iconPictureBox1.Click += new System.EventHandler(this.iconPictureBox1_Click);
            // 
            // signUpLink
            // 
            this.signUpLink.ActiveLinkColor = System.Drawing.Color.White;
            this.signUpLink.AutoSize = true;
            this.signUpLink.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.signUpLink.LinkColor = System.Drawing.Color.DimGray;
            this.signUpLink.Location = new System.Drawing.Point(104, 530);
            this.signUpLink.Name = "signUpLink";
            this.signUpLink.Size = new System.Drawing.Size(69, 16);
            this.signUpLink.TabIndex = 15;
            this.signUpLink.TabStop = true;
            this.signUpLink.Text = "Contact us";
            this.signUpLink.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.signUpLink_LinkClicked);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("맑은 고딕", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.label2.ForeColor = System.Drawing.SystemColors.ControlDarkDark;
            this.label2.Location = new System.Drawing.Point(0, 530);
            this.label2.Margin = new System.Windows.Forms.Padding(0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(48, 17);
            this.label2.TabIndex = 14;
            this.label2.Text = "ver 1.0";
            // 
            // Setting_Btn
            // 
            this.Setting_Btn.BackColor = System.Drawing.Color.DarkGray;
            this.Setting_Btn.FlatAppearance.BorderColor = System.Drawing.Color.DarkGray;
            this.Setting_Btn.FlatAppearance.MouseDownBackColor = System.Drawing.Color.White;
            this.Setting_Btn.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Silver;
            this.Setting_Btn.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.Setting_Btn.Font = new System.Drawing.Font("맑은 고딕", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.Setting_Btn.IconChar = FontAwesome.Sharp.IconChar.Gear;
            this.Setting_Btn.IconColor = System.Drawing.Color.Black;
            this.Setting_Btn.IconFont = FontAwesome.Sharp.IconFont.Auto;
            this.Setting_Btn.IconSize = 35;
            this.Setting_Btn.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.Setting_Btn.Location = new System.Drawing.Point(9, 176);
            this.Setting_Btn.Name = "Setting_Btn";
            this.Setting_Btn.Padding = new System.Windows.Forms.Padding(5, 5, 0, 0);
            this.Setting_Btn.Size = new System.Drawing.Size(180, 50);
            this.Setting_Btn.TabIndex = 8;
            this.Setting_Btn.Text = "   프로그램 설정";
            this.Setting_Btn.UseVisualStyleBackColor = false;
            this.Setting_Btn.Click += new System.EventHandler(this.Setting_Btn_Click);
            // 
            // UesrMod_Btn
            // 
            this.UesrMod_Btn.BackColor = System.Drawing.Color.DarkGray;
            this.UesrMod_Btn.FlatAppearance.BorderColor = System.Drawing.Color.DarkGray;
            this.UesrMod_Btn.FlatAppearance.MouseDownBackColor = System.Drawing.Color.White;
            this.UesrMod_Btn.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Silver;
            this.UesrMod_Btn.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.UesrMod_Btn.Font = new System.Drawing.Font("맑은 고딕", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.UesrMod_Btn.IconChar = FontAwesome.Sharp.IconChar.User;
            this.UesrMod_Btn.IconColor = System.Drawing.Color.Black;
            this.UesrMod_Btn.IconFont = FontAwesome.Sharp.IconFont.Auto;
            this.UesrMod_Btn.IconSize = 35;
            this.UesrMod_Btn.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.UesrMod_Btn.Location = new System.Drawing.Point(9, 120);
            this.UesrMod_Btn.Name = "UesrMod_Btn";
            this.UesrMod_Btn.Padding = new System.Windows.Forms.Padding(5, 5, 0, 0);
            this.UesrMod_Btn.Size = new System.Drawing.Size(180, 50);
            this.UesrMod_Btn.TabIndex = 7;
            this.UesrMod_Btn.Text = "   회원 정보 수정";
            this.UesrMod_Btn.UseVisualStyleBackColor = false;
            this.UesrMod_Btn.Click += new System.EventHandler(this.UesrMod_Btn_Click);
            // 
            // SearchCategory_Btn
            // 
            this.SearchCategory_Btn.BackColor = System.Drawing.Color.DarkGray;
            this.SearchCategory_Btn.FlatAppearance.BorderColor = System.Drawing.Color.DarkGray;
            this.SearchCategory_Btn.FlatAppearance.MouseDownBackColor = System.Drawing.Color.White;
            this.SearchCategory_Btn.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Silver;
            this.SearchCategory_Btn.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.SearchCategory_Btn.Font = new System.Drawing.Font("맑은 고딕", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.SearchCategory_Btn.IconChar = FontAwesome.Sharp.IconChar.MagnifyingGlassArrowRight;
            this.SearchCategory_Btn.IconColor = System.Drawing.Color.Black;
            this.SearchCategory_Btn.IconFont = FontAwesome.Sharp.IconFont.Auto;
            this.SearchCategory_Btn.IconSize = 35;
            this.SearchCategory_Btn.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.SearchCategory_Btn.Location = new System.Drawing.Point(9, 64);
            this.SearchCategory_Btn.Name = "SearchCategory_Btn";
            this.SearchCategory_Btn.Padding = new System.Windows.Forms.Padding(5, 0, 0, 0);
            this.SearchCategory_Btn.Size = new System.Drawing.Size(180, 50);
            this.SearchCategory_Btn.TabIndex = 6;
            this.SearchCategory_Btn.Text = "   카테고리 검색";
            this.SearchCategory_Btn.UseVisualStyleBackColor = false;
            this.SearchCategory_Btn.Click += new System.EventHandler(this.SearchCategory_Btn_Click);
            // 
            // Search_Btn
            // 
            this.Search_Btn.BackColor = System.Drawing.Color.DarkGray;
            this.Search_Btn.FlatAppearance.BorderColor = System.Drawing.Color.DarkGray;
            this.Search_Btn.FlatAppearance.MouseDownBackColor = System.Drawing.Color.White;
            this.Search_Btn.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Silver;
            this.Search_Btn.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.Search_Btn.Font = new System.Drawing.Font("맑은 고딕", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.Search_Btn.IconChar = FontAwesome.Sharp.IconChar.MagnifyingGlass;
            this.Search_Btn.IconColor = System.Drawing.Color.Black;
            this.Search_Btn.IconFont = FontAwesome.Sharp.IconFont.Auto;
            this.Search_Btn.IconSize = 35;
            this.Search_Btn.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.Search_Btn.Location = new System.Drawing.Point(9, 8);
            this.Search_Btn.Name = "Search_Btn";
            this.Search_Btn.Padding = new System.Windows.Forms.Padding(5, 0, 0, 0);
            this.Search_Btn.Size = new System.Drawing.Size(180, 50);
            this.Search_Btn.TabIndex = 5;
            this.Search_Btn.Text = "  통합 검색";
            this.Search_Btn.UseVisualStyleBackColor = false;
            this.Search_Btn.Click += new System.EventHandler(this.Search_Ib_Click);
            // 
            // panelInf
            // 
            this.panelInf.Location = new System.Drawing.Point(198, 50);
            this.panelInf.Name = "panelInf";
            this.panelInf.Size = new System.Drawing.Size(800, 550);
            this.panelInf.TabIndex = 5;
            this.panelInf.Paint += new System.Windows.Forms.PaintEventHandler(this.panelInf_Paint);
            // 
            // Main
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1000, 600);
            this.Controls.Add(this.panelInf);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.panel2);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "Main";
            this.Text = "Form3";
            this.Load += new System.EventHandler(this.Form3_Load);
            this.panel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.iconPictureBox2)).EndInit();
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.iconPictureBox1)).EndInit();
            this.ResumeLayout(false);

        }


        #endregion

        private Panel panel1;
        private FontAwesome.Sharp.IconButton minButton;
        private Label SignUplabel;
        private FontAwesome.Sharp.IconButton closeButton;
        private FontAwesome.Sharp.IconButton maxButton;
        private Panel panel2;
        private FontAwesome.Sharp.IconButton Search_Btn;
        private FontAwesome.Sharp.IconButton SearchCategory_Btn;
        private FontAwesome.Sharp.IconButton UesrMod_Btn;
        private FontAwesome.Sharp.IconButton Setting_Btn;
        private Label label2;
        private LinkLabel signUpLink;
        private FontAwesome.Sharp.IconPictureBox iconPictureBox1;
        private FontAwesome.Sharp.IconPictureBox iconPictureBox2;
        private Panel panelInf;
        private Panel panel3;
        private FontAwesome.Sharp.IconButton btnPermit;
        private FontAwesome.Sharp.IconButton btnAddTag;
    }
}