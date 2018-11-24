package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2018/11/24 10:10
 */
public class ClientTwo {

    public static void main(String args[]) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("127.0.0.1",80));
            OutputStream outputStream = socket.getOutputStream();
            byte[] bytes = new byte[1024];
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String next = scanner.next();
                bytes = next.getBytes();
                outputStream.write(bytes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
