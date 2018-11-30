package com.youme.candid.youmeapp.Activity.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GenderActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    ImageView img_male_select, img_male_unselect, img_female_select, img_female_unselect, next_btn, previous_btn;
    Context context;
    private SeekBar mSeekBar, fSeekbar;
    TextView select_gender, select_age, interval_text_view, male, female;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    FrameLayout frameLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        //For All view Initialization this methos will be invoked
        initView();
/*

        boolean register = sessionManager.isGender();
        {
            if (register)
            {
                startActivity(new Intent(context,ZodiacActivity.class));
            }
        }
*/

        //For All click Events this methos will be invoked
        clickEvents();

        //loadData();
    }

    private void initView() {

        context = this;
        //lets initialize the session here...
        sessionManager = new SessionManager(context);

        //lets initialize the internet connection-detector here...
        connectionDetector = new ConnectionDetector();

        //All textview Initialization will be invoked
        select_gender = (TextView) findViewById(R.id.gender);
        select_age = (TextView) findViewById(R.id.age_textview);
        interval_text_view = (TextView) findViewById(R.id.age_interval);
        male = (TextView) findViewById(R.id.male_text);
        female = (TextView) findViewById(R.id.female_text);

        //All seekbar Initialization will be invoked
        mSeekBar = (SeekBar) findViewById(R.id.male_seekbar);
        fSeekbar = (SeekBar) findViewById(R.id.female_seekbar);

        //All Imageview Initialization will be invoked
        img_male_select = (ImageView) findViewById(R.id.male_icon_selected);
        img_female_select = (ImageView) findViewById(R.id.female_icon_selected);
        img_male_unselect = (ImageView) findViewById(R.id.male_icon);
        img_female_unselect = (ImageView) findViewById(R.id.female_icon);
        next_btn = (ImageView) findViewById(R.id.gender_next_step);
        previous_btn = (ImageView) findViewById(R.id.gender_previous_step);
        // loadData();

        frameLayout = (FrameLayout)findViewById(R.id.gender_loading_frame);
        progressBar = (ProgressBar)findViewById(R.id.gender_progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void clickEvents() {
        //setting click event fornext previous button..
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_age_update();
            }
        });
        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //setting click event for seekbar
        mSeekBar.setOnSeekBarChangeListener(this);
        fSeekbar.setOnSeekBarChangeListener(this);

        //setting click event for  select male female image..
        img_female_select.setOnClickListener(this);
        img_male_select.setOnClickListener(this);
        img_male_unselect.setOnClickListener(this);
        img_female_unselect.setOnClickListener(this);

    }

    String gender;
    String age;

    private void gender_age_update() {
        String mf_gender = select_gender.getText().toString().trim();

        if (mf_gender.equalsIgnoreCase("I am Female,")) {
            gender = "Female";
            Log.i("pos", "fop123" + gender);
            age = String.valueOf(fSeekbar.getProgress());
            Log.i("age", "fage" + age);

            // sessionManager.setData(SessionManager.KEY_GENDER_,female);
            // sessionManager.gender(female, String.valueOf(fSeekbar.getProgress()));
        } else {
            gender = "Male";
            Log.i("pos", "op123" + gender);
            age = String.valueOf(mSeekBar.getProgress());
            Log.i("age", "mage" + age);
        }

        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_AGE_GENDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!", "1235" + response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success")) {
                        saveData();
                        startActivity(new Intent(context, ZodiacActivity.class));
                    }
                    else
                        {
                          Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("gender", gender);
                Log.i("pos", "param" + age);
                params.put("age", age);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    private void saveData() {
        String mf_gender = select_gender.getText().toString().trim();
        Log.i("pos", "op123" + mf_gender);

        if (mf_gender.equalsIgnoreCase("I am Female,")) {
            String female = "Female";
            Log.i("pos", "op123" + female);

            sessionManager.setData(SessionManager.KEY_GENDER_, female);
            sessionManager.setData(SessionManager.KEY_AGE, String.valueOf(fSeekbar.getProgress()));
            sessionManager.gender(female, String.valueOf(fSeekbar.getProgress()));
        }
        if (mf_gender.equalsIgnoreCase("I am Male,")) {
            String male = "Male";
            Log.i("pos", "op123" + male);

            sessionManager.setData(SessionManager.KEY_GENDER_, male);
            sessionManager.setData(SessionManager.KEY_AGE, String.valueOf(mSeekBar.getProgress()));
            sessionManager.gender(male, String.valueOf(mSeekBar.getProgress()));

        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.male_icon_selected) {
            if (img_female_unselect.getVisibility() == View.VISIBLE) {
                img_female_unselect.setVisibility(View.VISIBLE);
                img_female_select.setVisibility(View.GONE);
                img_male_select.setVisibility(View.VISIBLE);
                img_male_unselect.setVisibility(View.GONE);
            }
        }

        if (i == R.id.female_icon) {
            if (img_female_unselect.getVisibility() == View.VISIBLE) {
                img_female_unselect.setVisibility(View.GONE);
                img_female_select.setVisibility(View.VISIBLE);
                img_male_select.setVisibility(View.GONE);
                img_male_unselect.setVisibility(View.VISIBLE);

                int pro = fSeekbar.getProgress();
                select_age.setText(pro + " Years old.");
                select_gender.setText("I am Female,");
                fSeekbar.setVisibility(View.VISIBLE);
                mSeekBar.setVisibility(View.GONE);
                female.setTextColor(getResources().getColor(R.color.pink));
                male.setTextColor(getResources().getColor(R.color.grey_light));

            }
        }

        if (i == R.id.female_icon_selected) {
            if (img_female_unselect.getVisibility() == View.GONE) {
                img_female_unselect.setVisibility(View.GONE);
                img_female_select.setVisibility(View.VISIBLE);
                img_male_select.setVisibility(View.GONE);
                img_male_unselect.setVisibility(View.VISIBLE);
            }
        }

        if (i == R.id.male_icon) {
            if (img_male_select.getVisibility() == View.GONE) {
                img_female_unselect.setVisibility(View.VISIBLE);
                img_female_select.setVisibility(View.GONE);
                img_male_select.setVisibility(View.VISIBLE);
                img_male_unselect.setVisibility(View.GONE);

                int pro = mSeekBar.getProgress();
                select_age.setText(pro + " Years old.");

                select_gender.setText("I am Male,");
                fSeekbar.setVisibility(View.GONE);
                mSeekBar.setVisibility(View.VISIBLE);
                male.setTextColor(getResources().getColor(R.color.blue));
                female.setTextColor(getResources().getColor(R.color.grey_light));


            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (progress <= 16) {
            seekBar.setProgress(16);
        }
        if (progress >= 16) {
            select_age.setText(progress + " Years old.");
        }
        // Display the current progress of SeekBar
        //select_age.setText(total+" years old.");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

  /*  @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

*/

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {

        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_AGE_GENDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!", "1235" + response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                        String gender = jsonObject1.getString("gender");
                        String age = jsonObject1.getString("age");
                        setData(age, gender);

                    }
                    else {
                        frameLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                frameLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void setData(String age, String gender) {

        String user_gender = gender;
        String user_age = age;

        Log.i("gender", "gen " + user_gender);
        Log.i("gender", "gen " + user_age);

       // Toast.makeText(context, "gender age " + user_gender + user_age, Toast.LENGTH_SHORT).show();

        if (!user_gender.equalsIgnoreCase("null") && !user_age.equalsIgnoreCase("null")) {
            Log.i("gender", "gen " + user_gender);
            Log.i("gender", "gen " + user_age);
            if (user_gender.equalsIgnoreCase("Female")) {
                Log.i("gender", "fegen " + user_gender);
                Log.i("gender", "fegen " + user_age);
                img_female_unselect.setVisibility(View.GONE);
                img_female_select.setVisibility(View.VISIBLE);
                img_male_select.setVisibility(View.GONE);
                img_male_unselect.setVisibility(View.VISIBLE);
                fSeekbar.setProgress(Integer.parseInt(user_age));
                select_gender.setText("I am Female,");
                select_age.setText(user_age + " Years old.");
                fSeekbar.setVisibility(View.VISIBLE);
                mSeekBar.setVisibility(View.GONE);
                female.setTextColor(getResources().getColor(R.color.pink));
                male.setTextColor(getResources().getColor(R.color.grey_light));
                startActivity(new Intent(context, ZodiacActivity.class));
            }
            else if (user_gender.equalsIgnoreCase("Male"))
            {
                Log.i("gender", "mgen " + user_gender);
                Log.i("gender", "mgen " + user_age);

                img_female_unselect.setVisibility(View.VISIBLE);
                img_female_select.setVisibility(View.GONE);
                img_male_select.setVisibility(View.VISIBLE);
                img_male_unselect.setVisibility(View.GONE);
                mSeekBar.setProgress(Integer.parseInt(user_age));
                select_age.setText(user_age + " Years old.");
                select_gender.setText("I am Male,");
                mSeekBar.setVisibility(View.VISIBLE);
                fSeekbar.setVisibility(View.GONE);
                male.setTextColor(getResources().getColor(R.color.blue));
                female.setTextColor(getResources().getColor(R.color.grey_light));
                startActivity(new Intent(context, ZodiacActivity.class));
            }
        }
        else {
            Log.i("gender", "3gen " + user_gender);
            Log.i("gender", "3gen " + user_age);
            progressBar.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);

            img_female_unselect.setVisibility(View.VISIBLE);
            img_female_select.setVisibility(View.GONE);
            img_male_select.setVisibility(View.VISIBLE);
            img_male_unselect.setVisibility(View.GONE);
            mSeekBar.setProgress(16);
            select_age.setText(16 + " Years old.");
            select_gender.setText("I am Male,");
            mSeekBar.setVisibility(View.VISIBLE);
            fSeekbar.setVisibility(View.GONE);
            male.setTextColor(getResources().getColor(R.color.blue));
            female.setTextColor(getResources().getColor(R.color.grey_light));
        }


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GenderActivity.this,StepOneActivity.class));
    }

}
