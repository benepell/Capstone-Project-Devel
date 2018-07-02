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

public class T3 implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private T3Listing data;
    public final static Parcelable.Creator<T3> CREATOR = new Creator<T3>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T3 createFromParcel(Parcel in) {
            return new T3(in);
        }

        public T3[] newArray(int size) {
            return (new T3[size]);
        }

    };

    @SuppressWarnings("WeakerAccess")
    protected T3(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((T3Listing) in.readValue((T3Listing.class.getClassLoader())));
    }

    public T3() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public T3Listing getData() {
        return data;
    }

    public void setData(T3Listing data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(kind);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
