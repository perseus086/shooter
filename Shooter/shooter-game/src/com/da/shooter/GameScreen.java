package com.da.shooter;

import java.util.ArrayList;
import java.util.List;

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
import com.da.shooter.elements.Avatar;
import com.da.shooter.elements.MapContactListener;
import com.da.shooter.elements.Platform;

public class GameScreen implements Screen {

	private Game game;
	
	
	// Box2D
	private World world;
	private Box2DDebugRenderer box2DRenderer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	// Avatars
	private List<Avatar> avatars;
	private Avatar currentAvatar;
	
	public GameScreen(Game game){
		this.game = game;
		System.out.println("Game Screen");
	}
	
	public void newGame(){
		avatars = new ArrayList<Avatar>();
		
		// Current player
		currentAvatar = new Avatar(true);
		currentAvatar.createObject(new Vector2(30,100),world);
		
		// Enemies
		(new Avatar(false)).createObject(new Vector2(50,100), world);
		
		// Contact listener
		world.setContactListener(new MapContactListener());
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

	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		//Box2D
		world.step(Box2DConstants.STEP, Box2DConstants.VELOCITY_ITERATIONS, Box2DConstants.POSITION_ITERATIONS);  //delta? en lugar de 1/60f  de 6 a 8 y de 2 a 3
		renderer.setView(camera);
		renderer.render();
		box2DRenderer.render(world, camera.combined);
		
		float cameraX = currentAvatar.getBody().getPosition().x;
		float cameraY = currentAvatar.getBody().getPosition().y;
		camera.position.set(cameraX, cameraY, 0);
		
		if(Gdx.input.isKeyPressed(Input.Keys.Z)){
			camera.zoom += 0.1f;
//			System.out.println(camera.zoom);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.X)){
			camera.zoom -= 0.1f;
//			System.out.println(camera.zoom);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			currentAvatar.jump();
//			camera.translate(0, 1);
		}else if(Gdx.input.isKeyPressed(Input.Keys.S)){
//			camera.translate(0, -1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			currentAvatar.left();
		}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			currentAvatar.right();
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			currentAvatar.shoot();
		}
		
		camera.update();
		
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = height/25;
		camera.viewportWidth = width/25;
		camera.update();
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

}


//Box2D constants
interface Box2DConstants{
	float STEP =  1/60f;
	int VELOCITY_ITERATIONS=6;
	int POSITION_ITERATIONS=2;
	Vector2 GRAVITY= new Vector2(0, -29.81f);
	float TILED_UNIT_SCALE = 0.1f;//0.0625f;
}
