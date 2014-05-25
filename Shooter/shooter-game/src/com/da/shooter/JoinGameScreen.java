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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.da.shooter.ui.utils.ScreenUtils;

public class JoinGameScreen implements Screen{

	private MyShooterGame game;
	
	private Stage stage;
	private TextButton joinButton;
	private TextButton menuButton;
	private TextField inputAddress;
	private BitmapFont font60;
	private BitmapFont white36nonoFont;
	private BitmapFont font100orange;
	private BitmapFont white30boldFont;
	private TextureAtlas atlas;
	private Skin skin;
	private Label titleLabel;
	private Label label1;
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
				font100orange = new BitmapFont(Gdx.files.internal("fonts/prehistorik100orange.fnt"));		
				titleLabel = new Label(this.title, new LabelStyle(font100orange, Color.WHITE));
				titleLabel.setPosition(Gdx.graphics.getWidth()/2.0f - titleLabel.getWidth()/2, 7.1f* Gdx.graphics.getHeight()/9.0f);
				
				//Textbox label
				white30boldFont = new BitmapFont(Gdx.files.internal("fonts/white30bold.fnt"));		
				label1 = new Label("IP adress of game creator:", new LabelStyle(white30boldFont, Color.WHITE));
				label1.setPosition(Gdx.graphics.getWidth()/2.0f-label1.getWidth()/2, 6* Gdx.graphics.getHeight()/9.0f);
				
				// Username
				Label usernameLabel = ScreenUtils.createLabel("Username:", 0, 0);
				usernameLabel.setPosition(Gdx.graphics.getWidth()/2f - usernameLabel.getWidth()/2f, 4*Gdx.graphics.getHeight()/9.0f);
				
				final TextField usernameInput = ScreenUtils.createTextField("", 0, 0);
				usernameInput.setPosition(Gdx.graphics.getWidth()/2f - usernameInput.getWidth()/2f, 3*Gdx.graphics.getHeight()/9.0f);
				
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
				
				joinButton = new TextButton("Join Game", buttonStyle);
				joinButton.setHeight(100f);
				joinButton.setWidth(500f);
				joinButton.setPosition(Gdx.graphics.getWidth()/2.0f - joinButton.getWidth()/2, 1.5f*Gdx.graphics.getHeight()/9.0f);
				
				menuButton = new TextButton("Main Menu", buttonStyle);
				menuButton.setHeight(100f);
				menuButton.setWidth(500f);
				menuButton.setPosition(Gdx.graphics.getWidth()/2.0f - joinButton.getWidth()/2, 0f*Gdx.graphics.getHeight()/9.0f);
				
				//TextField
				white36nonoFont = new BitmapFont(Gdx.files.internal("fonts/white36mono.fnt"));
				TextFieldStyle tfs = new TextFieldStyle();
				tfs.font = white36nonoFont;
				tfs.cursor = skin.getDrawable("cursor");
				tfs.background = skin.getDrawable("textField");
				tfs.fontColor = Color.WHITE;
				inputAddress = new TextField("localhost", tfs);
				inputAddress.setHeight(50);
				inputAddress.setWidth(300);
				inputAddress.setPosition(Gdx.graphics.getWidth()/2.0f - inputAddress.getWidth()/2, 5*Gdx.graphics.getHeight()/9.0f);

				
				//add actors
				stage.addActor(joinButton);
				stage.addActor(menuButton);
				stage.addActor(inputAddress);
				stage.addActor(titleLabel);
				stage.addActor(usernameLabel);
				stage.addActor(usernameInput);
				stage.addActor(titleLabel);
				stage.addActor(label1);
								

				joinButton.addListener(new ChangeListener() {
					
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						System.out.println(inputAddress.getText());
						
						GameScreen.createInstance(game,false,inputAddress.getText(),usernameInput.getText());
						game.setScreen(GameScreen.getInstance());
//						game.initInputListeners();
						
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
		font60.dispose();
		white36nonoFont.dispose();
		font100orange.dispose();
		
	}

}
