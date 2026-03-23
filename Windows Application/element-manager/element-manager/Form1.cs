using System.Runtime.InteropServices;
using MySql.Data.MySqlClient;

namespace element_manager
{
    public partial class Form1 : Form
    {
        public static readonly Form1 Login = new Form1();
        public static readonly Form2 SignUp = new Form2();
        public static readonly Main Main = new Main();

        public Form1()
        {
            InitializeComponent();
        }

        private void iconButton1_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void minButton_Click(object sender, EventArgs e)
        {

        }

        private void maxButton_Click(object sender, EventArgs e)
        {

        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void signUpLink_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            SignUp.Show();
            this.Hide();
        }

        private void loginBtn_Click(object sender, EventArgs e)
        {
            try
            {
                //DB연결 정보
                string myConnection = "Server = 127.0.0.1; Port=3306; Database=pbl; Uid=root; Pwd=1234;";
                //연결문 선언
                MySqlConnection myConn = new MySqlConnection(myConnection);


                myConn.Open();
                string sql = "select * from user where user_id = '" + textID.Text + "' and user_pw = '" + textPW.Text + "';";

                MySqlCommand cmd = new MySqlCommand(sql, myConn);
                MySqlDataReader myReader = cmd.ExecuteReader();
                int count = 0;

                //SQL문이 돌아서 몇개의 데이터가 검사됬는가?
                while (myReader.Read())
                {
                    count = count + 1;
                }

                if (count == 1) // login 성공
                {
                    myConn.Close();
                    this.Hide();
                    //emp = textID.Text;
                    Console.WriteLine("[SYS]로그인 완료");
                    Main.sessid(textID.Text);
                    Main.Show();
                }
                else if (count > 1)
                {
                    MessageBox.Show("아이디와 패스워드가 중복됩니다.");
                    myConn.Close();
                }
                else
                {
                    MessageBox.Show("아이디와 패스워드가 불일치합니다.");
                    myConn.Close();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void panel1_Paint(object sender, PaintEventArgs e)
        {

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

        private void label1_MouseDown(object sender, MouseEventArgs e)
        {
            ReleaseCapture();
            SendMessage(this.Handle, 0x112, 0xf012, 0);
        }
    }
}