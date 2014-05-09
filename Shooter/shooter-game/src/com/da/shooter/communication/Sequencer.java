package com.da.shooter.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Sequencer{
	
	public static final int SEQUENCER_PORT = 9467;
	
	private static Sequencer instance;
	
	public static Sequencer getInstance(){
		if(instance == null){
			instance = new Sequencer();
		}
		return instance;
	}
	
	private int msgPosition;
	private List<Socket> sockets;
	private int currentPlayer;
	
	public Sequencer() {
		super();
		this.msgPosition = 0;
		this.sockets = new ArrayList<Socket>();
		this.currentPlayer = 1;
	}

	public synchronized int getNextPlayerId(){
		return currentPlayer++;
	}
	
	public synchronized int getPosition(){
		return msgPosition++;
	}
	
	public synchronized List<Socket> getSockets() {
		return sockets;
	}

	public static void main(String[] args){
		
//		(new Thread("Sequencer receiver"){
//			@Override
//			public void run(){
//				while(true){
//					synchronized (Sequencer.getInstance().sockets) {
//						List<Socket> toRemove = new ArrayList<Socket>();
//						for (Socket socket : Sequencer.getInstance().sockets) {
//							synchronized (socket) {
//								try {
//									// Receive message
//									ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//									Message msg = (Message) objectInputStream.readObject();
//									System.out.println("Msg in: "+msg.getType());
//									if(msg.getType() == Message.Type.REQUEST_ID){
//										// Player number
//										msg.setData((Integer)Sequencer.getInstance().currentPlayer++);
//										
//										// Multicast message
//										for (Socket socketOut : Sequencer.getInstance().sockets) {
//											if(!socket.equals(socketOut)){
//												msg.setType(Message.Type.NEW_PLAYER);
//											}
//											ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketOut.getOutputStream());
//											objectOutputStream.writeObject(msg);
//											
//										}
//										System.out.println("Msg Out: new player "+(Integer)msg.getData());
//										
//									}else{
//										// Set message number
//										msg.setNumber(Sequencer.getInstance().getPosition());
//										
//										// Multicast message
//										for (Socket socketOut : Sequencer.getInstance().sockets) {
//											ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketOut.getOutputStream());
//											objectOutputStream.writeObject(msg);
//											System.out.println("Msg in: "+msg.getType());
//										}
//										System.out.println("Msg Out: msg order "+(Integer)msg.getNumber());
//									}
//									
//								} catch (Exception e) {
//									toRemove.add(socket);
//									e.printStackTrace();
//								}
//								
//							}
//						}
//						
//						for (Socket socket : toRemove) {
//							try {
//								socket.close();
//								Sequencer.getInstance().sockets.remove(socket);
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//							
//						}
//						
//					}
//
//				}
//			}
//		}).start();
		
		
		while(true){
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(SEQUENCER_PORT);
				Socket socket = serverSocket.accept();
				synchronized (Sequencer.getInstance().sockets) {
					Sequencer.getInstance().sockets.add(socket);
				}
				(new SequencerProcessor(socket)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(serverSocket!=null){
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}	
