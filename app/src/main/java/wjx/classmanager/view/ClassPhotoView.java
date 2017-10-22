package wjx.classmanager.view;

import java.util.List;

import wjx.classmanager.model.BmobPhoto;

/**
 * Created by wjx on 2017/10/18.
 */

public interface ClassPhotoView {
    void onPicPostSuccess(String objectId);

    void onPicPostFailed(String message);

    void onPicPostSuccess(List<String> urls);

    void onStartLoadPic(String s);

    void onUpdatePhotoSuccess(BmobPhoto photo);

    void onUpdatePhotoFailed(String message);
}
