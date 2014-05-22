package com.da.shooter.communication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.da.shooter.GameScreen;
import com.da.shooter.communication.utils.TcpMessageUtils;
import com.da.shooter.elements.Action;

public class CommunicationManager extends Thread{

	private static CommunicationManager instance;
	
	public static void createInstance(String ownerUrl){
		instance = new CommunicationManager(ownerUrl);
	}
	
	public static CommunicationManager getInstance(){
		return instance;
	}

	private InetAddress sequencerAddress;
	
	private Socket sequencerSocket;
	
	private Map<Integer,Player> players;
	
	private int creatorPort;
	
	private int playerPort;
	
	private boolean creator;
	
	private int status;
	
//	private Map<Integer,InputMessageProcessor> inputProcessors;
//	private List<OutputMessageProcessor> outputProcessors;
	
//	private List<Socket> sockets;
	private TreeMap<Integer,Message> msgInQueue;
	private Queue<Message> msgOutQueue;
	
	private int lastMessage;
	
	private CommunicationManager(String sequencerUrl){
		this.sequencerAddress = getInetAddress(sequencerUrl);
		this.creator = GameScreen.getInstance().isCreator();
		this.creatorPort = 10000;
		this.playerPort = 10001;
		
//		inputProcessors = new HashMap<Integer, InputMessageProcessor>();
//		outputProcessors = new ArrayList<OutputMessageProcessor>();
		
		players = new HashMap<Integer, Player>();
		
		msgInQueue = new TreeMap<Integer,Message>() ;
		msgOutQueue = new ConcurrentLinkedQueue<Message>();
	}
	
	@Override
	public void start(){
		super.start();
		this.status = Status.RUNNING;
	}
	
	public void sendAction(Action action){
		Message msg = new Message(Message.Type.ACTION);
		msg.setData(action);
		msg.setAvatarId(action.getAvatarId());
		sendMessage(msg);
	}
	
	public void sendMessage(Message msg){
		if(GameScreen.getInstance().checkStatus(GameScreen.GameStatus.GAME_OVER)) return;
		synchronized (msgOutQueue) {
			msgOutQueue.add(msg);
			msgOutQueue.notifyAll();
		}
	}
	
	public void sendLifeReduce(int avatarId, int lifeReduce){
		Message msg = new Message(Message.Type.LIFE_REDUCE);
		msg.setAvatarId(avatarId);
		msg.setData((Integer)lifeReduce);
		sendMessage(msg);
	}
	
	public void sendGameOver(int avatarId) {
		Message msg = new Message(Message.Type.GAME_OVER);
		msg.setAvatarId(avatarId);
		sendMessage(msg);
	}
	
	@Override
	public void run(){
		
//		(new Thread("Socket receiver"){
//			public void run(){
//				while(true){
//					try {
//						ServerSocket serverSocket = new ServerSocket(INPUT_PORT);
//						sequencerSocket = serverSocket.accept();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				
//			}
//		}).start();
		
		(new Thread("Message enqueuer"){
			public void run(){
				while(true){
					try {
						if(sequencerSocket != null){
							Message msg = TcpMessageUtils.getMessage(sequencerSocket);
							if(msg != null){
								synchronized (msgInQueue) {
									msgInQueue.put(msg.getNumber(),msg);
									msgInQueue.notifyAll();
								}
							}
						}
					} catch (Exception e) {
						System.err.println("[Client][Error]: Senquencer can not be reached.");
//						e.printStackTrace();
					}
				}
				
			}
		}).start();
		
		(new Thread("Message consumer"){
			public void run(){
				while(true){
					try {
						synchronized (msgInQueue) {
							if(msgInQueue.size() ==0 || msgInQueue.ceilingEntry(lastMessage) == null){
								msgInQueue.wait(1000);
							}else if(msgInQueue.size() > 0){
								Message msg = msgInQueue.remove(msgInQueue.ceilingEntry(lastMessage).getKey());
								
								switch (msg.getType()) {
									case Message.Type.NEW_PLAYER:
										int avatarId = (Integer)msg.getData(); 
										addPlayer(avatarId);
									break;
									case Message.Type.ACTION:
										Action action = (Action)msg.getData();
										GameScreen.getInstance().putAction(action);
									break;
									case Message.Type.LIFE_REDUCE:
										avatarId = msg.getAvatarId();
										int lifeReduce = (Integer)msg.getData();
										GameScreen.getInstance().reduceLife(avatarId,lifeReduce);
									break;
									case Message.Type.GAME_OVER:
										avatarId = msg.getAvatarId();
										GameScreen.getInstance().gameOver(avatarId);
									break;
									default:
									break;
								}
								
							}
									
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		
		(new Thread("Message sender"){
			public void run(){
				while(true){
					try {
						synchronized (msgOutQueue) {
							while(msgOutQueue.size() == 0){
								msgOutQueue.wait(1000);
							}
							Message msg = msgOutQueue.remove();
							TcpMessageUtils.sendMessage(sequencerSocket, msg);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		
	}

//	private void sendData() {
//		List<Message> outMessages = new ArrayList<Message>();
//		for (OutputMessageProcessor processor : outputProcessors) {
//			Message msg = processor.process();
//			if(msg != null){
//				outMessages.add(msg);
//			}
//		}
////		UdpMessage outMessage = new UdpMessage(UdpMessage.TYPE_PLAYER_DATA, CarGame.getInstance().getMyPlayer(), System.currentTimeMillis(),CarGame.getInstance().getGameId());
////		outMessage.setAddress(this.peerAddress);
//		UdpMessageUtils.sendMessages(outMessages);
//
//	}
	
	private boolean receiveData(){
		
//		Message inMessage = UdpMessageUtils.receiveMessage((creator)?this.creatorPort:this.playerPort, MESSAGE_LENGTH, 20);
//		if(inMessage == null) return false; // No new message
//		if(this.inputProcessors.containsKey(inMessage.getType())){
//			this.inputProcessors.get(inMessage.getType()).addMessage(inMessage);
//			return true;
//		}
		
//		if(!CarGame.getInstance().getGameId().equals(inMessage.getGameId())) return false; // Message from other game
//		if(inMessage.getTime() <= this.lastReceivedPlayerTime) return true; // Ignore old packet
//		this.lastReceivedPlayerTime = inMessage.getTime(); 
//		
//		if(this.server && this.peerAddress == null){
//			this.peerAddress = inMessage.getAddress();
//			System.out.println("Client address:"+this.peerAddress.getHostAddress());
//		}
//		
//		switch(inMessage.getType()){
//			case UdpMessage.TYPE_PLAYER_DATA:
//				Player receivedPlayer = (Player)inMessage.getData();
//				if(receivedPlayer != null){
//					syncPlayerInfo(receivedPlayer);
//				}
//			break;
//		}
		return false;
	}
	
//	public void addInputProcessor(int messageType,InputMessageProcessor inputProcessor){
//		this.inputProcessors.put(messageType, inputProcessor);
//		inputProcessor.start();
//	}
//	
//	public void addOutputProcessor(OutputMessageProcessor outputProcessor){
//		this.outputProcessors.add(outputProcessor);
//		outputProcessor.start();
//	}
	
	private InetAddress getInetAddress(String url){
		try {
			return InetAddress.getByName(url);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void requestAvatarId(){
		
		try {
			sequencerSocket = new Socket(this.sequencerAddress ,Sequencer.SEQUENCER_PORT);
			Message msgOut = new Message(Message.Type.REQUEST_ID);
			TcpMessageUtils.sendMessage(sequencerSocket, msgOut);
			Message msgIn = TcpMessageUtils.getMessage(sequencerSocket);
			int avatarId = (Integer)msgIn.getData();
			Player player = addPlayer(avatarId);
			GameScreen.getInstance().setPlayer(player);
			
			// Create other players
			for(int i=1; i<avatarId;i++){
				addPlayer(i);
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public InetAddress getCreatorAddress() {
		return sequencerAddress;
	}

	public int getCreatorPort() {
		return creatorPort;
	}

	public int getPlayerPort() {
		return playerPort;
	}
	
	public Map<Integer, Player> getPlayers() {
		return players;
	}

	public Player addPlayer(int avatarId){
		if(this.players.containsKey(avatarId)){
			return this.players.get(avatarId);
		}
		Player player = new Player();
		player.setAvatarId(avatarId);
		this.players.put(avatarId, player);
		GameScreen.getInstance().createAvatar(player);
		return player;
	}

	interface Status{
		int WAITING = 0;
		int RUNNING = 1;
	}
}
