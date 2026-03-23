namespace element_manager
{
    partial class AddTag
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
            this.textTagName = new System.Windows.Forms.TextBox();
            this.textTagInfo = new System.Windows.Forms.TextBox();
            this.textTagElement = new System.Windows.Forms.TextBox();
            this.btnRequest = new System.Windows.Forms.Button();
            this.label6 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // textTagName
            // 
            this.textTagName.Font = new System.Drawing.Font("맑은 고딕", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.textTagName.Location = new System.Drawing.Point(87, 12);
            this.textTagName.Name = "textTagName";
            this.textTagName.Size = new System.Drawing.Size(701, 33);
            this.textTagName.TabIndex = 7;
            // 
            // textTagInfo
            // 
            this.textTagInfo.Font = new System.Drawing.Font("맑은 고딕", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.textTagInfo.Location = new System.Drawing.Point(87, 53);
            this.textTagInfo.Multiline = true;
            this.textTagInfo.Name = "textTagInfo";
            this.textTagInfo.Size = new System.Drawing.Size(701, 250);
            this.textTagInfo.TabIndex = 8;
            this.textTagInfo.TextChanged += new System.EventHandler(this.textBox2_TextChanged);
            // 
            // textTagElement
            // 
            this.textTagElement.Font = new System.Drawing.Font("맑은 고딕", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.textTagElement.Location = new System.Drawing.Point(87, 309);
            this.textTagElement.Multiline = true;
            this.textTagElement.Name = "textTagElement";
            this.textTagElement.Size = new System.Drawing.Size(701, 200);
            this.textTagElement.TabIndex = 9;
            this.textTagElement.TextChanged += new System.EventHandler(this.textBox3_TextChanged);
            // 
            // btnRequest
            // 
            this.btnRequest.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.btnRequest.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnRequest.Location = new System.Drawing.Point(713, 515);
            this.btnRequest.Name = "btnRequest";
            this.btnRequest.Size = new System.Drawing.Size(75, 23);
            this.btnRequest.TabIndex = 10;
            this.btnRequest.Text = "요청";
            this.btnRequest.UseVisualStyleBackColor = false;
            // 
            // label6
            // 
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label6.ForeColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.label6.Location = new System.Drawing.Point(5, 13);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(76, 27);
            this.label6.TabIndex = 41;
            this.label6.Text = "타입 명";
            this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label1.ForeColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.label1.Location = new System.Drawing.Point(5, 53);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(76, 27);
            this.label1.TabIndex = 42;
            this.label1.Text = "타입 설명";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label2
            // 
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label2.ForeColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.label2.Location = new System.Drawing.Point(5, 309);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(76, 27);
            this.label2.TabIndex = 43;
            this.label2.Text = "타입 요소";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // AddTag
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 550);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.btnRequest);
            this.Controls.Add(this.textTagElement);
            this.Controls.Add(this.textTagInfo);
            this.Controls.Add(this.textTagName);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "AddTag";
            this.Text = "AddTag";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private TextBox textTagName;
        private TextBox textTagInfo;
        private TextBox textTagElement;
        private Button btnRequest;
        private Label label6;
        private Label label1;
        private Label label2;
    }
}