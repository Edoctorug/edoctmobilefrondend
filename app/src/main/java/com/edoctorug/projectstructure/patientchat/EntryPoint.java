package com.edoctorug.projectstructure.patientchat;

import androidx.activity.ComponentActivity;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Response;

public class EntryPoint extends ComponentActivity {

    Unsleeper unsleeper;
    AlertDialog alertDialog;
    Thread unsleeper_thread = new Thread(new Runnable() {
        @Override
        public void run() {
            Response unsleeper_response = unsleeper.unsleep();
            if(unsleeper_response==null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertDialog = new AlertDialog.Builder(this.getApplicationContext()).create();
        unsleeper = new Unsleeper();
        //setContentView(R.layout.activity_entry_point);
        unsleeper_thread.start();

        try{
            unsleeper_thread.join();
            Toast.makeText(getApplicationContext(),"server connected",Toast.LENGTH_LONG).show();
            //showMyDialog("CONNECTION STATUS","Conection successful");
            startActivity(new Intent(this, MainActivity.class));
        }
        catch (InterruptedException ioe){
            Toast.makeText(getApplicationContext(),"server failed",Toast.LENGTH_LONG).show();
            ioe.printStackTrace();
        }


       //showMyDialog("CONNECTION STATUS","Conection error");
        finish();

    }

    public void showMyDialog(String title, String message){
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK",(btn,params)->{
            btn.dismiss();
                }
        );
        alertDialog.show();

    }
}