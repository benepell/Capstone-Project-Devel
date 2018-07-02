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

public class Gif implements Parcelable
{

    @SerializedName("source")
    @Expose
    private SourceGif source;
    @SerializedName("resolutions")
    @Expose
    private List<ResolutionGif> resolutions = null;
    public final static Parcelable.Creator<Gif> CREATOR = new Creator<Gif>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Gif createFromParcel(Parcel in) {
            return new Gif(in);
        }

        public Gif[] newArray(int size) {
            return (new Gif[size]);
        }

    }
    ;

    protected Gif(Parcel in) {
        this.source = ((SourceGif) in.readValue((SourceGif.class.getClassLoader())));
        in.readList(this.resolutions, (ResolutionGif.class.getClassLoader()));
    }

    public Gif() {
    }

    public SourceGif getSource() {
        return source;
    }

    public void setSource(SourceGif source) {
        this.source = source;
    }

    public List<ResolutionGif> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<ResolutionGif> resolutions) {
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
