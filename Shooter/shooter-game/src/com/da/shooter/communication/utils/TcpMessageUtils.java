package com.da.shooter.communication.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.da.shooter.communication.Message;

/**
 * The Class UdpMessageUtils.
 */
public class TcpMessageUtils {
	
	public static Message getMessage(Socket socket){
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Message msg = (Message) objectInputStream.readObject();
//			objectInputStream.close();
			System.out.println("MSG IN: "+msg.getType());
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void sendMessage(Socket socket,Message msg){
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(msg);
//			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
