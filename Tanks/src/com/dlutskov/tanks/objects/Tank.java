package com.dlutskov.tanks.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dlutskov.tanks.R;
import com.dlutskov.tanks.actions.ActionMove;

/**
 * Represent tank as a container of graphic ImageView objects and allow to make
 * some actions which are fit for tanks
 */
public class Tank extends RelativeLayout {

	/**
	 * Properties of this tank
	 */
	public TankProperties properties;

	/**
	 * Image of tank body
	 */
	private ImageView tankImageBody;
	
	/**
	 * Image of tank gun
	 */
	private ImageView tankImageGun;
		
	/**
	 * CONSTRUCTOR
	 */
	public Tank(Context context) {
		super(context);
		this.properties = new TankProperties();
		properties.setSpeed(3);
		
		//this.setBackgroundColor(Color.TRANSPARENT);
		this.setBackgroundColor(Color.RED);

		// Create tank body image
		int width = Integer.parseInt(getResources().getString(
				R.string.width_tank));
		int height = Integer.parseInt(getResources().getString(
				R.string.height_tank));
		float dpScale = this.getResources().getDisplayMetrics().density;
		LayoutParams params = new LayoutParams((int) (width * dpScale + 0.5f),
				(int) (height * dpScale + 0.5f));
		tankImageBody = new ImageView(context);
		tankImageBody.setImageBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.tank_shell));
		tankImageBody.setLayoutParams(params);
			
		this.addView(tankImageBody);
	}
	
	// GET SET METHODS
	public void setTankImageBody(Bitmap image) {
		tankImageBody.setImageBitmap(image);
	}
	
	public ImageView getTankImageBody() {
		return this.tankImageBody;
	}

	/**
	 * Move tank to essential position
	 * 
	 * @param x
	 *            essential x position
	 * @param y
	 *            essential y position
	 */
	public void move(float x, float y, ActionMove moving) {

		// Finish current moving to make new moving
		moving.interruptMoving();
		this.clearAnimation();
				
		// Draw tank on the current position when previous move
		// wasn't be finished
		LayoutParams params =  (RelativeLayout.LayoutParams)getLayoutParams();
		params.leftMargin = properties.getCoordinates().x;
		params.topMargin = properties.getCoordinates().y;
		this.setLayoutParams(params);
		this.startAnimation(createRotateAnimation(properties.getRotateAngle(), 0));
		
		// Getting essential path
		//float pathX = x - this.getLeft();
		//float pathY = y - this.getTop();
		float pathX = x - properties.getCoordinates().x;
		float pathY = y - properties.getCoordinates().y;
				
		if (pathX >= 0)
			pathX -= getWidth();
		if (pathY >= 0)
			pathY -= getHeight();

		// Getting essential rotation angle
		double absPath = getAbsolutePath(Math.abs(pathX), Math.abs(pathY));
		float angle = (float) getRotateAngle(absPath, pathY);
		
		//Log.d("TAG", "Before: " + angle + " ");
		if (pathX < 0) {
			angle = 180 - angle;
		}

		
		
		
		moving.move(pathX, pathY, angle);
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
	 * Crating animation which will rotate tank to essential direction before
	 * tank start moving
	 */
	private Animation createRotateAnimation(float angle,
			float durationCoefficient) {
		// Rotate animation
		Animation rotate = new RotateAnimation(
				properties.getRotateAngle(), angle,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration((long) (Math.abs(angle) * durationCoefficient));
		rotate.setFillAfter(true);

		return rotate;
	}
		
}


