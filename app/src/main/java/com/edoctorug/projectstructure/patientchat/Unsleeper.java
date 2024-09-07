package com.edoctorug.projectstructure.patientchat;

import android.app.Application;
import android.util.Log;

import com.edoctorug.projectstructure.patientchat.constants.ConnectionParams;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Unsleeper {
    OkHttpClient okhttpclient;
    Response http_response = null;
    String http_url = ConnectionParams.hospital_url;
    int http_port = ConnectionParams.hospital_port;
    String path = "";
    String send_http_url;
    public Response unsleep(){

        okhttpclient = new OkHttpClient.Builder().build();

        if(http_port>0) //check if port is specified
        {
            if(http_url.contains("http") || http_url.contains("https"))
            {
                send_http_url = http_url+":"+http_port+path;
            }
            else
            {
                send_http_url = "http://"+http_url+":"+http_port+path;
            }

        }
        else
        {
            if(http_url.contains("http") || http_url.contains("https")) {
                send_http_url = http_url + path;
            }
            else{
                send_http_url = "http://" + http_url + path;
            }
        }
        Log.i("Using SPLASH URL",send_http_url);
        Request arequest = new Request.Builder().url(send_http_url).get().build();


        try
        {
            int status_code;

            do{
                http_response = okhttpclient.newCall(arequest).execute();
                String response_str = http_response.body().string();
                //System.out.println(response_str);
                status_code = http_response.code();
                Log.i("UNSLEEPER RESPONSE",""+status_code+"\n\n"+response_str);
            }while(status_code!=200);

           return http_response;


        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            Log.e("error message",ioe.getMessage());
            return null;
        }
    }


}
