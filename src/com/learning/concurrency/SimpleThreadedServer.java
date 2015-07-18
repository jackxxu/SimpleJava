package com.learning.concurrency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Jack.Xu on 7/18/15.
 */
public class SimpleThreadedServer {

    public static void main(String... args) throws IOException {

        ServerSocket ss = new ServerSocket(8080);
        while(true) {
            Socket s = ss.accept();
            // move the logic off to a new thread for execution
            new Thread(() -> Util.process(s)).start();
        }

    }
}
