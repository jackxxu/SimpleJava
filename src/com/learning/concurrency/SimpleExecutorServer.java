package com.learning.concurrency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Created by Jack.Xu on 7/18/15.
 */
public class SimpleExecutorServer {

    public static void main(String... args) throws IOException {

        ServerSocket ss = new ServerSocket(8080);
        ExecutorService pool = new ThreadPoolExecutor(0, 1000,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        try {
            while (true) {
                Socket s = ss.accept();
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
