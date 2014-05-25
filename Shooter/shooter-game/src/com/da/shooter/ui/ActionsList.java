package com.da.shooter.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.da.shooter.elements.Action;
import com.da.shooter.ui.utils.ScreenUtils;

public class ActionsList {

	private List<Action> actions;
	private int limit;
	private List<Label> labels;

	public ActionsList(int limit, float[] offset, Stage stage) {
		this.limit = limit;
		this.actions = new ArrayList<Action>();
		this.labels = new ArrayList<Label>();
		
		for (int i = 0; i < limit; i++) {
			Label label = ScreenUtils.createLabel("", offset[0], offset[1]+i*30);
			this.labels.add(label);
			stage.addActor(label);
		}
	}
	
	public void addAction(Action action){
		synchronized (actions) {
			actions.add(action);
		}
	}
	
	public void update(){
		for (int i = 0; i < limit; i++) {
			if(actions.size() > i){
				labels.get(i).setText("[P"+actions.get(i).getAvatarId()+"]"+actions.get(i).getUsername()+": "+ actions.get(i).getName());
			}else{
				labels.get(i).setText("");
			}
		}
	}

	public Action peek(){
		synchronized (actions) {
			if(actions.size() >0){
				return actions.get(0);
			}
			return null;
		}
	}
	
	public Action pop() {
		synchronized (actions) {
			if(actions.size() >0){
				return actions.remove(0);
			}
			return null;
		}
	}
	
}
