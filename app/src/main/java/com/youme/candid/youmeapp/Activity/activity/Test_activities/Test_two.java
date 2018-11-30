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
import com.youme.candid.youmeapp.Activity.activity.MyProfileActiivty;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Test_two extends AppCompatActivity {
    ImageView img_nxt,img_previous;
    EditText edt_testtwo;
    LinearLayout linearLayout;
    Context context;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_two);

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

        final String test_ans = sessionManager.getData(SessionManager.KEY_TEST_Two);

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
                        edt_testtwo.setEnabled(true);
                        edt_testtwo.setText(test_ans);
                    }
                    else {


                        if (Status.equals("success")) {

                            JSONObject jsonObject = obj.getJSONObject("result");
                            String user_impress_date = jsonObject.getString("impress_date");

                            if (!user_impress_date.equalsIgnoreCase("")) {
                                System.out.println("scnd---##222");
                                edt_testtwo.setEnabled(false);
                                edt_testtwo.setText(user_impress_date);

                                // setData();
                            } else {
                                System.out.println("frst---##11");
                                edt_testtwo.setEnabled(true);
                                edt_testtwo.setText(test_ans);
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
        img_nxt = (ImageView)findViewById(R.id.test_two_next_step);
        img_previous = (ImageView)findViewById(R.id.test_two_previous_step);
        edt_testtwo = (EditText)findViewById(R.id.test_two);
        linearLayout = (LinearLayout)findViewById(R.id.linear_two);
    }

    private void click() {

        img_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String test_ans = edt_testtwo.getText().toString();
                if (test_ans.equalsIgnoreCase(""))
                {
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.error_border));
                }
                else
                {
                    sessionManager.setData(SessionManager.KEY_TEST_Two,test_ans);
                    startActivity(new Intent(Test_two.this, Test_three.class));
                }
            }
        });

        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Test_two.this, Test_one.class));
            }
        });
    }
}
