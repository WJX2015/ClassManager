package wjx.classmanager.presenter.impl;

import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import wjx.classmanager.R;
import wjx.classmanager.application.MyApplication;
import wjx.classmanager.model.Message;
import wjx.classmanager.presenter.MessagePrestener;
import wjx.classmanager.view.MessageView;
import wjx.classmanager.widget.MessageItemView;

/**
 * Created by wjx on 2017/10/9.
 */

public class MessagePresenterImpl implements MessagePrestener {

    private MessageView mMessageView;
    private List<Message> mMessages = new ArrayList<>();

    public MessagePresenterImpl(MessageView messageView){
        mMessageView =messageView;
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setIcon(R.drawable.title_bar_icon);
            message.setTitle("标题");
            message.setTime("2017-10-03" + "  ," + i);
            mMessages.add(message);
        }
    }

    public List<Message> getMessageList(){
        return mMessages;
    }
}
