package com.youme.candid.youmeapp.Activity.activity.Test_activities;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Test_three extends AppCompatActivity {
    ImageView img_nxt,img_previous;
    EditText edt_testthree;
    LinearLayout linearLayout;
    SessionManager sessionManager;
   Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_three);

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
        final String test_ans = sessionManager.getData(SessionManager.KEY_TEST_THREE);
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
                        edt_testthree.setEnabled(true);
                        edt_testthree.setText(test_ans);
                    }
                    else {

                        if (Status.equals("success")) {

                            JSONObject jsonObject = obj.getJSONObject("result");
                            String user_kind_of_partner_u = jsonObject.getString("kind_of_partner_u");

                            if (!user_kind_of_partner_u.equalsIgnoreCase("")) {
                                System.out.println("scnd---##222");
                                edt_testthree.setEnabled(false);
                                edt_testthree.setText(user_kind_of_partner_u);

                                // setData();
                            } else {
                                System.out.println("frst---##11");
                                edt_testthree.setEnabled(true);
                                edt_testthree.setText(test_ans);
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
        img_nxt = (ImageView)findViewById(R.id.tstthree_next_step);
        img_previous = (ImageView)findViewById(R.id.testthree_previous_step);
        edt_testthree = (EditText)findViewById(R.id.test_three);
        linearLayout = (LinearLayout)findViewById(R.id.ll_three);
    }

    private void click() {

        img_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test_ans = edt_testthree.getText().toString();
                if (test_ans.equalsIgnoreCase(""))
                {
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.error_border));
                }
                else
                {
                    sessionManager.setData(SessionManager.KEY_TEST_THREE,test_ans);
                    startActivity(new Intent(Test_three.this, Test_four.class));
                }
            }
        });

        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Test_three.this, Test_two.class));
            }
        });
    }
}
