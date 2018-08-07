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
 *//*
 *  _    _  _     _  _______     ___                      _
 * ( )  ( )( )   ( )(_____  )   |  _`\                   ( )
 * `\`\/'/'`\`\_/'/'     /'/'   | (_) )   __     _ _    _| |   __   _ __
 *   >  <    `\ /'     /'/'     | ,  /  /'__`\ /'_` ) /'_` | /'__`\( '__)
 *  /'/\`\    | |    /'/'___    | |\ \ (  ___/( (_| |( (_| |(  ___/| |
 * (_)  (_)   (_)   (_______)   (_) (_)`\____)`\__,_)`\__,_)`\____)(_)
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

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.util.Objects;

public class ImageUtil {

    public static boolean isSmallImage(Context context, int widthPixel, int heightPixel) {
        if ((context != null) || (widthPixel != 0) || (heightPixel != 0)) {
            int dpW = widthPixel / (int) Objects.requireNonNull(context).getResources().getDisplayMetrics().density;
            int dpH = heightPixel / (int) context.getResources().getDisplayMetrics().density;

            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                return (dpWidth * 0.3) > dpW;

            } else {
                return (dpHeight * 0.3) > dpH;

            }
        }
        return false;
    }

    public static int getColor(Context ctx, int colorResource) {
        if (Build.VERSION.SDK_INT >= 23) {
            return ctx.getColor(colorResource);
        } else {
            return ResourcesCompat.getColor(ctx.getResources(), colorResource, ctx.getTheme());
        }
    }

    public static void createRoundImage(Context context, ImageView imageView, Bitmap resource) {
        if ((context != null) && (imageView != null) && (resource != null)) {
            RoundedBitmapDrawable circularBitmapDrawable;
            circularBitmapDrawable = RoundedBitmapDrawableFactory.create(imageView.getResources(), resource);
            circularBitmapDrawable.setCircular(true);
            imageView.setImageDrawable(circularBitmapDrawable);

        }
    }

    public static String getStringFromColor(Context context, int idColor) {

        return ((context != null) && (idColor != 0)) ? "#" + Integer.toHexString(ContextCompat.getColor(context, idColor)) : null;
    }

}


