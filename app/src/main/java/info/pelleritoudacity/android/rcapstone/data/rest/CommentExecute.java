package info.pelleritoudacity.android.rcapstone.data.rest;

import android.support.annotation.NonNull;

import java.util.HashMap;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.SubmitData;
import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentExecute {

    private final OnRestCallBack mCallback;
    private static RedditAPI sApi;
    private final String mCode;
    private final String mText;
    private final String mFullname;

    public CommentExecute(OnRestCallBack callback,String code ,String text, String fullname) {

        sApi = RetrofitClient.createService(Costant.REDDIT_OAUTH_URL, SubmitData.class);
        mCallback = callback;
        mCode = code;
        mText = text;
        mFullname = fullname;

    }

    public void postData() {

        HashMap<String, String> fieldMap;

        fieldMap = new HashMap<>();
        fieldMap.put("text", mText);
        fieldMap.put("thing_id", mFullname);


        sApi.postCommentAuth(TextUtil.authCode(mCode), fieldMap).enqueue(new Callback<SubmitData>() {
            @Override
            public void onResponse(@NonNull Call<SubmitData> call, @NonNull Response<SubmitData> response) {
                mCallback.success(response.body(),response.code());
            }

            @Override
            public void onFailure(@NonNull Call<SubmitData> call, @NonNull Throwable t) {
                mCallback.unexpectedError(t);
            }
        });

    }

    public interface OnRestCallBack {

        void success(@SuppressWarnings("unused") SubmitData response, @SuppressWarnings("unused") int code);

        @SuppressWarnings("EmptyMethod")
        void unexpectedError(@SuppressWarnings("unused") Throwable tList);
    }

}
