package com.lzaromskis.camerasecurity.communication;

import java.util.Enumeration;

public class PacketDataSerializer {
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String PAIR_SEPARATOR = ";";

    public String serialize(PacketData data) {
        StringBuilder builder = new StringBuilder();
        Enumeration<String> e = data.getAllAttributeNames();

        while(e.hasMoreElements()) {
            String key = e.nextElement();
            String val = data.getAttribute(key);

            builder.append(key);
            builder.append(KEY_VALUE_SEPARATOR);
            builder.append(val);
            builder.append(PAIR_SEPARATOR);
        }

        return builder.toString();
    }

    public PacketData deserialize(String data) {
        PacketData packet = new PacketData();

        for (String d : data.split(PAIR_SEPARATOR)) {
            int length = d.length();
            int split_index = d.indexOf(KEY_VALUE_SEPARATOR);
            if (split_index == -1 || split_index == length - 1)
                continue;
            String key = d.substring(0, split_index);
            String value = d.substring(split_index + 1);
            packet.addAttribute(key, value);
        }

        return packet;
    }

}
