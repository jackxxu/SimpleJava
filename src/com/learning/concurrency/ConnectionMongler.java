package com.learning.concurrency;

import java.io.IOException;
import java.net.Socket;
/**
 * Created by Jack.Xu on 7/18/15.
 */
public class ConnectionMongler {

    public static void main(String... args) {
        for(int i=0; i < 3000; i++) {
            try {
                new Socket("localhost", 8080);
                System.out.println("connection " + i); // this stops @ 2073 b/c kern.num_taskthreads: 2048 (sysctl  -A | grep thread)
            } catch(IOException o) {
                System.out.println("can not connection " + o);
            }
        }
    }
}
