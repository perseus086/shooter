package com.da.shooter.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Sequencer extends Thread{
	
	public static final int SEQUENCER_PORT = 9467;
	

	private int msgPosition;
	private List<Socket> sockets;
	private int currentPlayer;
	private int playersNumber;
		
	public Sequencer(int playersNumber) {
		super();
		this.msgPosition = 0;
		this.playersNumber = playersNumber;
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
	
	public boolean allPlayersConnected(){
		return (currentPlayer > playersNumber);
	}

	@Override
	public void run(){
		
		while(true){
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(SEQUENCER_PORT);
				Socket socket = serverSocket.accept();
				synchronized (this.sockets) {
					this.sockets.add(socket);
				}
				(new SequencerProcessor(this,socket)).start();
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
