package wjx.classmanager.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import wjx.classmanager.R;
import wjx.classmanager.adapter.MessageAdapter;
import wjx.classmanager.model.Message;
import wjx.classmanager.widget.RecycleViewDivider;

/**
 * Created by wjx on 2017/9/16.
 */

public class MessageFragment extends BaseFragment{

    private RecyclerView mRecyclerView;
    private MessageAdapter mMessageAdapter;

    private List<Message> mMessages = new ArrayList<>();

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_msg);
        mMessageAdapter = new MessageAdapter(mMessages);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerView.setAdapter(mMessageAdapter);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        for(int i=0;i<10;i++){
            Message message=new Message();
            message.setIcon(R.drawable.title_bar_icon);
            message.setTitle("标题");
            message.setTime("2017-10-03");
            mMessages.add(message);
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_msg;
    }
}
