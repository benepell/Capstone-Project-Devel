package info.pelleritoudacity.android.rcapstone.media;

import android.content.Context;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import info.pelleritoudacity.android.rcapstone.utility.Costants;

public class ExoPlayerManager {
    private Context mContext;

    public ExoPlayerManager(Context context) {
        mContext = context;
    }

    protected SimpleExoPlayer createPlayer(SimpleExoPlayer player, boolean isRendering, boolean isAdaptiveStreaming) {

        int extensionRendererMode;

        if (isRendering) {
            extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON;

        } else {
            extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;

        }

        if (isAdaptiveStreaming) {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            return ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(mContext, extensionRendererMode)
                    , trackSelector, loadControl);


        }

        return ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(mContext, extensionRendererMode)
                , new DefaultTrackSelector());


    }

    protected DefaultDataSourceFactory createDataSourceFactory(boolean isAdaptiveStreaming) {

        String userAgent = Util.getUserAgent(mContext, Costants.USER_AGENT_MEDIA);
        DefaultDataSourceFactory dataSourceFactory;

        if (isAdaptiveStreaming) {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

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
