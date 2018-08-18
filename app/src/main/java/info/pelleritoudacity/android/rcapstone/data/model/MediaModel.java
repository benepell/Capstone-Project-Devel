package info.pelleritoudacity.android.rcapstone.data.model;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.ui.PlayerView;

public class MediaModel {

    private ImaAdsLoader imaAdsLoader;

    private PlayerView playerView;

    private ProgressBar progressBar;

    private ImageView imagePlay;
    private ImageView imagePreview;

    private TextView tvErrorPlayer;


    public MediaModel() {
    }

    public ImaAdsLoader getImaAdsLoader() {
        return imaAdsLoader;
    }

    public void setImaAdsLoader(ImaAdsLoader imaAdsLoader) {
        this.imaAdsLoader = imaAdsLoader;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public void setPlayerView(PlayerView playerView) {
        this.playerView = playerView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public TextView getTvErrorPlayer() {
        return tvErrorPlayer;
    }

    public void setTvErrorPlayer(TextView tvErrorPlayer) {
        this.tvErrorPlayer = tvErrorPlayer;
    }

    public ImageView getImagePlay() {
        return imagePlay;
    }

    public void setImagePlay(ImageView imagePlay) {
        this.imagePlay = imagePlay;
    }

    public ImageView getImagePreview() {
        return imagePreview;
    }

    public void setImagePreview(ImageView imagePreview) {
        this.imagePreview = imagePreview;
    }

}
