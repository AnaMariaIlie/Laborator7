package ro.pub.cs.systems.eim.lab07.googlesearcher.network;

import android.os.AsyncTask;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import ro.pub.cs.systems.eim.lab07.googlesearcher.general.Constants;

public class GoogleSearcherAsyncTask extends AsyncTask<String, Void, String> {

    private WebView googleResultsWebView;

    public GoogleSearcherAsyncTask(WebView googleResultsWebView) {
        this.googleResultsWebView = googleResultsWebView;
    }

    @Override
    protected String doInBackground(String... params) {

        // TODO exercise 6b)
        // create an instance of a HttpClient object
        // create an instance of a HttpGet object, encapsulating the base Internet address (http://www.google.com) and the keyword
        // create an instance of a ResponseHandler object
        // execute the request, thus generating the result
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(Constants.GOOGLE_INTERNET_ADDRESS + params[0]);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try {
            return httpClient.execute(httpGet, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onPostExecute(String content) {

        // TODO exercise 6b)
        // display the result into the googleResultsWebView through loadDataWithBaseURL() method
        // - base Internet address is http://www.google.com
        // - page source code is the response
        // - mimetype is text/html
        // - encoding is UTF-8
        // - history is null

        googleResultsWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        googleResultsWebView.loadDataWithBaseURL(
                Constants.GOOGLE_INTERNET_ADDRESS,
                content,
                Constants.MIME_TYPE,
                Constants.CHARACTER_ENCODING,
                null);

    }
}
