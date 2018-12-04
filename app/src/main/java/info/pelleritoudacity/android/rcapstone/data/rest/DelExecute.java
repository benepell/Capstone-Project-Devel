package info.pelleritoudacity.android.rcapstone.data.rest;


import androidx.annotation.NonNull;
import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DelExecute {

    private final OnRestCallBack mCallback;
    private static RedditAPI sApi;
    private final String mCode;
    private final String mId;

    public DelExecute(OnRestCallBack callback, String code, String id) {

        sApi = RetrofitClient.createService(Costant.REDDIT_OAUTH_URL, null);
        mCallback = callback;
        mCode = code;
        mId = id;

    }

    public void delData() {

        sApi.postDelAuth(TextUtil.authCode(mCode), mId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                mCallback.success(response.body(), response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mCallback.unexpectedError(t);
            }
        });

    }

    public interface OnRestCallBack {

        void success(@SuppressWarnings("unused") ResponseBody response, int code);

        @SuppressWarnings("EmptyMethod")
        void unexpectedError(@SuppressWarnings("unused") Throwable tList);
    }

}
