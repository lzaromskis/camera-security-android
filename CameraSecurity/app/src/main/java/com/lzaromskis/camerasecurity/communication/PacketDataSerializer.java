package com.lzaromskis.camerasecurity.communication;

import java.util.Enumeration;

public class PacketDataSerializer {
    private static final String KEY_VALUE_SEPARATOR = "&";
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
            String[] pair = d.split(KEY_VALUE_SEPARATOR);

            if (pair.length != 2)
                continue;

            packet.addAttribute(pair[0], pair[1]);
        }

        return packet;
    }

}
