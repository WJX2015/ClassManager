package wjx.classmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wjx.classmanager.model.Member;
import wjx.classmanager.widget.MemberItemView;

/**
 * Created by wjx on 2017/10/12.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private List<Member> mMembers=new ArrayList<>();
    private Context mContext;

    public MemberAdapter(List<Member> members){
        mMembers=members;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext =parent.getContext();
        return new MemberViewHolder(new MemberItemView(mContext));
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        holder.mMemberItemView.bindView(mMembers.get(position));
    }

    @Override
    public int getItemCount() {
        if(mMembers==null){
            return 0;
        }
        return mMembers.size();
    }

    class MemberViewHolder extends RecyclerView.ViewHolder{

        MemberItemView mMemberItemView;

        public MemberViewHolder(MemberItemView itemView) {
            super(itemView);
            mMemberItemView=itemView;
        }
    }
}
