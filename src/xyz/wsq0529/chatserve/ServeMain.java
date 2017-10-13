package xyz.wsq0529.chatserve;

import java.awt.List;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.jws.soap.SOAPBinding.Use;

public class ServeMain {
	public static final int PORT = 12345;// 监听的端口
	static HashMap<String, Socket> map = new HashMap<>();// 存储对应关系

	public static void main(String[] args) {
		System.out.println("服务器启动...");
		ServeMain serveMain = new ServeMain();
		serveMain.init();
	}

	private void init() {
		try {
			ServerSocket serveSocket = new ServerSocket(PORT);
			while (true) {
				Socket client = serveSocket.accept();
				// 处理函数
				new Hand(client);
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	private class Hand implements Runnable {
		String name;
		String msg;
		Socket client;

		public Hand(Socket client) {
			this.client = client;
			new Thread(this).start();
		}

		public void run() {
			try {
				InputStream inputStream = client.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				name = bufferedReader.readLine();// 获取客户端名称
				System.out.println("姓名是：" + name);
				if (!map.containsKey(name)) {
					// 新用户
					map.put(name, client);
				}
				msg = bufferedReader.readLine();
				System.out.println("信息是：" + msg);
				//如果exit则销毁socket
				if(msg.equals("exit")) {
					map.get(name).close();
					map.remove(name);
					return;
				}
				
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			
			// 分发消息
			System.out.println("socket的个数：" + map.size());
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Socket> entry = (Entry<String, Socket>) iter.next();
				String nameKey = entry.getKey();
				Socket clientValue = entry.getValue();
				if (!nameKey.equals(name)) {
					try {
						// 分发给其他用户
						DataOutputStream outputStream = new DataOutputStream(clientValue.getOutputStream());
						outputStream.writeUTF(name + "说：" + msg);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}
		}
	}
}
