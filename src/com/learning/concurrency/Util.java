package com.learning.concurrency;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Jack.Xu on 7/18/15.
 */
public class Util {

    public static int transmogrify(int v) {
        return Character.isLetter(v) ? v ^ ' ' : v;
    }

    public static void process(Socket s) {
        try {
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            int data;
            while ((data = in.read()) != -1) {
                out.write(transmogrify(data));
            }
        } catch (IOException e) {
            System.err.println("Connection Problem - " + e);
        }
    }

    public static void process(SocketChannel sc) {
        try {
            ByteBuffer buf = ByteBuffer.allocateDirect(1024);
            while(sc.read(buf) != -1) {
                buf.flip();
                for (int i = 0; i < buf.limit(); i++) {
                    buf.put(i, (byte) transmogrify(buf.get(i)));
                }
                sc.write(buf);
                buf.clear();
            }
        } catch (IOException e) {
            System.err.println("Connection Problem - " + e);
        }
    }
}
