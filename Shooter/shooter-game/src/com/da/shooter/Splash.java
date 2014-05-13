package com.da.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Splash implements Screen {
	
	private MyShooterGame game;
	private Texture splashImage;
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	private final String gameName = "- DEMO -";
	
	public Splash(MyShooterGame game){
		this.game = game;
		System.out.println("Splash Screen");
		
		spriteBatch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("fonts/prehistorik38.fnt"));
		splashImage = new Texture(Gdx.files.internal("img/splash.png"));
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 0.5f, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
			spriteBatch.draw(splashImage, 
					Gdx.graphics.getWidth()/2.0f - splashImage.getWidth()/2,
					Gdx.graphics.getHeight()/2.0f - splashImage.getHeight()/2);
			
			font.draw(spriteBatch,
					gameName, 
					Gdx.graphics.getWidth()/2.0f - gameName.length()*10, 
					7 * Gdx.graphics.getHeight()/8.0f);

			String msg = "- PRESS ENTER OR TAP TO CONTINUE -"; 
			font.draw(spriteBatch,
					msg, 
					Gdx.graphics.getWidth()/2.0f - gameName.length()*47, 
					2 * Gdx.graphics.getHeight()/8.0f);
			
		spriteBatch.end();
		
		if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.ENTER))
		{
			game.setScreen(new MenuScreen(game));
		}
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
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
		font.dispose();
		splashImage.dispose();
		spriteBatch.dispose();
	}

}
