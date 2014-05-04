package com.perseus.super_game.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public class Splash implements Screen{
	private Game game;
	
	private Stage stage;
	private Skin skin;
	private TextButton textButton;
	private Game mygame;
	private BitmapFont font;
	private TextureAtlas atlas;
	private TextureAtlas atlas2;
	private SpriteBatch batch;
	private TextureRegion test;
	private TextField textField;
	
	private Texture texture;
	private TextureRegion[][] sprites;
	private TextureRegion[] sprites2;
	private TextureRegion[] spritee;
	private Animation animation;
	float stateTime;
	
	
	public Splash(Game game)
	{
		this.game = game;
	}
	
	
	@Override
	public void show() {
		batch = new SpriteBatch();
		stage = new Stage();
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("buttons/bot.pack");
		skin = new Skin(atlas);
		font = new BitmapFont(Gdx.files.internal("fonts/arialfnt.fnt"),false);
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button.up");
		buttonStyle.over = skin.getDrawable("button.over");
		buttonStyle.down = skin.getDrawable("button.down");
		buttonStyle.font = font;
		textButton = new TextButton("Click here", buttonStyle);
		textButton.setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f);
		textButton.setHeight(100f);
		textButton.setWidth(300f);
		
		
		
		atlas2 = new TextureAtlas("sprites/hero.txt");
		spritee = new TextureRegion[6];
		spritee[0] = atlas2.findRegion("hit left 1");
		spritee[1] = atlas2.findRegion("hit left 2");
		spritee[2] = atlas2.findRegion("hit left 3");
		spritee[3] = atlas2.findRegion("hit left 4");
		spritee[4] = atlas2.findRegion("hit left 5");
		spritee[5] = atlas2.findRegion("hit left 6");
		
		TextFieldStyle tfs = new TextFieldStyle();
		tfs.font = font;
		tfs.cursor = skin.getDrawable("button.down");
		tfs.background = skin.getDrawable("button.up");
		tfs.fontColor = Color.WHITE;
		textField = new TextField("bla", tfs);;
		textField.setWidth(600);
		textField.setPosition(200f, 500f);
		
		
		
		test = atlas.findRegion("button.up"); //Testing the atlas
		
		stage.addActor(textButton);
		stage.addActor(textField);
		
		textButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println(textField.getText());
				game.setScreen(new SuperGame(game));
				
			}
		});
		
		
		//////PRUEBAS CON SPRITE
		Texture texture = new Texture(Gdx.files.internal("img/halo-left.png"));
		sprites = TextureRegion.split(texture, 64, 64);
		
		///////PRUEBAS CON ANIMATION
		sprites2 = TextureRegion.split(texture, 64, 64)[0];
		animation = new Animation(1/2.0f, spritee);
		
		
		
		
	
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw(); //shit
		
		
		stateTime += delta;

		batch.begin();
		batch.draw(test, 300, 300); //It works
		batch.draw(sprites[0][0], 400, 400);
		batch.draw(spritee[0], 20, 20);
//		batch.draw(spritee[1], 40, 20);
//		batch.draw(spritee[2], 60, 20);
//		batch.draw(sprites[1][3], 20, 40);
//		batch.draw(sprites[2][1], 600, 600);
		batch.draw(animation.getKeyFrame(stateTime, true), 500, 600);
		font.draw(batch, "BLA BLA!", 50, 500); //It works
        batch.end();
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
		batch.dispose();
		
		
		
	}

}
