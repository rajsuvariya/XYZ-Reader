package com.iamraj.xyzreader.ui;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by HP-HP on 30-03-2016.
 */
public class ImageLoadingUtils {

    private static ImageRequest imageRequest;

    public static void load(final SimpleDraweeView imageView, final String url)
    {

        imageRequest= ImageRequest.fromUri(url);
        CacheKey cacheKey= DefaultCacheKeyFactory.getInstance()
                .getEncodedCacheKey(imageRequest, null);
        BinaryResource resource = ImagePipelineFactory.getInstance()
                .getMainFileCache().getResource(cacheKey);

        if(resource!=null) {
            Uri uri = Uri.parse(url);
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .build();

            imageView.setController(draweeController);
        }
        else {
            networkLoad(imageView,url);
        }
    }

    private static void networkLoad(final SimpleDraweeView imageView, final String url)
    {

        Uri uri = Uri.parse(url);

        imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setImageRequest(imageRequest)
                .setTapToRetryEnabled(true)
                .build();

        imageView.setController(draweeController);
    }

    public static ImageRequest getCurrentImageRequest() {
        return imageRequest;
    }

}
