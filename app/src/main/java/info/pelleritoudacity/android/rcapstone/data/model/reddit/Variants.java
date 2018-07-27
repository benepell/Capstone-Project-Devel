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

public class Variants implements Parcelable
{

    @SerializedName("gif")
    @Expose
    private Gif gif;
    @SerializedName("mp4")
    @Expose
    private Mp4 mp4;
    public final static Parcelable.Creator<Variants> CREATOR = new Creator<Variants>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Variants createFromParcel(Parcel in) {
            return new Variants(in);
        }

        public Variants[] newArray(int size) {
            return (new Variants[size]);
        }

    }
            ;

    Variants(Parcel in) {
        this.gif = ((Gif) in.readValue((Gif.class.getClassLoader())));
        this.mp4 = ((Mp4) in.readValue((Mp4.class.getClassLoader())));
    }

    public Variants() {
    }

    @SuppressWarnings("unused")
    public Gif getGif() {
        return gif;
    }

    @SuppressWarnings("unused")
    public void setGif(Gif gif) {
        this.gif = gif;
    }

    public Mp4 getMp4() {
        return mp4;
    }

    @SuppressWarnings("unused")
    public void setMp4(Mp4 mp4) {
        this.mp4 = mp4;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(gif);
        dest.writeValue(mp4);
    }

    public int describeContents() {
        return  0;
    }

}
