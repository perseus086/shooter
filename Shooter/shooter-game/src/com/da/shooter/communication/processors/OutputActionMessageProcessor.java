package com.da.shooter.communication.processors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.da.shooter.GameScreen;
import com.da.shooter.communication.CommunicationManager;
import com.da.shooter.communication.Message;

public class OutputActionMessageProcessor extends OutputMessageProcessor {

	
	private int lastVersion;

	@Override
	public Message process() {
		if(GameScreen.getInstance() == null) return null;
		ArrayList<Integer> actions = (ArrayList<Integer>) GameScreen.getInstance().getActions(GameScreen.getInstance().getAvatarId());
		if(actions == null) return null;
		if(lastVersion >= GameScreen.getInstance().getAvatarVersion(GameScreen.getInstance().getAvatarId())) return null;
		synchronized (actions) {
//			ArrayList<Integer> actions = (ArrayList<Integer>) GameScreen.getInstance().getActions(GameScreen.getInstance().getAvatarId());
//			if(actions.size() == 0) return null;
			actions = (ArrayList<Integer>) actions.clone(); 
			Message msg = new Message(Message.Type.ACTION);
			msg.setAvatarId(GameScreen.getInstance().getAvatarId());
			msg.setAddress(CommunicationManager.getInstance().getCreatorAddress());
			msg.setPort(CommunicationManager.getInstance().getCreatorPort());
			msg.setData((Serializable) actions);
			msg.setNumber(this.msgCounter++);
//			System.out.println("Version:"+GameScreen.getInstance().getAvatarVersion(GameScreen.getInstance().getAvatarId())+" Actions: "+actions.toString());
			this.lastVersion = GameScreen.getInstance().getAvatarVersion(GameScreen.getInstance().getAvatarId());
			return msg;
		}
	}

}
