package com.da.shooter.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.da.shooter.elements.Avatar.Constants;
import com.da.shooter.utils.Box2DUtils;


public class Sword implements Element {

	private Body body;
	
	private Avatar owner;
	
	public Sword(Avatar owner) {
		super();
		this.owner = owner;
		
	}
	
	public Body createBody(){
		// Sword
		Vector2 position = owner.getBody().getPosition().cpy();
		position.add(0, 2);
		body = Box2DUtils.createPolygonBody(owner.getBody().getWorld(), Constants.BODY_SWORD, position, 0.5f, 5f, 1f, 1f, 4f, true, true, false);
		
		// Revolute joint
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.initialize(owner.getBody(), body, position);
		jointDef.collideConnected = false;
		jointDef.enableLimit = true;
		jointDef.lowerAngle = (float)(-Math.PI/2);
		jointDef.upperAngle = (float)(Math.PI/2);
		jointDef.localAnchorB.set(0, -3f);
		this.getBody().getWorld().createJoint(jointDef);
		body.setUserData(this);
		
		return body;
	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	public Avatar getOwner() {
		return owner;
	}


}
