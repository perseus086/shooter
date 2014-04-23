package com.da.shooter.communication;

public class ActionMessage extends Message {

	private static final long serialVersionUID = 6209420854552302893L;

	private int action;
	
	public ActionMessage(int type, int action) {
		super(type);
		this.action = action;
		// TODO Auto-generated constructor stub
	}

}
