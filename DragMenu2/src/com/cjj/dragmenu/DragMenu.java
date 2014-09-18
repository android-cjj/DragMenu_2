package com.cjj.dragmenu;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.provider.OpenableColumns;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

/**
 * 
 * 左右拖拉菜单
 * qq:929178101
 * @author cjj
 *
 */
public class DragMenu extends HorizontalScrollView{
	private int PADDING = 100;
	/**距离的边距*/
	private int dragMenuPadding = PADDING;
	/**dragmenu的宽度*/
	private int dragMenuWidth ;
	/**screen width*/
	private int screenWidth;
	/**各个界面*/
	private ViewGroup dm_left_menu;
	private ViewGroup dm_right_menu;
	private ViewGroup dm_content;
	public boolean isRightOpen,isLeftOpen,isClose;

	public DragMenu(Context context) {
		this(context, null,0);
	}

	public DragMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs,defStyle);
	}

	public DragMenu(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	private void init(AttributeSet attrs, int defStyle)
	{
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DragMenu, defStyle,0);
		
		dragMenuPadding = a.getDimensionPixelSize(R.styleable.DragMenu_drag_menu_padding, DensityUtil.dip2px(getContext(), PADDING));
		
		a.recycle();
		
		screenWidth = GetPhoneResolutionUtil.getScreenWidth(getContext());
	}
	
	/**
	 * 设置边距
	 * @param dragMenuPadding
	 */
	public void setDragMenuPadding(int dragMenuPadding )
	{
		this.dragMenuPadding = dragMenuPadding;
	}
	
	@Override
	protected void onFinishInflate() {
		Log.i("cjj", "onFinishInflate");
		ViewGroup vg = (ViewGroup) getChildAt(0);
		dm_left_menu = (ViewGroup) vg.getChildAt(0);
		dm_content = (ViewGroup) vg.getChildAt(1);
		dm_right_menu = (ViewGroup) vg.getChildAt(2);
		
		dm_content.setOnClickListener(listener);
		
		dragMenuPadding = DensityUtil.dip2px(getContext(), dragMenuPadding);
		
		dragMenuWidth = screenWidth - dragMenuPadding;
		dm_left_menu.getLayoutParams().width = dragMenuWidth;
		dm_content.getLayoutParams().width = screenWidth;
		dm_right_menu.getLayoutParams().width = dragMenuWidth;
		super.onFinishInflate();
	}
	
	public OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(isLeftOpen||isRightOpen)
			{
				closeMenu();
			}
		}
	};
	
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.scrollTo(dragMenuWidth, 0);  
		super.onLayout(changed, l, t, r, b);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action)
		{
		case MotionEvent.ACTION_UP:
			int offsetX = getScrollX();
			Log.i("cjj", "offset="+offsetX);
			if (offsetX < dragMenuWidth/2)
			{
				this.smoothScrollTo(0, 0);
				isLeftOpen = true;
			
			} else if(offsetX>screenWidth+dragMenuWidth/3)
			{
				this.smoothScrollTo(dragMenuWidth+screenWidth, 0);
				isRightOpen = true;
				
			}else
			{
				this.smoothScrollTo(dragMenuWidth, 0);
				isClose = true;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		
		float scale = l * 1.0f / dragMenuWidth;
		float scale2 = (l-dragMenuWidth) * 1.0f / dragMenuWidth;
		
		float leftScale = 1 - 0.3f * scale;
		float contentScale = 0.8f + scale * 0.2f;
		float contentScale2 = 1.2f - scale * 0.2f;
		float rightScale =  0.7f + 0.3f * scale2;
		
	
		if(l>dragMenuWidth&&l<dragMenuWidth+screenWidth){
			ViewHelper.setScaleX(dm_right_menu, rightScale);
			ViewHelper.setScaleY(dm_right_menu, rightScale);
			ViewHelper.setAlpha(dm_right_menu, 0.6f + 0.4f * (scale2));
			ViewHelper.setTranslationX(dm_right_menu,-10*(1-scale2) );
			
			ViewHelper.setPivotX(dm_content, screenWidth);
			ViewHelper.setPivotY(dm_content, dm_content.getHeight() / 2);
			ViewHelper.setScaleX(dm_content, contentScale2);
			ViewHelper.setScaleY(dm_content, contentScale2);
			
		}else if(l<=dragMenuWidth)
		{
			ViewHelper.setScaleX(dm_left_menu, leftScale);
			ViewHelper.setScaleY(dm_left_menu, leftScale);
			ViewHelper.setAlpha(dm_left_menu, 0.6f + 0.4f * (1 - scale));
			ViewHelper.setTranslationX(dm_left_menu, dragMenuWidth * scale * 0.7f);
			
			ViewHelper.setPivotX(dm_content, 0);
			ViewHelper.setPivotY(dm_content, dm_content.getHeight() / 2);
			ViewHelper.setScaleX(dm_content, contentScale);
			ViewHelper.setScaleY(dm_content, contentScale);
		}
		
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	
	/**
	 * 打开右边菜单
	 */
	public void openRightMenu()
	{
		if(isLeftOpen||isRightOpen)
		{
			return;
		}
		this.smoothScrollTo(dragMenuWidth+screenWidth, 0);
		
		isRightOpen = true;
	}
	
	/**
	 * 打开左边菜单
	 */
	public void openLeftMenu()
	{
		if(isLeftOpen||isRightOpen)
		{
			return;
		}
		
		this.smoothScrollTo(0, 0);
		isLeftOpen = true;
	}
	
	/**
	 * 关闭菜单
	 */
	public void closeMenu()
	{
		if(isLeftOpen){
			isLeftOpen = false;
		}else if(isRightOpen){
			isRightOpen = false;
		}
		isClose = true;
		this.smoothScrollTo(dragMenuWidth, 0);
	}
	
	/**
	 * 切换菜单
	 */
	public void switchMenu(boolean dir)
	{
		if(isLeftOpen||isRightOpen){
			closeMenu();
		}else
		{
			if(dir)
			{
				openLeftMenu();
			}else 
			{
				openRightMenu();
			}
		}
	}

}
