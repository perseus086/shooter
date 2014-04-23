package com.da.shooter.communication.processors;

import com.da.shooter.GameScreen;
import com.da.shooter.communication.Message;
import com.da.shooter.communication.Player;

public class InputRequestResponseProcessor extends InputMessageProcessor {

	@Override
	public Message process(Message msg) {
		Player player = (Player)msg.getData();
		GameScreen.getInstance().setPlayer(player);
		System.out.println("AvatarId: "+player.getAvatarId());
		return null;
	}

}
