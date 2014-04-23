package com.da.shooter.communication;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.da.shooter.GameScreen;
import com.da.shooter.communication.processors.InputMessageProcessor;
import com.da.shooter.communication.processors.OutputMessageProcessor;
import com.da.shooter.communication.utils.IdGenerator;
import com.da.shooter.communication.utils.UdpMessageUtils;

public class CommunicationManager extends Thread{
	
	private static final int MESSAGE_LENGTH = 700;

	private static CommunicationManager instance;
	
	public static void createInstance(String ownerUrl){
		instance = new CommunicationManager(ownerUrl);
	}
	
	public static CommunicationManager getInstance(){
		return instance;
	}

	private InetAddress creatorAddress;
	
	private Map<String,Player> players;
	
	private int creatorPort;
	
	private int playerPort;
	
	private boolean creator;
	
	private int status;
	
	private Map<Integer,InputMessageProcessor> inputProcessors;
	private List<OutputMessageProcessor> outputProcessors;
	
	private CommunicationManager(String ownerUrl){
		this.creatorAddress = getInetAddress(ownerUrl);
		this.creator = GameScreen.getInstance().isCreator();
		this.creatorPort = 10000;
		this.playerPort = 10001;
		
		inputProcessors = new HashMap<Integer, InputMessageProcessor>();
		outputProcessors = new ArrayList<OutputMessageProcessor>();
		
		players = new HashMap<String, Player>(); 
	}
	
	@Override
	public void start(){
		super.start();
		this.status = Status.RUNNING;
	}
	
	@Override
	public void run(){
		int lost_packets = 0;
		while( this.status == Status.RUNNING || this.status == Status.WAITING){
			if( this.status == Status.RUNNING && !GameScreen.getInstance().checkStatus(GameScreen.GameStatus.ENDED)){
				if(receiveData()){
					lost_packets = 0;
//					CarGame.getInstance().setConnectionLost(false);
				}else{
					lost_packets++;
				}
//				if(CarGame.getInstance().checkStatus(CarGame.STATUS_PLAYING) && lost_packets >= PERMITED_MESSAGE_LOST){
//					CarGame.getInstance().setConnectionLost(true);
//				}
//				if(this.peerAddress != null){
//					sendData();
//				}
			}
			
//			if(CarGame.getInstance().checkStatus(CarGame.STATUS_WAITING) && (System.currentTimeMillis() -this.waitingTime >= WAIT_TIME )){
//				CarGame.getInstance().setWaitStop(true);
//			}
		}
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
		
		Message inMessage = UdpMessageUtils.receiveMessage((creator)?this.creatorPort:this.playerPort, MESSAGE_LENGTH, 20);
		if(inMessage == null) return false; // No new message
		if(this.inputProcessors.containsKey(inMessage.getType())){
			this.inputProcessors.get(inMessage.getType()).addMessage(inMessage);
			return true;
		}
		
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
	
	public void addInputProcessor(int messageType,InputMessageProcessor inputProcessor){
		this.inputProcessors.put(messageType, inputProcessor);
		inputProcessor.start();
	}
	
	public void addOutputProcessor(OutputMessageProcessor outputProcessor){
		this.outputProcessors.add(outputProcessor);
		outputProcessor.start();
	}
	
	private InetAddress getInetAddress(String url){
		try {
			return InetAddress.getByName(url);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void requestAvatarId(){
		if(GameScreen.getInstance().isCreator()){
			GameScreen.getInstance().setPlayer(this.addPlayer(getInetAddress("localhost")));
		}else{
			(new Thread(){
				@Override
				public void run(){
					while(GameScreen.getInstance().getAvatarId() == null){
						Message msg = new Message(Message.Type.REQUEST_ID);
						msg.setAddress(creatorAddress);
						msg.setPort(creatorPort);
						UdpMessageUtils.sendMessage(msg);
						try {
							sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

	}
	
	public InetAddress getCreatorAddress() {
		return creatorAddress;
	}

	public int getCreatorPort() {
		return creatorPort;
	}

	public int getPlayerPort() {
		return playerPort;
	}
	
	public Map<String, Player> getPlayers() {
		return players;
	}

	public Player addPlayer(InetAddress address){
		if(this.players.containsKey(address.getHostAddress())){
			return this.players.get(address.getHostAddress());
		}
		Player player = new Player();
		player.setAddress(address);
		player.setAvatarId(IdGenerator.generateId());
		this.players.put(address.getHostName(), player);
		GameScreen.getInstance().createAvatar(player.getAvatarId());
		return player;
	}

	interface Status{
		int WAITING = 0;
		int RUNNING = 1;
	}
}
