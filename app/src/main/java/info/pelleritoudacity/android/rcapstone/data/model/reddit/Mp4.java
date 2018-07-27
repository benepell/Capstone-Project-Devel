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

public class Mp4 implements Parcelable
{

    @SerializedName("source")
    @Expose
    private SourceMp4 source;
    @SerializedName("resolutions")
    @Expose
    private List<ResolutionMp4> resolutions = null;
    public final static Parcelable.Creator<Mp4> CREATOR = new Creator<Mp4>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Mp4 createFromParcel(Parcel in) {
            return new Mp4(in);
        }

        public Mp4 [] newArray(int size) {
            return (new Mp4[size]);
        }

    }
    ;

    Mp4(Parcel in) {
        this.source = ((SourceMp4) in.readValue((SourceMp4.class.getClassLoader())));
        in.readList(this.resolutions, (ResolutionMp4.class.getClassLoader()));
    }

    public Mp4() {
    }

    public SourceMp4 getSource() {
        return source;
    }

    @SuppressWarnings("unused")
    public void setSource(SourceMp4 source) {
        this.source = source;
    }

    public List<ResolutionMp4> getResolutions() {
        return resolutions;
    }

    @SuppressWarnings("unused")
    public void setResolutions(List<ResolutionMp4> resolutions) {
        this.resolutions = resolutions;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(source);
        dest.writeList(resolutions);
    }

    public int describeContents() {
        return  0;
    }

}
