package com.perseus.super_game.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Prehistorik implements Screen{

	private TextureAtlas atlas;
	private SpriteBatch batch;
	
	private TextureRegion[] hitLeft;
	private TextureRegion[] hitRight;
	private TextureRegion[] jumpLeft;
	private TextureRegion[] jumpRight;
	private TextureRegion[] idleLeft;
	private TextureRegion[] idleRight;
	private TextureRegion[] runLeft;
	private TextureRegion[] runRight;
	private Animation hitLeftAnimation;
	private Animation hitRightAnimation;
	private Animation jumpLeftAnimation;
	private Animation jumpRightAnimation;
	private Animation idleLeftAnimation;
	private Animation idleRightAnimation;
	private Animation runLeftAnimation;
	private Animation runRightAnimation;
	
	private float stateTime;
	
	@SuppressWarnings("unused")
	private Game game;
	
	public Prehistorik(Game game){
		this.game = game;
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		stateTime += delta;

		batch.begin();
		
			batch.draw(hitLeftAnimation.getKeyFrame(stateTime, true), 10, 300);
			batch.draw(hitRightAnimation.getKeyFrame(stateTime, true), 60, 300);
			batch.draw(jumpLeftAnimation.getKeyFrame(stateTime, true), 110, 300);
			batch.draw(jumpRightAnimation.getKeyFrame(stateTime, true), 160, 300);
			batch.draw(runLeftAnimation.getKeyFrame(stateTime, true), 210, 300);
			batch.draw(runRightAnimation.getKeyFrame(stateTime, true), 260, 300);
			batch.draw(idleLeftAnimation.getKeyFrame(stateTime, true), 310, 300);
			batch.draw(idleRightAnimation.getKeyFrame(stateTime, true), 360, 300);
		
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		
		atlas = new TextureAtlas("sprites/hero.txt");
		hitLeft = new TextureRegion[6];
		hitLeft[0] = atlas.findRegion("hit left 1");
		hitLeft[1] = atlas.findRegion("hit left 2");
		hitLeft[2] = atlas.findRegion("hit left 3");
		hitLeft[3] = atlas.findRegion("hit left 4");
		hitLeft[4] = atlas.findRegion("hit left 5");
		hitLeft[5] = atlas.findRegion("hit left 6");
		
		hitRight = new TextureRegion[6];
		hitRight[0] = atlas.findRegion("hit right 1");
		hitRight[1] = atlas.findRegion("hit right 2");
		hitRight[2] = atlas.findRegion("hit right 3");
		hitRight[3] = atlas.findRegion("hit right 4");
		hitRight[4] = atlas.findRegion("hit right 5");
		hitRight[5] = atlas.findRegion("hit right 6");
		
		jumpLeft = new TextureRegion[5];
		jumpLeft[0] = atlas.findRegion("jump left 1");
		jumpLeft[1] = atlas.findRegion("jump left 2");
		jumpLeft[2] = atlas.findRegion("jump left 3");
		jumpLeft[3] = atlas.findRegion("jump left 4");
		jumpLeft[4] = atlas.findRegion("jump left 5");
		
		jumpRight = new TextureRegion[5];
		jumpRight[0] = atlas.findRegion("jump right 1");
		jumpRight[1] = atlas.findRegion("jump right 2");
		jumpRight[2] = atlas.findRegion("jump right 3");
		jumpRight[3] = atlas.findRegion("jump right 4");
		jumpRight[4] = atlas.findRegion("jump right 5");
		
		runLeft = new TextureRegion[6];
		runLeft[0] = atlas.findRegion("run left 1");
		runLeft[1] = atlas.findRegion("run left 2");
		runLeft[2] = atlas.findRegion("run left 3");
		runLeft[3] = atlas.findRegion("run left 4");
		runLeft[4] = atlas.findRegion("run left 5");
		runLeft[5] = atlas.findRegion("run left 6");
		
		runRight = new TextureRegion[6];
		runRight[0] = atlas.findRegion("run right 1");
		runRight[1] = atlas.findRegion("run right 2");
		runRight[2] = atlas.findRegion("run right 3");
		runRight[3] = atlas.findRegion("run right 4");
		runRight[4] = atlas.findRegion("run right 5");
		runRight[5] = atlas.findRegion("run right 6");
		
		idleLeft = new TextureRegion[3];
		idleLeft[0] = atlas.findRegion("idle left 1");
		idleLeft[1] = atlas.findRegion("idle left 2");
		idleLeft[2] = atlas.findRegion("idle left 3");
		
		idleRight = new TextureRegion[3];
		idleRight[0] = atlas.findRegion("idle right 1");
		idleRight[1] = atlas.findRegion("idle right 2");
		idleRight[2] = atlas.findRegion("idle right 3");
		
		hitLeftAnimation = new Animation(1/6.0f, hitLeft);
		hitRightAnimation = new Animation(1/6.0f, hitRight);
		jumpLeftAnimation = new Animation(1/2.0f, jumpLeft);
		jumpRightAnimation = new Animation(1/2.0f, jumpRight);
		runLeftAnimation = new Animation(1/6.0f, runLeft);
		runRightAnimation = new Animation(1/6.0f, runRight);
		idleLeftAnimation = new Animation(1/2.0f, idleLeft);
		idleRightAnimation = new Animation(1/2.0f, idleRight);
		
		
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
		batch.dispose();		
	}

}
