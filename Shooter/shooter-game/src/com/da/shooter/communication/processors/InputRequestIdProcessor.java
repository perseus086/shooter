package com.da.shooter.communication.processors;

import java.io.Serializable;

import com.da.shooter.communication.CommunicationManager;
import com.da.shooter.communication.Message;
import com.da.shooter.communication.utils.IdGenerator;

public class InputRequestIdProcessor extends InputMessageProcessor {

	@Override
	public Message process(Message msg) {
		Message reply = new Message(Message.Type.REQUEST_ID_RESPONSE);
		reply.setData((Serializable) CommunicationManager.getInstance().addPlayer(msg.getAddresses().get(0)));
		reply.setAddress(msg.getAddresses().get(0));
		reply.setPort(CommunicationManager.getInstance().getPlayerPort());
		return reply;
	}


}
