package com.da.shooter.communication.processors;


import java.util.Map;

import com.da.shooter.GameScreen;
import com.da.shooter.communication.Message;
import com.da.shooter.communication.Player;

public class InputPositionsProcessor extends InputMessageProcessor {

	@Override
	Message process(Message msg) {
		Map<String,Player> positions = (Map<String, Player>) msg.getData();
		GameScreen.getInstance().setOwnerPositions(positions);
		return null;
	}

}
