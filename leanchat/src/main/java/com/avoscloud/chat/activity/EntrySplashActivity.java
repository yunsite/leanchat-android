package com.avoscloud.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avoscloud.chat.R;
import com.avoscloud.chat.model.LeanchatUser;
import com.avoscloud.chat.redpacket.RedPacketUtils;
import com.avoscloud.chat.redpacket.RequestTask;

import cn.leancloud.chatkit.LCChatKit;

public class EntrySplashActivity extends AVBaseActivity {
  public static final int SPLASH_DURATION = 2000;
  private static final int GO_MAIN_MSG = 1;
  private static final int GO_LOGIN_MSG = 2;

  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case GO_MAIN_MSG:
          RedPacketUtils.getInstance().initUserData(LeanchatUser.getCurrentUserId(), LeanchatUser.getCurrentUser().getUsername(), LeanchatUser.getCurrentUser().getAvatarUrl());
          imLogin();
          break;
        case GO_LOGIN_MSG:
          Intent intent = new Intent(EntrySplashActivity.this, EntryLoginActivity.class);
          EntrySplashActivity.this.startActivity(intent);
          finish();
          break;
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.entry_splash_layout);
    if (LeanchatUser.getCurrentUser() != null) {
      LeanchatUser.getCurrentUser().updateUserInfo();
      handler.sendEmptyMessageDelayed(GO_MAIN_MSG, SPLASH_DURATION);
    } else {
      handler.sendEmptyMessageDelayed(GO_LOGIN_MSG, SPLASH_DURATION);
    }
  }

  private void imLogin() {
    LCChatKit.getInstance().open(LeanchatUser.getCurrentUserId(), new AVIMClientCallback() {
      @Override
      public void done(AVIMClient avimClient, AVIMException e) {
        if (filterException(e)) {
          RequestTask.getInstance().initRedPacketNet(getApplicationContext(), LeanchatUser.getCurrentUserId());
          Intent intent = new Intent(EntrySplashActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
        }
      }
    });
  }
}
