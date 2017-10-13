package xyz.wsq0529.chatserve;

import java.net.Socket;

import javax.jws.soap.SOAPBinding.Use;

public class User {
	public String Name;
	public Socket socket;
	public User(String Name,Socket socket) {
		this.Name=Name;
		this.socket=socket;
	}
}
