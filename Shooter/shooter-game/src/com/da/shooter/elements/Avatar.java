package com.da.shooter.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.da.shooter.utils.Box2DUtils;

/**
 * The Class Car.
 */
public class Avatar implements Element,Comparable<Avatar>{
	
	/** The body. */
	private Body body;
	
	/** The sprite path. */
	private String spritePath; 
	
	private boolean grounded;

	private boolean currentPlayer;
	
	private boolean direction; // true right; false left
	
	public Avatar(boolean currentPlayer) {
		this.grounded = false;
		this.currentPlayer = currentPlayer;
	}
	
	public void createObject(Vector2 initPosition, World world){
		
		// Body
		body = Box2DUtils.createPolygonBody(world, AvatarConstants.FIXTURE_BODY, initPosition, 2f, 5f, 5f, 5f, 0f,true,false,true);
		
		// Foot
		Box2DUtils.addSensorFixture(body,AvatarConstants.FIXTURE_FOOT,1f,1f,new Vector2(0f, -4.1f));
		
		
		body.setUserData(this);
		
		// Image
//		Sprite sprite = new Sprite(new Texture(this.spritePath));
//		sprite.setSize(15f, 15f);
//		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
//		body.setUserData(sprite);
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
	
	public void shoot(){
		Bullet bullet = new Bullet(this);
		bullet.createObject(this.getBody().getWorld(),this.direction);
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
	
}

interface AvatarConstants{
	int FIXTURE_FOOT = 0;
	int FIXTURE_BODY = 1;
}
