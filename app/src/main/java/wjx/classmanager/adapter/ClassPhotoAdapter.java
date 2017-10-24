package wjx.classmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wjx.classmanager.model.BmobPhoto;
import wjx.classmanager.widget.BmobPhotoItemView;

/**
 * Created by wjx on 2017/10/18.
 */

public class ClassPhotoAdapter extends RecyclerView.Adapter<ClassPhotoAdapter.ClassPhotoViewHolder> {

    private List<BmobPhoto> mClassPhotos = new ArrayList<>();
    private Context mContext;

    public ClassPhotoAdapter(List<BmobPhoto> classPhotos){
        mClassPhotos=classPhotos;
    }

    @Override
    public ClassPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext =parent.getContext();
        return new ClassPhotoViewHolder(new BmobPhotoItemView(mContext));
    }

    @Override
    public void onBindViewHolder(ClassPhotoViewHolder holder, int position) {
        holder.mBmobPhotoItemView.bindView(mContext,mClassPhotos.get(position));
    }

    @Override
    public int getItemCount() {
        return mClassPhotos.size();
    }

    class ClassPhotoViewHolder extends RecyclerView.ViewHolder{

        private BmobPhotoItemView mBmobPhotoItemView;

        public ClassPhotoViewHolder(BmobPhotoItemView itemView) {
            super(itemView);
            mBmobPhotoItemView =itemView;
        }
    }

    public void addPhoto(BmobPhoto classPhoto){
        mClassPhotos.add(0,classPhoto);
        Log.e( "addPhoto: ",classPhoto.getUrl() + "=-="+classPhoto.getDate() );
        notifyItemInserted(0);
    }
}
