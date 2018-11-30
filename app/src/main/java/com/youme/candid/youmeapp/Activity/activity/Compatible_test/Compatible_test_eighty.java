package com.youme.candid.youmeapp.Activity.activity.Compatible_test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.youme.candid.youmeapp.Activity.activity.MyProfileActiivty;
import com.youme.candid.youmeapp.Activity.activity.UserProfileActivity;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Compatible_test_eighty extends AppCompatActivity {

    ImageView img_nxt, img_previous;
    SessionManager sessionManager;
    RadioButton radioButton_yes, radioButton_no;
    Context context;
    TextView btn_reset_txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compatible_test_eighteen);

        //for initialize all the view this method will be invoked
        init();
        //for all click events this method will be invoked
        click();
    }

    //view initializtion....//
    private void init() {
        context = this;
        sessionManager = new SessionManager(context);
        radioButton_yes = (RadioButton) findViewById(R.id.c_eighty_yes);
        radioButton_no = (RadioButton) findViewById(R.id.c_eighty_no);
        img_nxt = (ImageView) findViewById(R.id.ctesteighty_next_step);
        img_previous = (ImageView) findViewById(R.id.ctesteighty_previous_step);
        btn_reset_txtview = (TextView)findViewById(R.id.reset_btn);

        final String test_eighteen = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_EIGHTEEN);
        System.out.println("test 18"+ test_eighteen);
        if (test_eighteen!=null) {
            if (test_eighteen.equalsIgnoreCase("Yes")) {
                radioButton_yes.setChecked(true);
            } else if (test_eighteen.equalsIgnoreCase("No")) {
                radioButton_no.setChecked(true);
            }
        }
    }

    //click events.......//
    private void click() {
        radioButton_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true) {
                    radioButton_no.setChecked(false);
                    radioButton_yes.setChecked(true);
                } else {
                    radioButton_yes.setChecked(false);
                    radioButton_no.setChecked(true);
                }
            }
        });

        radioButton_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true) {
                    radioButton_no.setChecked(true);
                    radioButton_yes.setChecked(false);
                } else {
                    radioButton_yes.setChecked(true);
                    radioButton_no.setChecked(false);
                }
            }
        });


        //after complete the test
        img_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioButton_yes.isChecked()==false && radioButton_no.isChecked()==false)
                {
                    Toast.makeText(context, "You have to chooose one.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (radioButton_yes.isChecked()==true)
                    {
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_EIGHTEEN, "Yes");
                        startActivity(new Intent(Compatible_test_eighty.this, Compatible_test_ninty.class));
                    }
                    else if(radioButton_no.isChecked()==true)
                    {
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_EIGHTEEN, "No");
                        startActivity(new Intent(Compatible_test_eighty.this, Compatible_test_ninty.class));
                    }
                }


            }
        });

        //without complete the test
        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Compatible_test_eighty.this, Compatible_test_seventy.class));
            }
        });
        btn_reset_txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });
    }

    private void alertDialog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Quit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_ONE,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_TWO,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_THREE,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_FOUR,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_FIVE,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_SIX,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_SEVEN,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_EIGHT,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_NINE,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_TEN,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_ELEVEN,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_TWELVE,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_THIRTEEN,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_FOURTEEN,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_FIFTEEN,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_SIXTEEN,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_SEVENTEEN,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_EIGHTEEN,"");


                        startActivity(new Intent(context, UserProfileActivity.class));

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }


}
