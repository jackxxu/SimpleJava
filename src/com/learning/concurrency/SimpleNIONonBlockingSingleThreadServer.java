package com.learning.concurrency;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by Jack.Xu on 7/21/15.
 */
public class SimpleNIONonBlockingSingleThreadServer {

    private static Collection<SocketChannel> sockets =
            Collections.newSetFromMap(
                    new HashMap<SocketChannel, Boolean>()
            );

    public static void main(String[] args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress("localhost", 8080));
        ssc.configureBlocking(false);

        while (true) {
            SocketChannel s = ssc.accept();
            if (s != null) {
                System.out.println("Connection from " + s);

                s.configureBlocking(false);
                sockets.add(s);
            }

            for(Iterator<SocketChannel> itor = sockets.iterator(); itor.hasNext();) {

                SocketChannel socket = itor.next();
                try {
                    ByteBuffer buf = ByteBuffer.allocateDirect(1024);
                    int read = socket.read(buf);

                    if(read == -1) {
                        itor.remove();
                    } else if(read != 0) {
                        buf.flip();
                        for (int i = 0; i < buf.limit(); i++) {
                            buf.put(i, (byte) Util.transmogrify(buf.get(i)));
                        }
                        socket.write(buf);
                        buf.clear();
                    }
                } catch (IOException e) {
                    System.err.println("Connection Problem - "  + e);
                    itor.remove();
                }
            }

        }
    }
}
