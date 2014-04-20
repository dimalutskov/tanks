package com.dlutskov.tanks.objects;

import android.content.Context;
import android.graphics.BitmapFactory;

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
	 * Private constructor to make this object singleton
	 */
	private UserTank(Context context) {
		super(context);

		this.setTankImageBody(BitmapFactory.decodeResource(getResources(),
				R.drawable.tank_shell));
	}

	/**
	 * WARNING THREAD SAFETY
	 * 
	 * @param context
	 * @return
	 */
	public static UserTank getTank(Context context) {
		if (tank == null) {
			tank = new UserTank(context);
		}
		return tank;
	}


}
