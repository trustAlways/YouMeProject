package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.youme.candid.youmeapp.Activity.model.UserDetails;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fullview_User_Profile extends AppCompatActivity {

    TextView txtview_pseudoname,txt_distance,txt_height,txt_user_zodiac,txt_user_name,txt_looking_for
            ,txt_ethnicity,txt_education,txt_profession

            ,txt_lifestyle,txt_hobbies,txt_appearance,txt_nature
            ,txt_result_compatible;

    LinearLayout ll_linear_lifestyle,ll_linear_hobbies,ll_linear_appearnce,ll_linear_nature,full_view_lay;
    ImageView img_lifestyle_plus,img_lifestyle_minus,img_hobbies_plus,img_hobbies_minus,img_appearance_plus,img_appearance_minus,
    img_nature_plus,img_nature_minus,img_back,profile_image,img_no_data;

    ProgressBar progressBar;
    Context context;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    ArrayList<UserDetails> userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_view_my_profile);

        // Get the widgets reference from XML layout
        initView();

        Boolean internet = connectionDetector.isConnected(context);
        if (internet)
        {
            //for set user data
           setData();
        }
        else
        {
            Toast.makeText(context, "Check your internet connection.", Toast.LENGTH_LONG).show();
            img_no_data.setVisibility(View.VISIBLE);
        }


        // for all the click events
        click();
    }



    private void initView() {
        context = this;
        sessionManager = new SessionManager(context);
        connectionDetector = new ConnectionDetector();
        userDetails = new ArrayList<>();
        System.out.println("initview3");

        //textviews
        txtview_pseudoname = (TextView)findViewById(R.id.user_pseudonym_name);
        txt_distance = (TextView)findViewById(R.id.user_distance);
        txt_height = (TextView)findViewById(R.id.user_height);
        txt_user_zodiac = (TextView)findViewById(R.id.user_zodiac);

        txt_user_name = (TextView)findViewById(R.id.real_name_user);
        txt_looking_for = (TextView)findViewById(R.id.user_looking_for);
        txt_ethnicity = (TextView)findViewById(R.id.user_ethenicity);
        txt_education = (TextView)findViewById(R.id.user_education);
        txt_profession = (TextView)findViewById(R.id.user_profession);

        txt_lifestyle = (TextView)findViewById(R.id.txt_user_lifestyle);
        txt_hobbies = (TextView)findViewById(R.id.txt_user_hoobies);
        txt_appearance = (TextView)findViewById(R.id.txt_user_appearance);
        txt_nature = (TextView)findViewById(R.id.txt_user_nature);

        txt_result_compatible = (TextView)findViewById(R.id.user_testresult_progressbar);


        progressBar = (ProgressBar)findViewById(R.id.circularProgressbar);
        //linearlayout's
         ll_linear_lifestyle = (LinearLayout)findViewById(R.id.user_lifestyle_detail);
         ll_linear_hobbies = (LinearLayout)findViewById(R.id.user_hobiies_interest_detail);
         ll_linear_appearnce = (LinearLayout)findViewById(R.id.user_myapperance_detail);
         ll_linear_nature = (LinearLayout)findViewById(R.id.user_nature_detail);
         full_view_lay = (LinearLayout)findViewById(R.id.full_view_layout);
       //ImageView's
        img_lifestyle_plus = (ImageView)findViewById(R.id.lifestyle_heartplus);
        img_lifestyle_minus = (ImageView)findViewById(R.id.lifestyle_heartminus);
        img_hobbies_plus = (ImageView)findViewById(R.id.hobbiesandinterest_heartplus);
        img_hobbies_minus = (ImageView)findViewById(R.id.hobbiesandinterest_heartminus);
        img_appearance_plus = (ImageView)findViewById(R.id.myApperance_heartplus);
        img_appearance_minus = (ImageView)findViewById(R.id.myApperance_heartminus);
        img_nature_plus = (ImageView)findViewById(R.id.mynature_heartplus);
        img_nature_minus = (ImageView)findViewById(R.id.mynature_heartminus);
        img_back = (ImageView)findViewById(R.id.user_profile_back);
        profile_image = (ImageView)findViewById(R.id.my_profile_image);
        img_no_data = (ImageView)findViewById(R.id.img_no_data_available);

    }

    private void setData()
    {
        final String another_user_id = sessionManager.getData(SessionManager.KEY_ANOTHER_USER_ID);
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ANOTHER_USER_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            pd.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("response..!","1235"+response);

                            String Status = jsonObject.getString("status");
                            String Status_code = jsonObject.getString("status_code");
                            String msg = jsonObject.getString("message");

                            if (Status.equals("success"))
                            {
                                JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                                String user_pseudonym = jsonObject2.getString("pseudonym");
                                String first_name = jsonObject2.getString("first_name");
                                String last_name = jsonObject2.getString("last_name");
                                String gender = jsonObject2.getString("gender");
                                String age = jsonObject2.getString("age");
                                String zodiac_sign = jsonObject2.getString("zodiac_sign");
                                String looking_for = jsonObject2.getString("looking_for");
                                String education_user = jsonObject2.getString("education");
                                String ethnicity_user = jsonObject2.getString("ethnicity");
                                String profession = jsonObject2.getString("profession");
                                String user_lifestyle = jsonObject2.getString("life_style");
                                String user_relation_stts = jsonObject2.getString("relationship_status");
                                String user_appearnce = jsonObject2.getString("appearance");
                                String user_nature = jsonObject2.getString("nature");
                                String user_intrested_hooby = jsonObject2.getString("interest_hobby");
                                String user_email = jsonObject2.getString("email");
                                String user_height = jsonObject2.getString("height");
                                String user_weight = jsonObject2.getString("weight");
                                String profilepic = jsonObject2.getString("profile_pic");
                                String distance = jsonObject2.getString("distance");
                                String compatible = jsonObject2.getString("compatibility");

                               // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                                userDetails.add(new UserDetails(first_name,last_name,user_pseudonym,gender,age,zodiac_sign,looking_for
                                ,education_user,ethnicity_user,profession,user_lifestyle,user_relation_stts,user_appearnce,user_nature
                                ,user_intrested_hooby,user_email,user_height,user_weight,profilepic,distance,compatible));

                                setUserData(first_name,last_name,user_pseudonym,gender,age,zodiac_sign,looking_for,education_user,ethnicity_user
                                ,profession,user_lifestyle,user_relation_stts,user_appearnce,user_nature,user_intrested_hooby,user_email,user_height
                                ,user_weight,profilepic,distance,compatible);

                                full_view_lay.setVisibility(View.VISIBLE);

                            }
                            else if (Status.equals("error"))
                            {
                                pd.dismiss();
                                Glide.with(context).load(R.drawable.user_profile).into(profile_image);
                                full_view_lay.setVisibility(View.VISIBLE);
                                // img_no_data.setVisibility(View.VISIBLE);
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
                pd.dismiss();
               img_no_data.setVisibility(View.VISIBLE);
               Toast.makeText(getApplicationContext(), "couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("other_user_id",another_user_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void setUserData(String first_name, String last_name, String user_pseudonym, String gender,
                             String age, String zodiac_sign, String looking_for,
                             String education_user, String ethnicity_user, String profession, String user_lifestyle,
                             String user_relation_stts, String user_appearnce, String user_nature, String user_intrested_hooby,
                             String user_email, String user_height, String user_weight,String profile_pic,String distance,String compatible) {


       String type = sessionManager.getData(SessionManager.KEY_USER_TYPE);
        if (type.equalsIgnoreCase("premium")) {
            //Bitmap bitmap = decodeBase64(pr_img);
            txt_user_name.setText(first_name+" "+last_name);
        }



        if (!profile_pic.equalsIgnoreCase("")) {
            //Bitmap bitmap = decodeBase64(pr_img);
            Glide.with(context).load(profile_pic).into(profile_image);
        }
        else
        {
            Glide.with(context).load(R.drawable.user_profile).into(profile_image);
        }
        if (!user_pseudonym.equalsIgnoreCase("null"))
        {
            txtview_pseudoname.setText(user_pseudonym+", "+age);
            //txt_user_name.setText(first_name+" "+last_name);
        }
        if (!distance.equalsIgnoreCase("null"))
        {
           txt_distance.setText(distance+" kms away");
        }
        if (!zodiac_sign.equalsIgnoreCase("null"))
        {
            txt_user_zodiac.setText(zodiac_sign);
        }
        if (!ethnicity_user.equalsIgnoreCase("null"))
        {
            txt_ethnicity.setText(ethnicity_user);
        }
        if (!education_user.equalsIgnoreCase("null"))
        {
            txt_education.setText(education_user);
        }

        if (!profession.equalsIgnoreCase("null"))
        {
            txt_profession.setText(profession);
        }
        if (!looking_for.equalsIgnoreCase("null"))
        {
            txt_looking_for.setText(looking_for);
        }
        if (!user_lifestyle.equalsIgnoreCase("null"))
        {
            txt_lifestyle.setText(user_lifestyle);
        }
        if (!user_intrested_hooby.equalsIgnoreCase("null"))
        {
            txt_hobbies.setText(user_intrested_hooby);
        }
        if (!user_appearnce.equalsIgnoreCase("null"))
        {
            txt_appearance.setText(user_appearnce);
        }
        if (!user_nature.equalsIgnoreCase("null"))
        {
            txt_nature.setText(user_nature);
        }
        if (!compatible.equalsIgnoreCase("null"))
        {
            txt_result_compatible.setText(compatible+"%");
            progressBar.setProgress(Integer.parseInt(compatible));
        }


    }
    private void click()
    {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_lifestyle_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_linear_lifestyle.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.slide_in_right));
                img_lifestyle_plus.setVisibility(View.GONE);
                img_lifestyle_minus.setVisibility(View.VISIBLE);
                ll_linear_lifestyle.setVisibility(View.VISIBLE);

            }
        });

        img_lifestyle_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_linear_lifestyle.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.slide_out_right));
                img_lifestyle_minus.setVisibility(View.GONE);
                img_lifestyle_plus.setVisibility(View.VISIBLE);
                ll_linear_lifestyle.postDelayed(new Runnable() {
                    public void run() {
                        ll_linear_lifestyle.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });

        img_hobbies_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_linear_hobbies.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.slide_in_left));
                img_hobbies_plus.setVisibility(View.GONE);
                img_hobbies_minus.setVisibility(View.VISIBLE);
                ll_linear_hobbies.setVisibility(View.VISIBLE);

            }
        });

        img_hobbies_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_linear_hobbies.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.slide_out_left));
                img_hobbies_plus.setVisibility(View.VISIBLE);
                img_hobbies_minus.setVisibility(View.GONE);
                ll_linear_hobbies.postDelayed(new Runnable() {
                    public void run() {
                        ll_linear_hobbies.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });


        img_appearance_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_linear_appearnce.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.slide_in_right));
                img_appearance_plus.setVisibility(View.GONE);
                img_appearance_minus.setVisibility(View.VISIBLE);
                ll_linear_appearnce.setVisibility(View.VISIBLE);

            }
        });

        img_appearance_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_linear_appearnce.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.slide_out_right));
                img_appearance_minus.setVisibility(View.GONE);
                img_appearance_plus.setVisibility(View.VISIBLE);
                ll_linear_appearnce.postDelayed(new Runnable() {
                    public void run() {
                        ll_linear_appearnce.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });

        img_nature_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_linear_nature.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.slide_in_left));
                img_nature_plus.setVisibility(View.GONE);
                img_nature_minus.setVisibility(View.VISIBLE);
                ll_linear_nature.setVisibility(View.VISIBLE);

            }
        });

        img_nature_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_linear_nature.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.slide_out_left));
                img_nature_plus.setVisibility(View.VISIBLE);
                img_nature_minus.setVisibility(View.GONE);
                ll_linear_nature.postDelayed(new Runnable() {
                    public void run() {
                        ll_linear_nature.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });


    }

    // slide the view from below itself to the current position
    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

}
