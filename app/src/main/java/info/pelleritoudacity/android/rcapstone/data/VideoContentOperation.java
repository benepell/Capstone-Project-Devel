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

package info.pelleritoudacity.android.rcapstone.data;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.model.ModelContent;
import info.pelleritoudacity.android.rcapstone.model.reddit.Mp4;
import info.pelleritoudacity.android.rcapstone.model.reddit.ResolutionMp4;
import info.pelleritoudacity.android.rcapstone.model.reddit.SourceMp4;
import info.pelleritoudacity.android.rcapstone.model.reddit.T3Data;
import info.pelleritoudacity.android.rcapstone.model.reddit.Variants;

public class VideoContentOperation {

    private final Context mContext;

    public VideoContentOperation(Context context) {
        mContext = context;
    }

    public ArrayList<ModelContent> showVideoContent(T3Data t3DataChild, boolean originalSize) {

        int sizeResolution = 0;

        Mp4 mp4 = null;
        SourceMp4 dataSourceMp4 = null;
        Variants variants;
        ModelContent modelContent;

        ArrayList<ModelContent> optimizeArrayList;
        List<ResolutionMp4> dataResolutionMp4s = null;

        if ((mContext == null) || (t3DataChild == null) || (t3DataChild.getPreview() == null)) {
            return null;
        }

        optimizeArrayList = new ArrayList<>(1);
        modelContent = new ModelContent();

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
            int index;
            int densityDpi = mContext.getResources().getDisplayMetrics().densityDpi;
            switch (densityDpi) {
                case DisplayMetrics.DENSITY_XXXHIGH:
                case DisplayMetrics.DENSITY_560:
                    // XXXHDPI
                    if (sizeResolution >= 6) {
                        index = 5;
                        modelContent.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContent.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContent.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContent);
                        return optimizeArrayList;
                    }

                case DisplayMetrics.DENSITY_XXHIGH:
                case DisplayMetrics.DENSITY_360:
                case DisplayMetrics.DENSITY_400:
                case DisplayMetrics.DENSITY_420:
                    // XXHDPI
                    if (sizeResolution >= 5) {
                        index = 4;
                        modelContent.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContent.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContent.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContent);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_XHIGH:
                case DisplayMetrics.DENSITY_280:
                    // XHDPI
                    if (sizeResolution >= 4) {
                        index = 3;
                        modelContent.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContent.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContent.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContent);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_TV:
                case DisplayMetrics.DENSITY_HIGH:
                    // HDPI
                    if (sizeResolution >= 3) {
                        index = 2;
                        modelContent.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContent.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContent.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContent);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_MEDIUM:
                    // MDPI
                    if (sizeResolution >= 2) {
                        index = 1;
                        modelContent.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContent.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContent.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContent);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_LOW:
                    if (sizeResolution >= 1) {
                        index = 0;
                        modelContent.setUrl( dataResolutionMp4s.get(index).getUrl());
                        modelContent.setWidth(dataResolutionMp4s.get(index).getWidth());
                        modelContent.setHeight(dataResolutionMp4s.get(index).getHeight());
                        optimizeArrayList.add(modelContent);
                        return optimizeArrayList;
                    }


                default:
                    index = 0;
                    modelContent.setUrl( Objects.requireNonNull(dataResolutionMp4s).get(index).getUrl());
                    modelContent.setWidth(dataResolutionMp4s.get(index).getWidth());
                    modelContent.setHeight(dataResolutionMp4s.get(index).getHeight());
                    optimizeArrayList.add(modelContent);
                    return optimizeArrayList;
            }
        }
        return null;
    }

}
