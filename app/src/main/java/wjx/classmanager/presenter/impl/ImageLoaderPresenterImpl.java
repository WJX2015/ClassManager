package wjx.classmanager.presenter.impl;

import wjx.classmanager.presenter.ImageLoaderPresenter;
import wjx.classmanager.view.ImageLoaderView;

/**
 * Created by wjx on 2017/10/19.
 */

public class ImageLoaderPresenterImpl implements ImageLoaderPresenter {

    private ImageLoaderView mImageLoaderView;

    public ImageLoaderPresenterImpl(ImageLoaderView imageLoaderView){
        mImageLoaderView=imageLoaderView;
    }
}
