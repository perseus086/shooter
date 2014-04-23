package com.da.shooter.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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
	
	private int version;
	
	private boolean grounded;

	
	private boolean direction; // true right; false left
	
	private Map<Integer,Body> bodies;
	
	private List<Integer> actions;
	
	public Avatar() {
		this.grounded = false;
		this.bodies = new HashMap<Integer, Body>();
		this.status = Constants.STATUS_NONE;
		this.version = 0;
	}
	
	public void createObject(Vector2 initPosition, World world){
		
		// Body
		body = Box2DUtils.createPolygonBody(world, Constants.FIXTURE_BODY, initPosition, 2f, 5f, 5f, 5f, 0f,true,false,true);
//		MassData massData = new MassData();
//		massData.mass = 100;
//		body.setMassData(massData);
		
		// Foot
		Box2DUtils.addSensorFixture(body,Constants.FIXTURE_FOOT,1.9f,1f,new Vector2(0f, -4.2f),0);
		
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
		Body swordBody = Box2DUtils.createPolygonBody(this.getBody().getWorld(), Constants.BODY_SWORD, position, 0.5f, 5f, 1f, 1f, 4f, true, true, false);
		
		this.bodies.put(Constants.BODY_SWORD,swordBody);

		// Revolute joint
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.initialize(this.getBody(), swordBody, position);
		jointDef.collideConnected = false;
		jointDef.enableLimit = true;
		jointDef.lowerAngle = (float)(-Math.PI/2);
		jointDef.upperAngle = (float)(Math.PI/2);
		// jointDef.bodyA = this.getBody();
//		jointDef.bodyB = swordBody;
//		jointDef.collideConnected = true;
//		jointDef.localAnchorA.set(position);
		jointDef.localAnchorB.set(0, -3f);
		this.getBody().getWorld().createJoint(jointDef);
		
	}

	/* (non-Javadoc)
	 * @see cargame.gamelogic.elements.Element#getBody()
	 */
	public Body getBody(){
		return body;
	}
	
	public void jump(){
		if(this.grounded){
			this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x, 30);
//			this.getBody().applyForceToCenter(new Vector2(0f,400000f), true);
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
		this.status = Constants.STATUS_STRIKING;
		this.bodies.get(Constants.BODY_SWORD).applyTorque(((direction)?-1:1)*110000f, true);
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

	public List<Integer> getActions() {
		if(actions == null){
			actions = new ArrayList<Integer>();
		}
		return actions;
	}

	public void setActions(List<Integer> actions) {
		this.actions = actions;
	}

	@Override
	public void render() {
		Body swordBody = this.bodies.get(Constants.BODY_SWORD);
		if(Math.abs(swordBody.getAngle()) >= Math.PI/2 || 
				(direction && swordBody.getAngularVelocity() > 0) || 
				(!direction && swordBody.getAngularVelocity() < 0)){
			this.status = Constants.STATUS_NONE;
		}
		
		switch (this.status) {
			case Constants.STATUS_NONE:
				swordBody.setAngularVelocity(0);
				swordBody.setTransform(swordBody.getPosition(),0);// ((this.direction)?-1:1)*(float)(Math.PI/8.0));
			break;
			default:
			break;
		}
		
	}
	
	public void addAction(int action){
		getActions().add(action);
		this.version++;
	}
	
	public void removeAction(String avatarId,int action){
		getActions().remove((Integer)action);
		this.version++;
	}
	
	public void executeAction(int gameAction){
		switch (gameAction) {
			case Type.JUMP:
				jump();
			break;
			case Type.LEFT:
				left();
			break;
			case Type.RIGHT:
				right();
			break;
			case Type.ACTION:
				strike();
			break;
			default:
			break;
		}
	}
	
	public int getVersion() {
		return version;
	}
	public Body getBody(int type){
		return (this.bodies.containsKey(type))?this.bodies.get(type):null;
	}

	// Actions
	public interface Type{
		int LEFT = 0;
		int RIGHT = 1;
		int JUMP = 2;
		int ACTION = 3;
	}
	
	public interface Constants{
		int STATUS_NONE = 0;
		int STATUS_STRIKING = 1;
		
		int FIXTURE_FOOT = 0;
		int FIXTURE_BODY = 1;
		
		int BODY_SWORD = 2;
	}
	
}


