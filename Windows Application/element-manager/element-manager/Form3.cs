using FontAwesome.Sharp;
using MySql.Data.MySqlClient;
using Org.BouncyCastle.Asn1.Cmp;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using System.Windows.Forms;

namespace element_manager
{
    public partial class Main : Form
    {
        public static readonly UserUpdate UserUpdate = new UserUpdate();
        public static readonly Search Search = new Search();
        public static readonly AddTag AddTag = new AddTag();
        public static readonly UserPermit UserPermit = new UserPermit();
        public static readonly ElementPublisher ElementPublisher = new ElementPublisher();
        string sid;

        public void sessid(string id)
        {
            sid = id;
            UserUpdate.showUser(id);
        }

        public Main()
        {
            InitializeComponent();
            ElementPublisher.main = this;
            Search.main = this;
            Search.populateItems();
        }

        private void Form3_Load(object sender, EventArgs e)
        {
            loadForm(UserUpdate);
            loadForm(Search);
            loadForm(AddTag);
            loadForm(UserPermit);
            loadForm(ElementPublisher);
        }

        //창 조절
        private void minButton_Click(object sender, EventArgs e)
        {

        }

        private void maxButton_Click(object sender, EventArgs e)
        {

        }

        private void closeButton_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void SignUp_Btn_Click(object sender, EventArgs e)
        {

        }

        private void panel2_Paint(object sender, PaintEventArgs e)
        {

        }

        private void iconPictureBox1_Click(object sender, EventArgs e)
        {

        }

        private void Search_Ib_Click(object sender, EventArgs e)
        {
            //검색 버튼
            changeColorBtnBackground(Search_Btn);
            changeForm(Search);
        }

        private void UesrMod_Btn_Click(object sender, EventArgs e)
        {
            //유저 정보 수정 버튼
            changeColorBtnBackground(UesrMod_Btn);
            changeForm(UserUpdate);
        }

        private void SearchCategory_Btn_Click(object sender, EventArgs e)
        {
            changeColorBtnBackground(SearchCategory_Btn);
        }

        private void Setting_Btn_Click(object sender, EventArgs e)
        {
            changeColorBtnBackground(Setting_Btn);
        }

        private void btnAddTag_Click(object sender, EventArgs e)
        {
            changeColorBtnBackground(btnAddTag);
            changeForm(AddTag);
        }

        private void btnPermit_Click(object sender, EventArgs e)
        {
            Console.WriteLine(sid);
            int check;

            try
            {
                //DB연결 정보
                string myConnection = "Server = 127.0.0.1; Port=3306; Database=pbl; Uid=root; Pwd=1234;";
                //연결문 선언
                MySqlConnection myConn = new MySqlConnection(myConnection);


                myConn.Open();
                string sql = "SELECT op_check FROM user WHERE user_id = '" + sid + "';";
                MySqlCommand cmd = new MySqlCommand(sql, myConn);

                MySqlDataReader reader = cmd.ExecuteReader();
                reader.Read();

                check = reader.GetInt32(0);

                if (check == 3)
                {
                    changeColorBtnBackground(btnPermit);
                    changeForm(UserPermit);
                    reader.Close();
                }

                else
                {
                    MessageBox.Show("권한이 없습니다.");
                    reader.Close();
                }


                myConn.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        }

        private void panelInf_Paint(object sender, PaintEventArgs e)
        {

        }

        private void panel1_Paint(object sender, PaintEventArgs e)
        {

        }

        [DllImport("user32.DLL", EntryPoint = "ReleaseCapture")]
        private extern static void ReleaseCapture();

        [DllImport("user32.DLL", EntryPoint = "SendMessage")]
        private extern static void SendMessage(System.IntPtr hWnd, int wMsg, int wParam, int lParam);

        //창 위치 변경
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



        private void iconPictureBox2_MouseDown(object sender, MouseEventArgs e)
        {
            ReleaseCapture();
            SendMessage(this.Handle, 0x112, 0xf012, 0);
        }

        

        private void signUpLink_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            contactUs contactUs = new contactUs();
            contactUs.ShowDialog();
        }

        private void changeColorBtnBackground(IconButton obj)
        {
            List<IconButton> listBtn = new List<IconButton>();

            listBtn.Add(Search_Btn);
            listBtn.Add(SearchCategory_Btn);
            listBtn.Add(UesrMod_Btn);
            listBtn.Add(Setting_Btn);
            listBtn.Add(btnPermit);
            listBtn.Add(btnAddTag);

            for (int i = 0; i < listBtn.Count; i++)
            {
                listBtn[i].BackColor = Color.DarkGray;
            }
            obj.BackColor = Color.White;
        }

        public void changeForm(Form form)
        {
            List<Form> listForm = new List<Form>();
            listForm.Add(AddTag);
            listForm.Add(UserUpdate);
            listForm.Add(Search);
            listForm.Add(UserPermit);
            listForm.Add(ElementPublisher);

            for (int i = 0; i < listForm.Count; i++)
            {
                listForm[i].Hide();
                panelInf.Controls.Remove(listForm[i]);
            }

            panelInf.Controls.Add(form);
            form.Show();
        }

        private void loadForm(Form form)
        {
            form.TopLevel = false;
            form.TopMost = true;
            form.FormBorderStyle = FormBorderStyle.None;
        }
        
        // Element 클릭했을 때 Element 세부 페이지로 이동
        public void viewElement(int eid)
        {
            // ElementPublisher.getElement(eid); // Element ID로 데이터 로드
            changeForm(ElementPublisher); // 화면 이동
        }

        public void changeToSearch()
        {
            changeForm(Search);
        }
    }
}
