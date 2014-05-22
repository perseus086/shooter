package com.da.shooter.communication;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SequencerProcessor extends Thread {
	
	private final int STATUS_STOP = 0;
	private final int STATUS_RUNNING = 1;
	
	private Socket socket;
	private Sequencer sequencer;
	private int status;

	public SequencerProcessor(Sequencer sequencer, Socket socket) {
		super();
		this.socket = socket;
		this.sequencer = sequencer;
		this.status =STATUS_RUNNING;
	}
	
	@Override
	public void run(){
		while(this.status == STATUS_RUNNING){
			try{
				// Receive message
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) objectInputStream.readObject();
				
				System.out.println("[SEQ][In]["+socket.getInetAddress().getHostAddress()+"]: "+msg.getMessageTypeName());
				if(msg.getType() == Message.Type.REQUEST_ID){
					// Player number
					msg.setData((Integer)sequencer.getNextPlayerId());
					
					// Multicast message
					for (Socket socketOut : sequencer.getSockets()) {
						if(!socket.equals(socketOut)){
							msg.setType(Message.Type.NEW_PLAYER);
						}
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketOut.getOutputStream());
						objectOutputStream.writeObject(msg);
						
					}
					System.out.println("[SEQ][Out][Multicast]: New player "+(Integer)msg.getData());
					
				}else if(sequencer.allPlayersConnected()){
					// Set message number
					msg.setNumber(sequencer.getPosition());
					
					// Multicast message
					for (Socket socketOut : sequencer.getSockets()) {
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketOut.getOutputStream());
						objectOutputStream.writeObject(msg);
					}
					System.out.println("[SEQ][Out][Multicast]: Order assigned "+(Integer)msg.getNumber()+ "| "+msg.getMessageTypeName());
				}
			}catch(EOFException ex){
				System.out.println("[SEQ][Error]: Player disconnected or network faillure.");
				this.status = STATUS_STOP;
//				ex.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
			}
		}
	}
}	
