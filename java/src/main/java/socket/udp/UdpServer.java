package socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 和TCP编程相比，UDP编程就简单得多，因为UDP没有创建连接，数据包也是一次收发一个，所以没有流的概念。
 * <p>
 * 在Java中使用UDP编程，仍然需要使用Socket，因为应用程序在使用UDP时必须指定网络接口（IP）和端口号。
 * <p>
 * 注意：UDP端口和TCP端口虽然都使用0~65535，但他们是两套独立的端口，即一个应用程序用TCP占用了端口1234，不影响另一个应用程序用UDP占用端口1234
 */
public class UdpServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(6666); // 监听指定端口
        System.out.println("server is running...");
        for (; ; ) {
            // 接收:
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            ds.receive(packet);
            String cmd = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
            // 发送:
            String resp = "bad command";
            switch (cmd) {
                case "date":
                    resp = LocalDate.now().toString();
                    break;
                case "time":
                    resp = LocalTime.now().withNano(0).toString();
                    break;
                case "datetime":
                    resp = LocalDateTime.now().withNano(0).toString();
                    break;
                case "weather":
                    resp = "sunny, 10~15 C.";
                    break;
            }
            System.out.println(cmd + " >>> " + resp);
            packet.setData(resp.getBytes(StandardCharsets.UTF_8));
            ds.send(packet);
        }
    }
}
