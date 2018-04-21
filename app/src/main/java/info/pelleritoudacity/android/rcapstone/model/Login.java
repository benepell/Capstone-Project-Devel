package info.pelleritoudacity.android.rcapstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("json")
    @Expose
    private LoginJson mLoginJson;

    public LoginJson getJson() {
        return mLoginJson;
    }

    public void setJson(LoginJson json) {
        mLoginJson = json;
    }

    @Override
    public String toString() {
        return "Login{" +
                "json=" + mLoginJson +
                '}';
    }

}
