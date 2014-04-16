package com.da.shooter.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.da.shooter.utils.Box2DUtils;

public class Bullet implements Element {

	private Body body;
	private Avatar owner;
	
	public Bullet(Avatar owner) {
		super();
		this.owner = owner;
	}

	public void createObject(World world,boolean direction){
		body = Box2DUtils.createPolygonBody(world,null, owner.getBody().getPosition(), 0.1f, 0.1f, 0f, 0f, 0f,true,true,true);
		body.setUserData(this);
		this.getBody().applyLinearImpulse(new Vector2((direction)?600f:-300f,0), new Vector2(0, 0), true);
		// Image
//		Sprite sprite = new Sprite(new Texture(this.spritePath));
//		sprite.setSize(15f, 15f);
//		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
//		body.setUserData(sprite);
	}
	
	@Override
	public Body getBody() {
		return body;
	}

}
