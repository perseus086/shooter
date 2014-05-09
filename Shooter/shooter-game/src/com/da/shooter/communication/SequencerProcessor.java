package com.da.shooter.communication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SequencerProcessor extends Thread {
	private Socket socket;

	public SequencerProcessor(Socket socket) {
		super();
		this.socket = socket;
	}
	
	@Override
	public void run(){
		while(true){
			try{
				// Receive message
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) objectInputStream.readObject();
				System.out.println("Msg in: "+msg.getType());
				if(msg.getType() == Message.Type.REQUEST_ID){
					// Player number
					msg.setData((Integer)Sequencer.getInstance().getNextPlayerId());
					
					// Multicast message
					for (Socket socketOut : Sequencer.getInstance().getSockets()) {
						if(!socket.equals(socketOut)){
							msg.setType(Message.Type.NEW_PLAYER);
						}
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketOut.getOutputStream());
						objectOutputStream.writeObject(msg);
						
					}
					System.out.println("Msg Out: new player "+(Integer)msg.getData());
					
				}else{
					// Set message number
					msg.setNumber(Sequencer.getInstance().getPosition());
					
					// Multicast message
					for (Socket socketOut : Sequencer.getInstance().getSockets()) {
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketOut.getOutputStream());
						objectOutputStream.writeObject(msg);
						System.out.println("Msg in: "+msg.getType());
					}
					System.out.println("Msg Out: msg order "+(Integer)msg.getNumber());
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}	
