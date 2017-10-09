package wjx.classmanager.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import wjx.classmanager.R;
import wjx.classmanager.adapter.MessageAdapter;
import wjx.classmanager.application.MyApplication;
import wjx.classmanager.model.Message;
import wjx.classmanager.presenter.impl.MessagePresenterImpl;
import wjx.classmanager.view.MessageView;
import wjx.classmanager.widget.MessageItemView;

/**
 * Created by wjx on 2017/9/16.
 */

public class MessageFragment extends BaseFragment implements MessageView{

    private RecyclerView mRecyclerView;
    private MessageAdapter mMessageAdapter;
    private MessagePresenterImpl mMessagePresenter;

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
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_msg;
    }
}
