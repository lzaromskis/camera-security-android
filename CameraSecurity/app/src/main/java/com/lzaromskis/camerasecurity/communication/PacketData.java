package com.lzaromskis.camerasecurity.communication;

import android.util.Pair;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class PacketData {
    private final Dictionary<String, String> _attributes;

    public PacketData() {
        _attributes = new Hashtable<>();
    }

    public void addAttribute(String attributeName, String attributeValue) {
        _attributes.put(attributeName, attributeValue);
    }

    public String getAttribute(String attributeName) {
        return _attributes.get(attributeName);
    }

    public Enumeration<String> getAllAttributeNames() {
        return _attributes.keys();
    }

    public Boolean isValid() {
        String val = _attributes.get("code");
        if (val != null)
        {
            try {
                int v = Integer.parseInt(val);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }
}
