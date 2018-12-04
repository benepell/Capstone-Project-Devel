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


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class RedditToken {
 
    @SerializedName("scope")
    @Expose
    private String scope;
 
    @SerializedName("token_type") 
    @Expose 
    private String token_type;
 
    @SerializedName("expires_in") 
    @Expose 
    private long expires_in;
 
    @SerializedName("access_token") 
    @Expose 
    private String access_token;
 
    @SerializedName("refresh_token") 
    @Expose 
    private String refresh_token;
 
    @SerializedName("error") 
    @Expose 
    private String error;
 
    @SuppressWarnings("unused")
    public String getScope() {
        return scope;
    } 
 
    public String getRefresh_token() {
        return refresh_token;
    } 
 
    @SuppressWarnings("unused")
    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    } 
 
    @SuppressWarnings("unused")
    public void setScope(String scope) {
        this.scope = scope;
    } 
 
    @SuppressWarnings("unused")
    public String getToken_type() {
        return token_type;
    } 
 
    @SuppressWarnings("unused")
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    } 
 
    public long getExpires_in() { 
        return expires_in;
    } 
 
    @SuppressWarnings("unused")
    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    } 
 
    public String getAccess_token() {
        return access_token;
    } 
 
    @SuppressWarnings("unused")
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    } 
 
    @NonNull
    @Override
    public String toString() {
        return "RedditToken{" + 
                "scope='" + scope + '\'' +
                ", token_type='" + token_type + '\'' +
                ", error='" + error + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                '}'; 
    } 
 
    @SuppressWarnings("unused")
    public String getError() {
        return error;
    } 
 
    @SuppressWarnings("unused")
    public void setError(String error) {
        this.error = error;
    } 
} 