package server;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Describle This Class Is 单线程Bio
 * @Author ZengMin
 * @Date 2018/11/24 10:10
 */
public class SingleThreadBio {

    public static void main(String args[]) throws Exception {
        ServerSocket serverSocket = new ServerSocket(80);
        while (true) {
            Socket accept = serverSocket.accept();
            System.out.println();
            try {
                if (!accept.isClosed()) {
                    //单线程  阻塞
                    System.out.println("客户端连接:" + accept.getInetAddress());
                    InputStream inputStream = accept.getInputStream();
                    byte[] bytes = new byte[1024];
                    while (true) {
                        int read = inputStream.read(bytes);
                        if (read != -1) {
                            System.out.println(new String(bytes, 0, read));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                accept.close();
            }
        }
    }
}
