package wjx.classmanager.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import wjx.classmanager.R;
import wjx.classmanager.adapter.MessageAdapter;
import wjx.classmanager.model.Constant;
import wjx.classmanager.model.Message;
import wjx.classmanager.presenter.impl.MessagePresenterImpl;
import wjx.classmanager.view.MessageView;

import static wjx.classmanager.model.Constant.Receiver.RECEIVE;

/**
 * Created by wjx on 2017/9/16.
 */

public class MessageFragment extends BaseFragment implements MessageView{

    private RecyclerView mRecyclerView;
    private MessageAdapter mMessageAdapter;
    private MessagePresenterImpl mMessagePresenter;

    private IntentFilter mIntentFilter;
    private MessageReceiver mMessageReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mMessagePresenter = new MessagePresenterImpl(this);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_msg);
        mMessageAdapter = new MessageAdapter(mMessagePresenter.getMessageList());
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerView.setAdapter(mMessageAdapter);
        mRecyclerView.setLayoutManager(manager);

        mIntentFilter=new IntentFilter();
        mIntentFilter.addAction(Constant.Receiver.ACTION);
        mMessageReceiver = new MessageReceiver();
        mLocalBroadcastManager =LocalBroadcastManager.getInstance(mContext);
        mLocalBroadcastManager.registerReceiver(mMessageReceiver,mIntentFilter);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_msg;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mMessageReceiver);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Message message= (Message) intent.getSerializableExtra(RECEIVE);
            if(message!=null){
                mMessageAdapter.addMessageItem(message);
            }
            Log.e( "onReceive: ","我接收到信息啦" );
        }
    }
}