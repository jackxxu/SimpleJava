package com.learning.concurrency;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

/**
 * Created by Jack.Xu on 7/18/15.
 */
public class SimpleNIOServer {

    public static void main(String... args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress("localhost", 8080));
        ExecutorService pool = Executors.newFixedThreadPool(1000);

        try {
            while (true) {
                SocketChannel s = ssc.accept();
                // move the logic off to a new thread for execution
                pool.submit(() -> Util.process(s));
            }
        } catch(Exception e) {
            System.out.println("Exception in Server " + e);
        } finally {
            pool.shutdown();
        }

    }
}
