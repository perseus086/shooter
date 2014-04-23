package com.da.shooter.communication.processors;

import java.util.HashMap;
import java.util.Map;

import com.da.shooter.communication.Message;
import com.da.shooter.communication.utils.UdpMessageUtils;

public abstract class InputMessageProcessor extends Thread {
	
	private Map<String,Message> messages;
	
	abstract Message process(Message msg);
	
	private int status;
	
	public InputMessageProcessor() {
		super();
		this.status = Status.STOP;
		messages = new HashMap<String, Message>();
	}
	
	@Override
	public void start(){
		super.start();
		this.status = Status.RUNNING;
	}

	public void addMessage(Message msg){
		synchronized (messages) {
			Message prev = messages.put(msg.getAvatarId(),msg);
			if(prev != null && prev.getNumber() > msg.getNumber()){
				messages.put(msg.getAvatarId(),prev);
			}
		}
	}
	
	@Override
	public void run(){
		while(this.status == Status.RUNNING){
			synchronized (messages) {
				if(messages != null && messages.size() > 0){
					for(String avatarId : messages.keySet()){
						Message msg = messages.remove(avatarId);
						Message reply = process(msg);
						if(reply != null){
//							reply.setPort(msg.getPort());
							UdpMessageUtils.sendMessage(reply);
						}
					}
				}
			}
		}
	}
	
	interface Status{
		int RUNNING = 0;
		int STOP = 1;
	}
}
