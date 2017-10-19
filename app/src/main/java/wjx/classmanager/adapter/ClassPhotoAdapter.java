package wjx.classmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wjx.classmanager.model.ClassPhoto;
import wjx.classmanager.widget.ClassPhotoItemView;

/**
 * Created by wjx on 2017/10/18.
 */

public class ClassPhotoAdapter extends RecyclerView.Adapter<ClassPhotoAdapter.ClassPhotoViewHolder> {

    private List<ClassPhoto> mClassPhotos = new ArrayList<>();
    private Context mContext;

    public ClassPhotoAdapter(List<ClassPhoto> classPhotos){
        mClassPhotos=classPhotos;
    }

    @Override
    public ClassPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext =parent.getContext();
        return new ClassPhotoViewHolder(new ClassPhotoItemView(mContext));
    }

    @Override
    public void onBindViewHolder(ClassPhotoViewHolder holder, int position) {
        holder.mClassPhotoItemView.bindView(mContext,mClassPhotos.get(position));
    }

    @Override
    public int getItemCount() {
        return mClassPhotos.size();
    }

    class ClassPhotoViewHolder extends RecyclerView.ViewHolder{

        private ClassPhotoItemView mClassPhotoItemView;

        public ClassPhotoViewHolder(ClassPhotoItemView itemView) {
            super(itemView);
            mClassPhotoItemView =itemView;
        }
    }

    public void addPhoto(ClassPhoto classPhoto){
        mClassPhotos.add(0,classPhoto);
        notifyItemInserted(0);
    }
}
