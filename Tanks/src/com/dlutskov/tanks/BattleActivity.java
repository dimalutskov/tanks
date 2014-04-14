package com.dlutskov.tanks;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dlutskov.tanks.objects.UserTank;

/**
 * Main action activity which will represent game and information panel in the
 * right side
 */
public class BattleActivity extends AbstractActivity implements OnTouchListener {

	/**
	 * Layout which contains all dynamic views of battle area
	 */
	private RelativeLayout battleArea;

	/**
	 * Allow to set sizes in dp
	 */
	private float dpScale;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		// Setting up initial settings
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setContentView(R.layout.activity_battle);
		dpScale = this.getResources().getDisplayMetrics().density;

		// Setting up onTouchListener
		LinearLayout applicationArea = (LinearLayout)this.findViewById(R.id.applicationArea);
		applicationArea.setOnTouchListener(this);

		int width = (int) (100 * dpScale + 0.5f);
		int height = (int) (50 * dpScale + 0.5f);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				width, height);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.bottomMargin = 10;
		params.leftMargin = 100;
		battleArea = (RelativeLayout) this.findViewById(R.id.battleArea);

		UserTank tank = UserTank.getTank(this);
		tank.setLayoutParams(params);
		battleArea.addView(tank);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: 
			UserTank.getTank(this).move(x, y);
			break;
		case MotionEvent.ACTION_MOVE: 

			break;
		case MotionEvent.ACTION_UP: 

			break;
		}

		return true;

	}

}
