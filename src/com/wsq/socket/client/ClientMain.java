package com.wsq.socket.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {
	public static String Name = String.valueOf(System.currentTimeMillis());
	public static final String IP = "0.0.0.0";// "106.14.160.65";
	public static final int PORT = 12345;
	static int flag=0;
	static Thread gethread;
	public static void main(String[] args) {
		System.out.println("�ͻ�������...");
		int i = 0;
		while (true) {
			Socket socket = null;
			try {
				socket = new Socket(IP, PORT);
				ClientMain clientMain = new ClientMain();
				// ��һ�ο��������߳�
				if (i == 0) {
					gethread=new Thread(new Read(socket));
					gethread.start();
					i++;
				}
				// ���������������,�ȷ�������
				// DataOutputStream outputStream=new DataOutputStream(socket.getOutputStream());
				OutputStream outputStream = socket.getOutputStream();
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
				BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
				System.out.println("������:");
				String str = new BufferedReader(new InputStreamReader(System.in)).readLine();

				bufferedWriter.write(Name);
				bufferedWriter.newLine();
				// bufferedWriter.flush();

				bufferedWriter.write(str);
				bufferedWriter.newLine();
				bufferedWriter.flush();
				
				if(str.equals("exit")) {
					flag=1;
					break;
				}

			} catch (Exception e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}

	}

}
