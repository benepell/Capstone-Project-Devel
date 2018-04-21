package info.pelleritoudacity.android.rcapstone.rest;

import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.model.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginExecute {
    private final LoginManager loginManager;
    private Login mLogin;

    public LoginExecute(String username, String password) {
        loginManager = LoginManager.getInstance(username, password);
    }

    public void loginData(final RestLogin myCallBack) {
        Callback<Login> callback = new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                if (response.isSuccessful()) {
                    mLogin = response.body();
                    myCallBack.onRestLogin(mLogin);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorLogin(t);
                }
            }
        };
        loginManager.getLoginAPI(callback);
    }

    public interface RestLogin {

        void onRestLogin(Login listenerData);

        void onErrorLogin(Throwable t);
    }
}
