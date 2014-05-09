package com.da.shooter.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Test {

	public static void main(String[] args){
		Socket socket = null;
		try {
			socket = new Socket("localhost",9467);
			while (true ) {
				Message msgOut = new Message(Message.Type.REQUEST_MESSAGE_ID);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(msgOut);
				
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				Message msgIn = (Message)objectInputStream.readObject(); 
				System.out.println("ID: "+msgIn.getNumber());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
