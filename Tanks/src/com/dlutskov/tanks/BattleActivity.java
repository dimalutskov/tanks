package com.dlutskov.tanks;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Main action activity which will represent game and information panel in the
 * right side
 */
public class BattleActivity extends AbstractActivity {

	/**
	 * Layout contain battle area fragment
	 */
	private FrameLayout applicationAreaBattle;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		// Setting up initial settings
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setContentView(R.layout.activity_battle);
		
		applicationAreaBattle = (FrameLayout)findViewById(R.id.applicationAreaBattle);
		
		// Add BattleAreaFragment
		BattleAreaFragment battleArea = new BattleAreaFragment();
		FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
		tr.add(R.id.applicationAreaBattle, battleArea);
		tr.commit();
	}

}
