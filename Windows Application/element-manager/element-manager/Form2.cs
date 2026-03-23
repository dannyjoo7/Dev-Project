using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;

namespace element_manager
{
    public partial class Form2 : Form
    {
        public static readonly Form1 Login = new Form1();
        public static readonly Form2 SignUp = new Form2();
        string gender = "man";

        public Form2()
        {
            InitializeComponent();
        }

        

        private void closeButton_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        [DllImport("user32.DLL", EntryPoint = "ReleaseCapture")]
        private extern static void ReleaseCapture();

        [DllImport("user32.DLL", EntryPoint = "SendMessage")]
        private extern static void SendMessage(System.IntPtr hWnd, int wMsg, int wParam, int lParam);

        private void panel1_MouseDown(object sender, MouseEventArgs e)
        {
            ReleaseCapture();
            SendMessage(this.Handle, 0x112, 0xf012, 0);
        }

        private void SignUplabel_MouseDown(object sender, MouseEventArgs e)
        {
            ReleaseCapture();
            SendMessage(this.Handle, 0x112, 0xf012, 0);
        }

        private void SU_man_rb_CheckedChanged(object sender, EventArgs e)
        {
            gender = "man";
        }

        private void SU_woman_rb_CheckedChanged(object sender, EventArgs e)
        {
            gender = "woman";
        }


        private void loginBtn_Click(object sender, EventArgs e)
        {
            if ((SU_ID_text.Text.Length == 0) || 
                (SU_Pw_text.Text.Length == 0) || 
                (SU_Name_text.Text.Length == 0) || 
                (SU_Email_text.Text.Length == 0) || 
                (SU_Phone_text.Text.Length == 0) || 
                (SU_Addr_text.Text.Length == 0))
            {
                MessageBox.Show("빈 항목이 존재합니다");
            }
            else
            {
                if (SU_Pw_text.Text != SU_VerPw_text.Text)
                {
                    MessageBox.Show("비밀번호가 다릅니다");
                }
                else
                {
                    try
                    {
                        string myConnection = "Server = 127.0.0.1; Port=3306; Database=pbl; Uid=root; Pwd=1234;";
                        MySqlConnection myConn = new MySqlConnection(myConnection);
                        myConn.Open();

                        string sql = "INSERT INTO user (user_name, user_id, user_pw, user_sex, user_email, user_phone, user_addr, introduce, op_check, user_state) " +
                            "VALUES('" + SU_Name_text.Text +
                            "', '" + SU_ID_text.Text +
                            "', '" + SU_Pw_text.Text +
                            "', '" + gender +
                            "', '" + SU_Email_text.Text +
                            "', '" + SU_Phone_text.Text +
                            "', '" + SU_Addr_text.Text +
                            "', '', '1', '정상');";

                        MySqlCommand cmd = new MySqlCommand(sql, myConn);
                        cmd.ExecuteNonQuery();



                        if (op_checkbox.Checked)
                        {
                            string sql2 = "INSERT INTO op_signup (user_id, confirm_kno) VALUES('" +
                                SU_ID_text.Text + "', '처리대기');";

                            MySqlCommand cmd2 = new MySqlCommand(sql2, myConn);
                            cmd2.ExecuteNonQuery();

                            MessageBox.Show("사용자 계정 생성이 완료되었습니다.\n운용자 전환 : 관리자 승인을 대기중입니다.");
                        }

                        else
                            MessageBox.Show("회원가입에 성공하였습니다");

                        Login.Show();
                        this.Hide();
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                }
            }
        }
    }
}
