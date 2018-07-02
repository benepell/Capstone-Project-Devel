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

public class SecureMediaEmbed implements Parcelable
{

    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("scrolling")
    @Expose
    private Boolean scrolling;
    @SerializedName("media_domain_url")
    @Expose
    private String mediaDomainUrl;
    @SerializedName("height")
    @Expose
    private Integer height;
    public final static Parcelable.Creator<SecureMediaEmbed> CREATOR = new Creator<SecureMediaEmbed>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SecureMediaEmbed createFromParcel(Parcel in) {
            return new SecureMediaEmbed(in);
        }

        public SecureMediaEmbed[] newArray(int size) {
            return (new SecureMediaEmbed[size]);
        }

    }
            ;

    protected SecureMediaEmbed(Parcel in) {
        this.content = ((String) in.readValue((String.class.getClassLoader())));
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.scrolling = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.mediaDomainUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public SecureMediaEmbed() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Boolean getScrolling() {
        return scrolling;
    }

    public void setScrolling(Boolean scrolling) {
        this.scrolling = scrolling;
    }

    public String getMediaDomainUrl() {
        return mediaDomainUrl;
    }

    public void setMediaDomainUrl(String mediaDomainUrl) {
        this.mediaDomainUrl = mediaDomainUrl;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(content);
        dest.writeValue(width);
        dest.writeValue(scrolling);
        dest.writeValue(mediaDomainUrl);
        dest.writeValue(height);
    }

    public int describeContents() {
        return  0;
    }

}
