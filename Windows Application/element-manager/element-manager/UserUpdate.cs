using MySql.Data.MySqlClient;

namespace element_manager
{
    public partial class UserUpdate : Form
    {
        public UserUpdate()
        {
            InitializeComponent();
        }
        
        // Database에서 회원정보 불러오기
        public void showUser(string id)
        {
            Console.WriteLine("[SYS] showUser 호출, ID:" + id);
            try
            {
                string myConnection = "Server = 127.0.0.1; Port=3306; Database=pbl; Uid=root; Pwd=1234;";
                MySqlConnection myConn = new MySqlConnection(myConnection);

                myConn.Open();
                string sql = "select * from user where user_id='" + id + "';";
                MySqlCommand cmd = new MySqlCommand(sql, myConn);
                MySqlDataReader table = cmd.ExecuteReader();

                Console.WriteLine("[SYS] 회원정보 조회: MySQL 연결 성공");
                while (table.Read())
                {
                    textUpdateId.Text = id;
                    string? user_name = table["user_name"].ToString();
                    textUpdateName.Text = user_name;
                    string? user_sex = table["user_sex"].ToString();
                    if (user_sex == "man")
                        rbMale.Checked = true;
                    else
                        rbFemale.Checked = true;
                    string? user_email = table["user_email"].ToString();
                    textUpdateEmail.Text = user_email;
                    string? user_phone = table["user_phone"].ToString();
                    textUpdatePhone.Text = user_phone;
                    string? user_addr = table["user_addr"].ToString();
                    textUpdateAddr.Text = user_addr;
                    string? introduce = table["introduce"].ToString();
                    textIntro.Text = introduce;
                    int op_check = (int)table["op_check"]; // 1: 사용자, 2: 운용자
                    textOp.Text = (op_check == 1) ? "사용자" : "운용자";
                    string? user_state = table["user_state"].ToString();
                    textState.Text = user_state;
                }
            }
            catch (Exception) {}
            
        }

        private void UserUpdate_Load(object sender, EventArgs e)
        {

        }

        private void iconPictureBox2_Click(object sender, EventArgs e)
        {

        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {

        }

        private void loginBtn_Click(object sender, EventArgs e)
        {

        }

        private void SU_ID_text_TextChanged(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void textBox3_TextChanged(object sender, EventArgs e)
        {

        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void btn_Click(object sender, EventArgs e)
        {
            
        }

        private void signUpLink_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            UserResign UseResign = new UserResign();

            UseResign.ShowDialog();
        }

        private void btnFindAdr_Click(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged_1(object sender, EventArgs e)
        {

        }

        private void btnUpdateConfirm_Click(object sender, EventArgs e)
        {
            try
            {
                string user_s;
                if (rbMale.Checked == true)
                    user_s = "man";
                else
                    user_s = "woman";


                string myConnection = "Server = 127.0.0.1; Port=3306; Database=pbl; Uid=root; Pwd=1234;";
                MySqlConnection myConn = new MySqlConnection(myConnection);

                myConn.Open();
                string sql = "UPDATE user SET " +
                    "user_name = '" + textUpdateName.Text +
                    "', user_email = '" + textUpdateEmail.Text +
                    "', introduce = '" + textIntro.Text +
                    "', user_sex = '" + user_s +
                    "', user_addr = '" + textUpdateAddr.Text +
                    "', user_phone = '" + textUpdatePhone.Text +
                    "' WHERE user_id = '" + textUpdateId.Text + "';";

                MySqlCommand cmd = new MySqlCommand(sql, myConn);
                cmd.ExecuteReader();

                MessageBox.Show("변경되었습니다.");
                showUser(textUpdateId.Text);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
