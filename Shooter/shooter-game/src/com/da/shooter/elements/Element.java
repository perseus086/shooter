package com.da.shooter.elements;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * The Interface Element.
 */
public interface Element {
	
	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	Body getBody();

	void render();
}