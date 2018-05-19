package info.pelleritoudacity.android.rcapstone.data;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

import info.pelleritoudacity.android.rcapstone.model.ModelContent;
import info.pelleritoudacity.android.rcapstone.model.reddit.Resolution;
import info.pelleritoudacity.android.rcapstone.model.reddit.Source;
import info.pelleritoudacity.android.rcapstone.model.reddit.T3Data;

public class ImageContentOperation {

    private Context mContext;

    public ImageContentOperation(Context context) {
        mContext = context;
    }


    public ArrayList<ModelContent> showImageT3(T3Data t3DataChild, boolean originalSize) {

        int sizeResolution = 0;
        ArrayList<ModelContent> optimizeArrayList;
        ModelContent modelContentImage;

        if ((mContext == null) || (t3DataChild == null) || (t3DataChild.getPreview() == null)) {
            return null;
        }

        int densityDpi = mContext.getResources().getDisplayMetrics().densityDpi;


        if ((t3DataChild.getPreview().getImages() != null) && (!originalSize)) {

            sizeResolution = t3DataChild.getPreview().getImages().get(0).getResolutions().size();

        }

        optimizeArrayList = new ArrayList<>(1);
        modelContentImage = new ModelContent();
        List<Resolution> dataResolution = t3DataChild.getPreview().getImages().get(0).getResolutions();
        Source dataSource = t3DataChild.getPreview().getImages().get(0).getSource();

        int index;
        switch (densityDpi) {
            case DisplayMetrics.DENSITY_XXXHIGH:
            case DisplayMetrics.DENSITY_560:
                // XXXHDPI
                if (sizeResolution >= 6) {
                    index = 5;
                    modelContentImage.setUrl(dataResolution.get(index).getUrl());
                    modelContentImage.setWidth(dataResolution.get(index).getWidth());
                    modelContentImage.setHeight(dataResolution.get(index).getHeight());
                    optimizeArrayList.add(modelContentImage);
                    return optimizeArrayList;
                }

            case DisplayMetrics.DENSITY_XXHIGH:
            case DisplayMetrics.DENSITY_360:
            case DisplayMetrics.DENSITY_400:
            case DisplayMetrics.DENSITY_420:
                // XXHDPI
                if (sizeResolution >= 5) {
                    index = 4;
                    modelContentImage.setUrl(dataResolution.get(index).getUrl());
                    modelContentImage.setWidth(dataResolution.get(index).getWidth());
                    modelContentImage.setHeight(dataResolution.get(index).getHeight());
                    optimizeArrayList.add(modelContentImage);
                    return optimizeArrayList;
                }


            case DisplayMetrics.DENSITY_XHIGH:
            case DisplayMetrics.DENSITY_280:
                // XHDPI
                if (sizeResolution >= 4) {
                    index = 3;
                    modelContentImage.setUrl(dataResolution.get(index).getUrl());
                    modelContentImage.setWidth(dataResolution.get(index).getWidth());
                    modelContentImage.setHeight(dataResolution.get(index).getHeight());
                    optimizeArrayList.add(modelContentImage);
                    return optimizeArrayList;
                }


            case DisplayMetrics.DENSITY_TV:
            case DisplayMetrics.DENSITY_HIGH:
                // HDPI
                if (sizeResolution >= 3) {
                    index = 2;
                    modelContentImage.setUrl(dataResolution.get(index).getUrl());
                    modelContentImage.setWidth(dataResolution.get(index).getWidth());
                    modelContentImage.setHeight(dataResolution.get(index).getHeight());
                    optimizeArrayList.add(modelContentImage);
                    return optimizeArrayList;
                }


            case DisplayMetrics.DENSITY_MEDIUM:
                // MDPI
                if (sizeResolution >= 2) {
                    index = 1;
                    modelContentImage.setUrl(dataResolution.get(index).getUrl());
                    modelContentImage.setWidth(dataResolution.get(index).getWidth());
                    modelContentImage.setHeight(dataResolution.get(index).getHeight());
                    optimizeArrayList.add(modelContentImage);
                    return optimizeArrayList;
                }


            case DisplayMetrics.DENSITY_LOW:
                if (sizeResolution >= 1) {
                    index = 0;
                    modelContentImage.setUrl(dataResolution.get(index).getUrl());
                    modelContentImage.setWidth(dataResolution.get(index).getWidth());
                    modelContentImage.setHeight(dataResolution.get(index).getHeight());
                    optimizeArrayList.add(modelContentImage);
                    return optimizeArrayList;
                }


            default:
                index = 0;
                modelContentImage.setUrl(dataResolution.get(index).getUrl());
                modelContentImage.setWidth(dataResolution.get(index).getWidth());
                modelContentImage.setHeight(dataResolution.get(index).getHeight());
                optimizeArrayList.add(modelContentImage);
                return optimizeArrayList;
        }
    }

}
