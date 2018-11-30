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
import com.youme.candid.youmeapp.Activity.activity.User_Profile_Setting;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Compatible_test_ten extends AppCompatActivity {

    ImageView img_nxt, img_previous;
    SessionManager sessionManager;
    RadioButton radioButton_yes, radioButton_no;
    Context context;
    TextView btn_reset_txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compatible_test_ten);

        //for initialize all the view this method will be invoked
        init();
        //for all click events this method will be invoked
        click();
    }

    //view initializtion....//
    private void init() {
        context = this;
        sessionManager = new SessionManager(context);
        radioButton_yes = (RadioButton) findViewById(R.id.c_ten_yes);
        radioButton_no = (RadioButton) findViewById(R.id.c_ten_no);
        img_nxt = (ImageView) findViewById(R.id.ctestten_next_step);
        img_previous = (ImageView) findViewById(R.id.ctestten_previous_step);
        btn_reset_txtview = (TextView)findViewById(R.id.reset_btn);


        final String test_ten = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_TEN);
        System.out.println(" test 10" + test_ten);

        if (test_ten != null) {
            if (test_ten.equalsIgnoreCase("Family")) {
                radioButton_yes.setChecked(true);
            } else if (test_ten.equalsIgnoreCase("Friends")) {
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
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_TEN, "Family");
                        startActivity(new Intent(Compatible_test_ten.this, Compatible_test_eleven.class));
                    }
                    else if(radioButton_no.isChecked()==true)
                    {
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_TEN, "Friends");
                        startActivity(new Intent(Compatible_test_ten.this, Compatible_test_eleven.class));
                    }
                }

            }
        });

        //without complete the test
        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Compatible_test_ten.this, Compatible_test_nine.class));
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

    private void updateData() {

        final String test_one = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_ONE);
        final String test_two = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_TWO);
        final String test_three = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_THREE);
        final String test_four = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_FOUR);
        final String test_five = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_FIVE);
        final String test_six = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_SIX);
        final String test_seven = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_SEVEN);
        final String test_eight = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_EIGHT);
        final String test_nine = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_NINE);
        final String test_ten = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_TEN);


        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(true);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_COMPATIBILITY_TEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //print the response from server
                            Log.i("response", "compativility test..." + response);

                            String Status = obj.getString("status");
                            String Statuscode = obj.getString("status_code");

                            String msg = obj.getString("message");

                            //if no error in response
                            if (Status.equals("success")) {

                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Compatible_test_ten.this, MyProfileActiivty.class));

                            } else if (Status.equals("error")) {
                                dialog.dismiss();
                                Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                params.put("intro_extro",test_one);
                params.put("introduce_frankly",test_two);
                params.put("relaxed_under pressure",test_three);
                params.put("initiate_conversation",test_four);
                params.put("organized_adaptable",test_five);
                params.put("attention_seeker",test_six);
                params.put("are_you_practical",test_seven);
                params.put("truth_dare",test_eight);
                params.put("call_dreamer",test_nine);
                params.put("spending_time",test_ten);

                return params;
            }

           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params1 = new HashMap<>();
                params1.put("X-API-KEY","TEST@123");
                params1.put("Authorization","Bearer "+ Tkn );
                return params1;
            }
*/

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


}
