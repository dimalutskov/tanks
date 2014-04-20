package com.dlutskov.tanks.actions;

import java.util.concurrent.TimeUnit;

import android.graphics.Point;

/**
 * Thread which will change real object coordinates while move
 * animation will executing
 */
public class CoordinatesChangingThread extends Thread {

	/**
	 * Update coordinates every 50 milliseconds
	 */
	private final int updateCoordinates = 50;
	
	/**
	 * Allow to change coordinates of any objects which
	 * set this interface in constructor
	 */
	private CoordinatesChangingListener listener;
	
	/**
	 * Initial object coordinates before moving
	 */
	private Point initialCoordinates;
		
	/**
	 * Move this way for x per millisecond
	 */
	private float pathX;
	
	/**
	 * Move this way for y per millisecond
	 */
	private float pathY;
	
	private int duration;
				
	// Constructor
	public CoordinatesChangingThread(CoordinatesChangingListener listener, 
			Point initialCoordinates, float pathX, float pathY, int duration) {
		this.listener = listener;
		this.initialCoordinates = initialCoordinates;
		this.pathX = pathX;
		this.pathY = pathY;
		this.duration = duration;
	}
	
	@Override
	public void run() {
		float stepX = pathX / duration;
		float stepY = pathY / duration;
				
		for (int i = 0; i <= duration; i+=updateCoordinates) {
			if (isInterrupted()) {
				return;
			}
			listener.changeCoordinate(initialCoordinates.x + stepX * i, 
					initialCoordinates.y + stepY * i);
			try {
				TimeUnit.MILLISECONDS.sleep(updateCoordinates);
			} catch (InterruptedException e) {
				return;
			}			
		}		
	}
			
	/**
	 * Change coordinates of any specific object
	 */
	public interface CoordinatesChangingListener {
		public void changeCoordinate(float x, float y);
	}

}
