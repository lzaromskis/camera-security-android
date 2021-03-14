using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Windows.Input;
using Xamarin.Essentials;
using Xamarin.Forms;
using CameraSecurityAndroid.ImageUtility;
using CameraSecurityAndroid.Communication;
using System.IO;

namespace CameraSecurityAndroid.ViewModels
{
    public class AboutViewModel : BaseViewModel
    {
        public ImageSource SomeImageSource
        {
            get
            {
                return _someImageSource;
            }
            set
            {
                _someImageSource = value;
                OnPropertyChanged(nameof(SomeImageSource));
            }
        }


        private ImageSource _someImageSource;

        public AboutViewModel()
        {
            Title = "About";
            OpenWebCommand = new Command(async () => await Browser.OpenAsync("https://aka.ms/xamarin-quickstart"));

            int bufferSize = 2 * 1024 * 1024;
            byte[] buffer = new byte[bufferSize];
            byte[] msg = Encoding.ASCII.GetBytes("code=35;secret=secret;");
            
            TcpClient client = new TcpClient("10.0.2.2", 7500);
            NetworkStream stream = client.GetStream();
            stream.Write(msg, 0, msg.Length);

            /*
            int bytesRead = stream.Read(buffer, 0, buffer.Length);
            stream.Flush();
            stream.Close(1000);
            client.Close();*/

            StreamReader reader = new StreamReader(stream);
            string dataString = reader.ReadToEnd();
            reader.Close();
            stream.Close();
            client.Close();

            /*
            var host = Dns.GetHostEntry("10.0.2.2");
            var ip = host.AddressList[0];
            IPEndPoint remoteEP = new IPEndPoint(ip, 7500);
            Socket socket = new Socket(ip.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
            socket.Connect(remoteEP);
            
            int bytesSent = socket.Send(msg, msg.Length, SocketFlags.None);
            int bytesRead = socket.Receive(buffer, bufferSize, SocketFlags.None);
            socket.Shutdown(SocketShutdown.Both);
            socket.Close();*/

            /*
            byte[] packetDataBytes = new byte[bytesRead];
            Array.Copy(buffer, packetDataBytes, bytesRead);
            string packetDataString = Encoding.ASCII.GetString(packetDataBytes);
            */
            var packetSer = new PacketDataSerializer();
            var packet = packetSer.Deserialize(dataString);
            var imageData = packet.GetAttribute("image");
            ImageSource source = null;
            try
            {
                var des = new BmpBase64ImageDeserializer();
                source = des.DeserializeGetSource(imageData);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            
            SomeImageSource = source;

        }

        public ICommand OpenWebCommand { get; }
    }
}