package com.da.shooter.elements;

import java.util.Arrays;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MapContactListener implements ContactListener {

	public MapContactListener() {
		super();
	}
	

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#endContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
		Object obj1 =  contact.getFixtureA().getBody().getUserData();
		Object obj2 =  contact.getFixtureB().getBody().getUserData();
		if(obj1 == null || obj2 == null) return;
		Avatar avatar= (Avatar) getObject(Avatar.class,new Object[]{obj1,obj2});
		Bullet bullet= (Bullet) getObject(Bullet.class,new Object[]{obj1,obj2});
		if(avatar != null && avatar.isCurrentPlayer() && bullet == null){
			avatar.setGrounded(false);
		}
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#beginContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void beginContact(Contact contact) {
		Object objA =  contact.getFixtureA().getBody().getUserData();
		Object objB =  contact.getFixtureB().getBody().getUserData();
		if(objA == null || objB == null) return;
		
		Bullet bullet= (Bullet) getObject(Bullet.class,new Object[]{objA,objB});
		Avatar avatar= (Avatar) getObject(Avatar.class,new Object[]{objA,objB});
		Platform platform = (Platform) getObject(Platform.class,new Object[]{objA,objB});
		if(bullet != null && avatar != null && !avatar.isCurrentPlayer()){
			System.out.println("Bullet hit");
		}
		
		if(avatar != null && platform != null){
			Fixture avatarFixture = null;
			avatarFixture = (objA == avatar)?contact.getFixtureA():contact.getFixtureB();
			
			switch ((Integer)avatarFixture.getUserData()) {
				case AvatarConstants.FIXTURE_FOOT:
					avatar.setGrounded(true);
				break;
				default:
					
				break;
			}
			
		}
		
//		if(obj1.getClass().equals(Avatar.class) && obj2.getClass().equals(Platform.class)){
//			((Avatar)obj1).setGrounded(true);
//		}
//		
//		if(obj2.getClass().equals(Avatar.class) && obj1.getClass().equals(Platform.class)){
//			((Avatar)obj2).setGrounded(true);
//		}
	}
	
	int count = 0;
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#postSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.ContactImpulse)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse contactImpulse) {
		Object objA =  contact.getFixtureA().getBody().getUserData();
		Object objB =  contact.getFixtureB().getBody().getUserData();
		if(objA == null || objB == null) return;
////		System.out.println("Tspeed:"+Arrays.toString(contactImpulse.getNormalImpulses())+","+Arrays.toString(contactImpulse.getTangentImpulses()));
		Avatar avatar= (Avatar) getObject(Avatar.class,new Object[]{objA,objB});
		Platform platform = (Platform) getObject(Platform.class,new Object[]{objA,objB});
		
		if(avatar != null && avatar.isCurrentPlayer() && platform != null){
			Fixture avatarFixture = (objA == avatar)?contact.getFixtureA():contact.getFixtureB();
			switch ((Integer)avatarFixture.getUserData()) {
				case AvatarConstants.FIXTURE_BODY:
					if(contactImpulse.getNormalImpulses()[0]>200){
						if(count ==0){
							contact.setFriction(0);
//							count++;
						}
						System.out.println("NI[0]:"+Arrays.toString(contactImpulse.getNormalImpulses()));
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
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#preSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.Manifold)
	 */
	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private Object getObject(Class<?> type,Object[] objects){
		for (Object object : objects) {
			if(object != null && object.getClass().equals(type)){
				return object;
			}
		}
		return null;
	}
	
}
