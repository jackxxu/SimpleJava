package com.learning.concurrency;

import com.sun.tools.doclets.internal.toolkit.util.DocFinder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Jack.Xu on 7/18/15.
 */
public class SimpleServer {

    public SimpleServer() {
    }

    public static void main(String... args) throws IOException {

        ServerSocket ss = new ServerSocket(8080);
        while(true) {
            Socket s = ss.accept();
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            int data;
            while((data = in.read()) != -1) {
                out.write(Util.transmogrify(data));
            }
        }
    }
}
