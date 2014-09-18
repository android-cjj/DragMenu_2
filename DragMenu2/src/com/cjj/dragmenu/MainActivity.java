package com.cjj.dragmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity
{

	private DragMenu dm ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		dm = (DragMenu) this.findViewById(R.id.dm);
	}
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.ibtn_icon1:
			
			dm.switchMenu(true);
			
			break;
		case R.id.ibtn_icon2:
			
			dm.switchMenu(false);
			
			break;
		}
	}

}
