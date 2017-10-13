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
	public static final int PORT = 12345;// �����Ķ˿�
	static HashMap<String, Socket> map = new HashMap<>();// �洢��Ӧ��ϵ

	public static void main(String[] args) {
		System.out.println("����������...");
		ServeMain serveMain = new ServeMain();
		serveMain.init();
	}

	private void init() {
		try {
			ServerSocket serveSocket = new ServerSocket(PORT);
			while (true) {
				Socket client = serveSocket.accept();
				// ������
				new Hand(client);
			}
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
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
				name = bufferedReader.readLine();// ��ȡ�ͻ�������
				System.out.println("�����ǣ�" + name);
				if (!map.containsKey(name)) {
					// ���û�
					map.put(name, client);
				}
				msg = bufferedReader.readLine();
				System.out.println("��Ϣ�ǣ�" + msg);
				//���exit������socket
				if(msg.equals("exit")) {
					map.get(name).close();
					map.remove(name);
					return;
				}
				
			} catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			
			// �ַ���Ϣ
			System.out.println("socket�ĸ�����" + map.size());
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Socket> entry = (Entry<String, Socket>) iter.next();
				String nameKey = entry.getKey();
				Socket clientValue = entry.getValue();
				if (!nameKey.equals(name)) {
					try {
						// �ַ��������û�
						DataOutputStream outputStream = new DataOutputStream(clientValue.getOutputStream());
						outputStream.writeUTF(name + "˵��" + msg);
					} catch (IOException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}
			}
		}
	}
}
