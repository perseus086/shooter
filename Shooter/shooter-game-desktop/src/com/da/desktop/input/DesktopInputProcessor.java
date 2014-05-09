package com.da.desktop.input;

import com.badlogic.gdx.InputProcessor;


/**
 * The Class DesktopInputProcessor.
 */
public class DesktopInputProcessor implements InputProcessor {
	
	
	/**
	 * Instantiates a new desktop input processor.
	 *
	 * @param game the game
	 */
	public DesktopInputProcessor() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
//		GameScreen gameScreen = GameScreen.getInstance();
//		if(gameScreen.getAvatarId() == null) return false;
//		
//		switch(keycode){
//			case 19: //Up
//				GameScreen.getInstance().addAction(gameScreen.getAvatarId(), Avatar.Type.JUMP);
//			break;
//			case 20: //Down
//			break;
//			case 21: //Left
//				GameScreen.getInstance().addAction(gameScreen.getAvatarId(), Avatar.Type.LEFT);
//			break;
//			case 22: //Right
//				GameScreen.getInstance().addAction(gameScreen.getAvatarId(), Avatar.Type.RIGHT);
//			break;
//			case 61: //Tab
//			break;
//			case 62: //Space
//				GameScreen.getInstance().addAction(gameScreen.getAvatarId(), Avatar.Type.ACTION);
//			break;
//		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		
//		GameScreen gameScreen = GameScreen.getInstance();
//		if(gameScreen.getAvatarId() == null) return false;
//		switch(keycode){
//		case 19: //Up
//			GameScreen.getInstance().removeAction(gameScreen.getAvatarId(), Avatar.Type.JUMP);
//		break;
//		case 20: //Down
//		break;
//		case 21: //Left
//			GameScreen.getInstance().removeAction(gameScreen.getAvatarId(), Avatar.Type.LEFT);
//		break;
//		case 22: //Right
//			GameScreen.getInstance().removeAction(gameScreen.getAvatarId(), Avatar.Type.RIGHT);
//		break;
//		case 61: //Tab
//		break;
//		case 62: //Space
//			GameScreen.getInstance().removeAction(gameScreen.getAvatarId(), Avatar.Type.ACTION);
//		break;
//	}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
