package ro.pub.cs.systems.eim.lab07.calculatorwebservice.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import ro.pub.cs.systems.eim.lab07.calculatorwebservice.general.Constants;

public class CalculatorWebServiceAsyncTask extends AsyncTask<String, Void, String> {

    private TextView resultTextView;

    public CalculatorWebServiceAsyncTask(TextView resultTextView) {
        this.resultTextView = resultTextView;
    }

    @Override
    protected String doInBackground(String... params) {
        String operator1 = params[0];
        String operator2 = params[1];
        String operation = params[2];
        int method = Integer.parseInt(params[3]);

        // TODO exercise 4
        // signal missing values through error messages
        String errorMessage = null;
        if (operator1 == null || operator1.isEmpty()) {
            errorMessage = Constants.ERROR_MESSAGE_EMPTY;
        }
        if (operator2 == null || operator2.isEmpty()) {
            errorMessage = Constants.ERROR_MESSAGE_EMPTY;
        }
        if (errorMessage != null) {
            return errorMessage;
        }

        // create an instance of a HttpClient object

        // get method used for sending request from methodsSpinner

        // 1. GET
        // a) build the URL into a HttpGet object (append the operators / operations to the Internet address)
        // b) create an instance of a ResultHandler object
        // c) execute the request, thus generating the result
        HttpClient httpClient = new DefaultHttpClient();

        switch(method) {
            case Constants.GET_OPERATION:
                HttpGet httpGet = new HttpGet(Constants.GET_WEB_SERVICE_ADDRESS
                        + "?" + Constants.OPERATION_ATTRIBUTE + "=" + operation
                        + "&" + Constants.OPERATOR1_ATTRIBUTE + "=" + operator1
                        + "&" + Constants.OPERATOR2_ATTRIBUTE + "=" + operator2);
                ResponseHandler<String> responseHandlerGet = new BasicResponseHandler();
                try {
                    return httpClient.execute(httpGet, responseHandlerGet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case Constants.POST_OPERATION:
                // 2. POST
                // a) build the URL into a HttpPost object
                // b) create a list of NameValuePair objects containing the attributes and their values (operators, operation)
                // c) create an instance of a UrlEncodedFormEntity object using the list and UTF-8 encoding and attach it to the post request
                // d) create an instance of a ResultHandler object
                // e) execute the request, thus generating the result

                HttpPost httpPost = new HttpPost(Constants.POST_WEB_SERVICE_ADDRESS);
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair(Constants.OPERATION_ATTRIBUTE, operation));
                params1.add(new BasicNameValuePair(Constants.OPERATOR1_ATTRIBUTE, operator1));
                params1.add(new BasicNameValuePair(Constants.OPERATOR2_ATTRIBUTE, operator2));
                try {
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params1, HTTP.UTF_8);
                    httpPost.setEntity(urlEncodedFormEntity);
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    Log.e(Constants.TAG, unsupportedEncodingException.getMessage());
                    if (Constants.DEBUG) {
                        unsupportedEncodingException.printStackTrace();
                    }
                }

                ResponseHandler<String> responseHandlerPost = new BasicResponseHandler();
                try {
                    return httpClient.execute(httpPost, responseHandlerPost);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // display the result in resultTextView
        resultTextView.setText(result);
    }

}
