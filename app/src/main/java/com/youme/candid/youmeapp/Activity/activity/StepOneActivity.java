package com.youme.candid.youmeapp.Activity.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_ZodiacSign;
import com.youme.candid.youmeapp.Activity.model.LifeStyle;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepOneActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    EditText edt_fname,edt_lname,edt_user_name;
    ImageView img_next;
    FrameLayout frameLayout;
    ProgressBar progressBar;
    LinearLayout l_linear_fname,l_linear_lname,l_usernm_linear;
    SessionManager sessionManager;
    Context context;
    ConnectionDetector connectionDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_one);

        //for initialize all view this method will invoke
        initView();

       /* boolean register = sessionManager.isStepone();
        {
            if (register)
            {
                startActivity(new Intent(context,GenderActivity.class));
            }
        }*/

        //for all clickable fuction //his method will invoke
        clickEvents();
    }

    private void initView() {

        context = this;
        sessionManager = new SessionManager(context);
        connectionDetector = new ConnectionDetector();


        edt_fname = (EditText)findViewById(R.id.f_name);
        edt_lname = (EditText)findViewById(R.id.lasst_name);
        edt_user_name = (EditText)findViewById(R.id.user_name);


        img_next =(ImageView)findViewById(R.id.next_step);

        l_linear_fname = (LinearLayout)findViewById(R.id.first_name_linear);
        l_linear_lname = (LinearLayout)findViewById(R.id.last_name_linear);
        l_usernm_linear = (LinearLayout)findViewById(R.id.username_linear);

        frameLayout = (FrameLayout)findViewById(R.id.loading_frame);
        progressBar = (ProgressBar)findViewById(R.id.step_one_progress);
        progressBar.setVisibility(View.VISIBLE);

    }

    private void clickEvents() {

        edt_fname.setOnFocusChangeListener(this);
        edt_lname.setOnFocusChangeListener(this);
        edt_user_name.setOnFocusChangeListener(this);


        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });



    }

    public static boolean isValid(String str)
    {
        boolean isValid = false;
        String expression = "^[a-z_A-Z ]*$";
        CharSequence inputStr = str;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches())
        {
            isValid = true;
        }
        return isValid;
    }

    private void validate()
    {
        final String f_name = edt_fname.getText().toString().trim();
        final String l_name = edt_lname.getText().toString().trim();
        final String username = edt_user_name.getText().toString().trim();



        boolean iserror=false;

        if(f_name.equalsIgnoreCase(""))
        {
            iserror=true;
            l_linear_fname.setFocusable(true);
            l_linear_fname.setBackground(getResources().getDrawable(R.drawable.error_border));
        }
        else if(!isValid(f_name))
        {
            iserror=true;
            edt_fname.setError("Only alphabates are allowed");
            l_linear_fname.setFocusable(true);
            l_linear_fname.setBackground(getResources().getDrawable(R.drawable.error_border));
        }
        else
        {
            l_linear_fname.setFocusable(true);
            l_linear_fname.setBackground(getResources().getDrawable(R.drawable.corner_border));
        }


        if(l_name.equalsIgnoreCase(""))
        {
            iserror=true;
            l_linear_lname.setFocusable(true);
            l_linear_lname.setBackground(getResources().getDrawable(R.drawable.error_border));

        }

        else if(!isValid(l_name))
        {
            iserror=true;
            edt_lname.setError("Only alphabates are allowed");

            l_linear_lname.setFocusable(true);
            l_linear_lname.setBackground(getResources().getDrawable(R.drawable.error_border));
        }

        else
        {
            l_linear_lname.setFocusable(true);
            l_linear_lname.setBackground(getResources().getDrawable(R.drawable.corner_border));
        }

        if(username.equalsIgnoreCase(""))
        {
            iserror=true;

            l_usernm_linear.setFocusable(true);
            l_usernm_linear.setBackground(getResources().getDrawable(R.drawable.error_border));

        }

        else if(!isValid(username))
        {
            iserror=true;
            edt_user_name.setError("Only alphabates are allowed");

            l_usernm_linear.setFocusable(true);
            l_usernm_linear.setBackground(getResources().getDrawable(R.drawable.error_border));
        }


        else
        {
            l_usernm_linear.setFocusable(true);
            l_usernm_linear.setBackground(getResources().getDrawable(R.drawable.corner_border));
        }


        if(!iserror)
        {
            l_linear_fname.setBackground(getResources().getDrawable(R.drawable.corner_border));
            l_linear_lname.setBackground(getResources().getDrawable(R.drawable.corner_border));
            l_usernm_linear.setBackground(getResources().getDrawable(R.drawable.corner_border));


            ZodiacUpdate();
           // sessionManager.steponesession(f_name,l_name,username);

            //SignupRequest();

        }
    }


    private void ZodiacUpdate()
    {

        final String f_name = edt_fname.getText().toString().trim();
        final String l_name = edt_lname.getText().toString().trim();
        final String username = edt_user_name.getText().toString().trim();
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final String token = sessionManager.getData(SessionManager.KEY_TOKEN);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER_STEP_2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!","1235"+response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");
                    Toast.makeText(StepOneActivity.this, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success"))
                    {
                        saveData();
                        startActivity(new Intent(StepOneActivity.this,GenderActivity.class));
                    }
                    else
                    {
                        Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                  dialog.dismiss();
                Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("first_name",f_name);
                params.put("last_name",l_name);
                params.put("pseudonym",username);
                params.put("api_token",token);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    private void saveData() {

        final String f_name = edt_fname.getText().toString().trim();
        final String l_name = edt_lname.getText().toString().trim();
        final String username = edt_user_name.getText().toString().trim();

        sessionManager.setData(SessionManager.KEY_FIRST_NAME,f_name);
        sessionManager.setData(SessionManager.KEY_LAST_NAME,l_name);
        sessionManager.setData(SessionManager.KEY_PSEUDONYM,username);
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        saveData();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {

        final  String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_NAME_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!","1235"+response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");
                    if (Status.equals("success"))
                    {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("user");
                         String f_name = jsonObject2.getString("first_name");
                         String l_name =jsonObject2.getString("last_name");
                         String username =jsonObject2.getString("pseudonym");
                        String prf_pic = jsonObject2.getString("profile_pic");
                        String life_stle =jsonObject2.getString("life_style");
                        String looking_fr =jsonObject2.getString("looking_for");
                        String zodiac = jsonObject2.getString("zodiac_sign");
                        String age =jsonObject2.getString("age");
                        String intrestedhobby =jsonObject2.getString("interest_hobby");


                       setData(f_name,l_name,username,age,prf_pic,life_stle,looking_fr,zodiac,intrestedhobby);

                    }
                    else
                    {
                        dialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        frameLayout.setVisibility(View.GONE);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
                dialog.dismiss();
                Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void setData(String f_name, String l_name, String username, String age, String prf_pic, String life_stle,
                         String looking_fr, String zodiac, String intrestedhobby) {

        System.out.println("###deatil--"+f_name+age+prf_pic+life_stle+zodiac+" "+intrestedhobby+" "+looking_fr);

        if (f_name.equalsIgnoreCase(""))
        {
            System.out.println("###deatil--"+1);

            progressBar.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);

           /* edt_fname.setText(f_name);
            edt_lname.setText(l_name);
            edt_user_name.setText(username);

            l_linear_fname.setBackground(getResources().getDrawable(R.drawable.corner_border));
            l_linear_lname.setBackground(getResources().getDrawable(R.drawable.corner_border));
            l_usernm_linear.setBackground(getResources().getDrawable(R.drawable.corner_border));*/

        }
        else if (age.equalsIgnoreCase("0") )
        {
            System.out.println("###deatil--"+2);

            Intent intent = new Intent(context,GenderActivity.class);
            startActivity(intent);
        }
        else if (zodiac.equalsIgnoreCase("null"))
        {
            System.out.println("###deatil--"+3);

            Intent intent = new Intent(context,ZodiacActivity.class);
            startActivity(intent);
        }
        else if (life_stle.equalsIgnoreCase("null"))
        {
            System.out.println("###deatil--"+4);

            Intent intent = new Intent(context, LifeStyleActivity.class);
            startActivity(intent);
        }
        else if (intrestedhobby.equalsIgnoreCase("null"))
        {
            System.out.println("###deatil--"+5+" "+intrestedhobby);

            Intent intent = new Intent(context, HobbiesActivity.class);
            startActivity(intent);
        }
        else if (prf_pic.equalsIgnoreCase(""))
        {
            System.out.println("###deatil--"+6);

            Intent intent = new Intent(context, Upload2.class);
            startActivity(intent);
        }
        else if (looking_fr.equalsIgnoreCase("null"))
        {
            System.out.println("###deatil--"+7);

            Intent intent = new Intent(context, LookingForActivity.class);
            startActivity(intent);
        }

        else
        {
            Intent intent = new Intent(context, UserProfileActivity.class);
            startActivity(intent);
        }


    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v==edt_fname) {
            if (!hasFocus) {
                if (edt_fname.getText().toString().trim().equals("")) {
                    l_linear_fname.setBackground(getResources().getDrawable(R.drawable.corner_bordertwo));
                }
                else
                {
                    l_linear_fname.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }
            }
            else {
                if (edt_fname.getText().toString().trim().equals("")) {
                    l_linear_fname.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }

            }
        }


        if (v==edt_lname) {
            if (!hasFocus) {
                if (edt_lname.getText().toString().trim().equals("")) {
                    l_linear_lname.setBackground(getResources().getDrawable(R.drawable.corner_bordertwo));
                }
                else
                {
                    l_linear_lname.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }
            }
            else {
                if (edt_lname.getText().toString().trim().equals("")) {
                    l_linear_lname.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }

            }
        }

        if (v==edt_user_name) {
            if (!hasFocus) {
                if (edt_user_name.getText().toString().trim().equals("")) {
                    l_usernm_linear.setBackground(getResources().getDrawable(R.drawable.corner_bordertwo));
                }
                else
                {
                    l_usernm_linear.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }
            }
            else {
                if (edt_user_name.getText().toString().trim().equals("")) {
                    l_usernm_linear.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }

            }
        }

        }

    @Override
    public void onBackPressed() {
        Toast.makeText(context, "Nothing for Back!..Sorry", Toast.LENGTH_SHORT).show();
    }
}
