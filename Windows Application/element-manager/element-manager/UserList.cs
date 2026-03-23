using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace element_manager
{
    public partial class UserList : UserControl
    {
        public UserList()
        {
            InitializeComponent();
        }

        #region Properties
        private string _username;
        private string _company;
        private Image _pic;

        [Category("Custom props")]
        public string Username
        {
            get { return _username; }
            set { _username = value; lblName.Text = value; }
        }

        [Category("Custom props")]
        public string Company
        {
            get { return _company; }
            set { _company = value; lblCompany.Text = value; }
        }

        [Category("Custom props")]
        public Image Pic
        {
            get { return _pic; }
            set { _pic = value; picture.Image = value; }
        }

        #endregion


        private void pictureBox1_Click(object sender, EventArgs e)
        {

        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                //DB연결 정보
                string myConnection = "Server = 127.0.0.1; Port=3306; Database=pbl; Uid=root; Pwd=1234;";
                //연결문 선언
                MySqlConnection myConn = new MySqlConnection(myConnection);


                myConn.Open();
                string sql = "UPDATE user SET op_check = 2 WHERE user_id = '" + lblName.Text + "';";
                string sql2 = "DELETE FROM op_signup WHERE user_id = '" + lblName.Text + "';";

                MySqlCommand cmd = new MySqlCommand(sql, myConn);
                cmd.ExecuteNonQuery();

                MySqlCommand cmd2 = new MySqlCommand(sql2, myConn);
                cmd2.ExecuteNonQuery();

                MessageBox.Show("운용자 계정으로 변경되었습니다.");



            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    

        private void btnInfo_Click(object sender, EventArgs e)
        {
            try
            {
                //DB연결 정보
                string myConnection = "Server = 127.0.0.1; Port=3306; Database=pbl; Uid=root; Pwd=1234;";
                //연결문 선언
                MySqlConnection myConn = new MySqlConnection(myConnection);


                myConn.Open();
                string sql = "DELETE FROM op_signup WHERE user_id = '" + lblName.Text + "';";
                MySqlCommand cmd = new MySqlCommand(sql, myConn);
                cmd.ExecuteReader();
                MessageBox.Show("운용자 계정 변경신청을 거부하였습니다.");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
