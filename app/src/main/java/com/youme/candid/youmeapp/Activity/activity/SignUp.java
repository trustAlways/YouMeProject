package com.youme.candid.youmeapp.Activity.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.wang.avi.AVLoadingIndicatorView;
import com.youme.candid.youmeapp.Activity.model.Hobbies;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.GPSTracker;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class SignUp extends AppCompatActivity implements View.OnFocusChangeListener {

    EditText signup_mobile_no, signup_password;
    TextView signup_mobile_no_err, signup_password_err,chek_err,
    message_view,btn_retry_txtview;
    RelativeLayout relativeLayout;
    LinearLayout linear_mobile,password_linear,full_view;
    ImageView not_available_data;
    Context context;
    Button btn_signup,btn_signin;
    AVLoadingIndicatorView avLoadingIndicatorView;
    CheckBox checkBox;
    int click;
    double currentlat,currentlong;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;


    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //for initialize all the views this method will be invoke
        initView();

        startLocationUpdates();

        //for resume activity according to login status
        startUp();


        //for click events this method will be invoked
        clickevents();

    }

    private void startUp()
    {
        //checkForPermission();
        boolean internet = connectionDetector.isConnected(context);
        if (internet)
        {
            getLocation();

            boolean login = sessionManager.isLoggedIn();
            System.out.println("login status"+ login);

            if (login)
            {
                System.out.println("login status"+ login);
                String pr_img = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
                String user_fname = sessionManager.getData(SessionManager.KEY_FIRST_NAME);
                String user_agee = sessionManager.getData(SessionManager.KEY_AGE);
                String user_zodiac = sessionManager.getData(SessionManager.KEY_ZODIAC);
                String lookingfor = sessionManager.getData(SessionManager.KEY_LOOKIN_FOR);
                String hobiy = sessionManager.getData(SessionManager.KEY_HOBBY);
                String gender = sessionManager.getData(SessionManager.KEY_GENDER_);


                setData(user_fname,gender,user_agee,user_zodiac,hobiy,pr_img,lookingfor);
                // startActivity(new Intent(context,StepOneActivity.class));
            }
            boolean register = sessionManager.isRegisterdIn();
            System.out.println("register status"+ register);
            if (register)
            {
                       /* System.out.println("register status"+ register);
                        String pr_img = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
                        String user_fname = sessionManager.getData(SessionManager.KEY_FIRST_NAME);
                        String user_agee = sessionManager.getData(SessionManager.KEY_AGE);
                        String user_zodiac = sessionManager.getData(SessionManager.KEY_ZODIAC);
                        String lookingfor = sessionManager.getData(SessionManager.KEY_LOOKIN_FOR);
                        String hobyy = sessionManager.getData(SessionManager.KEY_HOBBY);
                        String gender = sessionManager.getData(SessionManager.KEY_GENDER_);

                        setData(user_fname,gender,user_agee,user_zodiac,hobyy,pr_img,lookingfor);*/
                startActivity(new Intent(context,StepOneActivity.class));
            }

        }
        else
        {

            full_view.setVisibility(View.GONE);
            btn_retry_txtview.setVisibility(View.VISIBLE);
            message_view.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "Please check your internet connection. Thankyou!", Snackbar.LENGTH_SHORT);

            snackbar.show();

        }
    }

    private void getLocation() {
        GPSTracker gps = new GPSTracker(context);
        if (gps.canGetLocation()) {
             currentlat = gps.getLatitude();
             currentlong = gps.getLongitude();

           /* geocoder = new Geocoder(context(), Locale.getDefault());
            try {
                address  = geocoder.getFromLocation(currentlat,currentlong,1);
                if (address != null && address.size() > 0) {
                    String addresss = address.get(0).getAddressLine(0);
                    String city = address.get(0).getLocality();
                    String country = address.get(0).getCountryName();

                    sessionManager.setData(SessionManager.KEY_USER_CURRENT_ADD, String.valueOf(address));

                    userlocation.setText(addresss + " " + city);
                    located_at.clearAnimation();
                }
                else
                {
                    Toast.makeText(context(), "No Address Found.", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/


            //Toast.makeText(context, currentlat+""+currentlong, Toast.LENGTH_SHORT).show();


            sessionManager.setData(SessionManager.KEY_LATITUDE, String.valueOf(currentlat));
            sessionManager.setData(SessionManager.KEY_LONGITUDE, String.valueOf(currentlong));


        }
        else {
            gps.showSettingsAlert();
        }
    }

    private void initView() {

        context = this;
        sessionManager = new SessionManager(context);
        connectionDetector = new ConnectionDetector();


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mSettingsClient = LocationServices.getSettingsClient(context);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                System.out.println("Current Latitude@@@" + mCurrentLocation.getLatitude());
                System.out.println("Current Longitude@@@" + mCurrentLocation.getLongitude());

                getLocation();
            }
        };

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(15000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        relativeLayout = (RelativeLayout)findViewById(R.id.relative_signup);
        avLoadingIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.loader);

        signup_mobile_no = (EditText)findViewById(R.id.signup_mobile_no);
        signup_password =  (EditText)findViewById(R.id.sign_up_password);

         linear_mobile  =  (LinearLayout)findViewById(R.id.linear_mobile_no);
        password_linear =  (LinearLayout)findViewById(R.id.linear_password);
        full_view  =  (LinearLayout)findViewById(R.id.layout_view);

        not_available_data = (ImageView)findViewById(R.id.no_data_image);

        checkBox = (CheckBox)findViewById(R.id.age_checkBox);
        btn_signup = (Button)findViewById(R.id.bt_sign_up);
        btn_signin = (Button)findViewById(R.id.bt_sign_in);

        btn_retry_txtview = (TextView)findViewById(R.id.btn_retry);
        message_view = (TextView)findViewById(R.id.message_view);

    }

    private void clickevents() {

        signup_password.setOnFocusChangeListener(this);
        signup_mobile_no.setOnFocusChangeListener(this);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click=0;
                System.out.println("click count "+click);
                 checkForPermission(click);

            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click = 1;
                System.out.println("click count "+click);
                checkForPermission(click);

            }
        });

        btn_retry_txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUp();
            }
        });
    }

    private void Validate() {

        final String mobile_no = signup_mobile_no.getText().toString().trim();
        final String password = signup_password.getText().toString().trim();
        boolean iserror=false;


        if(mobile_no.equalsIgnoreCase(""))
        {
            iserror=true;
            //edtMobile.setError("");
            //signup_mobile_no_err.setVisibility(View.VISIBLE);
            //signup_mobile_no_err.setText(Html.fromHtml("This field is required"));
            linear_mobile.setFocusable(true);
            linear_mobile.setBackground(getResources().getDrawable(R.drawable.error_border));

        }
        else
        {
            if (mobile_no.length() < 10)
            {
                iserror=true;
                signup_mobile_no.setError("Mobile number must be 10 digit.");

                linear_mobile.setFocusable(true);
                linear_mobile.setBackground(getResources().getDrawable(R.drawable.error_border));

                //signup_mobile_no_err.setVisibility(View.VISIBLE);
               // signup_mobile_no_err.setText(Html.fromHtml("Phone number minimum 9 digit required."));
            }
            else {
                linear_mobile.setBackground(getResources().getDrawable(R.drawable.corner_border));
            }

        }


        if(password.equalsIgnoreCase(""))
        {
            iserror=true;
           // signup_password_err.setVisibility(View.VISIBLE);
          //  signup_password_err.setText(Html.fromHtml("This field is required"));

            password_linear.setFocusable(true);
            password_linear.setBackground(getResources().getDrawable(R.drawable.error_border));


        }
        else
        {
            if (password.length() < 6)
            {
                iserror=true;
              //  signup_password_err.setVisibility(View.VISIBLE);
              //  signup_password_err.setText(Html.fromHtml("Password minimum 6 character in length."));
                signup_password.setError("Password must be 6 character in length.");
                password_linear.setFocusable(true);
                password_linear.setBackground(getResources().getDrawable(R.drawable.error_border));

            }
            else
            {
                password_linear.setBackground(getResources().getDrawable(R.drawable.corner_border));
            }

            //edtPassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
            //tvpassworderr.setVisibility(View.GONE);
        }

        if(!checkBox.isChecked())
        {
            iserror=true;
            //edtPassword.setError("");
           // chek_err.setVisibility(View.VISIBLE);
           // chek_err.setText(Html.fromHtml("Please check age restriction"));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                checkBox.setButtonTintList(getResources().getColorStateList(R.color.red));
            }

        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                checkBox.setButtonTintList(getResources().getColorStateList(R.color.blue));
            }
            //chek_err.setVisibility(View.GONE);
        }

        if(!iserror)
        {
            //signup_mobile_no_err.setVisibility(View.GONE);
          //  signup_password_err.setVisibility(View.GONE);
            //chek_err.setVisibility(View.GONE);
            linear_mobile.setBackground(getResources().getDrawable(R.drawable.corner_border));
            password_linear.setBackground(getResources().getDrawable(R.drawable.corner_border));

            if (click==0)
            {
                RegisterRequest();
            }
            else
            {
                singinRequest();
            }


        }


    }

    private void RegisterRequest()
    {
         startAnim();
        final String mobile_no = signup_mobile_no.getText().toString().trim();
        final String password = signup_password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER_STEP_1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stopAnim();
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!","1235"+response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");

                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success"))
                    {
                        JSONObject result_object = jsonObject.getJSONObject("result");
                        JSONObject object = result_object.getJSONObject("user");

                            String userid = object.getString("id");
                            String mobile_no = object.getString("mobile_number");
                            String token = object.getString("api_token");

                            sessionManager.setData(SessionManager.KEY_USER_ID, userid);
                            sessionManager.setData(SessionManager.KEY_TOKEN, token);

                            sessionManager.createRegistersession(mobile_no,userid);

                            Intent intent = new Intent(context,StepOneActivity.class);
                            startActivity(intent);

                        //finish();
                    }
                    else if (Status_code.equalsIgnoreCase("422"))
                    {
                        stopAnim();
                        Toast.makeText(context, "The mobile number has been already registered with YouMe.", Toast.LENGTH_SHORT).show();
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
                stopAnim();
                Toast.makeText(getApplicationContext(), "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("mobile_number",mobile_no);
                params.put("password",password);
                params.put("user_lat", String.valueOf(currentlat));
                params.put("user_long", String.valueOf(currentlong));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    private void singinRequest()
    {
        startAnim();
        final String mobile_no = signup_mobile_no.getText().toString().trim();
        final String password = signup_password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SIGN_IN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stopAnim();
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!","1235"+response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");
                    String msg = jsonObject.getString("message");

                    if (Status.equals("success"))
                    {
                        JSONObject result_object = jsonObject.getJSONObject("result");
                        JSONObject object = result_object.getJSONObject("user");

                        String userid = object.getString("id");
                        String mobile_no = object.getString("mobile_number");
                        String frst_nm = object.getString("first_name");
                        String lst_nm = object.getString("last_name");
                        String pseudonym = object.getString("pseudonym");
                        String gender = object.getString("gender");
                        String profile_picture = object.getString("profile_pic");
                        String looking_fr = object.getString("looking_for");
                        String zodiac_signature = object.getString("zodiac_sign");
                        String agee = object.getString("age");
                        String hobbyy = object.getString("interest_hobby");
                        String mail = object.getString("email");

                        String token = object.getString("api_token");
                        System.out.println("tokennn "+ token);
                        sessionManager.setData(SessionManager.KEY_EMAIL, mail);
                        sessionManager.setData(SessionManager.KEY_USER_ID, userid);
                        sessionManager.setData(SessionManager.KEY_TOKEN, token);

                        sessionManager.createLoginsession(mobile_no,userid);

                        setData(frst_nm,gender,agee,zodiac_signature,hobbyy,profile_picture,looking_fr);
                        //finish();
                    }
                    else if (Status_code.equalsIgnoreCase("401"))
                    {
                        stopAnim();
                        Toast.makeText(context, "Invalid Mobile Number Or Password..", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                stopAnim();
                Toast.makeText(getApplicationContext(), "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("mobile_number",mobile_no);
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void setData(String frst_nm, String gender, String agee, String zodiac_signature, String hobbyy,
                         String profile_picture, String looking_fr)
    {
        System.out.println("hobuuu7687878........"+hobbyy +gender+ profile_picture + looking_fr  + zodiac_signature);
        if (frst_nm.equalsIgnoreCase(""))
        {
            Intent intent = new Intent(context,StepOneActivity.class);
            startActivity(intent);
        }
        else if (gender.equalsIgnoreCase("") && agee.equalsIgnoreCase(""))
        {
            Intent intent = new Intent(context,GenderActivity.class);
            startActivity(intent);
        }
        else if (zodiac_signature.equalsIgnoreCase("null"))
        {
            Intent intent = new Intent(context,ZodiacActivity.class);
            startActivity(intent);
        }
        else if (hobbyy.equalsIgnoreCase("null"))
        {
            Intent intent = new Intent(context, HobbiesActivity.class);
            startActivity(intent);
        }

        else if (profile_picture.equalsIgnoreCase("null"))
        {
            Intent intent = new Intent(context, Upload2.class);
            startActivity(intent);
        }
        else if (looking_fr.equalsIgnoreCase(""))
        {
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

        if (v==signup_password) {
            if (!hasFocus) {
                if (signup_password.getText().toString().trim().equals("")) {
                    password_linear.setBackground(getResources().getDrawable(R.drawable.corner_bordertwo));
                }
                else
                {
                    password_linear.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }
            }
            else {
                if (signup_password.getText().toString().trim().equals("")) {
                    password_linear.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }

            }
        }
        if (v==signup_mobile_no)
            if (!hasFocus) {
                if (signup_mobile_no.getText().toString().trim().equals("")) {
                    linear_mobile.setBackground(getResources().getDrawable(R.drawable.corner_bordertwo));
                }
                else
                {
                    linear_mobile.setBackground(getResources().getDrawable(R.drawable.corner_border));
                }
            }
            else
            {
                if (signup_mobile_no.getText().toString().trim().equals("")) {
                    linear_mobile.setBackground(getResources().getDrawable(R.drawable.corner_border));
            }

        }


    }
    void startAnim(){
        avLoadingIndicatorView.show();
        // or avi.smoothToShow();
    }

    void stopAnim(){
        avLoadingIndicatorView.hide();
        // or avi.smoothToHide();
    }

    private void checkForPermission(int click) {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                     Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now

                            boolean internet = connectionDetector.isConnected(context);
                            if (internet)
                            {

                                Validate();
                            }
                            else
                            {
                                Snackbar snackbar = Snackbar
                                        .make(relativeLayout, "Please check your internet connection. Thankyou!", Snackbar.LENGTH_LONG);

                                snackbar.show();

                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            showSettingsDialog() ;

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getPackageName(), null)));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @Override
    public void onBackPressed() {
        alertDialog();
    }

    private void alertDialog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
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


    /* private void checkForPermission() {
        int permissionCheckForCamera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheckForGallery = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheckForAccessCamera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int permissionCheckForAccessFinelocation = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCheckForAccessCoarselocation = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheckForCallPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheckForCamera != PackageManager.PERMISSION_GRANTED ||
                permissionCheckForGallery != PackageManager.PERMISSION_GRANTED ||
                permissionCheckForAccessCamera != PackageManager.PERMISSION_GRANTED ||
                permissionCheckForAccessFinelocation != PackageManager.PERMISSION_GRANTED ||
                permissionCheckForCallPhone != PackageManager.PERMISSION_GRANTED ||
                permissionCheckForAccessCoarselocation != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.CALL_PHONE,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    1001);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("", "Permission callback called-------");
        switch (requestCode) {
            case 1001: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("", "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                )
                        {
                            showDialogOK("Some permission must required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkForPermission();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                           *//* Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();*//*

                            showDialogOK("Permission's must required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                    Uri.fromParts("package", getPackageName(), null)));

                                        }

                                    });
                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", okListener)
                //.setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
*/



    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener((Activity) context, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //Toast.makeText(context(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        getLocation();
                    }
                })
                .addOnFailureListener((Activity) context, new OnFailureListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    // rae.startResolutionForResult(context(), 100);
                                    startIntentSenderForResult(rae.getResolution().getIntentSender(), 100, null, 0, 0, 0, null);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                GPSTracker gpsTracker = new GPSTracker(context);
                                gpsTracker.showSettingsAlert();

                                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        // updateLocationUI();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult()", Integer.toString(resultCode));

        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case 100:
                Log.e(TAG, "User agreed to make required location settings changes." + requestCode);
                switch (resultCode) {
                    case Activity.RESULT_OK:
                    {
                        Log.e(TAG, "User agreed to make required location settings changes." + requestCode);
                        Toast.makeText(context, "Started location updates!", Toast.LENGTH_SHORT).show();

                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        startLocationUpdates();
                        break;
                    }

                    case Activity.RESULT_CANCELED:
                    {
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        startLocationUpdates();
                        break;
                    }
                    default:
                    {
                        break;
                    }

                }
                break;
        }
    }

}
