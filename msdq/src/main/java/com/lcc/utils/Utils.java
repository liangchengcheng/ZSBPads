package com.lcc.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import zsbpj.lccpj.frame.FrameManager;

public class Utils {

	public static int dpToPx(float dp, Resources resources){
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
		return (int) px;
	}
	
	public static int getRelativeTop(View myView) {
	    if(myView.getId() == android.R.id.content)
	        return myView.getTop();
	    else
	        return myView.getTop() + getRelativeTop((View) myView.getParent());
	}
	
	public static int getRelativeLeft(View myView) {
		if(myView.getId() == android.R.id.content)
			return myView.getLeft();
		else
			return myView.getLeft() + getRelativeLeft((View) myView.getParent());
	}

	public static int dpToPx(int dp) {
		DisplayMetrics displayMetrics = FrameManager.getAppContext().getResources().getDisplayMetrics();
		int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}

	
}
