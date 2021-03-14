using CameraSecurityAndroid.ViewModels;
using System.ComponentModel;
using Xamarin.Forms;

namespace CameraSecurityAndroid.Views
{
    public partial class ItemDetailPage : ContentPage
    {
        public ItemDetailPage()
        {
            InitializeComponent();
            BindingContext = new ItemDetailViewModel();
        }
    }
}