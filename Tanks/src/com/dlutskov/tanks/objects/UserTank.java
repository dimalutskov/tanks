package com.dlutskov.tanks.objects;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.dlutskov.tanks.AbstractActivity;
import com.dlutskov.tanks.R;

/**
 * Main user tank view object which is able to make some actions for user's
 * desire
 */
public class UserTank extends Tank {

	/**
	 * Singleton object
	 */
	private static UserTank tank;

	/**
	 * View which will represent tank as a graphic object
	 */
	private ImageView gunView;
	
	/**
	 * Private constructor to make this object singleton
	 */
	private UserTank(AbstractActivity context) {
		super(context);

		this.setImageBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.tank_shell));
		this.setBackgroundColor(Color.TRANSPARENT);

	}

	@Override
	public void setLayoutParams(LayoutParams params) {
		// TODO Auto-generated method stub
		super.setLayoutParams(params);
	}

	/**
	 * WARNING THREAD SAFETY
	 * 
	 * @param context
	 * @return
	 */
	public static UserTank getTank(AbstractActivity context) {
		if (tank == null) {
			tank = new UserTank(context);
		}
		return tank;
	}

	public void move(float x, float y) {
		super.move(x, y);
	}

	public void shoot() {
		// TODO Auto-generated method stub

	}

}
