package com.youme.candid.youmeapp.Activity.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_Add_Photo;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_Age;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_Apperance;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_Education;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_Ethenicity;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_Hobbies;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_Lifestyle;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_Looking_For;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_Nature;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_Profession;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_RelationshipStatus;
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_ZodiacSign;
import com.youme.candid.youmeapp.Activity.model.CardItem;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.youme.candid.youmeapp.Activity.activity.UserProfileActivity.decodeBase64;

public class Edit_UserProfile_Actiivty extends AppCompatActivity {

     TextView f_nm,l_nm,user_psudoname,user_mobile,edt_email_popup;
     TextView txt_user_email,user_age,zodiac_sign,user_relationship_stts
            ,user_lookingfor,user_ethnicity,user_education,user_profession;
     LinearLayout edt_profile_set_data, user_add_photo,user_age_linear,user_zodiac_linear,user_relationshipstts,user_looking_for_linear,
             user_ethnecity_linear,user_education_linear
             ,user_profession_linear, linear_user_lifestle,linear_user_hobby,ll_user_apperance,ll_nature;
     //all the edittext here...
     EditText dialog_enter_email;
     ImageView user_profile_img,img_back_arrow,img_send_arrow,img_hide_dialog,no_data;

     //.Dialog data
    Dialog mydialog;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    Context context;
    FrameLayout frameLayout;
    ProgressBar progressBar;

    //.........................................................//.........................................................................//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_my_profile);

        //for initiialize all the view this method will be invoked
        initView();

        Boolean internet = connectionDetector.isConnected(context);
        if (internet)
        {
            //for when activity is created
            //user filled detail will show
            //now  user can chnage the detail.
            getUserData();
        }
        else
        {
            Toast.makeText(context, "Check your internet connection.", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
        }

        //for click events this methos will be invoked
        onClickEvents();
    }




    //.........................................................//.........................................................................//
    private void initView() {
        context = this;
        sessionManager = new SessionManager(context);
        connectionDetector = new ConnectionDetector();


        //all the imageviews initialioze here
        user_profile_img = (ImageView)findViewById(R.id.img_user);
        img_back_arrow = (ImageView)findViewById(R.id.editprofile_arrow);
        no_data = (ImageView)findViewById(R.id.img_no_data_available_edt_profile);

        //all the Textview will be initialize here
        f_nm = (TextView) findViewById(R.id.first_name_user);
        l_nm = (TextView)findViewById(R.id.last_name_user);
        user_psudoname = (TextView)findViewById(R.id.psuedoname_user);
        user_mobile = (TextView)findViewById(R.id.mobile_user);
        txt_user_email = (TextView)findViewById(R.id.email_user);
        user_age = (TextView)findViewById(R.id.age_user);
        zodiac_sign = (TextView)findViewById(R.id.zodiac_user);
        user_relationship_stts = (TextView)findViewById(R.id.user_status_relationship);
        user_lookingfor = (TextView)findViewById(R.id.user_lookingfor);
        user_ethnicity = (TextView)findViewById(R.id.edt_ethenicity);
        user_education = (TextView)findViewById(R.id.user_education);
        user_profession = (TextView)findViewById(R.id.user_profession);
        edt_email_popup = (TextView)findViewById(R.id.edt_user_prflAdd_email_popup);

        //all the linearLayout will be initialize here
        edt_profile_set_data = (LinearLayout)findViewById(R.id.edt_profile_full_lay);

        user_add_photo = (LinearLayout)findViewById(R.id.add_photo);
        user_age_linear = (LinearLayout)findViewById(R.id.ll_age);
        user_zodiac_linear = (LinearLayout)findViewById(R.id.ll_zodiac);
        user_relationshipstts = (LinearLayout)findViewById(R.id.ll_relationshipstts);
        user_looking_for_linear = (LinearLayout)findViewById(R.id.ll_looking_for);
        user_ethnecity_linear = (LinearLayout)findViewById(R.id.ll_ethenicity);
        user_education_linear = (LinearLayout)findViewById(R.id.ll_education);
        user_profession_linear = (LinearLayout)findViewById(R.id.ll_profession);
        linear_user_lifestle = (LinearLayout)findViewById(R.id.ll_user_lifestyle);
        linear_user_hobby = (LinearLayout)findViewById(R.id.ll_user_hobbiesandlifestyle);
        ll_user_apperance= (LinearLayout)findViewById(R.id.ll_user_apperance);
        ll_nature = (LinearLayout)findViewById(R.id.ll_user_nature);

        frameLayout = (FrameLayout)findViewById(R.id.user_profile_loading_frame);
        progressBar = (ProgressBar)findViewById(R.id.user_profile_progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    //.........................................................//.........................................................................//

    private void getUserData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(true);
        progressDialog.show();

        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!", "1235" + response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");
                    String msg = jsonObject.getString("message");

                    if (Status.equals("success")) {

                        progressBar.setVisibility(View.GONE);
                        edt_profile_set_data.setVisibility(View.VISIBLE);
                        JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                        JSONObject jsonObject1 = jsonObject2.getJSONObject("user");

                            String user_id = jsonObject1.getString("id");
                            String first_name = jsonObject1.getString("first_name");
                            String last_name = jsonObject1.getString("last_name");
                            String pseudonym = jsonObject1.getString("pseudonym");
                            String email = jsonObject1.getString("email");
                            String education = jsonObject1.getString("education");
                            String profession = jsonObject1.getString("profession");
                            String ethnicity = jsonObject1.getString("ethnicity");
                            String gender = jsonObject1.getString("gender");
                            String age = jsonObject1.getString("age");
                            String zodiac_sign = jsonObject1.getString("zodiac_sign");
                            String looking_for = jsonObject1.getString("looking_for");
                            String relationship_status = jsonObject1.getString("relationship_status");
                            String user_height = jsonObject1.getString("height");
                            String user_weight = jsonObject1.getString("weight");
                            String profilepic = jsonObject1.getString("profile_pic");

                        sessionManager.setData(SessionManager.KEY_FIRST_NAME,first_name);
                        sessionManager.setData(SessionManager.KEY_LAST_NAME,last_name);
                        sessionManager.setData(SessionManager.KEY_PSEUDONYM,pseudonym);
                        sessionManager.setData(SessionManager.KEY_EMAIL,email);
                        sessionManager.setData(SessionManager.KEY_GENDER_,gender);
                        sessionManager.setData(SessionManager.KEY_AGE,age);
                        sessionManager.setData(SessionManager.KEY_ZODIAC,zodiac_sign);
                        sessionManager.setData(SessionManager.KEY_LOOKIN_FOR,looking_for);
                         sessionManager.setData(SessionManager.KEY_ETHENICITY,ethnicity);
                        sessionManager.setData(SessionManager.KEY_EDUCATION,education);
                        sessionManager.setData(SessionManager.KEY_PROFESSION,profession);
                        sessionManager.setData(SessionManager.KEY_RELATION_STATUS,relationship_status);
                        sessionManager.setData(SessionManager.KEY_USER_WEIGHT,user_weight);
                        sessionManager.setData(SessionManager.KEY_USER_HEIGHT,user_height);
                        sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE,profilepic);

                        System.out.println("user id" + user_id);
                        System.out.println("user id" + profilepic);
                        System.out.println("user id" + first_name+last_name);
                        System.out.println("user id" + pseudonym);
                        System.out.println("user id" + gender + age + zodiac_sign + looking_for);
                        System.out.println("user id" + ethnicity + education + profession + relationship_status);
                        System.out.println("user id" + user_height + user_weight);

                        setData();

                    }
                    else if (Status.equals("error")) {
                        frameLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                        //startActivity(new Intent(context,MyProfileActiivty.class));
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                    else if (Status_code.equals("500") && msg.equals("There is no users available."))
                    {
                        frameLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);

                        // startActivity(new Intent(context,MyProfileActiivty.class));
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }


                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                frameLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Could not connect to server.", Toast.LENGTH_SHORT).show();
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

    //.........................................................//.........................................................................//

    private void setData()
    {
        String pr_img = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
        String user_fname = sessionManager.getData(SessionManager.KEY_FIRST_NAME);
        String user_lname = sessionManager.getData(SessionManager.KEY_LAST_NAME);
        String user_pseudoname = sessionManager.getData(SessionManager.KEY_PSEUDONYM);
        String user_email = sessionManager.getData(SessionManager.KEY_EMAIL);
        String user_agee = sessionManager.getData(SessionManager.KEY_AGE);
        String user_moble = sessionManager.getData(SessionManager.KEY_MOBILE);
        String user_zodiac = sessionManager.getData(SessionManager.KEY_ZODIAC);
        String relation_ship_status = sessionManager.getData(SessionManager.KEY_RELATION_STATUS);
        String lookingfor = sessionManager.getData(SessionManager.KEY_LOOKIN_FOR);
        String ethenicity = sessionManager.getData(SessionManager.KEY_ETHENICITY);
        String education = sessionManager.getData(SessionManager.KEY_EDUCATION);
        String profession = sessionManager.getData(SessionManager.KEY_PROFESSION);

        System.out.println("img"+pr_img);

        f_nm.setText(user_fname);
        l_nm.setText(user_lname);
        user_psudoname.setText(user_pseudoname);
        if (!user_email.equalsIgnoreCase("null"))
        {
            edt_email_popup.setVisibility(View.GONE);
            txt_user_email.setVisibility(View.VISIBLE);
            txt_user_email.setText(user_email);
        }
        user_mobile.setText(user_moble);
        user_age.setText(user_agee);
        zodiac_sign.setText(user_zodiac);
        if (!relation_ship_status.equalsIgnoreCase("null"))
        {
            user_relationship_stts.setText(relation_ship_status);
        }
        if (!ethenicity.equalsIgnoreCase("null"))
        {
            user_ethnicity.setText(ethenicity);
        }
        if (!education.equalsIgnoreCase("null"))
        {
            user_education.setText(education);
        }
        if (!profession.equalsIgnoreCase("null"))
        {
            user_profession.setText(profession);
        }
        user_lookingfor.setText(lookingfor);

        if (pr_img!=null)
        {
            Glide.with(context).load(pr_img).into(user_profile_img);
           // Bitmap bitmap = decodeBase64(pr_img);
            //user_profile_img.setImageBitmap(bitmap);
        }

         frameLayout.setVisibility(View.GONE);
         progressBar.setVisibility(View.GONE);
    }

    //.........................................................//.........................................................................//


    private void onClickEvents()
    {

        img_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MyProfileActiivty.class);
                startActivity(intent);
            }
        });

        edt_email_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopup();
            }
        });

       //all linearlayout click events will invoked here
        linear_user_lifestle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(context,Edit_User_Lifestyle.class);
               startActivity(intent);

           }
       });

       linear_user_hobby.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context,Edit_User_Hobbies.class);
               startActivity(intent);
           }
       });

       ll_user_apperance.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context,Edit_User_Apperance.class);
               startActivity(intent);
           }
       });

       ll_nature.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context,Edit_User_Nature.class);
               startActivity(intent);
           }
       });

       //here user click to upload 5 images to server.
       user_add_photo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, Edit_Add_Photo.class);
               startActivity(intent);
           }
       });

        user_age_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, Edit_User_Age.class);
               // startActivity(intent);
            }
        });

        user_zodiac_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Edit_User_ZodiacSign.class);
                intent.putExtra("zodiac_name",sessionManager.getData(SessionManager.KEY_ZODIAC));

                startActivity(intent);
            }
        });


       user_relationshipstts.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, Edit_User_RelationshipStatus.class);
               startActivity(intent);
           }
       });

        user_looking_for_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Edit_User_Looking_For.class);
                startActivity(intent);
            }
        });

        user_ethnecity_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Edit_User_Ethenicity.class);
                startActivity(intent);
            }
        });
        user_education_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Edit_User_Education.class);
                startActivity(intent);
            }
        });

        user_profession_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Edit_User_Profession.class);
                startActivity(intent);
            }
        });



    }

    //....................................................\\Email verification popup//..............................................................
    private void openPopup() {
        try{
            mydialog = new Dialog(context);
            mydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mydialog.setContentView(R.layout.setemail_dialog);

            /*initializing all the views of dialog */
            dialog_enter_email = (EditText)mydialog.findViewById(R.id.enter_email);
            img_send_arrow = (ImageView) mydialog.findViewById(R.id.addemail);
            img_hide_dialog = (ImageView)mydialog.findViewById(R.id.hideDialog);

            img_hide_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mydialog.dismiss();
                }
            });
            img_send_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Validate1();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        mydialog.show();
    }

    private void Validate1()
    {
        String email = dialog_enter_email.getText().toString().trim();
        boolean iserror=false;

        if(email.equalsIgnoreCase(""))
        {
            iserror=true;
            dialog_enter_email.setError("Please enter your email.");

        }
        else
        {
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                iserror=true;
                dialog_enter_email.setError("Valid email is required.");

            }
            else
            {
                Toast.makeText(context, "Valid Email.", Toast.LENGTH_SHORT).show();
            }

        }

        if(!iserror)
        {
            RegisterEmail();
        }

    }
    private void RegisterEmail()
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String user_email = dialog_enter_email.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_EMAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    progressDialog.dismiss();

                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!","1235"+response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");

                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success"))
                    {
                        mydialog.dismiss();
                        edt_email_popup.setVisibility(View.GONE);
                        txt_user_email.setVisibility(View.VISIBLE);
                        txt_user_email.setText(user_email);
                        sessionManager.setData(SessionManager.KEY_EMAIL,user_email);
                        Toast.makeText(context, "Successfully registerd email.", Toast.LENGTH_SHORT).show();
                        //JSONObject result_object = jsonObject.getJSONObject("result");
                        // JSONObject object = result_object.getJSONObject("user");


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
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",sessionManager.getData(SessionManager.KEY_USER_ID));
                params.put("email",user_email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context,MyProfileActiivty.class);
        startActivity(intent);
    }

    //.........................................................//.........................................................................//

}
