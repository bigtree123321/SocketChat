package com.wsq.socket.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Read implements Runnable {
	Socket socket;
	public  Read(Socket socket) {
		// TODO �Զ����ɵĹ��캯�����
		this.socket=socket;
	}
	@Override
	public void run() {
		// TODO �Զ����ɵķ������
		while(true) {
			// ��ȡ����������
			DataInputStream inputStream;
			try {
				inputStream = new DataInputStream(socket.getInputStream());
				String ret = inputStream.readUTF();
				System.out.println("�������˷��ص��ǣ�" + ret);
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
		}
	}

}
