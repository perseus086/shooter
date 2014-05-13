package com.da.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen implements Screen{

	private MyShooterGame game;
	
	private Stage stage;
	private TextButton sequencerButton;
	private TextButton joinButton;
	private TextButton configurationButton;
	private BitmapFont font60;
	private BitmapFont white36nonoFont;
	private BitmapFont font48orange;
	private TextureAtlas atlas;
	private Skin skin;
	private Label label;
	private String title;
	
	
	public MenuScreen(MyShooterGame game){
		this.game = game;
		this.title = "Main Menu";
		System.out.println("Menu Screen");
	}
	
	@Override
	public void show() {
		//Creates an stage that contains all the GUI elements
		stage = new Stage();
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		//Title label
		font48orange = new BitmapFont(Gdx.files.internal("fonts/prehistorik100orange.fnt"));		
		label = new Label(this.title, new LabelStyle(font48orange, Color.WHITE));
		label.setPosition(Gdx.graphics.getWidth()/2.0f - label.getWidth()/2, 5* Gdx.graphics.getHeight()/7.0f);
				
		//Atlas of the GUI
		atlas = new TextureAtlas("gui/gui.pack");
		skin = new Skin(atlas);
		
		//Buttons
		font60 = new BitmapFont(Gdx.files.internal("fonts/prehistorik60black.fnt"),false);
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button1");
		buttonStyle.over = skin.getDrawable("button1-over");
		buttonStyle.down = skin.getDrawable("button1-down");
		buttonStyle.font = font60;
		
		sequencerButton = new TextButton("Start sequencer", buttonStyle);
		sequencerButton.setHeight(100f);
		sequencerButton.setWidth(500f);
		sequencerButton.setPosition(Gdx.graphics.getWidth()/2.0f - sequencerButton.getWidth()/2, 4*Gdx.graphics.getHeight()/7.0f);
		
		joinButton = new TextButton("Join Game", buttonStyle);
		joinButton.setHeight(100f);
		joinButton.setWidth(500f);
		joinButton.setPosition(Gdx.graphics.getWidth()/2.0f - sequencerButton.getWidth()/2, 3*Gdx.graphics.getHeight()/7.0f);
		
		stage.addActor(sequencerButton);
		stage.addActor(joinButton);
		stage.addActor(label);
		
		sequencerButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new SequencerScreen(game));
			}
		});
		
		
		joinButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new JoinGameScreen(game));
				
			}
		});
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1568f, 0.1568f, 0.1568f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {

	}

	

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		font60.dispose();
		white36nonoFont.dispose();
		font48orange.dispose();
		
	}

}
