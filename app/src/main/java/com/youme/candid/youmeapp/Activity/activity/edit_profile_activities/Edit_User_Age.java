package com.youme.candid.youmeapp.Activity.activity.edit_profile_activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.youme.candid.youmeapp.Activity.activity.Edit_UserProfile_Actiivty;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.R;

public class Edit_User_Age extends AppCompatActivity {

    SeekBar seekbar;
    TextView tv_detail;
    ImageView imageView_backarrow;
    SessionManager sessionManager;
    Context context;
    String user_gender,user_age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__user__age);

        initView();

        //set previous data
        setData();

        // for all the click events
        click();
    }



    public void initView()
    {
         context = this;
         sessionManager = new SessionManager(context);

         imageView_backarrow = (ImageView)findViewById(R.id.edt_age_arrow);
         seekbar = (SeekBar)findViewById(R.id.edt_age_seekbar);
         tv_detail = (TextView)findViewById(R.id.user_gender_age);
    }

    private void setData()
    {
        user_gender =   sessionManager.getData(SessionManager.KEY_GENDER_);
        user_age =      sessionManager.getData(SessionManager.KEY_AGE);

        Log.i("gender","gen "+ user_gender);
        Log.i("gender","gen "+ user_age);

        Toast.makeText(context, "gender age "+user_gender+user_age, Toast.LENGTH_SHORT).show();

        if (user_gender!=null && user_age!=null)
        {
            Log.i("gender","gen "+ user_gender);
            Log.i("gender","gen "+ user_age);
            if (user_gender.equalsIgnoreCase("Female"))
            {
                seekbar.setProgress(Integer.parseInt(user_age));
                tv_detail.setText("I am "+user_gender+", "+user_age+" Years old.");
                Log.i("gender","gen "+ user_gender);
                Log.i("gender","gen "+ user_age);


            }
            else if (user_gender.equalsIgnoreCase("Male"))

            {
                seekbar.setProgress(Integer.parseInt(user_age));
                tv_detail.setText("I am "+user_gender+", "+user_age+" Years old.");
                Log.i("gender","gen "+ user_gender);
                Log.i("gender","gen "+ user_age);


            }
        }

    }
    private void click() {

        imageView_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setData(SessionManager.KEY_GENDER_,user_gender);
                sessionManager.setData(SessionManager.KEY_AGE, String.valueOf(seekbar.getProgress()));
                Intent intent = new Intent(context, Edit_UserProfile_Actiivty.class);
                startActivity(intent);
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress <= 16){
                    seekBar.setProgress(16);
                }
                if(progress >= 16) {
                    tv_detail.setText("I am "+user_gender+", "+progress+" Years old.");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
