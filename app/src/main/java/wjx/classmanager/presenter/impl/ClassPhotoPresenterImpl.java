package wjx.classmanager.presenter.impl;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import wjx.classmanager.presenter.ClassPhotoPresenter;
import wjx.classmanager.view.ClassPhotoView;

/**
 * Created by wjx on 2017/10/18.
 */

public class ClassPhotoPresenterImpl implements ClassPhotoPresenter {

    private ClassPhotoView mClassPhotoView;

    public ClassPhotoPresenterImpl(ClassPhotoView classPhotoView){
        mClassPhotoView=classPhotoView;
    }

    @Override
    public void postPicToBmob(String path) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    mClassPhotoView.onPicPostSuccess(bmobFile.getFileUrl());
                }else{
                    mClassPhotoView.onPicPostFailed(e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }
}
