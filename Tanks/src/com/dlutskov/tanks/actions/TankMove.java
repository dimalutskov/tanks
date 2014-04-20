package com.dlutskov.tanks.actions;

import android.graphics.Point;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.dlutskov.tanks.actions.CoordinatesChangingThread.CoordinatesChangingListener;
import com.dlutskov.tanks.objects.Tank;

/**
 * Moving tank changing current position with animation which will store current
 * tank points
 */
public class TankMove implements ActionMove {

	/**
	 * Tank which is about to move
	 */
	private Tank tank;

	/**
	 * Animation which will represent tank moving
	 */
	private AnimationSet moveAnimation;

	/**
	 * Rotate speed coefficient. Change 2 degree in 1 millisecond
	 */
	private final int rotateSpeed = 2;

	/**
	 * Thread which will change tank coordinates while animation executing
	 */
	private CoordinatesChangingThread coordinatesThread;

	/**
	 * CONSTRUCTOR
	 */
	public TankMove(Tank tank) {
		this.tank = tank;
	}

	/**
	 * Move tank to essential position using several translate animations
	 */
	@Override
	public void move(float pathX, float pathY, float angle) {
		moveAnimation = new AnimationSet(true);

		moveAnimation.addAnimation(createRotateAnimation(angle, rotateSpeed));
		moveAnimation.addAnimation(createMoveAnimation(pathX, pathY,
				(long) Math.abs(angle) * rotateSpeed));
		moveAnimation.setAnimationListener(new MoveAnimationListener(pathX,
				pathY, angle));
		
		startCoordinateChanging(pathX, pathY);
		tank.startAnimation(moveAnimation);
	}

	/**
	 * Start change coordinates concurrently with animation
	 */
	private void startCoordinateChanging(float pathX, float pathY) {
		// Coordinates receiver
		CoordinatesChangingListener listener = new CoordinatesChangingListener() {
			@Override
			public void changeCoordinate(float x, float y) {
				tank.properties.setCoordinates(new Point((int) x, (int) y));
			}
		};
		int duration = (int) getAbsolutePath(pathX, pathY)
				* tank.properties.getSpeed();
		// Start thread which will change tank coordinates
		coordinatesThread = new CoordinatesChangingThread(listener,
				tank.properties.getCoordinates(), pathX, pathY, duration);
		coordinatesThread.start();
	}

	@Override
	public void interruptMoving() {
		if (coordinatesThread != null) {
			coordinatesThread.interrupt();
		}
	}

	/**
	 * Create essential translate animation with current parameters
	 */
	private Animation createMoveAnimation(float pathX, float pathY,
			long startOffset) {

		long duration = (long) (getAbsolutePath(pathX, pathY) * tank.properties
				.getSpeed());

		// Move animation
		Animation translate = new TranslateAnimation(0, pathX, 0, pathY);
		translate.setInterpolator(new LinearInterpolator());
		translate.setDuration(duration);
		translate.setStartOffset(startOffset);
		translate.setFillAfter(true);

		return translate;
	}

	/**
	 * Getting absolute path which tank has to move using Pythagoras' theorem
	 */
	private double getAbsolutePath(float x, float y) {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Crating animation which will rotate tank to essential direction before
	 * tank start moving
	 */
	private Animation createRotateAnimation(float angle,
			float durationCoefficient) {
		// Rotate animation
		Animation rotate = new RotateAnimation(
				tank.properties.getRotateAngle(), angle,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration((long) (Math.abs(angle) * durationCoefficient));
		rotate.setFillAfter(true);

		return rotate;
	}

	private class MoveAnimationListener implements AnimationListener {

		private float pathX;

		private float pathY;

		private float angle;

		public MoveAnimationListener(float pathX, float pathY, float angle) {
			this.pathX = pathX;
			this.pathY = pathY;
			this.angle = angle;
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tank
					.getLayoutParams();
			params.leftMargin += pathX;
			params.topMargin += pathY;
			tank.setLayoutParams(params);
			tank.properties.setRotateAngle((int) angle);
			tank.startAnimation(createRotateAnimation(angle, 0));
		}

		@Override
		public void onAnimationStart(Animation animation) {
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	}
}
