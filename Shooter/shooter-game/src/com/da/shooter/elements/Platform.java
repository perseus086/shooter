package com.da.shooter.elements;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class Platform implements Element{

	private Body body;
	
	public Platform(Body body) {
		super();
		this.body = body;
		this.body.setUserData(this);
	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public void render(float delta, Camera camera,SpriteBatch spriteBatch) {
	}
	
}
