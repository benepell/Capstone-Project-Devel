package info.pelleritoudacity.android.rcapstone.utility;

import android.os.Build;
import android.text.Html;

import java.util.ArrayList;
import java.util.Collections;

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

    @Deprecated
    public static String textFromHtml(String text) {

        if (android.text.TextUtils.isEmpty(text)) return "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            //noinspection deprecation
            return Html.fromHtml(text).toString();
        }
    }

}
