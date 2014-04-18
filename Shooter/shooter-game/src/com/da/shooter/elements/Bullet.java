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

	public void createObject(World world){
//		position = (direction)?position.add(1, 0):position.add(-1, 0);
		body = Box2DUtils.createPolygonBody(world,null, new Vector2(-100f,-100f), 0.3f, 0.1f, 0f, 0f, 0f,true,true,true);
		body.setUserData(this);
		
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

	public Avatar getOwner() {
		return owner;
	}

	public void destroy() {
		this.getBody().setTransform(-1000, -1000, 0);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

}
