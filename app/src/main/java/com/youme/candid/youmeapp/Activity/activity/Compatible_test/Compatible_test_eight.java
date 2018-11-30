package com.youme.candid.youmeapp.Activity.activity.Compatible_test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.youme.candid.youmeapp.Activity.activity.MyProfileActiivty;
import com.youme.candid.youmeapp.Activity.activity.UserProfileActivity;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.R;

public class Compatible_test_eight extends AppCompatActivity {

    ImageView img_nxt,img_previous;
    SessionManager sessionManager;
    RadioButton radioButton_yes,radioButton_no;
    Context context;
    TextView btn_reset_txtview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compatible_test_eight);

        //for initialize all the view this method will be invoked
        init();
        //for all click events this method will be invoked
        click();
    }

    //view initializtion....//
    private  void init(){
        context = this;
        sessionManager = new SessionManager(context);
        radioButton_yes = (RadioButton)findViewById(R.id.c_eight_yes);
        radioButton_no = (RadioButton)findViewById(R.id.c_eight_no);
        img_nxt = (ImageView)findViewById(R.id.ctesteight_next_step);
        img_previous = (ImageView)findViewById(R.id.ctesteight_previous_step);
        btn_reset_txtview = (TextView)findViewById(R.id.reset_btn);



        final String test_eight = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_EIGHT);
        System.out.println(" test 8" + test_eight);

        if (test_eight != null) {
            if (test_eight.equalsIgnoreCase("Truth")) {
                radioButton_yes.setChecked(true);
            } else if (test_eight.equalsIgnoreCase("Dare")) {
                radioButton_no.setChecked(true);
            }
        }




    }

    //click events.......//
    private void click() {


        radioButton_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked==true)
                {
                    radioButton_no.setChecked(false);
                    radioButton_yes.setChecked(true);
                }
                else
                {
                    radioButton_yes.setChecked(false);
                    radioButton_no.setChecked(true);
                }
            }
        });

        radioButton_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked==true)
                {
                    radioButton_no.setChecked(true);
                    radioButton_yes.setChecked(false);
                }
                else
                {
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
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_EIGHT,"Truth");
                        startActivity(new Intent(Compatible_test_eight.this, Compatible_test_nine.class));
                    }
                    else if(radioButton_no.isChecked()==true)
                    {
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_EIGHT,"Dare");
                        startActivity(new Intent(Compatible_test_eight.this, Compatible_test_nine.class));
                    }
                }


            }
        });

        //without complete the test
        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Compatible_test_eight.this, Compatible_test_seven.class));
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
