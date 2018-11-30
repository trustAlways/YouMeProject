package com.youme.candid.youmeapp.Activity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.youme.candid.youmeapp.R;

public class SpalshScreen_Activity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spalshscreen_activity);

        // ---------------------------- For Initilize UI -------------------------------------------------------------------------------//
        initUI1();
    }

    private void initUI1() {

        context = SpalshScreen_Activity.this;

        final Thread t = new Thread() {
            @Override
            public void run() {

                try {

                    sleep(3 * 1000);

                    Intent ii = new Intent(SpalshScreen_Activity.this, SignUp.class);
                    startActivity(ii);

                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("$$Exception**=" + e);
                }

            }
        };
        t.start();
    }
}
