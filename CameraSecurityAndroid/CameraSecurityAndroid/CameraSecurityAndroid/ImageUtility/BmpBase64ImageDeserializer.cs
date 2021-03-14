using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using Xamarin.Forms;

namespace CameraSecurityAndroid.ImageUtility
{
    public class BmpBase64ImageDeserializer : IImageDeserializer
    {
        public Image Deserialize(string data)
        {
            byte[] imageData = Convert.FromBase64String(data);
            return new Image
            {
                Source = ImageSource.FromStream(() => new MemoryStream(imageData)),
            };
        }

        public ImageSource DeserializeGetSource(string data)
        {
            byte[] imageData = Convert.FromBase64String(data);
            return ImageSource.FromStream(() => new MemoryStream(imageData));
        }
    }
}
