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

package info.pelleritoudacity.android.rcapstone.utility;

import android.net.Uri;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

import timber.log.Timber;

public class TextUtil {

    public static ArrayList<String> stringToArray(String string) {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, string.split(Costants.STRING_SEPARATOR));
        return arrayList;
    }

    public static String arrayToString(ArrayList<String> arrayList) {
        if (arrayList != null) {
            StringBuilder builder = new StringBuilder();

            for (String s : arrayList) {
                builder.append(s).append(Costants.STRING_SEPARATOR);
            }
            String str = builder.toString();

            if (str.length() > 0) {
                str = str.substring(0, str.length() - 1);
            }
            return str;
        }
        return "";
    }

    public static String normalizeSubRedditLink(String link) {
        return (!android.text.TextUtils.isEmpty(link)) ?
                link.replace("/r/", "")
                        .replaceAll("[^a-zA-Z0-9]", "")
                        .trim() : "";
    }

    public static String textFromHtml(String url) {
        try {
            if (!TextUtils.isEmpty(url)) {
                url = URLEncoder.encode(url, StandardCharsets.UTF_8.name());
                url = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
                url = url.replaceAll("&amp;", "&");
                url = url.replaceAll("&lt;", "<");
                url = url.replaceAll("&gt;", ">");
            }

        } catch (UnsupportedEncodingException e) {
            Timber.e("Encoder error %s", e.getMessage());
            return null;
        }

        return url;
    }

    public static String buildCommentLink(String redditnamePrefix, String redditNameId) {
        Uri.Builder builderRedditComments = new Uri.Builder();

            return builderRedditComments.scheme("https")
                    .authority(Costants.REDDIT_AUTH_URL)
                    .appendEncodedPath(redditnamePrefix)
                    .appendPath(Costants.REDDIT_COMMENTS)
                    .appendPath(redditNameId).build().toString();

    }

    public static String buildCommentDetailLink(String permalink) {
        Uri.Builder builderRedditComments = new Uri.Builder();

        return builderRedditComments.scheme("https")
                .authority(Costants.REDDIT_AUTH_URL)
                .appendEncodedPath(permalink).build().toString();

    }
    public static String youtubeValue(String youtubeUrl) {
        int nSearchLeft;
        int nSearchRight;

        Uri uri = null;
        try {
            uri = Uri.parse(URLDecoder.decode(youtubeUrl, StandardCharsets.UTF_8.name()));

        } catch (UnsupportedEncodingException e) {
            Timber.e("youtubeValue error %s", e.getMessage());
        }

        if (uri.getAuthority().equals("www.youtube.com")) {

            String v = uri.getQueryParameter("v");

            if ((v == null) && (uri.getEncodedPath().equals("/attribution_link"))) {
                return Uri.parse(uri.getQueryParameter("u")).getQueryParameter("v").toString();
            }
            return v;

        } else if (uri.getAuthority().equals("youtu.be")) {
            youtubeUrl = uri.getEncodedPath();

            nSearchLeft = youtubeUrl.indexOf("/");
            nSearchRight = youtubeUrl.indexOf("&");

            if (nSearchLeft >= 0) {
                if (nSearchRight > 0) {
                    return youtubeUrl.substring(nSearchLeft + 1, nSearchRight);

                } else {
                    return youtubeUrl.substring(nSearchLeft + 1);

                }
            }
        }
        return "";
    }
}
