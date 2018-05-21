package info.pelleritoudacity.android.rcapstone.media;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;

import info.pelleritoudacity.android.rcapstone.R;

public class ImaExoPlayer {

    private final ImaAdsLoader imaAdsLoader;
    private Context mContext;

    public ImaExoPlayer(Context context, Uri uriAdTagUrl) {
        mContext = context;
        imaAdsLoader = new ImaAdsLoader(mContext,uriAdTagUrl );
    }



}
