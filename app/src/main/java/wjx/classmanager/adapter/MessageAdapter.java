package wjx.classmanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import wjx.classmanager.model.Message;
import wjx.classmanager.widget.MessageItemView;

/**
 * Created by wjx on 2017/10/3.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

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
