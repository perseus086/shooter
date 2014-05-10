package com.da.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.da.shooter.communication.Sequencer;
import com.da.shooter.ui.utils.ScreenUtils;

public class SequencerScreen implements Screen {

	private MyShooterGame game;
	
	private Stage stage;
	
	public SequencerScreen(MyShooterGame game) {
		super();
		this.game = game;
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1568f, .1568f, .1568f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int arg0, int arg1) {

	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		stage = new Stage();
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		Label title = ScreenUtils.createLabel("Create sequencer", 0, 0);
		title.setPosition(Gdx.graphics.getWidth()/2f - title.getWidth()/2f, 6*Gdx.graphics.getHeight()/7f);
		
		Label numberLabel = ScreenUtils.createLabel("Number of players", 0, 0);
		title.setPosition(Gdx.graphics.getWidth()/2f - numberLabel.getWidth()/2f, 5*Gdx.graphics.getHeight()/7f);
		
		final TextField numberInput = ScreenUtils.createTextField("2", 0, 0);
		numberInput.setPosition(Gdx.graphics.getWidth()/2f - numberInput.getWidth()/2f, 4*Gdx.graphics.getHeight()/7f);
		
		Button startBtn = ScreenUtils.createButton("Start sequencer", 0, 0, 300,50);
		numberInput.setPosition(Gdx.graphics.getWidth()/2f - startBtn.getWidth()/2f, 3*Gdx.graphics.getHeight()/7f);
		
		stage.addActor(title);
		stage.addActor(numberLabel);
		stage.addActor(numberInput);
		stage.addActor(startBtn);
		
		startBtn.addListener(new ChangeListener() {
			private Sequencer sequencer;
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sequencer = new Sequencer(Integer.parseInt(numberInput.getText()));
				sequencer.start();

			}
		});
	}
}
