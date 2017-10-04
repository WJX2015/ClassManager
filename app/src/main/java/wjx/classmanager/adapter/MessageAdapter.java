package wjx.classmanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import wjx.classmanager.model.Message;
import wjx.classmanager.widget.MessageItemView;

/**
 * Created by wjx on 2017/10/3.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Message> mMessages;

    public MessageAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(new MessageItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, final int position) {
        holder.mMessageItemView.bindView(mMessages.get(position));
        holder.mMessageItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("========",position+"");
                //Collections.swap(mMessages, position,0);
                Message message =mMessages.get(position);
                mMessages.remove(position);
                mMessages.add(0,message);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mMessages==null){
            return 0;
        }
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        MessageItemView mMessageItemView;

        public MessageViewHolder(MessageItemView itemView) {
            super(itemView);
            mMessageItemView=itemView;
        }
    }
}
