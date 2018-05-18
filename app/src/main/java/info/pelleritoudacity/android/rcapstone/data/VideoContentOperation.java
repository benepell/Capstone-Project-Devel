package info.pelleritoudacity.android.rcapstone.data;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

import info.pelleritoudacity.android.rcapstone.model.data.ModelContentVideo;
import info.pelleritoudacity.android.rcapstone.model.rest.Mp4;
import info.pelleritoudacity.android.rcapstone.model.rest.ResolutionMp4;
import info.pelleritoudacity.android.rcapstone.model.rest.SourceMp4;
import info.pelleritoudacity.android.rcapstone.model.rest.T3Data;
import info.pelleritoudacity.android.rcapstone.model.rest.Variants;

public class VideoContentOperation {

    private Context mContext;

    public VideoContentOperation(Context context) {
        mContext = context;
    }

    public ArrayList<ModelContentVideo> showVideoContent(T3Data t3DataChild, boolean originalSize) {

        int sizeResolution = 0;

        Mp4 mp4 = null;
        SourceMp4 dataSourceMp4 = null;
        Variants variants;
        ModelContentVideo modelContentVideo;

        ArrayList<ModelContentVideo> optimizeArrayList;
        List<ResolutionMp4> dataResolutionMp4s = null;

        if ((mContext == null) || (t3DataChild == null) || (t3DataChild.getPreview() == null)) {
            return null;
        }

        optimizeArrayList = new ArrayList<>(1);
        modelContentVideo = new ModelContentVideo();

        variants = t3DataChild.getPreview().getImages().get(0).getVariants();

        if (variants != null) {
            mp4 = t3DataChild.getPreview().getImages().get(0).getVariants().getMp4();
        }

        if (mp4 != null) {
            dataSourceMp4 = mp4.getSource();
            dataResolutionMp4s = mp4.getResolutions();

            if ((dataResolutionMp4s != null) && (!originalSize)) {
                sizeResolution = t3DataChild.getPreview().getImages().get(0).getVariants().getMp4().getResolutions().size();
            }
        }

        if ((dataResolutionMp4s != null) || (dataSourceMp4 != null)) {
            int index = 0;
            int densityDpi = mContext.getResources().getDisplayMetrics().densityDpi;
            switch (densityDpi) {
                case DisplayMetrics.DENSITY_XXXHIGH:
                case DisplayMetrics.DENSITY_560:
                    // XXXHDPI
                    if (sizeResolution >= 6) {
                        index = 5;
                        modelContentVideo.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContentVideo.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContentVideo.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContentVideo);
                        return optimizeArrayList;
                    }

                case DisplayMetrics.DENSITY_XXHIGH:
                case DisplayMetrics.DENSITY_360:
                case DisplayMetrics.DENSITY_400:
                case DisplayMetrics.DENSITY_420:
                    // XXHDPI
                    if (sizeResolution >= 5) {
                        index = 4;
                        modelContentVideo.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContentVideo.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContentVideo.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContentVideo);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_XHIGH:
                case DisplayMetrics.DENSITY_280:
                    // XHDPI
                    if (sizeResolution >= 4) {
                        index = 3;
                        modelContentVideo.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContentVideo.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContentVideo.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContentVideo);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_TV:
                case DisplayMetrics.DENSITY_HIGH:
                    // HDPI
                    if (sizeResolution >= 3) {
                        index = 2;
                        modelContentVideo.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContentVideo.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContentVideo.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContentVideo);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_MEDIUM:
                    // MDPI
                    if (sizeResolution >= 2) {
                        index = 1;
                        modelContentVideo.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContentVideo.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContentVideo.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContentVideo);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_LOW:
                    if (sizeResolution >= 1) {
                        index = 0;
                        modelContentVideo.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContentVideo.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContentVideo.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContentVideo);
                        return optimizeArrayList;
                    }


                default:
                    index = 0;
                    modelContentVideo.setUrl( dataResolutionMp4s.get(index).getUrl());
                    modelContentVideo.setWidth(dataResolutionMp4s.get(index).getWidth());
                    modelContentVideo.setHeight(dataResolutionMp4s.get(index).getHeight());
                    optimizeArrayList.add(modelContentVideo);
                    return optimizeArrayList;
            }
        }
        return null;
    }

}
