package com.lzaromskis.camerasecurity.communication;

import com.lzaromskis.camerasecurity.utility.exceptions.InvalidHostnameException;
import com.lzaromskis.camerasecurity.utility.exceptions.InvalidResponseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client{

    private static final int BUFFER_SIZE = 2 * 1024 * 1024;
    private final String _host;
    private final int _port;
    private final char[] _buffer;

    public Client(String host) {
        _host = host;
        _port = 7500;
        _buffer = new char[BUFFER_SIZE];
    }

    public Client(String host, int port) {
        _host = host;
        _port = port;
        _buffer = new char[BUFFER_SIZE];
    }

    public String sendRequest(String request) throws InvalidHostnameException, InvalidResponseException, IOException, SocketTimeoutException {
            int bytesRead;
            char[] sizeBufferChars = new char[8];
            Socket socket = new Socket();
            InetSocketAddress address =  new InetSocketAddress(_host, _port);
            if (address.isUnresolved())
                throw new InvalidHostnameException("The host '" + _host + "' cannot be resolved.");
            socket.setSoTimeout(2500);
            socket.connect(address, socket.getSoTimeout());
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.print(request);
            writer.flush();
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            bytesRead = reader.read(sizeBufferChars, 0, 8);
            if (bytesRead != 8)
                throw new InvalidResponseException("The received response is invalid.");
            String sizeString = new String(sizeBufferChars);
            int size = Integer.parseInt(sizeString);
            try {
                for (bytesRead = 0; bytesRead < size; ) {
                    bytesRead += reader.read(_buffer, bytesRead, 16);
                }
                if (bytesRead != size)
                    throw new InvalidResponseException("The received response is invalid.");
            } catch (IndexOutOfBoundsException ignored) {
                return null;
            } finally {
                writer.close();
                reader.close();
                socket.close();
            }

            return new String(_buffer, 0, size);
    }
}
