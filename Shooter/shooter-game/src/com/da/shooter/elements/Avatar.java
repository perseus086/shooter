package com.da.shooter.elements;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.da.shooter.utils.Box2DUtils;

/**
 * The Class Car.
 */
public class Avatar implements Element,Comparable<Avatar>{
	
	/** The body. */
	private Body body;
	
	/** The sprite path. */
	private String spritePath; 
	
	private int status;
	
	private boolean grounded;

	private boolean currentPlayer;
	
	private boolean direction; // true right; false left
	
	private Map<Integer,Body> bodies;
	
	public Avatar(boolean currentPlayer) {
		this.grounded = false;
		this.currentPlayer = currentPlayer;
		this.bodies = new HashMap<Integer, Body>();
		this.status = AvatarConstants.STATUS_NONE;
	}
	
	public void createObject(Vector2 initPosition, World world){
		
		// Body
		body = Box2DUtils.createPolygonBody(world, AvatarConstants.FIXTURE_BODY, initPosition, 2f, 5f, 5f, 5f, 0f,true,false,true);

		// Foot
		Box2DUtils.addSensorFixture(body,AvatarConstants.FIXTURE_FOOT,1.9f,1f,new Vector2(0f, -4.2f),0);
		
		// Sword
		createSword();
		
		
		body.setUserData(this);
		
		// Image
//		Sprite sprite = new Sprite(new Texture(this.spritePath));
//		sprite.setSize(15f, 15f);
//		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
//		body.setUserData(sprite);
	}
	
	public void createSword(){
		// Sword
		Vector2 position = this.getBody().getPosition().cpy();
		position.add(0, 2);
		Body swordBody = Box2DUtils.createPolygonBody(this.getBody().getWorld(), AvatarConstants.BODY_SWORD, position, 0.5f, 5f, 1f, 1f, 4f, true, false, false);
		MassData massData = new MassData();
		massData.mass = 1000;
//		swordBody.setMassData(massData);
		this.bodies.put(AvatarConstants.BODY_SWORD,swordBody);

		// Revolute joint
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.initialize(this.getBody(), swordBody, position);
		jointDef.collideConnected = false;
		jointDef.enableLimit = true;
		jointDef.lowerAngle = (float)(-Math.PI/2.0);
		jointDef.upperAngle = (float)(Math.PI/2.0);
		// jointDef.bodyA = this.getBody();
//		jointDef.bodyB = swordBody;
//		jointDef.collideConnected = true;
//		jointDef.localAnchorA.set(position);
		jointDef.localAnchorB.set(0, -3f);
		RevoluteJoint joint = (RevoluteJoint) this.getBody().getWorld().createJoint(jointDef);
		
	}

	/* (non-Javadoc)
	 * @see cargame.gamelogic.elements.Element#getBody()
	 */
	public Body getBody(){
		return body;
	}
	
	public void jump(){
		if(this.grounded){
			this.getBody().applyForceToCenter(new Vector2(0f,200000f), true);
		}
	}
	
	public void right(){
		this.direction = true;
//		if(this.grounded || this.getBody().getLinearVelocity().x < 0){
			this.getBody().applyLinearImpulse(new Vector2(300f,0), new Vector2(0, 0), true); //setLinearVelocity(10f, this.getBody().getLinearVelocity().y);
//		}
	}
	
	public void left(){
		this.direction = false;
//		if(this.grounded || this.getBody().getLinearVelocity().x >  0){
			this.getBody().applyLinearImpulse(new Vector2(-300f,0), new Vector2(0, 0), true);
//		}
	}
	
//	public void shoot(){
//		Bullet bullet = null;
//		if(this.bullets.size() > 100){
//			bullet = this.bullets.remove(0);
//		}else{
//			bullet = new Bullet(this);
//			bullet.createObject(this.getBody().getWorld());
//		}
//		
//		Vector2 position = this.getBody().getPosition().cpy();
//		position = (direction)?position.add(1, 0):position.add(-1, 0);
//		bullet.getBody().setTransform(position.x, position.y, 0);
//		bullet.getBody().applyLinearImpulse(new Vector2((direction)?3000f:-3000f,0), new Vector2(0, 0), true);
//		this.bullets.add(bullet);
//	}
	
	public void strike(){
		this.status = AvatarConstants.STATUS_STRIKING;
		this.bodies.get(AvatarConstants.BODY_SWORD).applyTorque(((direction)?-1:1)*90000f, true);
//		this.fixtures.get(AvatarConstants.FIXTURE_SWORD).
	}
	
	
	
//	public void setPosition(MovingPosition movingPosition){
//		if(movingPosition == null) return;
//		this.getBody().setTransform(movingPosition.xPos, movingPosition.yPos, movingPosition.angle);
//		this.getBody().setLinearVelocity(new Vector2(movingPosition.linearSpeedX, movingPosition.linearSpeedY));
//		this.getBody().setAngularVelocity(movingPosition.angularSpeed);
//	}

//	public void updatePosition(){
//		if(player.movingPosition == null){
//			player.movingPosition = new MovingPosition();
//		}
//		
//		player.movingPosition.xPos = getBody().getPosition().x;
//		player.movingPosition.yPos = getBody().getPosition().y;
//		player.movingPosition.angle = getBody().getAngle();
//		player.movingPosition.linearSpeedX = getBody().getLinearVelocity().x;
//		player.movingPosition.linearSpeedY = getBody().getLinearVelocity().y;
//		player.movingPosition.angularSpeed = getBody().getAngularVelocity();
//	}

	public boolean isGrounded() {
		return grounded;
	}

	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}

	@Override
	public int compareTo(Avatar o) {
//		if(o.getPlayer().getLaps() == this.getPlayer().getLaps() && o.getPlayer().getBestLapTime() == this.getPlayer().getBestLapTime()){
			return 0;
//		}
//		if(o.getPlayer().getLaps() > this.getPlayer().getLaps()){
//			return 1;
//		}else if(o.getPlayer().getBestLapTime() < this.getPlayer().getBestLapTime()){
//			return 1;
//		}else{
//			return -1;
//		}
	}

	/**
	 * Gets the sprite path.
	 *
	 * @return the sprite path
	 */
	public String getSpritePath() {
		return spritePath;
	}

	public boolean isCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public void render() {
		Body swordBody = this.bodies.get(AvatarConstants.BODY_SWORD);
		if(Math.abs(swordBody.getAngle()) >= Math.PI/2.0){
			this.status = AvatarConstants.STATUS_NONE;
		}
		
		switch (this.status) {
			case AvatarConstants.STATUS_NONE:
				swordBody.setAngularVelocity(0);
				swordBody.setTransform(swordBody.getPosition(), ((this.direction)?-1:1)*(float)(Math.PI/8.0));
			break;
			default:
			break;
		}
		
	}
	
}

interface AvatarConstants{
	int STATUS_NONE = 0;
	int STATUS_STRIKING = 1;
	
	int FIXTURE_FOOT = 0;
	int FIXTURE_BODY = 1;
	
	int BODY_SWORD = 2;
}
