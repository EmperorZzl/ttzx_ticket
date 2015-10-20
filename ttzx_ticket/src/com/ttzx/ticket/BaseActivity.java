package com.ttzx.ticket;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ttzx.ticket.util.MySharedPreferences;
import com.ttzx.ticket.util.PhoneInforUtils;
import com.ttzx.ticket.view.TitleBar;

public abstract class BaseActivity extends FragmentActivity {
	/** GET请求方式 */
	protected int GET = 0;
	/** POST请求方式 */
	protected int POST = 1;
	/** 创建请求的工具 */
	private HttpUtils httpUtils = new HttpUtils();

	/** 全局的LayoutInflater对象，已经完成初始化. */
	public LayoutInflater mInflater;

	/** 全局的Application对象，已经完成初始化. */
	public Application mApplication = null;

	/** 全局的SharedPreferences对象，已经完成初始化. */
	public MySharedPreferences mSharedPreferences = null;
	/**
	 * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, FILL_PARENT
	 */
	public LinearLayout.LayoutParams layoutParamsFF = null;

	/**
	 * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, WRAP_CONTENT
	 */
	public LinearLayout.LayoutParams layoutParamsFW = null;

	/**
	 * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, FILL_PARENT
	 */
	public LinearLayout.LayoutParams layoutParamsWF = null;

	/**
	 * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, WRAP_CONTENT
	 */
	public LinearLayout.LayoutParams layoutParamsWW = null;

	/** 总布局. */
	public RelativeLayout ab_base = null;

	/** 标题栏布局. */
	private TitleBar mTitleBar = null;

	/** 副标题栏布局. */
	// private BottomBar mAbBottomBar = null;

	/** 主内容布局. */
	protected RelativeLayout contentLayout = null;
	/** 上拉加载更多 */
	public static final int LOAD_DATA = 0X11;
	/** 下拉刷新 */
	public static final int REFRESH = 0X13;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(view);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mInflater = LayoutInflater.from(this);

		layoutParamsFF = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParamsFW = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWF = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		if (PhoneInforUtils.getScreenWidth(this) < 720) {
			layoutParamsWW = new LinearLayout.LayoutParams(60, 60);
		} else if (PhoneInforUtils.getScreenWidth(this) >= 720
				&& PhoneInforUtils.getScreenWidth(this) < 1080) {
			layoutParamsWW = new LinearLayout.LayoutParams(80, 80);
		} else if (PhoneInforUtils.getScreenWidth(this) >= 1080) {
			layoutParamsWW = new LinearLayout.LayoutParams(100, 100);
		}

		// 主标题栏
		mTitleBar = new TitleBar(this);
		// 最外层布局
		ab_base = new RelativeLayout(this);
		ab_base.setBackgroundColor(Color.rgb(255, 255, 255));

		// 内容布局
		contentLayout = new RelativeLayout(this);
		contentLayout.setPadding(0, 0, 0, 0);

		// 副标题栏
		// mAbBottomBar = new AbBottomBar(this);

		// 填入View
		ab_base.addView(mTitleBar, layoutParamsFW);

		RelativeLayout.LayoutParams layoutParamsBottomBar = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParamsBottomBar.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
				RelativeLayout.TRUE);
		// ab_base.addView(mAbBottomBar, layoutParamsBottomBar);

		RelativeLayout.LayoutParams layoutParamsContent = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParamsContent.addRule(RelativeLayout.BELOW, mTitleBar.getId());
		// layoutParamsContent.addRule(RelativeLayout.ABOVE,
		// mBottomBar.getId());
		ab_base.addView(contentLayout, layoutParamsContent);

		// Application初始化
		mApplication = getApplication();

		// SharedPreferences初始化
		mSharedPreferences = MySharedPreferences.getMySharedPreferences(this);

		// 设置ContentView
		setContentView(ab_base, layoutParamsFF);
	}

	/**
	 * 描述：用指定资源ID表示的View填充主界面.
	 * 
	 * @param resId
	 *            指定的View的资源ID
	 */
	public void setBaseLayout(int layoutResID) {
		setMyContentView(mInflater.inflate(layoutResID, null));
		setFindViewById();
		init();
		setOnClick();
	}

	public abstract void setFindViewById();

	public abstract void setOnClick();

	public abstract void init();

	public abstract void onResults(HttpException error, String results,
			int identifying);

	/**
	 * 
	 * @param params
	 *            参数
	 * @param type
	 *            get或者post的方式
	 * @param url
	 *            链接
	 * @param identifying
	 *            标识用于标识不同的url
	 */
	public void sendHttpRTS(RequestParams params, int type, String url,
			final int identifying) {
		httpUtils.configTimeout(20000);
		if (type == GET) {
			httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException error, String results) {
					// TODO Auto-generated method stub
					onResults(error, results, identifying);
				}

				@Override
				public void onSuccess(ResponseInfo<String> results) {
					// TODO Auto-generated method stub
					onResults(null, results.result, identifying);
				}
			});
		} else {
			httpUtils.send(HttpMethod.POST, url, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException error,
								String results) {
							// TODO Auto-generated method stub

							onResults(error, results, identifying);

						}

						@Override
						public void onSuccess(ResponseInfo<String> results) {
							// TODO Auto-generated method stub
							onResults(null, results.result, identifying);

						}
					});
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	/**
	 * 描述：用指定的View填充主界面.
	 * 
	 * @param contentView
	 *            指定的View
	 */
	public void setMyContentView(View contentView) {
		contentLayout.removeAllViews();
		contentLayout.addView(contentView, layoutParamsFF);
		// ioc
		// initIocView();

	}

	/**
	 * 描述：设置界面显示（忽略标题栏）.
	 * 
	 * @param view
	 *            the view
	 * @param params
	 *            the params
	 * @see android.app.Activity#setContentView(android.view.View,
	 *      android.view.ViewGroup.LayoutParams)
	 */
	@Override
	public void setContentView(View view,
			android.view.ViewGroup.LayoutParams params) {
		super.setContentView(view, params);
	}

	/**
	 * 获取主标题栏布局.
	 * 
	 * @return the title layout
	 */
	public TitleBar getTitleBar() {
		return mTitleBar;
	}

	/**
	 * 获取副标题栏布局.
	 * 
	 * @return the bottom layout
	 */
	// public BottomBar getBottomBar() {
	// return mBottomBar;
	// }

	/**
	 * 描述：Activity结束.
	 * 
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		super.finish();
	}

}
