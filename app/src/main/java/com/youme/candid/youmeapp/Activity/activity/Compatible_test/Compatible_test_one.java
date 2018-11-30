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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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
import com.youme.candid.youmeapp.Activity.activity.Test_activities.Test_two;
import com.youme.candid.youmeapp.Activity.activity.UserProfileActivity;
import com.youme.candid.youmeapp.Activity.adapter.CrushAdapter;
import com.youme.candid.youmeapp.Activity.model.CrushRequestBeans;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Compatible_test_one extends AppCompatActivity {


     RelativeLayout frst_view;
     FrameLayout frm_second;
    TextView Intro_extro,you_frankly,relax_under_pressure,conversation_frst,organize_adapteble,attention_seeker,prcticl_creatve,eht_u_like
            ,your_dreamer,spend_time_with,into_relationshp,cheted_in_relation,foloo_crush,still_want_crush,one_crush_at_time,allow_crush_other
            ,strong_confession,secret_is_good,matter_for_successfull_relation,relligion_barrier,btn_reset_txtview;
    ImageView img_nxt,img_previous,no_data_available;
    SessionManager sessionManager;
    RadioButton radioButton_yes,radioButton_no;
    Context context;
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compatible_test_one);

        //for initialize all the view this method will be invoked
        init();
        //getData for check test is completed or not
        //getData();

        Boolean internet = connectionDetector.isConnected(context);
        if (internet)
        {
            //for get user data
            getData();
        }
        else
        {
            Toast.makeText(context, "Check your internet connection.", Toast.LENGTH_LONG).show();
            no_data_available.setVisibility(View.VISIBLE);
        }
        //for all click events this method will be invoked
        click();
    }

    private void getData() {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("Loading..");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_COMPATIBILITY_TEST_RESULT, new Response.Listener<String>() {
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

                  if (result.equalsIgnoreCase("Not Completed"))
                  {
                      System.out.println("frst---##11");
                      frm_second.setVisibility(View.GONE);
                      frst_view.setVisibility(View.VISIBLE);
                  }
                  else
                  {
                      if (Status.equals("success") ) {

                        JSONObject jsonObject = obj.getJSONObject("result");
                        String intro_extro = jsonObject.getString("intro_extro");
                        String introduce_frankly = jsonObject.getString("introduce_frankly");
                        String relaxed_under_pressure = jsonObject.getString("relaxed_under_pressure");
                        String initiate_conversation = jsonObject.getString("initiate_conversation");
                        String organized_adaptable = jsonObject.getString("organized_adaptable");
                        String atnsn_sekkr = jsonObject.getString("attention_seeker");
                        String are_you_practical = jsonObject.getString("are_you_practical");
                        String truth_dare = jsonObject.getString("truth_dare");
                        String call_dreamer = jsonObject.getString("call_dreamer");
                        String spending_time = jsonObject.getString("spending_time");
                        String into_relationship = jsonObject.getString("into_relationship");
                        String cheated_in_relationship = jsonObject.getString("cheated_in_relationship");
                        String follow_crush = jsonObject.getString("follow_crush");
                        String want_crush = jsonObject.getString("want_crush");
                        String more_crush = jsonObject.getString("more_crush");
                        String allow_crush = jsonObject.getString("allow_crush");
                        String stronger_by_confession = jsonObject.getString("stronger_by_confession");
                        String is_secrets_good = jsonObject.getString("is_secrets_good");
                        String successful_relationship = jsonObject.getString("successful_relationship");
                        String religion_barrier = jsonObject.getString("religion_barrier");

                        if (!intro_extro.equalsIgnoreCase(""))
                        {
                            System.out.println("scnd---##222");
                            frst_view.setVisibility(View.GONE);
                            frm_second.setVisibility(View.VISIBLE);

                        setData(intro_extro,introduce_frankly,relaxed_under_pressure,initiate_conversation,organized_adaptable,atnsn_sekkr,are_you_practical
                                ,truth_dare,call_dreamer,spending_time,into_relationship,cheated_in_relationship,follow_crush,want_crush,more_crush
                                ,allow_crush,stronger_by_confession,is_secrets_good,successful_relationship,religion_barrier);
                       }
                       else
                           {
                            System.out.println("frst---##11");
                               frm_second.setVisibility(View.GONE);
                               frst_view.setVisibility(View.VISIBLE);
                        }


                    }

                    else if (Status.equals("error")) {
                        pd.dismiss();
                        //pb.setVisibility(View.GONE);
                          no_data_available.setVisibility(View.VISIBLE);
                          Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        pd.dismiss();
                        // pb.setVisibility(View.GONE);
                          no_data_available.setVisibility(View.VISIBLE);
                          Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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
                no_data_available.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Couldn't connect to server..", Toast.LENGTH_SHORT).show();
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

    private void setData(String intro_extro, String introduce_frankly, String relaxed_under_pressure, String initiate_conversation,
                         String organized_adaptable, String atnsn_seekr, String are_you_practical, String truth_dare, String call_dreamer, String spending_time,
                         String into_relationship, String cheated_in_relationship, String follow_crush, String want_crush,
                         String more_crush, String allow_crush, String stronger_by_confession, String is_secrets_good,
                         String successful_relationship, String religion_barrier)
    {
          Intro_extro.setText(intro_extro);
          you_frankly.setText(introduce_frankly);
          relax_under_pressure.setText(relaxed_under_pressure);
          conversation_frst.setText(initiate_conversation);
          organize_adapteble.setText(organized_adaptable);
         attention_seeker.setText(atnsn_seekr);
        prcticl_creatve.setText(are_you_practical);
        eht_u_like.setText(truth_dare);
        your_dreamer.setText(call_dreamer);
        spend_time_with.setText(spending_time);
        into_relationshp.setText(into_relationship);
        cheted_in_relation.setText(cheated_in_relationship);
        foloo_crush.setText(follow_crush);
        still_want_crush.setText(want_crush);
        one_crush_at_time.setText(more_crush);
        allow_crush_other.setText(allow_crush);
        strong_confession.setText(stronger_by_confession);
        secret_is_good.setText(is_secrets_good);
        matter_for_successfull_relation.setText(successful_relationship);
        relligion_barrier.setText(religion_barrier);
    }
    //view initializtion....//
    private  void init(){
        context = this;
        connectionDetector = new ConnectionDetector();
        sessionManager = new SessionManager(context);

        radioButton_yes = (RadioButton)findViewById(R.id.c_one_yes);
        radioButton_no = (RadioButton)findViewById(R.id.c_one_no);
        img_nxt = (ImageView)findViewById(R.id.ctestone_next_step);
        img_previous = (ImageView)findViewById(R.id.ctestone_previous_step);
        no_data_available = (ImageView)findViewById(R.id.no_data);

        frm_second = (FrameLayout)findViewById(R.id.second_view);
        frst_view = (RelativeLayout)findViewById(R.id.frst_view);

        //all the text views
        Intro_extro = (TextView)findViewById(R.id.ctest_frst_ans);
        you_frankly = (TextView)findViewById(R.id.ctest_scnd_ans);
        relax_under_pressure = (TextView)findViewById(R.id.ctest_thrd_ans);
        conversation_frst = (TextView)findViewById(R.id.ctest_frth_ans);
        organize_adapteble = (TextView)findViewById(R.id.ctest_five_ans);//....karna hai
        attention_seeker = (TextView)findViewById(R.id.ctest_six_ans);
        prcticl_creatve = (TextView)findViewById(R.id.ctest_seven_ans);
        eht_u_like = (TextView)findViewById(R.id.ctest_eight_ans);
        your_dreamer = (TextView)findViewById(R.id.ctest_nine_ans);
        spend_time_with = (TextView)findViewById(R.id.ctest_ten_ans);
        into_relationshp = (TextView)findViewById(R.id.ctest_eleven_ans);
        cheted_in_relation = (TextView)findViewById(R.id.ctest_twelve_ans);
        foloo_crush = (TextView)findViewById(R.id.ctest_thrteen_ans);
        still_want_crush = (TextView)findViewById(R.id.ctest_frteen_ans);
        one_crush_at_time = (TextView)findViewById(R.id.ctest_ffteen_ans);
        allow_crush_other = (TextView)findViewById(R.id.ctest_sixteen_ans);
        strong_confession = (TextView)findViewById(R.id.ctest_seventeen_ans);
        secret_is_good = (TextView)findViewById(R.id.ctest_eighty_ans);
        matter_for_successfull_relation = (TextView)findViewById(R.id.ctest_ninty_ans);
        relligion_barrier = (TextView)findViewById(R.id.ctest_twenty_ans);
        btn_reset_txtview = (TextView)findViewById(R.id.reset_btn);

        final String test_one = sessionManager.getData(SessionManager.KEY_COMPATIBILITY_ONE);
        System.out.println("test 1"+ test_one);
        if (test_one!=null) {
            if (test_one.equalsIgnoreCase("Introvert")) {
                radioButton_yes.setChecked(true);
            } else if (test_one.equalsIgnoreCase("Extrovert")) {
                radioButton_no.setChecked(true);
            }
        }

    }

    //click events.......//
    private void click() {

        radioButton_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked==true)
                {
                    radioButton_no.setChecked(false);
                    radioButton_yes.setChecked(true);
                }
                else
                {
                    radioButton_yes.setChecked(false);
                    radioButton_no.setChecked(true);

                }
            }
        });

        radioButton_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked==true)
                {
                    radioButton_no.setChecked(true);
                    radioButton_yes.setChecked(false);
                }
                else
                {
                    radioButton_yes.setChecked(true);
                    radioButton_no.setChecked(false);
                }
            }
        });

        //after complete the test
        img_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("test one"+ radioButton_yes.isChecked()+" "+radioButton_no.isChecked());

                if (radioButton_yes.isChecked()==false && radioButton_no.isChecked()==false)
                {
                    Toast.makeText(context, "You have to chooose one.", Toast.LENGTH_SHORT).show();
                }
                else
                    {

                    if (radioButton_yes.isChecked()==true)
                    {
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_ONE,"Introvert");
                    }
                    else if (radioButton_no.isChecked()==true)
                    {
                        sessionManager.setData(SessionManager.KEY_COMPATIBILITY_ONE,"Extrovert");
                    }
                    startActivity(new Intent(Compatible_test_one.this, Compatible_test_two.class));

                }




            }
        });

        //without complete the test
        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Compatible_test_one.this, MyProfileActiivty.class));
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
}
