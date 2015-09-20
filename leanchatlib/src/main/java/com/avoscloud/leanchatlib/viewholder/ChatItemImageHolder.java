package com.avoscloud.leanchatlib.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avoscloud.leanchatlib.R;
import com.avoscloud.leanchatlib.controller.MessageHelper;
import com.avoscloud.leanchatlib.event.ImageItemClickEvent;
import com.avoscloud.leanchatlib.utils.PhotoUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by wli on 15/9/17.
 */
public class ChatItemImageHolder extends ChatItemHolder {

  protected ImageView contentView;

  public ChatItemImageHolder(Context context, ViewGroup root, boolean isLeft) {
    super(context, root, isLeft);
  }

  @Override
  public void initView() {
    super.initView();
    if (isLeft) {
      conventLayout.addView(View.inflate(getContext(), R.layout.chat_item_left_image_layout, null));
      contentView = (ImageView)itemView.findViewById(R.id.chat_item_left_image_view);
    } else {
      conventLayout.addView(View.inflate(getContext(), R.layout.chat_item_left_image_layout, null));
      contentView = (ImageView)itemView.findViewById(R.id.chat_item_left_image_view);
    }

    contentView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ImageItemClickEvent clickEvent = new ImageItemClickEvent();
        clickEvent.message = message;
        EventBus.getDefault().post(clickEvent);
      }
    });
  }

  @Override
  public void bindData(Object o) {
    super.bindData(o);
    AVIMMessage message = (AVIMMessage)o;
    if (message instanceof AVIMImageMessage) {
      AVIMImageMessage imageMsg = (AVIMImageMessage) message;
      PhotoUtils.displayImageCacheElseNetwork(contentView, MessageHelper.getFilePath(imageMsg),
        imageMsg.getFileUrl());
    }
  }
}