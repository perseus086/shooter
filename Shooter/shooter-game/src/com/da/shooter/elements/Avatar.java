package com.da.shooter.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.da.shooter.communication.Player;
import com.da.shooter.utils.Box2DUtils;

/**
 * The Class Car.
 */
public class Avatar implements Element,Comparable<Avatar>{
	
	private static TextureAtlas textureAtlas;
	private static Map<String,Animation> animations;
	static{
		// Load sprites
		textureAtlas = new TextureAtlas("sprites/hero.txt");
		Map<String,List<TextureRegion>> regions = new HashMap<String,List<TextureRegion>>();
		for (AtlasRegion region : textureAtlas.getRegions()) {
			String name = region.name.substring(0, region.name.length()-1);
			if(!regions.containsKey(name)){
				regions.put(name, new ArrayList<TextureRegion>());
			}
			regions.get(name).add(region);
		}
		animations = new HashMap<String,Animation>();
		for (String regionName : regions.keySet()) {
			animations.put(regionName, new Animation(1/6.0f, (TextureRegion[]) regions.get(regionName).toArray(new TextureRegion[]{})));
		}
	}
	
	/** The body. */
	private Body body;
	
	private String spritePath;
	
	
	private float stateTime;
	
	private int status;
	
	private int version;
	
	private boolean grounded;

	
	private boolean direction; // true right; false left
	
	private Map<Integer,Body> bodies;
	
	private List<Integer> actions;
	
	private Player player;
	
	private int life;
	
	private BitmapFont lifeFont;
	
	public Avatar(Player player) {
		this.player = player;
		this.grounded = false;
		this.bodies = new HashMap<Integer, Body>();
		this.status = Constants.STATUS_NONE;
		this.version = 0;
		this.stateTime = 0;
		this.life = 100;
		
	}
	
	public void createObject(Vector2 initPosition, World world){
		
		// Body
		body = Box2DUtils.createPolygonBody(world, Constants.FIXTURE_BODY, initPosition, 2f, 3f, 5f, 5f, 0f,true,false,true);
//		MassData massData = new MassData();
//		massData.mass = 100;
//		body.setMassData(massData);
		
		// Foot
		Box2DUtils.addSensorFixture(body,Constants.FIXTURE_FOOT,1.9f,1f,new Vector2(0f, -2.2f),0);
		
		// Sword
		createSword();
		
		
		body.setUserData(this);
	}
	
	public void createSword(){
		Sword sword = new Sword(this);
		this.bodies.put(Constants.BODY_SWORD,sword.createBody());
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
			this.setGrounded(false);
		}
	}
	
	public void right(){
		this.direction = true;
		this.getBody().applyLinearImpulse(new Vector2(3000f,0), new Vector2(0, 0), true); //setLinearVelocity(10f, this.getBody().getLinearVelocity().y);
	}
	
	public void left(){
		this.direction = false;
		this.getBody().applyLinearImpulse(new Vector2(-3000f,0), new Vector2(0, 0), true);
	}
	
	public void strike(){
		this.status = Constants.STATUS_STRIKING;
		this.bodies.get(Constants.BODY_SWORD).applyTorque(((direction)?-1:1)*11000f, true);
	}
	


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
	public void render(float delta, Camera camera,SpriteBatch spriteBatch) {
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
		
		// Speed
		if(this.getBody().getLinearVelocity().len2() < 100){
			this.getBody().setLinearVelocity(0, 0);
		}
		
		// Sprites
		renderSprites(delta,camera,spriteBatch);
	}
	
	private void renderSprites(float delta, Camera camera,SpriteBatch spriteBatch){
		
		OrthographicCamera oCamera = (OrthographicCamera) camera;
		
		stateTime += delta;
//		System.out.println(stateTime);
		
		TextureRegion region= null;
		if(this.getBody(Avatar.Constants.BODY_SWORD).getAngularVelocity() > 0 && !direction){
			region=animations.get("hit left ").getKeyFrame(stateTime, true);
		}else if(this.getBody(Avatar.Constants.BODY_SWORD).getAngularVelocity() > 0 && direction){
			region=animations.get("hit right ").getKeyFrame(stateTime, true);
		}else if(!this.grounded && !direction){
			region=animations.get("jump left ").getKeyFrame(2, true);
		}else if(!this.grounded && direction){
			region=animations.get("jump right ").getKeyFrame(2, true);
		}else if(this.getBody().getLinearVelocity().x < -10){
			region=animations.get("run left ").getKeyFrame(stateTime, true);
		}else if(this.getBody().getLinearVelocity().x > 10){
			region=animations.get("run right ").getKeyFrame(stateTime, true);
		}else if(direction){
			region=animations.get("idle right ").getKeyFrame(stateTime, true);
		}else{
			region=animations.get("idle left ").getKeyFrame(stateTime, true);
		}
//		System.out.println(oCamera.zoom);
		// Camera projection
		Vector3 pos = camera.project(new Vector3(this.getBody().getPosition().x -3f , this.getBody().getPosition().y-3f, 0));
		float x = pos.x;
		float y = pos.y;
		float ratio = (float) (1.0/oCamera.zoom)*5;
		
		spriteBatch.draw(region,x,y,region.getRegionWidth()*ratio,region.getRegionHeight()*ratio);
		
		// Life
		getLifeFont().setScale((float) (1.0/oCamera.zoom));
		getLifeFont().draw(spriteBatch, this.life+"/100", x, y +60*ratio);
//		font.draw(texture, x, y);
		
	}
	
	public BitmapFont getLifeFont(){
		if(lifeFont == null){
			lifeFont = new BitmapFont(Gdx.files.internal("fonts/white30bold.fnt"),false);
		}
		return lifeFont;
	}
	
	public void addAction(int action){
		getActions().add(action);
		this.version++;
	}
	
	public void removeAction(String avatarId,int action){
		getActions().remove((Integer)action);
		this.version++;
	}
	
	public void executeAction(Action action){
		stateTime = 0;
		switch (action.getId()) {
			case ActionType.JUMP:
				jump();
			break;
			case ActionType.LEFT:
				left();
			break;
			case ActionType.RIGHT:
				right();
			break;
			case ActionType.ACTION:
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

	public Player getPlayer() {
		return player;
	}
	
	public void reduceLife(int lifeReduce) {
		this.life-=lifeReduce;
	}

	// Actions
	public interface ActionType{
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


