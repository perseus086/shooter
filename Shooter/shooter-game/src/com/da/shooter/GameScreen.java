package com.da.shooter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.da.shooter.communication.CommunicationManager;
import com.da.shooter.communication.Message;
import com.da.shooter.communication.Player;
import com.da.shooter.communication.processors.InputActionProcessor;
import com.da.shooter.communication.processors.InputPositionsProcessor;
import com.da.shooter.communication.processors.InputRequestIdProcessor;
import com.da.shooter.communication.processors.InputRequestResponseProcessor;
import com.da.shooter.communication.processors.OutputActionMessageProcessor;
import com.da.shooter.communication.processors.OutputPositionsProcessor;
import com.da.shooter.elements.Avatar;
import com.da.shooter.elements.Element;
import com.da.shooter.elements.MapContactListener;
import com.da.shooter.elements.Platform;

public class GameScreen implements Screen {

	private static GameScreen instance;
	
	public static void createInstance(Game game, boolean creator, String ownerUrl){
		
		// Create instance
		instance = new GameScreen(game,creator);
		
		CommunicationManager.createInstance(ownerUrl);
	}
	
	public static GameScreen getInstance() {
		return instance;
	}
	
	private Game game;
	
	// Game info
	private int gameId;
	private Map<String, Player> ownerPositions;

	// Player info
	private boolean creator;
	private Player player;
	
	private int status;
	
	// Box2D
	private World world;
	private Box2DDebugRenderer box2DRenderer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private List<Body> bodiesToDestroy;
	
	// Avatars
	private Map<String,Avatar> avatars;
//	private Avatar currentAvatar;
//	private Map<String,List<Integer>> actions;
	
	private GameScreen(Game game, boolean creator){
		this.game = game;
		this.player = null;
		this.creator = creator;
		this.bodiesToDestroy = new ArrayList<Body>();
		this.avatars = new HashMap<String, Avatar>();
		this.ownerPositions = null;
//		this.actions = new HashMap<String, List<Integer>>();
		this.status = GameStatus.PLAYING;
		System.out.println("Game Screen");
	}
	
	public void newGame(){
		avatars = new HashMap<String, Avatar>();
		
		// Current player
//		Avatar currentAvatar = new Avatar(true);
//		currentAvatar.createObject(new Vector2(30,100),world);
//		this.avatars.put(this.avatarId, currentAvatar);
//		
//		// Enemies
//		(new Avatar(false)).createObject(new Vector2(50,100), world);
		
		// Contact listener
		world.setContactListener(new MapContactListener(this));
	}
	
	public void addAction(String avatarId, int action){
		if(avatars.containsKey(avatarId)){
			this.avatars.get(avatarId).addAction(action);
		}
	}
	
	public void removeAction(String avatarId,int action){
		if(avatars.containsKey(avatarId)){
			this.avatars.get(avatarId).removeAction(avatarId, action);
		}
	}
	
	private void executeAction(String avatarId, int gameAction){
		if(avatars.containsKey(avatarId)){
			Avatar avatar = this.avatars.get(avatarId);
			avatar.executeAction(gameAction);
		}
	}
	
	public Avatar createAvatar(Player player){
		Avatar avatar = new Avatar(player);
		avatar.createObject(new Vector2(30,100),world);
		
		this.avatars.put(player.getAvatarId(), avatar);
		return avatar;
	}
	
	@Override
	public void show() {
		
		//Creates box2d world
		world = new World(Box2DConstants.GRAVITY, true);
		box2DRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		
		//Tiled map
		TiledMap map = new TmxMapLoader().load("maps/map.tmx");
		Box2DMapObjectParser parser = new Box2DMapObjectParser(Box2DConstants.TILED_UNIT_SCALE);
		parser.load(world, map);
		for (Body body : parser.getBodies().values()) {
			new Platform(body);
		}
		
		camera.translate(363, 457);
//		camera.zoom = 26.3f;
		
		renderer = new OrthogonalTiledMapRenderer(map, parser.getUnitScale());
		
		newGame();
		
		// Communication manager
		CommunicationManager.getInstance().start();
		
		// Get unique id
		CommunicationManager.getInstance().requestAvatarId();
		
		if(instance.isCreator()){
			CommunicationManager.getInstance().addInputProcessor(Message.Type.REQUEST_ID, new InputRequestIdProcessor());
			CommunicationManager.getInstance().addInputProcessor(Message.Type.ACTION, new InputActionProcessor());
			CommunicationManager.getInstance().addOutputProcessor(new OutputPositionsProcessor());
		}else{
			CommunicationManager.getInstance().addInputProcessor(Message.Type.REQUEST_ID_RESPONSE, new InputRequestResponseProcessor());
			CommunicationManager.getInstance().addOutputProcessor(new OutputActionMessageProcessor());
			CommunicationManager.getInstance().addInputProcessor(Message.Type.POSITIONS, new InputPositionsProcessor());
		}

	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		// Box2D
		world.step(Box2DConstants.STEP, Box2DConstants.VELOCITY_ITERATIONS, Box2DConstants.POSITION_ITERATIONS);  //delta? en lugar de 1/60f  de 6 a 8 y de 2 a 3
		renderer.setView(camera);
		renderer.render();
		box2DRenderer.render(world, camera.combined);
		
		// Camera
		if(this.player != null && this.avatars.containsKey(this.player.getAvatarId())){
			float cameraX = this.avatars.get(this.player.getAvatarId()).getBody().getPosition().x;
			float cameraY = this.avatars.get(this.player.getAvatarId()).getBody().getPosition().y;
			camera.position.set(cameraX, cameraY, 0);
		}
		
		// Elements render
		Array<Body> bodies = new Array<Body>();
		this.world.getBodies(bodies);
		for (Body body : bodies) {
			if(body.getUserData() != null && body.getUserData() instanceof Element){
				((Element)body.getUserData()).render(delta,camera);
			}
		}
		
		// Input
		if(Gdx.input.isKeyPressed(Input.Keys.Z)){
			camera.zoom += 0.1f;
//			System.out.println(camera.zoom);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.X)){
			camera.zoom -= 0.1f;
//			System.out.println(camera.zoom);
		}
		
		// Continuous actions
		if(this.creator){
			if(this.checkStatus(GameStatus.PLAYING)){
				for(String avatarId : avatars.keySet()){
					List<Integer> actions = this.getActions(avatarId);
					if(actions == null) continue;
					for (int action : this.getActions(avatarId)) {
						this.executeAction(avatarId,action);
					}
				}
			}
		}
		
		camera.update();
		
		// Destroy bodies
//		if(!world.isLocked()){
//			System.out.println("Bef:"+world.getBodyCount());
//			for (Body body : this.bodiesToDestroy) {
//				for (Fixture fixture : body.getFixtureList()) {
//					body.destroyFixture(fixture);
//					fixture.setUserData(null);
//					fixture = null;
//				}
//				world.destroyBody(body);
//				body.setUserData(null);
//				body = null;
//			}
//			this.bodiesToDestroy.clear();
//			System.out.println("Aft:"+world.getBodyCount());
//		}
		// Sync bodis
		syncPositions();
	}

	public boolean checkStatus(int status) {
		return (status == this.status);
	}
	
	public void setStatus(int status){
		this.status = status;
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = height/25;
		camera.viewportWidth = width/25;
		camera.update();
	}

	public boolean isCreator() {
		return creator;
	}

	public String getAvatarId() {
		if(player == null) return null;
		return player.getAvatarId();
	}

	public void setPlayer(Player player) {
		this.player = player;
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
		world.dispose();
		renderer.dispose();
		box2DRenderer.dispose();
	}
	
	public void addBodyToDestroy(Body body){
		this.bodiesToDestroy.add(body);
	}
	
	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getAvatarVersion(String avatarId){
		if(avatars.containsKey(avatarId)){
			return avatars.get(avatarId).getVersion();
		}
		return 0;
	}
	
	public List<Integer> getActions(String avatarId) {
		if(avatars.containsKey(avatarId)){
			return avatars.get(avatarId).getActions();
		}
		return null;
	}

	public void setActions(String avatarId, List<Integer> actions) {
		if(avatars.containsKey(avatarId)){
			avatars.get(avatarId).setActions(actions);
		}
	}

	public Map<String, Avatar> getAvatars() {
		return avatars;
	}

	public void setOwnerPositions(Map<String, Player> positions) {
		if(ownerPositions == null){
			ownerPositions = positions;
		}else{
			synchronized (ownerPositions) {
				ownerPositions = positions;
			}
		}
		
	}
	
	private void syncPositions(){
		if(this.ownerPositions == null) return;
		synchronized (ownerPositions) {
			for (String avatarId : ownerPositions.keySet()) {
				Player player = ownerPositions.get(avatarId);
				Avatar avatar = null;
				if(!this.avatars.containsKey(avatarId)){
					avatar = createAvatar(player);
					this.avatars.put(avatarId,avatar);
				}else{
					avatar = this.avatars.get(avatarId);
				}
					
				avatar.getBody().setTransform(new Vector2(player.getBodyPos()[0], player.getBodyPos()[1]), 0);
				avatar.getBody(Avatar.Constants.BODY_SWORD).setTransform(new Vector2(player.getSwordPos()[0], player.getSwordPos()[1]), player.getSwordPos()[2]);
			}
			ownerPositions = null;
		}
	}
	
	// Status
	public interface GameStatus{
		int WAITING = 0;
		int PLAYING = 1;
		int ENDED = 2;
	}
	
	// Box2D constants
	interface Box2DConstants{
		float STEP =  1/60f;
		int VELOCITY_ITERATIONS=6;
		int POSITION_ITERATIONS=2;
		Vector2 GRAVITY= new Vector2(0,-29.81f);
		float TILED_UNIT_SCALE = 0.1f;//0.0625f;
	}


}


