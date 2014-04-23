package com.da.shooter.communication.processors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.da.shooter.GameScreen;
import com.da.shooter.communication.Message;

public class InputActionProcessor extends InputMessageProcessor {

	private Map<String,Integer> lastMsgNumber;
	
	public InputActionProcessor() {
		super();
		this.lastMsgNumber = new HashMap<String, Integer>();
	}

	@Override
	Message process(Message msg) {
		if(checkMsgNumber(msg.getAvatarId(),msg.getNumber())){
			synchronized (GameScreen.getInstance().getActions(msg.getAvatarId())) {
				GameScreen.getInstance().setActions(msg.getAvatarId(),(List<Integer>) msg.getData());
				lastMsgNumber.put(msg.getAvatarId(), msg.getNumber());
			}
		}
		return null;
	}
	
	private boolean checkMsgNumber(String avatarId, int number){
		if(!lastMsgNumber.containsKey(avatarId)){
			return true;
		}
		if(lastMsgNumber.get(avatarId) > number){
			return false;
		}
		return true;
	}

}
