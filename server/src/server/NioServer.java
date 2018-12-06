package server;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2018/12/6 20:24
 * @Company Matt
 */
public class NioServer {
    public static void main(String args[]) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(80));
        serverSocketChannel.configureBlocking(false);   //设置为非阻塞式
        System.out.println("msg: now nio server start on port :" + serverSocketChannel.getLocalAddress());
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  //已经连接的客户端
        while (true) {
            int select = selector.select();
            if (select == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();  //取所有selectKey
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {    //是连接上的
                    ServerSocketChannel socketChannel = (ServerSocketChannel) next.channel();
                    SocketChannel accept = socketChannel.accept();
//                    accept.configureBlocking(false);    //非阻塞式
                    System.out.println("客户端连接:" + accept.getRemoteAddress());
                    //业务逻辑
                    Socket socket = accept.socket();
                    InputStream inputStream = socket.getInputStream();
                        byte[] b = new byte[1024];
                        int read = inputStream.read(b);
                        if (read != -1) {
                            System.out.println(new String(b, 0, read));
                    }
                }
            }
        }
    }
}
