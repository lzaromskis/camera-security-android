using System;
using System.Collections.Generic;
using System.Text;

namespace CameraSecurityAndroid.Communication
{
    public class PacketDataSerializer
    {
        private const char KEY_VALUE_SEPARATOR = '=';
        private const char PAIR_SEPARATOR = ';';

        public string Serialize(PacketData data)
        {
            StringBuilder builder = new StringBuilder();

            foreach (var pair in data.GetAllAttributes())
            {
                builder.Append(pair.Key);
                builder.Append(KEY_VALUE_SEPARATOR);
                builder.Append(pair.Value);
                builder.Append(PAIR_SEPARATOR);
            }

            return builder.ToString();
        }

        public PacketData Deserialize(string data)
        {
            PacketData packet = new PacketData();

            foreach (var d in data.Split(PAIR_SEPARATOR))
            {
                var pair = d.Split(KEY_VALUE_SEPARATOR);
                if (pair.Length != 2)
                    continue;
                packet.AddAttribute(pair[0], pair[1]);
            }

            return packet;
        }
    }
}
