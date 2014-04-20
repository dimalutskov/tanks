package com.dlutskov.tanks.actions;

/**
 * Determine behavior of moving object
 */
public interface ActionMove {
	
	/**
	 * Move object to the essential position and with essential
	 * direction which is determined knowing angle value
	 */
	public void move(float pathX, float pathY, float angle);
	
	/**
	 * Interrupt moving and left object at the current position
	 */
	public void interruptMoving();
}
