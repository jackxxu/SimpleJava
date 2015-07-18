package com.learning.concurrency;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
                out.write(Util.transmogrify(data));
            }
        } catch (IOException e) {
            System.err.println("Connection Problem - " + e);
        }
    }
}
