package com.da.shooter.communication.processors;

import com.da.shooter.communication.Message;
import com.da.shooter.communication.utils.UdpMessageUtils;

public abstract class OutputMessageProcessor extends Thread {
	
	abstract Message process();
	
	private int status;
	protected int msgCounter;
	
	public OutputMessageProcessor() {
		super();
		this.status = Status.STOP;
		this.msgCounter = 0;
	}
	
	@Override
	public void start(){
		super.start();
		this.status = Status.RUNNING;
	}
	
	@Override
	public void run() {
		while (this.status == Status.RUNNING) {
			Message msg = process();
			if(msg != null){
				UdpMessageUtils.sendMessage(msg);
			}
		}
	}
	
	
	interface Status{
		int RUNNING = 0;
		int STOP = 1;
	}
}
