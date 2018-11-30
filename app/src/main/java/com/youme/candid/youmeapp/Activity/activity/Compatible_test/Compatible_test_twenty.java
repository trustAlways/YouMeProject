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

public class Compatible_test_twenty extends AppCompatActivity {

    ImageView img_nxt, img_previous;
    SessionManager sessionManager;
    RadioButton radioButton_yes, radioButton_no;
    Context context;
    TextView btn_reset_txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compatible_test_twenty);

        //for initialize all the view this method will be invoked
        init();
        //for all click events this method will be invoked
        click();
    }

    //view initializtion....//
    private void init() {
        context = this;
        sessionManager = new SessionManager(context);
        radioButton_yes = (RadioButton) findViewById(R.id.c_twenty_yes);
        radioButton_no = (RadioButton) findViewById(R.id.c_twenty_no);
        img_nxt = (ImageView) findViewById(R.id.ctesttwenty_next_step);
        img_previous = (ImageView) findViewById(R.id.ctesttwenty_previous_step);
        btn_reset_txtview = (TextView)findViewById(R.id.reset_btn);


        final String test_twenty = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_TWENTY);
        System.out.println("test 20"+ test_twenty);
        if (test_twenty!=null) {
            if (test_twenty.equalsIgnoreCase("Yes")) {
                radioButton_yes.setChecked(true);
            } else if (test_twenty.equalsIgnoreCase("No")) {
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
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_TWENTY, "Yes");
                        updateData();
                    }
                    else if(radioButton_no.isChecked()==true)
                    {
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_TWENTY, "No");
                        updateData();
                    }
                }
            }
        });

        //without complete the test
        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Compatible_test_twenty.this, Compatible_test_ninty.class));
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
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_NINTEEN,"");
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_TWENTY,"");


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
        final String test_elven = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_ELEVEN);
        final String test_twelve = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_TWELVE);
        final String test_thrteen = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_THIRTEEN);
        final String test_fourteen = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_FOURTEEN);
        final String test_fifteen = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_FIFTEEN);
        final String test_sixteen = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_SIXTEEN);
        final String test_seventeen = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_SEVENTEEN);
        final String test_eighteen = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_EIGHTEEN);
        final String test_ninety = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_NINTEEN);
        final String test_twenty = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_TWENTY);


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
                                startActivity(new Intent(Compatible_test_twenty.this, MyProfileActiivty.class));

                            } else if (Status.equals("error")) {
                                dialog.dismiss();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
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

                params.put("into_relationship",test_elven);
                params.put("cheated_in_relationship",test_twelve);
                params.put("follow_crush",test_thrteen);
                params.put("want_crush",test_fourteen);
                params.put("more_crush",test_fifteen);
                params.put("allow_crush",test_sixteen);
                params.put("stronger_by_confession",test_seventeen);
                params.put("is_secrets_good",test_eighteen);
                params.put("successful_relationship",test_ninety);
                params.put("religion_barrier",test_twenty);
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
