package com.da.shooter.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.da.shooter.GameScreen;
import com.da.shooter.communication.CommunicationManager;

public class MapContactListener implements ContactListener {

	private GameScreen gameScreen;
	
	public MapContactListener(GameScreen gameScreen) {
		super();
		this.gameScreen = gameScreen;
	}
	

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#endContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
//		
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#beginContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void beginContact(Contact contact) {
		Bullet bullet= (Bullet) getObject(Bullet.class,contact);
		Avatar avatar= (Avatar) getObject(Avatar.class,contact);
		Platform platform = (Platform) getObject(Platform.class,contact);
		Sword sword= (Sword) getObject(Sword.class,contact);
				
		if(sword != null && avatar != null){
			System.out.println("Pitcher: "+sword.getOwner().getPlayer().getAvatarId()); 
			System.out.println("Catcher: "+avatar.getPlayer().getAvatarId());
			CommunicationManager.getInstance().sendLifeReduce(avatar.getPlayer().getAvatarId(),1);
		}
		
		// Avatar
		if(avatar != null){
			Fixture avatarFixture = getElementFixture(avatar,contact);
			switch ((Integer)avatarFixture.getUserData()) {
				case Avatar.Constants.FIXTURE_FOOT:
					avatar.setGrounded(true);
				break;
				default:
					
				break;
			}
			
		}
		
	}
	
	int count = 0;
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#postSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.ContactImpulse)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse contactImpulse) {
////		System.out.println("Tspeed:"+Arrays.toString(contactImpulse.getNormalImpulses())+","+Arrays.toString(contactImpulse.getTangentImpulses()));
		Avatar avatar= (Avatar) getObject(Avatar.class,contact);
		Sword sword= (Sword) getObject(Sword.class,contact);
		Platform platform = (Platform) getObject(Platform.class,contact);
		
		if(avatar != null && platform != null){
			Fixture avatarFixture = getElementFixture(avatar, contact);
			switch ((Integer)avatarFixture.getUserData()) {
				case Avatar.Constants.FIXTURE_BODY:
					if(contactImpulse.getNormalImpulses()[0]>200){
						contact.setFriction(0);
//						System.out.println("NI[0]:"+Arrays.toString(contactImpulse.getNormalImpulses()));
					}else{
						contact.resetFriction();
					}
				break;
				default:
				break;
			}

			
//			contact.setFriction(0);
//			if(Math.abs(contactImpulse.getNormalImpulses()[0])>0 && Math.abs(contactImpulse.getNormalImpulses()[0])<100){
////				System.out.println("Tspeed:"+Arrays.toString(contactImpulse.getNormalImpulses())+","+Arrays.toString(contactImpulse.getTangentImpulses()));
//				avatar.setGrounded(true);
////				System.out.println("NI[0]:"+Arrays.toString(contactImpulse.getNormalImpulses()));
//			}else{
//			}
		}
		
//		Avatar aA = (Avatar) getObjectFromFixture(Avatar.class, contact.getFixtureA());
//		Avatar aB = (Avatar) getObjectFromFixture(Avatar.class, contact.getFixtureB());
//		
//		if(aA != null && aB != null){
//			System.out.println("Normal: "+Arrays.toString(contactImpulse.getNormalImpulses()));
//		}
		
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#preSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.Manifold)
	 */
	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private Object getObject(Class<?> type,Contact contact){
		Fixture[] fixtures = new Fixture[]{contact.getFixtureA(),contact.getFixtureB()};
		for (int i=0;i<fixtures.length;i++) {
			Fixture fixture = fixtures[i];
			if(fixture == null || fixture.getBody() == null) continue;
			
			if(fixture.getBody().getUserData() != null && fixture.getBody().getUserData().getClass().equals(type)){
				return fixture.getBody().getUserData();
			}
		}
		return null;
	}
	
	private Object getObjectFromFixture(Class<?> type, Fixture fixture){
		if(fixture.getBody().getUserData() != null && fixture.getBody().getUserData().getClass().equals(type)){
			return fixture.getBody().getUserData();
		}
		return null;
	}
	
	private Fixture getElementFixture(Element obj,Contact contact){
		Fixture[] fixtures = new Fixture[]{contact.getFixtureA(),contact.getFixtureB()};
		for (Fixture fixture : fixtures) {
			if(fixture == null || fixture.getBody() == null || fixture.getBody().getUserData() == null) continue;
			if(fixture.getBody().getUserData().equals(obj)){
				return fixture;
			}
		}
		return null;
	}
	
}
