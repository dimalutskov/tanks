package com.dlutskov.tanks.objects;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Represent tank as a graphic ImageView object and allow to make some actions
 * which are fit for tanks
 */
public class Tank extends ImageView {

	/**
	 * Properties of this tank
	 */
	protected TankProperties properties;

	/**
	 * Represent moving behavior and is null when tank is not moving
	 */
	private TankMoving moving = null;
	
	/**
	 * CONSTRUCTOR
	 * 
	 * @param context
	 */
	public Tank(Context context) {
		super(context);
		this.properties = new TankProperties();
		properties.setSpeed(3);
	}

	/**
	 * Move tank to essential position
	 * 
	 * @param x
	 *            essential x position
	 * @param y
	 *            essential y position
	 */
	public void move(float x, float y) {

		// Getting current position
		int coordinates[] = new int[2];
		this.getLocationOnScreen(coordinates);

		// Getting essential path
		float pathX = x - coordinates[0];
		float pathY = y - coordinates[1];
		if (pathX >= 0)
			pathX -= getWidth();
		if (pathY >= 0)
			pathY -= getHeight();
		
		// Getting essential rotation angle
		double absPath = getAbsolutePath(Math.abs(pathX), Math.abs(pathY));
		float angle = (float) getRotateAngle(absPath, pathY);
		if (pathX < 0)
			angle = 180 - angle;
		
		this.clearAnimation();
		moving = new TankMoving(pathX, pathY, angle);
		moving.move();

	}


	/**
	 * Getting absolute path which tank has to move using Pythagoras' theorem
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private double getAbsolutePath(float x, float y) {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Getting rotate angle which is necessary to rotate tank then to move on
	 * essential direction
	 * 
	 * @param absolutePath
	 * @param yPath
	 * @return
	 */
	private double getRotateAngle(double absolutePath, float yPath) {
		double sin = yPath / absolutePath;
		double asin = Math.asin(sin);
		return Math.toDegrees(asin);
	}

	/**
	 * Save changed view state after animation finish
	 */
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
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) Tank.this
					.getLayoutParams();
			params.bottomMargin -= pathY;
			params.leftMargin += pathX;
			Tank.this.setLayoutParams(params);
			Tank.this.properties.setRotateAngle((int) angle);

			//Tank.this.startAnimation(createRotateAnimation(angle, 0));
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationStart(Animation animation) {
		}
	}
	
	/**
	 * Moving tank changing current position with animation which will be repeated
	 * several times to pass all essential path
	 */
	private class TankMoving {
		
		/**
		 * All essential path for x
		 */
		private float pathX;
		
		/**
		 * All essential path for y
		 */
		private float pathY;
		
		/**
		 * Rotate angle to rotate tank 
		 */
		private float angle;
		
		/**
		 * Animation which will represent tank moving
		 */
		private AnimationSet moveAnimation;
		
		/**
		 * CONSTRUCTOR
		 */
		public TankMoving(float pathX, float pathY, float angle) {
			this.pathX = pathX;
			this.pathY = pathY;
			this.angle = angle;
		}
		
		/**
		 * Move tank to essential position using several translate animations
		 */
		public void move() {
			moveAnimation = new AnimationSet(true);
			
			//moveAnimation.addAnimation(createRotateAnimation(angle, 2));
			//moveAnimation.addAnimation(createMoveAnimation());
			Tank.this.startAnimation(moveAnimation);
			
		}
		
		/**
		 * Create essential translate animation with current parameters 
		 */
		private Animation createMoveAnimation(float pathX, float pathY,
				long startOffset) {

			long duration = (long) (getAbsolutePath(pathX, pathY) * properties
					.getSpeed());

			// Move animation
			Animation translate = new TranslateAnimation(0, pathX, 0, pathY);
			translate.setDuration(duration);
			translate.setStartOffset(startOffset);
			translate.setFillAfter(true);

			return translate;
		}

		/**
		 * Crating animation which will rotate tank to essential direction before
		 * tank start moving
		 */
		private Animation createRotateAnimation(float angle,
				float durationCoefficient) {
			// Rotate animation
			Animation rotate = new RotateAnimation(properties.getRotateAngle(),
					angle, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rotate.setDuration((long) (Math.abs(angle) * durationCoefficient));
			rotate.setFillAfter(true);

			return rotate;
		}
	}

}

/**
 * Determine main tank properties like sped, damage etc.
 */
class TankProperties {

	private int speed;

	private int rotateAngle = 0;

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

}
