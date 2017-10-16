package wjx.classmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wjx.classmanager.model.JoinClass;
import wjx.classmanager.widget.JoinClassItemView;

/**
 * Created by wjx on 2017/10/14.
 */

public class JoinClassAdapter extends RecyclerView.Adapter<JoinClassAdapter.JoinClassViewHolder> {

    private List<JoinClass> mJoinClasses=new ArrayList<>();
    private Context mContext;

    public JoinClassAdapter(List<JoinClass> joinClasses){
        mJoinClasses=joinClasses;
    }

    @Override
    public JoinClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext= parent.getContext();
        return new JoinClassViewHolder(new JoinClassItemView(mContext));
    }

    @Override
    public void onBindViewHolder(JoinClassViewHolder holder, int position) {
        holder.mJoinClassItemView.bindView(mJoinClasses.get(position));
    }

    @Override
    public int getItemCount() {
        if(mJoinClasses==null){
            return 0;
        }
        return mJoinClasses.size();
    }

    class JoinClassViewHolder extends RecyclerView.ViewHolder{
        JoinClassItemView mJoinClassItemView;

        public JoinClassViewHolder(JoinClassItemView itemView) {
            super(itemView);
            mJoinClassItemView =itemView;
        }
    }
}
