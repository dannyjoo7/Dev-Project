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
    public partial class SearchElement : UserControl
    {
        public Main main;
        public int eid = 1; // element ID

        
        
        public SearchElement()
        {
            InitializeComponent();
        }

        #region Properties
        private string _elename;
        private string _description;
        private string _eletag;
        private Image _picture;

        [Category("Custom props")]
        public string Elename
        {
            get { return _elename; }
            set { _elename = value; lblName.Text = value; }
        }

        [Category("Custom props")]
        public string Description
        {
            get { return _description; }
            set { _description = value; lblDescription.Text = value; }
        }

        [Category("Custom props")]
        public string Eletag
        {
            get { return _eletag; }
            set { _eletag = value; lblTag.Text = value; }
        }

        [Category("Custom props")]
        public Image Picture
        {
            get { return _picture; }
            set { _picture = value; pic.Image = value; }
        }

        #endregion

        private void lblName_Click(object sender, EventArgs e)
        {
            main.viewElement(eid);
        }

        private void lblDescription_Click(object sender, EventArgs e)
        {

        }

        private void pic_Click(object sender, EventArgs e)
        {

        }

        private void lblTag_Click(object sender, EventArgs e)
        {

        }
    }
}
