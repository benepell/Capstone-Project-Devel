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

package info.pelleritoudacity.android.rcapstone.model.reddit;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T3Listing implements Parcelable
{

    @SerializedName("modhash")
    @Expose
    private String modhash;
    @SerializedName("dist")
    @Expose
    private Integer dist;
    @SerializedName("children")
    @Expose
    private List<T3ListingData> children = null;
    @SerializedName("after")
    @Expose
    private String after;
    @SerializedName("before")
    @Expose
    private Object before;
    public final static Parcelable.Creator<T3Listing> CREATOR = new Creator<T3Listing>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T3Listing createFromParcel(Parcel in) {
            return new T3Listing(in);
        }

        public T3Listing[] newArray(int size) {
            return (new T3Listing[size]);
        }

    }
    ;

    protected T3Listing(Parcel in) {
        this.modhash = ((String) in.readValue((String.class.getClassLoader())));
        this.dist = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.children, (T3ListingData.class.getClassLoader()));
        this.after = ((String) in.readValue((String.class.getClassLoader())));
        this.before = in.readValue((Object.class.getClassLoader()));
    }

    public T3Listing() {
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public Integer getDist() {
        return dist;
    }

    public void setDist(Integer dist) {
        this.dist = dist;
    }

    public List<T3ListingData> getChildren() {
        return children;
    }

    public void setChildren(List<T3ListingData> children) {
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public Object getBefore() {
        return before;
    }

    public void setBefore(Object before) {
        this.before = before;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(modhash);
        dest.writeValue(dist);
        dest.writeList(children);
        dest.writeValue(after);
        dest.writeValue(before);
    }

    public int describeContents() {
        return  0;
    }

}
