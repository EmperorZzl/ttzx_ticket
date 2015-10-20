package com.ttzx.ticket;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * 登录页面
 * 
 * @author 子龙
 * @see 此页面集成了注册登录和第三方登录(QQ,微信,新浪微博)功能
 */
public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
	}

}
