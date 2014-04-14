package com.da.shooter;

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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen {

	private Game game;
	
	private World world;
	private Box2DDebugRenderer box2DRenderer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	public GameScreen(Game game){
		this.game = game;
		System.out.println("Game Screen");
	}
	
	@Override
	public void show() {
		//Creates box2d world
		world = new World(new Vector2(0, -9.81f), true);
		box2DRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		//Tiled map
		TiledMap map = new TmxMapLoader().load("maps/map.tmx");
		
		Box2DMapObjectParser parser = new Box2DMapObjectParser(1);
		parser.load(world, map);
		
		camera.translate(363, 457);
		camera.zoom = 26.3f;
		
		renderer = new OrthogonalTiledMapRenderer(map, parser.getUnitScale());

	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		//Box2D
		world.step(1/60f, 8, 3);  //delta? en lugar de 1/60f  de 6 a 8 y de 2 a 3
		renderer.setView(camera);
		renderer.render();
		box2DRenderer.render(world, camera.combined);
		
		camera.update();
		
		if(Gdx.input.isKeyPressed(Input.Keys.Z)){
			camera.zoom += 0.1f;
			System.out.println(camera.zoom);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.X)){
			camera.zoom -= 0.1f;
			System.out.println(camera.zoom);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			camera.translate(0, 1);
		}else if(Gdx.input.isKeyPressed(Input.Keys.S)){
			camera.translate(0, -1);
		}else if(Gdx.input.isKeyPressed(Input.Keys.A)){
			camera.translate(-1, 0);
		}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			camera.translate(1, 0);
		}
		
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
