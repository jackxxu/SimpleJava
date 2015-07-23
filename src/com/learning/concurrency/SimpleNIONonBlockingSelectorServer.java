package com.learning.concurrency;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Jack.Xu on 7/22/15.
 */
public class SimpleNIONonBlockingSelectorServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress("localhost", 8080));
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            for(Iterator<SelectionKey> itkeys = selector.selectedKeys().iterator(); itkeys.hasNext();) {
                SelectionKey key = itkeys.next();
                if(key.isValid()) {
                    if(key.isAcceptable()) { // a socket connection is established
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(256);
                        client.read(buffer);
                        String output = new String(buffer.array()).trim();
                        System.out.println("Message read from client: " + output);
                        if (output.equals("Bye.")) {
                            client.close();
                            System.out.println("Client messages are complete; close.");
                        }

                    }
                }
            }
        }
    }

}
