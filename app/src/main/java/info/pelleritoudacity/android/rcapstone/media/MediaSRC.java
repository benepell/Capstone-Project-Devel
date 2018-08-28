package info.pelleritoudacity.android.rcapstone.media;


import android.content.Context;

import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import info.pelleritoudacity.android.rcapstone.utility.Costant;

class MediaSRC {

    private final Context mContext;

    public MediaSRC(Context context) {
        mContext = context;
    }

    public DefaultDataSourceFactory createDataSourceFactory(boolean isAdaptiveStreaming) {

        String userAgent = Util.getUserAgent(mContext, Costant.USER_AGENT_MEDIA);

        if (isAdaptiveStreaming) {
            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
            DefaultHttpDataSourceFactory defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory(
                    userAgent,
                    defaultBandwidthMeter);

            return new DefaultDataSourceFactory(
                    mContext,
                    defaultBandwidthMeter,
                    defaultHttpDataSourceFactory);

        } else {

            return new DefaultDataSourceFactory(mContext,
                    Util.getUserAgent(mContext, userAgent));

        }

    }

}
