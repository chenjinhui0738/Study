package socket.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP是一种基于流的协议
 * 需要3次握手和4c次挥手建立连接（java底层已经帮我们实现）
 * 无论是tcp还是udp底层都是通过socket实现
 **/
public class TcpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6666); // 监听指定端口
        System.out.println("server is running...");
        //每当有新的客户端连接进来后，就返回一个Socket实例，这个Socket实例就是用来和刚连接的客户端进行通信的。
        //由于客户端很多，要实现并发处理，我们就必须为每个新的Socket创建一个新线程来处理，这样，主线程的作用就是接收新的连接，
        //每当收到新连接后，就创建一个新线程进行处理。如果没有客户端连接进来，accept()方法会阻塞并一直等待。如果有多个客户端同时连接进来，ServerSocket会把连接扔到队列里，然后一个一个处理。对于Java程序而言，只需要通过循环不断调用accept()就可以获取新的连接。
        for (; ; ) {
            Socket sock = ss.accept();
            System.out.println("connected from " + sock.getRemoteSocketAddress());
            Thread t = new Handler(sock);
            t.start();
        }
    }
}
