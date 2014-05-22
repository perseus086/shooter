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
	
	private static int outCount;
	
	static{
		outCount =0;
	}
	
	public static Message getMessage(Socket socket) throws IOException, ClassNotFoundException{
//		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Message msg = (Message) objectInputStream.readObject();
//			objectInputStream.close();
			System.out.println("[Client][IN]: "+msg.getMessageTypeName()+"|"+msg.getMessageDetails());
			return msg;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
	}
	
	public static void sendMessage(Socket socket,Message msg){
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(msg);
			System.out.println("[Client][OUT]["+getOutCount()+"]:"+msg.getMessageTypeName()+"|"+msg.getMessageDetails());
//			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static int getOutCount(){
		outCount = outCount +1;
		return outCount;
	}
}
