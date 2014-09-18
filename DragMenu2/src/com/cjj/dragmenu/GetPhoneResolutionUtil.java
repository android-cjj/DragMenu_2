package com.cjj.dragmenu;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 
 * @author cjj
 *
 */
public class GetPhoneResolutionUtil {
	
	/**
	 * 获得屏幕高度
	 * @param context
	 */
	public static int getScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获得屏幕宽度
	 * @param context
	 */
	public static int getScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
}
