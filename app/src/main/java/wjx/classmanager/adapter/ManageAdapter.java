package wjx.classmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import wjx.classmanager.R;
import wjx.classmanager.model.Manage;
import wjx.classmanager.widget.ManageItemView;

/**
 * Created by wjx on 2017/9/18.
 */

public class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.ManageViewHolder> {

    private List<Manage> mManages;
    private Context mContext;

    public ManageAdapter(List<Manage> manages) {
        mManages = manages;
    }

    @Override
    public ManageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        return new ManageViewHolder(new ManageItemView(mContext));
    }

    @Override
    public void onBindViewHolder(ManageViewHolder holder, final int position) {
        holder.mManageItemView.bindView(mContext,mManages.get(position));
    }

    @Override
    public int getItemCount() {
        if(mManages==null){
            return 0;
        }
        return mManages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ManageViewHolder extends RecyclerView.ViewHolder {

        ManageItemView mManageItemView;

        public ManageViewHolder(ManageItemView itemView) {
            super(itemView);
            mManageItemView=itemView;
        }
    }

}
