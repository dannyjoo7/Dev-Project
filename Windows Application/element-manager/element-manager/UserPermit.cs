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
    public partial class UserPermit : Form
    {
        public UserPermit()
        {
            InitializeComponent();
            populateItems();
        }

        public void populateItems()
        {
            try
            {
                List<UserList> uList = new List<UserList>();

                using (MySqlConnection Conn = new MySqlConnection("Server = 127.0.0.1; Port=3306; Database=pbl; Uid=root; Pwd=1234;"))
                {
                    Conn.Open();
                    string sql = "select user_id from op_signup;";
                    MySqlCommand cmd = new MySqlCommand(sql, Conn);
                    using (MySqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            UserList userlist = new UserList();
                            userlist.Username = reader.GetString(0);
                            Console.WriteLine(userlist.Username);
                            uList.Add(userlist);
                            flowLayoutPanel1.Controls.Add(userlist);
                        }
                        reader.Close();
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}