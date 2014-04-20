package com.dlutskov.tanks.objects;

import android.graphics.Point;

/**
 * Determine main tank properties like sped, damage etc.
 */
public class TankProperties {

	/**
	 * Speed coefficient. Number of pixels per millisecond
	 */
	private int speed;

	/**
	 * Current angle of rotate
	 */
	private int rotateAngle = 0;
	
	/**
	 * Current tank coordinates on the area
	 */
	private volatile Point coordinates;

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getRotateAngle() {
		return this.rotateAngle;
	}

	public void setRotateAngle(int rotateAngle) {
		this.rotateAngle = rotateAngle;
	}

	public Point getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}

}