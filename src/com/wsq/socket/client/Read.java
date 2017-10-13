package com.wsq.socket.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Read implements Runnable {
	Socket socket;
	public  Read(Socket socket) {
		// TODO 自动生成的构造函数存根
		this.socket=socket;
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		while(true) {
			// 读取服务器数据
			DataInputStream inputStream;
			try {
				inputStream = new DataInputStream(socket.getInputStream());
				String ret = inputStream.readUTF();
				System.out.println("服务器端返回的是：" + ret);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
	}

}
