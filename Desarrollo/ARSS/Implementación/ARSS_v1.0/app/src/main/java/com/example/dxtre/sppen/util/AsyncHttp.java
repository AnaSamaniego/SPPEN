package com.example.dxtre.sppen.util;

import android.util.Log;

import com.example.dxtre.sppen.model.Parameter;
import com.example.dxtre.sppen.util.interfaces.OnResponseHttp;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by DXtre on 22/06/16.
 */

public class AsyncHttp {

    private static final String BASE_URL = "http://easycode.mx/glamorize/webService/controller_last.php";
    private static int retry=3;
    private static int timeOut = 15 * 1000;

    private static void get(final OnResponseHttp onResponseHttp){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(retry, timeOut);

        RequestParams rpParams = new RequestParams();

        client.get(BASE_URL, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
                onResponseHttp.onStart();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here
                Log.d(ApiHelper.TAG, "Header: " + headers.toString());
                Log.d(ApiHelper.TAG, "Response: " + response.toString());
                onResponseHttp.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                onResponseHttp.onFailure(e);
            }
        });
    }

    public static void post(final OnResponseHttp onResponseHttp, ArrayList<Parameter> params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(retry, timeOut);

        RequestParams rpParams = new RequestParams();

        for (int i=0; i<params.size(); i++){
            rpParams.put(params.get(i).getParameter(), params.get(i).getValue());
        }

        Log.d(ApiHelper.TAG, "Header: " + rpParams.toString());

        client.post(BASE_URL, rpParams, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
                onResponseHttp.onStart();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here
                Log.d(ApiHelper.TAG, "Response: " + response.toString());
                onResponseHttp.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                onResponseHttp.onFailure(e);
            }
        });
    }

}