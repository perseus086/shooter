package com.perseus.super_game.views;

import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;
import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;
import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.perseus.super_game.controller.Teclado;
import com.perseus.super_game.model.Hero;

public class SuperGame implements Screen{
	
	private final float PPM = 1;//0.0625f;
	private Game game;
	private TextureRegion[][] sprites;
	private TextureRegion[] sprites2;
	private Animation animation;
	private SpriteBatch batch;
	private float stateTime;
	private Hero hero;
	
	
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private World world;
	private Box2DDebugRenderer box2DRenderer;
	private Sprite s;
	private AnimatedSprite animatedSprite;
	
	private Body body;
	
	public SuperGame(Game game)
	{
		this.game = game;
		
		
	}
	@Override
	public void show() {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -9.81f), true);
		box2DRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
//		camera.setToOrtho(false, Gdx.graphics.getWidth() /PPM, Gdx.graphics.getHeight() /PPM);
		TiledMap map = new TmxMapLoader().load("maps/map.tmx");
		
		Box2DMapObjectParser parser = new Box2DMapObjectParser(PPM);
		parser.load(world, map);
		
		renderer = new OrthogonalTiledMapRenderer(map, parser.getUnitScale());
//		camera.zoom = 20;
//		camera.translate(500, 2000);
//		camera.zoom = 0.7f;
//		camera.translate(0, 0);
		
		Texture texture = new Texture(Gdx.files.internal("img/hero1.png"));
		sprites = TextureRegion.split(texture, 64, 64);
		
		///////PRUEBAS CON ANIMATION
		sprites2 = TextureRegion.split(texture, 64, 64)[0];
		animation = new Animation(1/12.0f, sprites2);
		animation.setPlayMode(Animation.PlayMode.LOOP);
		AnimatedSprite animatedSprite = new AnimatedSprite(animation);
		////B2D BODIES
		
		BodyDef bdef = new BodyDef();
		bdef.position.set(500*PPM, 20*PPM);
		bdef.type = BodyType.StaticBody;
		
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50*PPM, 5*PPM);
		FixtureDef fixtureDefinition = new FixtureDef();
		fixtureDefinition.shape =shape;
		world.createBody(bdef).createFixture(fixtureDefinition);
		
		
		//Otro body
		bdef.position.set(490*PPM, 2200*PPM);
		bdef.type = BodyType.DynamicBody;
		shape.setAsBox(16*PPM,32*PPM);
		fixtureDefinition.shape = shape;
		fixtureDefinition.restitution = 0.01f;
		body = world.createBody(bdef);
		body.createFixture(fixtureDefinition).setUserData(new AnimatedBox2DSprite(animatedSprite));

		
		hero = new Hero(200,200);
		Gdx.input.setInputProcessor(new Teclado(hero));
		Gdx.input.setCatchBackKey(true);
		

	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		stateTime += delta;
		hero.update();
		
		
		//////////BOX2D
		world.step(1/60f, 8, 3);  //delta? en lugar de 1/60f  de 6 a 8 y de 2 a 3
		
		renderer.setView(camera);
		renderer.render();
		
		box2DRenderer.render(world, camera.combined);
		
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
		else if(Gdx.input.isKeyPressed(Input.Keys.U)){
			body.applyForce(0, 30, 0, 0, true);
		}else if(Gdx.input.isKeyPressed(Input.Keys.Y)){
			body.applyForce(-30, 0, 0, 0, true);
		}else if(Gdx.input.isKeyPressed(Input.Keys.O)){
			body.applyForce(30, 0, 0, 0, true);
		}
		
		batch.begin();
		batch.draw(sprites[0][0], 400, 400);
		batch.draw(animation.getKeyFrame(stateTime, true), hero.getX(), hero.getY());

		AnimatedBox2DSprite.draw(batch, world);
		batch.end();
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = height/25;
		camera.viewportWidth = width/25;
		camera.update();
		batch.setProjectionMatrix(camera.combined);
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
		batch.dispose();
		world.dispose();
		renderer.dispose();
		box2DRenderer.dispose();
	}

}
