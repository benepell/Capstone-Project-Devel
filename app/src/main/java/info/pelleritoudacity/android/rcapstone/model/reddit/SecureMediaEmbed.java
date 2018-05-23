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

import android.os.Parcel;
import android.os.Parcelable;

public class SecureMediaEmbed implements Parcelable
{

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
    }

    public SecureMediaEmbed() {
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return  0;
    }

}
