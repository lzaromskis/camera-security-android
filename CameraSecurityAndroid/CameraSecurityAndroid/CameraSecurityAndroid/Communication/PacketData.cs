using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CameraSecurityAndroid.Communication
{
    public class PacketData
    {
        private readonly Dictionary<string, string> _attributes;

        public PacketData()
        {
            _attributes = new Dictionary<string, string>();
        }

        public void AddAttribute(string attributeName, string attributeData)
        {
            _attributes.Add(attributeName, attributeData);
        }

        public string GetAttribute(string attributeName)
        {
            if (_attributes.TryGetValue(attributeName, out string val))
            {
                return val;
            }
            return null;
        }

        public IEnumerable<KeyValuePair<string, string>> GetAllAttributes()
        {
            return _attributes.AsEnumerable();
        }

        public bool IsValid()
        {
            if (_attributes.TryGetValue("code", out string code))
            {
                if (int.TryParse(code, out _))
                {
                    return true;
                }
            }
            return false;
        }
    }
}
