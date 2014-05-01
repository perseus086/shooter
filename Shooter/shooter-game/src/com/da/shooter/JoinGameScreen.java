package com.da.shooter;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class JoinGameScreen implements Screen{

	private MyShooterGame game;
	
	private Stage stage;
	private TextButton testButton;
	private TextButton menuButton;
	private TextField textField;
	private BitmapFont haloFont;
	private BitmapFont white36nonoFont;
	private BitmapFont halo48orangeFont;
	private BitmapFont white30boldFont;
	private TextureAtlas atlas;
	private Skin skin;
	private Label titleLabel;
	private Label label1;
	private Label message;
	private String title;
	
	public JoinGameScreen(MyShooterGame game){
		this.game = game;
		this.title = "Join Game";
		System.out.println("Join Game Screen");
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1568f, .1568f, .1568f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		//Creates an stage that contains all the GUI elements
				stage = new Stage();
				stage.clear();
				Gdx.input.setInputProcessor(stage);
				
				//Title label
				halo48orangeFont = new BitmapFont(Gdx.files.internal("fonts/halo48orange.fnt"));		
				titleLabel = new Label(this.title, new LabelStyle(halo48orangeFont, Color.WHITE));
				titleLabel.setPosition(Gdx.graphics.getWidth()/2.0f - titleLabel.getWidth()/2, 6* Gdx.graphics.getHeight()/7.0f);
				//Textbox label
				white30boldFont = new BitmapFont(Gdx.files.internal("fonts/white30bold.fnt"));		
				label1 = new Label("IP adress of game creator:", new LabelStyle(white30boldFont, Color.WHITE));
				label1.setPosition(Gdx.graphics.getWidth()/2.0f-label1.getWidth()/2, 4* Gdx.graphics.getHeight()/7.0f+60);
				
				message = new Label("E", new LabelStyle(white30boldFont, Color.WHITE));
				message.setPosition(Gdx.graphics.getWidth()/2.0f-message.getWidth()/2, 1* Gdx.graphics.getHeight()/7.0f);
				message.setText("");
				
				//Atlas of the GUI
				atlas = new TextureAtlas("gui/gui.pack");
				skin = new Skin(atlas);
				
				//Buttons
				haloFont = new BitmapFont(Gdx.files.internal("fonts/halo.fnt"),false);
				TextButtonStyle buttonStyle = new TextButtonStyle();
				buttonStyle.up = skin.getDrawable("button1");
				buttonStyle.over = skin.getDrawable("button1-over");
				buttonStyle.down = skin.getDrawable("button1-down");
				buttonStyle.font = haloFont;
				
				testButton = new TextButton("Join Game", buttonStyle);
				testButton.setHeight(50f);
				testButton.setWidth(350f);
				testButton.setPosition(Gdx.graphics.getWidth()/2.0f - testButton.getWidth()/2, 3*Gdx.graphics.getHeight()/7.0f);
				
				menuButton = new TextButton("Main Menu", buttonStyle);
				menuButton.setHeight(50f);
				menuButton.setWidth(350f);
				menuButton.setPosition(Gdx.graphics.getWidth()/2.0f - testButton.getWidth()/2, 2*Gdx.graphics.getHeight()/7.0f);
				
				//TextField
				white36nonoFont = new BitmapFont(Gdx.files.internal("fonts/white36mono.fnt"));
				TextFieldStyle tfs = new TextFieldStyle();
				tfs.font = white36nonoFont;
				tfs.cursor = skin.getDrawable("cursor");
				tfs.background = skin.getDrawable("textField");
				tfs.fontColor = Color.WHITE;
				textField = new TextField("localhost", tfs);
				textField.setHeight(50);
				textField.setWidth(300);
				textField.setPosition(Gdx.graphics.getWidth()/2.0f - textField.getWidth()/2, 4*Gdx.graphics.getHeight()/7.0f);

				
				//add actors
				stage.addActor(testButton);
				stage.addActor(menuButton);
				stage.addActor(textField);
				stage.addActor(titleLabel);
				stage.addActor(label1);
				stage.addActor(message);
				

				testButton.addListener(new ChangeListener() {
					
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						System.out.println(textField.getText());
						
						GameScreen.createInstance(game,false,textField.getText());
						game.setScreen(GameScreen.getInstance());
						
						String msg = "";
						message.setPosition(Gdx.graphics.getWidth()/2.0f - message.getWidth()*msg.length()/2.0f, 1* Gdx.graphics.getHeight()/7.0f);
						message.setText(msg);
					}
				});
				
				menuButton.addListener(new ChangeListener() {
					
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						game.setScreen(new MenuScreen(game));
					}
				});
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		haloFont.dispose();
		white36nonoFont.dispose();
		halo48orangeFont.dispose();
		
	}

}
