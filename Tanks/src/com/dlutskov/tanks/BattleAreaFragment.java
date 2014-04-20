package com.dlutskov.tanks;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.dlutskov.tanks.actions.ActionMove;
import com.dlutskov.tanks.actions.TankMove;
import com.dlutskov.tanks.objects.UserTank;

/**
 * Main action fragment which will represent game area
 */
public class BattleAreaFragment extends Fragment implements OnTouchListener {

	/**
	 * Layout which contains all dynamic views of battle area
	 */
	private RelativeLayout battleArea;
	
	/**
	 * Tank of user
	 */
	private UserTank userTank ;
	
	/**
	 * Determine behavior of user tank moving
	 */
	private ActionMove userTankMove;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View parentView = inflater.inflate(R.layout.fragment_battle_area, null);
		userTank = UserTank.getTank(getActivity());

		// Setting up onTouchListener
		battleArea = (RelativeLayout) parentView
				.findViewById(R.id.battleArea);
		battleArea.setOnTouchListener(this);
		battleArea.setBackgroundColor(Color.BLACK);
		
		// Create User Tank
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		userTank.properties.setCoordinates(new Point(100, 100)); // POSITION !!!!!
		params.leftMargin = userTank.properties.getCoordinates().x;
		params.topMargin = userTank.properties.getCoordinates().y;;
		userTank.setLayoutParams(params);		
		battleArea.addView(userTank);
		
		userTankMove = new TankMove(userTank);
		
		return parentView;

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		userTank.move(event.getX(), event.getY(), userTankMove);		
		return false;
	}
	
	

}
