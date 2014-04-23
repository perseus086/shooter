package com.da.shooter.communication.utils;

import java.util.UUID;

public class IdGenerator {
	public static String generateId(){
		return UUID.randomUUID().toString();
	}
}
