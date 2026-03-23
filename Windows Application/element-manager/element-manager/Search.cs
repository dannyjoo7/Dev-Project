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
    public partial class Search : Form
    {
        public Main main;

        public Search()
        {
            InitializeComponent();
            comboSearch.SelectedIndex = 0;
        }

        public Search(Main main)
        {
            InitializeComponent();
            this.main = main;
            populateItems();
            comboSearch.SelectedIndex = 0;
        }

        private void Search_Load(object sender, EventArgs e)
        {

        }

        private void iconPictureBox2_Click(object sender, EventArgs e)
        {

        }

        private void textUpdateName_TextChanged(object sender, EventArgs e)
        {

        }

        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {

        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        public void populateItems()
        {
            SearchElement[] elementList;

            try
            {
                List<SearchElement> mylist = new List<SearchElement>();
                //DB연결 정보
                using (MySqlConnection Conn = new MySqlConnection("Server = 127.0.0.1; Port=3306; Database=pbl; Uid=root; Pwd=1234;"))
                {
                    Conn.Open();
                    string sql = "select ele_name, ele_exp, ele_id from element;";
                    MySqlCommand cmd = new MySqlCommand(sql, Conn);
                    using (MySqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            SearchElement element = new SearchElement();
                            element.Elename = reader.GetString(0);
                            element.Description = reader.GetString(1);
                            element.eid = reader.GetInt32(2);
                            Console.WriteLine(element.eid);
                            mylist.Add(element);
                        }
                        reader.Close();
                    }
                }
                using (MySqlConnection Conn = new MySqlConnection("Server = 127.0.0.1; Port=3306; Database=pbl; Uid=root; Pwd=1234;"))
                {
                    Conn.Open();
                    foreach (SearchElement ele in mylist)
                    {
                        string tag = "";
                        string sql = "select tag_name from tag inner join eletag on tag.tag_id = eletag.tag_id where ele_id = " + ele.eid + ";";
                        MySqlCommand cmd = new MySqlCommand(sql, Conn);
                        using (MySqlDataReader reader = cmd.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                tag += reader.GetString(0) + ", ";
                            }
                            // ele.Eletag = tag;
                            ele.Eletag = tag.Substring(0, tag.Length-2);
                            ele.main = main;
                            searchPanel.Controls.Add(ele);
                            reader.Close();
                        }
                    }
                    Conn.Close();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
