package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;


public class WebviewActivity extends BaseActivity {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.reddit_webview)
    protected WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_webview);
        super.onCreate(savedInstanceState);

        getWebview(Preference.getLastCategory(getApplicationContext()));

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void getWebview(String category) {

        String url;
        if (!category.startsWith("/")) {
            category = "/r/".concat(category);
        }

        url = Costant.REDDIT_BASE_URL + category + "/" + "submit";

        CookieManager.getInstance().setAcceptThirdPartyCookies(mWebview, true);
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.contains(Costant.REDDIT_WEBVIEW_USE_APP)) {
                    finish();
                }

            }
        });

        mWebview.loadUrl(url);
    }

}
