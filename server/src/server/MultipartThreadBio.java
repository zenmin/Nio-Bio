package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Describle This Class Is 多线程Bio
 * @Author ZengMin
 * @Date 2018/11/24 10:10
 */
public class MultipartThreadBio {

    public static void main(String args[]) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(80);
        while (true) {
            Socket accept = serverSocket.accept();
            executorService.execute(() -> {
                //业务代码块
                try {
                    if (!accept.isClosed()) {
                        byte[] bytes = new byte[1024];
                        System.out.println("客户端连接:" + accept.getInetAddress());
                        InputStream inputStream = accept.getInputStream();
                        while (true) {
                            int read = inputStream.read(bytes);
                            if (read != -1) {
                                System.out.println(new String(bytes, 0, read));
                                System.out.println(Thread.getAllStackTraces());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        accept.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}
