package com.da.shooter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.da.shooter.communication.CommunicationManager;
import com.da.shooter.communication.Player;
import com.da.shooter.elements.Action;
import com.da.shooter.elements.Avatar;
import com.da.shooter.elements.Element;
import com.da.shooter.elements.MapContactListener;
import com.da.shooter.elements.Platform;
import com.da.shooter.ui.ActionsList;
import com.da.shooter.ui.utils.ScreenUtils;

public class GameScreen implements Screen {

	private static final float ACTIONS_DELAY = 0.01f;

	private static GameScreen instance;
	
	public static void createInstance(MyShooterGame game, boolean creator, String ownerUrl, String username){
		
		// Create instance
		instance = new GameScreen(game,creator,username);
		
		CommunicationManager.createInstance(ownerUrl);
	}
	
	public static GameScreen getInstance() {
		return instance;
	}
	
	private Stage stage;
	
	// Game info
	private int gameId;
	private Map<String, Player> ownerPositions;

	// Player info
	private boolean creator;
	private Player player;
	private String username;
	
	private int status;
	
	// Box2D
	private World world;
	private Box2DDebugRenderer box2DRenderer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private List<Body> bodiesToDestroy;
	private SpriteBatch spriteBatch;
	
	// Avatars
	private Map<Integer,Avatar> avatars;
//	private Avatar currentAvatar;
//	private Map<String,List<Integer>> actions;
	
	// Actions box
	private ActionsList actionsList;
	private float actionDelay;
	
	// Status
	private BitmapFont gameOverFont;

	
	
	private GameScreen(MyShooterGame game, boolean creator, String username){
		this.player = null;
		this.creator = creator;
		this.bodiesToDestroy = new ArrayList<Body>();
		this.avatars = new HashMap<Integer, Avatar>();
		this.ownerPositions = null;
//		this.actions = new HashMap<String, List<Integer>>();
		this.status = GameStatus.PLAYING;
		this.actionDelay = 0;
		this.username = username;
		System.out.println("Game Screen");
	}
	
	public void newGame(){
		avatars = new HashMap<Integer, Avatar>();
		
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
	
	public Avatar createAvatar(Player player){
		Avatar avatar = new Avatar(player);
		
		avatar.createObject(new Vector2(30*player.getAvatarId(),40),world);
		
		this.avatars.put(player.getAvatarId(), avatar);
		return avatar;
	}
	
	@Override
	public void show() {
		
		//Creates box2d world
		world = new World(Box2DConstants.GRAVITY, true);
		box2DRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		spriteBatch = new SpriteBatch();
		
		gameOverFont = new BitmapFont(Gdx.files.internal("fonts/prehistorik100orange.fnt"),false);
		
		//Tiled map
		TiledMap map = new TmxMapLoader().load("maps/map.tmx");
		Box2DMapObjectParser parser = new Box2DMapObjectParser(Box2DConstants.TILED_UNIT_SCALE);
		parser.load(world, map);
		for (Body body : parser.getBodies().values()) {
			new Platform(body);
		}
		
		camera.translate(363, 457);
		camera.zoom = 1.8f;
		
		renderer = new OrthogonalTiledMapRenderer(map, parser.getUnitScale());
		
		newGame();
		
		// Communication manager
		CommunicationManager.getInstance().requestAvatarId(); // Get unique id
		CommunicationManager.getInstance().start();
		
		// Stage
		stage = new Stage();
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		// Actions list
		actionsList = new ActionsList(7, new float[]{Gdx.graphics.getWidth()/2 - 100,20}, stage);
		
		// Buttons
		TextButton leftButton = ScreenUtils.createButton("Left",10 , 50,200,80);
		TextButton rightButton = ScreenUtils.createButton("Right",210 , 50,200,80);
		TextButton attackButton = ScreenUtils.createButton("Attack",Gdx.graphics.getWidth()-210 , 50,200,80);
		stage.addActor(leftButton);
		stage.addActor(rightButton);
		stage.addActor(attackButton);

		leftButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CommunicationManager.getInstance().sendAction(new Action(player,Avatar.ActionType.LEFT, "Move left"));
			}
		});
		
		rightButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CommunicationManager.getInstance().sendAction(new Action(player,Avatar.ActionType.RIGHT, "Move right"));
			}
		});
		
		attackButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CommunicationManager.getInstance().sendAction(new Action(player,Avatar.ActionType.ACTION, "Attack"));
			}
		});
		

	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		
		// Box2D
		world.step(Box2DConstants.STEP, Box2DConstants.VELOCITY_ITERATIONS, Box2DConstants.POSITION_ITERATIONS);  //delta? en lugar de 1/60f  de 6 a 8 y de 2 a 3
		renderer.setView(camera);
		renderer.render();
//		box2DRenderer.render(world, camera.combined);
		
		// Camera
		if(this.player != null && this.avatars.containsKey(this.player.getAvatarId())){
			float cameraX = this.avatars.get(this.player.getAvatarId()).getBody().getPosition().x;
			float cameraY = this.avatars.get(this.player.getAvatarId()).getBody().getPosition().y;
			camera.position.set(cameraX, cameraY, 0);
		}
		
		// Elements render
		spriteBatch.begin();
		Array<Body> bodies = new Array<Body>();
		this.world.getBodies(bodies);
		for (Body body : bodies) {
			if(body.getUserData() != null && body.getUserData() instanceof Element){
				((Element)body.getUserData()).render(delta,camera,spriteBatch);
			}
		}
		
		// Game over
		if(this.checkStatus(GameScreen.GameStatus.GAME_OVER)){
			String msg = "GAME OVER";
			gameOverFont.draw(spriteBatch,msg, Gdx.graphics.getWidth()/2 - msg.length()*10, Gdx.graphics.getHeight()/2);
		}
		
		spriteBatch.end();
		
//		camera.zoom = 1.8f;
		
		// Input
		if(Gdx.input.isKeyPressed(Input.Keys.Z)){
			camera.zoom += 0.1f;
//			System.out.println(camera.zoom);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.X)){
			camera.zoom -= 0.1f;
//			System.out.println(camera.zoom);
		}
		
		// Game over
		if(this.avatars!= null && player != null && this.avatars.containsKey(player.getAvatarId())){
			Avatar myAvatar = this.avatars.get(player.getAvatarId());
			if(myAvatar.getLife() <= 0){
				CommunicationManager.getInstance().sendGameOver(player.getAvatarId());
			}
		}
		
		for (Avatar avatar : this.getAvatars().values()) {
			if(avatar.getPlayer() != null && avatar.getPlayer().getStatus() == Player.Status.GAME_OVER){
				avatar.getBody().setTransform(new Vector2(1000, 1000), 0);
			}
		}
		
		Action action  = this.actionsList.peek();
		if(action != null && actionDelay > GameScreen.ACTIONS_DELAY){
			actionDelay = 0;
			Avatar avatar = this.avatars.get(action.getAvatarId());
			if(Player.Status.GAME_OVER == avatar.getPlayer().getStatus()){
				// Ignore game over players
				action  = this.actionsList.pop();
			}else{
				if(avatar.getBody().getLinearVelocity().len() == 0 && avatar.getBody(Avatar.Constants.BODY_SWORD).getAngularVelocity() == 0){
					action  = this.actionsList.pop();
					avatar.executeAction(action);
				}
			}
		}else{
			actionDelay+=delta;
		}
		
		// Sync bodis
//		syncPositions();
		actionsList.update();
		stage.draw();
		
		
		
		camera.update();
		
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

	public Integer getAvatarId() {
		if(player == null) return null;
		return player.getAvatarId();
	}

	public void setPlayer(Player player) {
		this.player = player;
		this.player.setUsername(username);
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
		spriteBatch.dispose();
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

	public Map<Integer, Avatar> getAvatars() {
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
	
	public void putAction(Action action) {
		this.actionsList.addAction(action);
	}
	
	public void gameOver(int avatarId) {
		Avatar avatar = this.avatars.get(avatarId);
//		avatar.getBody().setTransform(new Vector2(1000, 1000), 0);
		avatar.getPlayer().setStatus(Player.Status.GAME_OVER);
		if(avatarId == player.getAvatarId()){
			this.setStatus(GameScreen.GameStatus.GAME_OVER);
			
		}else{
			
		}
	}
	
	// Status
	public interface GameStatus{
		int WAITING = 0;
		int PLAYING = 1;
		int ENDED = 2;
		int GAME_OVER = 3;
	}
	
	// Box2D constants
	interface Box2DConstants{
		float STEP =  1/60f;
		int VELOCITY_ITERATIONS=6;
		int POSITION_ITERATIONS=2;
		Vector2 GRAVITY= new Vector2(0,-29.81f);
		float TILED_UNIT_SCALE = 0.1f;//0.0625f;
	}

	public void reduceLife(int avatarId, int lifeReduce) {
		synchronized (avatars) {
			Avatar avatar = avatars.get(avatarId);
			avatar.reduceLife(lifeReduce);
		}
		
	}

}


