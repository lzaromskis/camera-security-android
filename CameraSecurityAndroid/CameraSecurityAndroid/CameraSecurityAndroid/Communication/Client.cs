using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace CameraSecurityAndroid.Communication
{
    public class Client
    {
        private class StateObject
        {
            public Socket workSocket = null;
            public const int BufferSize = 2 * 1024 * 1024;
            public byte[] buffer = new byte[BufferSize];
            public StringBuilder sb = new StringBuilder();
        }

        private static ManualResetEvent connectDone = new ManualResetEvent(false);
        private static ManualResetEvent sendDone = new ManualResetEvent(false);
        private static ManualResetEvent receiveDone = new ManualResetEvent(false);

        private static string _response;

        private string _host;
        private int _port;

        public Client(string host, int port)
        {
            _host = host;
            _port = port;
        }

        public string SendRequest(string request)
        {
            try
            {
                IPHostEntry ipHostInfo = Dns.GetHostEntry(_host);
                IPAddress ipAddress = ipHostInfo.AddressList[0];
                IPEndPoint remoteEP = new IPEndPoint(ipAddress, _port);

                Socket client = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

                client.BeginConnect(remoteEP, new AsyncCallback(ConnectCallback), client);
                connectDone.WaitOne();

                Send(client, request);
                sendDone.WaitOne();

                Receive(client);
                receiveDone.WaitOne();

                client.Shutdown(SocketShutdown.Both);
                client.Close();

                return _response;
            }
            catch (Exception)
            {
                return null;
            }
        }

        private static void ConnectCallback(IAsyncResult ar)
        {
            Socket client = (Socket)ar.AsyncState;

            client.EndConnect(ar);

            Console.WriteLine($"Socket connected to {client.RemoteEndPoint.ToString()}");

            connectDone.Set();

        }

        private static void Receive(Socket client)
        {
            StateObject state = new StateObject
            {
                workSocket = client
            };

            client.BeginReceive(state.buffer, 0, StateObject.BufferSize, 0, new AsyncCallback(ReceiveCallback), state);
        }

        private static void ReceiveCallback(IAsyncResult ar)
        {
            StateObject state = (StateObject)ar.AsyncState;
            Socket client = state.workSocket;

            int bytesRead = client.EndReceive(ar);

            if (bytesRead > 0)
            {
                state.sb.Append(Encoding.ASCII.GetString(state.buffer, 0, bytesRead));

                client.BeginReceive(state.buffer, 0, StateObject.BufferSize, 0, new AsyncCallback(ReceiveCallback), state);
            }
            else
            {
                if (state.sb.Length > 1)
                    _response = state.sb.ToString();

                receiveDone.Set();
            }
        }

        private static void Send(Socket client, string data)
        {
            byte[] byteData = Encoding.ASCII.GetBytes(data);

            client.BeginSend(byteData, 0, byteData.Length, 0, new AsyncCallback(SendCallback), client);
        }

        private static void SendCallback(IAsyncResult ar)
        {
            Socket client = (Socket)ar.AsyncState;

            int bytesSent = client.EndSend(ar);
            Console.WriteLine($"Sent {bytesSent} bytes to the server.");

            sendDone.Set();
        }
    }
}
