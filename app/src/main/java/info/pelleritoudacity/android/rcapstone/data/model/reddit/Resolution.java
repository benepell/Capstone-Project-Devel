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

public class Resolution implements Parcelable
{

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    public final static Parcelable.Creator<Resolution> CREATOR = new Creator<Resolution>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Resolution createFromParcel(Parcel in) {
            return new Resolution(in);
        }

        public Resolution[] newArray(int size) {
            return (new Resolution[size]);
        }

    }
    ;

    Resolution(Parcel in) {
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Resolution() {
    }

    public String getUrl() {
        return url;
    }

    @SuppressWarnings("unused")
    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWidth() {
        return width;
    }

    @SuppressWarnings("unused")
    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    @SuppressWarnings("unused")
    public void setHeight(Integer height) {
        this.height = height;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(url);
        dest.writeValue(width);
        dest.writeValue(height);
    }

    public int describeContents() {
        return  0;
    }

}
