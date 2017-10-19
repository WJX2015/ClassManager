package wjx.classmanager.view;

import java.util.List;

/**
 * Created by wjx on 2017/10/18.
 */

public interface ClassPhotoView {
    void onPicPostSuccess(String fileUrl);

    void onPicPostFailed(String message);

    void onPicPostSuccess(List<String> urls);

    void onStartLoadPic(String s);
}
