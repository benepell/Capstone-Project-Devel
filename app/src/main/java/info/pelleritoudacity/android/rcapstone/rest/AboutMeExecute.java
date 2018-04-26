package info.pelleritoudacity.android.rcapstone.rest;

import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.model.RedditAboutMe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeExecute {
    private final AboutMeManager aboutMeManager;
    private RedditAboutMe mLogin;

    public AboutMeExecute(String code) {
        aboutMeManager = AboutMeManager.getInstance(code);
    }

    public void loginData(final RestAboutMe myCallBack) {
        Callback<RedditAboutMe> callback = new Callback<RedditAboutMe>() {
            @Override
            public void onResponse(@NonNull Call<RedditAboutMe> call, @NonNull Response<RedditAboutMe> response) {
                if (response.isSuccessful()) {
                    mLogin = response.body();
                    myCallBack.onRestAboutMe(mLogin);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RedditAboutMe> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorAboutMe(t);
                }
            }
        };
        aboutMeManager.getAboutMeAPI(callback);
    }

    public interface RestAboutMe {

        void onRestAboutMe(RedditAboutMe listenerData);

        void onErrorAboutMe(Throwable t);
    }
}
