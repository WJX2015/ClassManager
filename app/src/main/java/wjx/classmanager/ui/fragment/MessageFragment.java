package wjx.classmanager.ui.fragment;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import wjx.classmanager.R;
import wjx.classmanager.adapter.MessageAdapter;
import wjx.classmanager.model.Constant;
import wjx.classmanager.presenter.impl.MessagePresenterImpl;
import wjx.classmanager.receiver.MessageReceiver;
import wjx.classmanager.view.MessageView;

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
}
