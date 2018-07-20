/*
 *
 * ______                _____ _
 * | ___ \              /  ___| |
 * | |_/ /___ __ _ _ __ \ `--.| |_ ___  _ __   ___
 * |    // __/ _` | '_ \ `--. \ __/ _ \| '_ \ / _ \
 * | |\ \ (_| (_| | |_) /\__/ / || (_) | | | |  __/
 * \_| \_\___\__,_| .__/\____/ \__\___/|_| |_|\___|
 *                | |
 *                |_|
 *
 * Copyright (C) 2018 Benedetto Pellerito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.pelleritoudacity.android.rcapstone.data.db.Operation;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.model.ModelContent;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.Resolution;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.Source;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3Data;

class ImageContentOperation {

    private final Context mContext;

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
                if(originalSize){
                    Source sourceResolution = t3DataChild.getPreview().getImages().get(index).getSource();
                    modelContentImage.setUrl(sourceResolution.getUrl());
                    modelContentImage.setWidth(sourceResolution.getWidth());
                    modelContentImage.setHeight(sourceResolution.getHeight());

                }else {
                    modelContentImage.setUrl("");
                    modelContentImage.setWidth(0);
                    modelContentImage.setHeight(0);

                }

                optimizeArrayList.add(modelContentImage);
                return optimizeArrayList;
        }
    }

}
