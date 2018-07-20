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

package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Preview implements Parcelable
{

    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("reddit_video_preview")
    @Expose
    private RedditVideoPreview redditVideoPreview;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;

    public final static Parcelable.Creator<Preview> CREATOR = new Creator<Preview>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Preview createFromParcel(Parcel in) {
            return new Preview(in);
        }

        public Preview[] newArray(int size) {
            return (new Preview[size]);
        }

    }
    ;

    Preview(Parcel in) {
        in.readList(this.images, (Image.class.getClassLoader()));
        this.enabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.redditVideoPreview = ((RedditVideoPreview) in.readValue((RedditVideoPreview.class.getClassLoader())));
    }

    public Preview() {
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public RedditVideoPreview getRedditVideoPreview() {
        return redditVideoPreview;
    }

    public void setRedditVideoPreview(RedditVideoPreview redditVideoPreview) {
        this.redditVideoPreview = redditVideoPreview;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(images);
        dest.writeValue(redditVideoPreview);
        dest.writeValue(enabled);
    }

    public int describeContents() {
        return  0;
    }

}
