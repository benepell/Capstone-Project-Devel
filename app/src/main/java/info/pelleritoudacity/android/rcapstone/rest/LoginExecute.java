package info.pelleritoudacity.android.rcapstone.rest;

import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.model.RedditAccessToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginExecute {
    private final LoginManager loginManager;
    private RedditAccessToken mLogin;

    public LoginExecute(String code) {
        loginManager = LoginManager.getInstance(code);
    }

    public void loginData(final RestToken myCallBack) {
        Callback<RedditAccessToken> callback = new Callback<RedditAccessToken>() {
            @Override
            public void onResponse(@NonNull Call<RedditAccessToken> call, @NonNull Response<RedditAccessToken> response) {
                if (response.isSuccessful()) {
                    mLogin = response.body();
                    myCallBack.onRestToken(mLogin);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RedditAccessToken> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorToken(t);
                }
            }
        };
        loginManager.getLoginAPI(callback);
    }

    public interface RestToken {

        void onRestToken(RedditAccessToken listenerData);
        void onErrorToken(Throwable t);
    }
}
