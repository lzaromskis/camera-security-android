using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace CameraSecurityAndroid.ImageUtility
{
    public interface IImageDeserializer
    {
        Image Deserialize(string data);

        ImageSource DeserializeGetSource(string data);
    }
}
