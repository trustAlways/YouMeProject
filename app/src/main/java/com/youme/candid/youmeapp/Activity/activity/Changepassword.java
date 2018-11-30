package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Changepassword extends AppCompatActivity implements View.OnFocusChangeListener {
EditText oldpass,new_pass,cnfrmpass;
TextView oldpasserr,newpasserr,cnfrmerr;
Button btnupdate;
ImageView img_backarrow;
SessionManager sessionManager;
ConnectionDetector connectionDetector;
ProgressDialog pd ;
LinearLayout linear_old,linear__new,linear_cnfrm;
Context ctx;
String Tkn , id;
    String encrypt_oldpassword;
    String encrypt_newpassword;
    String encrypt_confirmpassword;
  View v;
   /* @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.user_change_password,null);

        Initialize();
        clickListner();

        return v;
    }
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_change_password);

        Initialize();
        clickListner();

    }

    private void clickListner() {

        oldpass.setOnFocusChangeListener(this);
        new_pass.setOnFocusChangeListener(this);
        cnfrmpass.setOnFocusChangeListener(this);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean internet = connectionDetector.isConnected(ctx);
                if(internet) {
                    Validate1();
                }
                else
                {
                    Toast.makeText(ctx, "Please Check Your Internet Connection..", Toast.LENGTH_SHORT).show();
                }


            }
        });

        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    private void Initialize() {
        ctx = this;
        sessionManager = new SessionManager(ctx);
        connectionDetector = new ConnectionDetector();
        pd = new ProgressDialog(ctx);

        oldpass = (EditText)findViewById(R.id.edt_user_oldpassword);
        new_pass = (EditText)findViewById(R.id.edt_user_newpassword);
        cnfrmpass = (EditText)findViewById(R.id.edt_user_confrmpassword);

        oldpasserr = (TextView) findViewById(R.id.edt_user_oldpassworderr);
        newpasserr = (TextView) findViewById(R.id.edt_user_newpassworderr);
        cnfrmerr = (TextView) findViewById(R.id.edt_user_confrmpassworderr);

        linear_old  =  (LinearLayout)findViewById(R.id.oldlinear);
        linear__new =  (LinearLayout)findViewById(R.id.newlinear);
        linear_cnfrm =  (LinearLayout)findViewById(R.id.confrmlinear);

        btnupdate = (Button)findViewById(R.id.btn_change_password);

        img_backarrow = (ImageView)findViewById(R.id.chngepassword_arrow);

        Tkn = sessionManager.getData(SessionManager.KEY_TOKEN);
        id = sessionManager.getData(SessionManager.KEY_USER_ID);
        }

    private void Validate1()
    {
        final String old = oldpass.getText().toString().trim();
        final String newpss = new_pass.getText().toString().trim();
        final String cnfrm = cnfrmpass.getText().toString().trim();
        boolean iserror=false;

        if(old.equalsIgnoreCase(""))
        {
            iserror=true;
            linear_old.setFocusable(true);
            linear_old.setBackground(getResources().getDrawable(R.drawable.error_border));

        }

        else
        {
            linear_old.setBackground(getResources().getDrawable(R.drawable.corner_border));

        }
        if(newpss.equalsIgnoreCase(""))
        {
            iserror=true;
            linear__new.setFocusable(true);
            linear__new.setBackground(getResources().getDrawable(R.drawable.error_border));


        }
        else
        {
            if (newpss.length() < 6)
            {
                iserror=true;
                new_pass.setError("Password must be 6 character in length.");

                linear__new.setFocusable(true);
                linear__new.setBackground(getResources().getDrawable(R.drawable.error_border));

            }
            else {
                linear__new.setBackground(getResources().getDrawable(R.drawable.corner_border));
            }

            //edtUserName.getBackground().mutate().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
            //newpasserr.setVisibility(View.GONE);
        }




        if(cnfrm.equalsIgnoreCase(""))
        {
            iserror=true;
            linear_cnfrm.setFocusable(true);
            linear_cnfrm.setBackground(getResources().getDrawable(R.drawable.error_border));
        }
        else
        {
            if (cnfrm.length() < 6)
            {
                iserror=true;
                cnfrmpass.setError("Password must be 6 character in length.");
                linear_cnfrm.setFocusable(true);
                linear_cnfrm.setBackground(getResources().getDrawable(R.drawable.error_border));

            }
            else {
               linear_cnfrm.setBackground(getResources().getDrawable(R.drawable.corner_border));
            }
            //edtUserName.getBackground().mutate().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
           // cnfrmerr.setVisibility(View.GONE);
        }






        if(!iserror)
        {
            linear_old.setBackground(getResources().getDrawable(R.drawable.corner_border));
            linear__new.setBackground(getResources().getDrawable(R.drawable.corner_border));
            linear_cnfrm.setBackground(getResources().getDrawable(R.drawable.corner_border));
            loadingChangePasswordData();
        }

    }


    private String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }


    private void loadingChangePasswordData() {
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        final String oldpassword = oldpass.getText().toString().trim();
        final String newpassword = new_pass.getText().toString().trim();
        final String confirmpassword = cnfrmpass.getText().toString().trim();

        final String unsecurepassword = oldpassword;
        //encrypt_oldpassword = md5(unsecurepassword );

        final String newpass = newpassword;
        //encrypt_newpassword = md5(newpass );

        final String cnfrmpassword = confirmpassword;
        //encrypt_confirmpassword = md5(cnfrmpass );

       if (TextUtils.isEmpty(oldpassword)) {
            pd.dismiss();
            oldpass.setError("Please enter old password.");
            oldpass.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(newpassword)) {
            pd.dismiss();
            new_pass.setError("Please enter new password.");
            new_pass.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cnfrmpassword)) {
            pd.dismiss();
            cnfrmpass.setError("Please enter confirm password.");
            cnfrmpass.requestFocus();
            return;
        }
        if(!cnfrmpassword.equalsIgnoreCase(newpassword))
        {
            cnfrmpass.setError("Confirm Password not matched with the new password ");
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_CHANGEPASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //print the response from server
                            Log.i("response","change password..."+response);

                            String  Status = obj.getString("status");
                            String  Statuscode = obj.getString("status_code");

                            String msg = obj.getString("message");

                            //if no error in response
                            if (Status.equals("success") || msg.equals("your password has been changed"))
                            {
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ctx,User_Profile_Setting.class);
                                startActivity(intent);

                                //oldpass.setText("");
                                //newpass.setText("");
                                //cnfrmpass.setText("");

                            }
                            else if (Status.equals("error") && Statuscode.equals("500"))
                            {
                                pd.dismiss();
                                String msgg = capitalize(msg);
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                            }
                            else if (Status.equals("error") && Statuscode.equals("422"))
                            {
                                pd.dismiss();
                                String msgg = capitalize(msg);
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                            }
                            else if (Status.equals("error")&& Statuscode.equals("401"))
                            {
                                pd.dismiss();
                                String msgg = capitalize(msg);
                                Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                pd.dismiss();
                                Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",sessionManager.getData(SessionManager.KEY_USER_ID));
                params.put("old_password",unsecurepassword);
                params.put("password", newpass);
                params.put("password_confirmation", cnfrmpassword);
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

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v==oldpass) {
            if (!hasFocus) {
                if (oldpass.getText().toString().trim().equals("")) {
                    linear_old.setBackground(getResources().getDrawable(R.drawable.corner_bordertwo));
                }
                else
                {
                    linear_old.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }
            }
            else {
                if (oldpass.getText().toString().trim().equals("")) {
                    linear_old.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }

            }
        }

        if (v==new_pass) {
            if (!hasFocus) {
                if (new_pass.getText().toString().trim().equals("")) {
                    linear__new.setBackground(getResources().getDrawable(R.drawable.corner_bordertwo));
                }
                else
                {
                    linear_old.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }
            }
            else {
                if (new_pass.getText().toString().trim().equals("")) {
                    linear__new.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }

            }
        }

        if (v==cnfrmpass) {
            if (!hasFocus) {
                if (cnfrmpass.getText().toString().trim().equals("")) {
                    linear_cnfrm.setBackground(getResources().getDrawable(R.drawable.corner_bordertwo));
                }
                else
                {
                    linear_cnfrm.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }
            }
            else {
                if (cnfrmpass.getText().toString().trim().equals("")) {
                    linear_cnfrm.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }

            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ctx,User_Profile_Setting.class);
        startActivity(intent);
    }

    /*  @Override
    public boolean onTouch(View v, MotionEvent event) {
       switch (v.getId())
       {
           case R.id.edt_user_oldpassword:
               oldpass.setEnabled(true);
               break;
           case R.id.edt_user_newpassword:
               newpass.setEnabled(true);
               break;
           case R.id.edt_user_confrmpassword:
               cnfrmpass.setEnabled(true);
               break;
       }

        return false;
    }*/
}
