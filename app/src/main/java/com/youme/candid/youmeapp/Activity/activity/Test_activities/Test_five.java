package com.youme.candid.youmeapp.Activity.activity.Test_activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.youme.candid.youmeapp.Activity.activity.MyProfileActiivty;
import com.youme.candid.youmeapp.Activity.adapter.SendDateAdapter;
import com.youme.candid.youmeapp.Activity.model.DateSentRequestBeans;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Test_five extends AppCompatActivity {

    ImageView img_nxt,img_previous;
    EditText edt_testfive;
    LinearLayout linearLayout;
    SessionManager sessionManager;
    Context context;
    String user_dream_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_five);

        //for initialize all the view this method will be invoked
        init();
        //getData for check test is completed or not
        getData();
        //for all click events this method will be invoked
        click();
    }

    private void getData() {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("Loading..");
        pd.show();
        final String test_ans = sessionManager.getData(SessionManager.KEY_TEST_Five);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_PERSONALITY_TEST_RESULT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    Log.i("response..!", "data%%%" + response);
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    String Status = obj.getString("status");
                    String msg = obj.getString("message");
                    String result = obj.getString("result");

                    if(result.equalsIgnoreCase("Not Completed"))
                    {
                        System.out.println("frst---##11");
                        edt_testfive.setEnabled(true);
                        edt_testfive.setText(test_ans);
                        user_dream_date="";
                    }
                    else {

                        if (Status.equals("success")) {

                            JSONObject jsonObject = obj.getJSONObject("result");
                            user_dream_date = jsonObject.getString("dream_date");

                            if (!user_dream_date.equalsIgnoreCase("")) {
                                System.out.println("scnd---##222");
                                edt_testfive.setEnabled(false);
                                edt_testfive.setText(user_dream_date);
                                edt_testfive.setTextSize(20);
                                linearLayout.setBackground(null);

                                // setData();
                            } else {
                                System.out.println("frst---##11");
                                edt_testfive.setEnabled(true);
                                edt_testfive.setText(test_ans);
                            }


                        } else if (Status.equals("error")) {
                            pd.dismiss();
                            //pb.setVisibility(View.GONE);
                            Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
                        } else {
                            pd.dismiss();
                            // pb.setVisibility(View.GONE);
                            Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // pb.setVisibility(View.GONE);
                pd.dismiss();
                Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    private  void init(){
        context = this;
        sessionManager = new SessionManager(context);
        img_nxt = (ImageView)findViewById(R.id.testfive_next_step);
        img_previous = (ImageView)findViewById(R.id.testfive_previous_step);
        edt_testfive = (EditText)findViewById(R.id.edt_test_five);
        linearLayout = (LinearLayout)findViewById(R.id.ll_five);
    }

    private void click() {

        img_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String test_ans = edt_testfive.getText().toString();
                if (test_ans.equalsIgnoreCase(""))
                {
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.error_border));
                }
                else
                {
                    sessionManager.setData(SessionManager.KEY_TEST_Five,test_ans);
                    if (!user_dream_date.equalsIgnoreCase(""))
                    {
                        startActivity(new Intent(Test_five.this, MyProfileActiivty.class));
                    }
                    else
                    {
                        alertDialog();
                    }

                }
            }
        });

        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Test_five.this, Test_four.class));
            }
        });
    }

    private void updateTestComplete()
    {

        final String one = sessionManager.getData(SessionManager.KEY_TEST_ONE);
        final String two = sessionManager.getData(SessionManager.KEY_TEST_Two);
        final String three = sessionManager.getData(SessionManager.KEY_TEST_THREE);
        final String fore = sessionManager.getData(SessionManager.KEY_TEST_Four);
        final String five = sessionManager.getData(SessionManager.KEY_TEST_Five);

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("Loading..");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_PERSONALITY_TEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    Log.i("response..!", "data%%%" + response);
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    String Status = obj.getString("status");
                    String msg = obj.getString("message");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    if (Status.equals("success") )
                    {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Test_five.this, MyProfileActiivty.class));
                    }
                     else if (Status.equals("error")) {
                        pd.dismiss();
                        //pb.setVisibility(View.GONE);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        // pb.setVisibility(View.GONE);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // pb.setVisibility(View.GONE);
                pd.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                 params.put("user_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                params.put("friend_say", one);
                params.put("impress_date", two);
                params.put("kind_of_partner_u",three);
                params.put("kind_of_partner_looking",fore);
                params.put("dream_date",five);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    private void alertDialog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to submit personality test?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        updateTestComplete();

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
