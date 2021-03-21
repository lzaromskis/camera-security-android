package com.lzaromskis.camerasecurity.communication;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import kotlin.text.Charsets;

public class Client{

    private static final int BUFFER_SIZE = 2 * 1024 * 1024;
    private final String _host;
    private final int _port;
    private final char[] _buffer;

    public Client(String host, int port) {
        _host = host;
        _port = port;
        _buffer = new char[BUFFER_SIZE];
    }

    public String sendRequest(String request) {
        try {
            int bytesRead;
            char[] sizeBufferChars = new char[8];
            byte[] sizeBufferBytes = new byte[4];
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(_host, _port));
            //socket.connect();
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.print(request);
            writer.flush();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            bytesRead = reader.read(sizeBufferChars, 0, 8);
            String sizeString = new String(sizeBufferChars);
            int size = Integer.parseInt(sizeString);
            for (int i = 0; i < size; i++) {
                bytesRead = reader.read(_buffer, i, 1);
            }
            //bytesRead = reader.read(_buffer, 0, size);
            writer.close();
            reader.close();
            socket.close();
            return new String(_buffer, 0, size);
        } catch (Exception ex) {
            return null;
        }
    }
}
