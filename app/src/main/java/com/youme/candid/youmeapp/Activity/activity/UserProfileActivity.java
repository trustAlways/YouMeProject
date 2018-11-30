package com.youme.candid.youmeapp.Activity.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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



import com.eminayar.panter.DialogType;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.interfaces.OnTextInputConfirmListener;
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
import com.skyfishjy.library.RippleBackground;
import com.youme.candid.youmeapp.Activity.adapter.SlidingImage_Adapter;
import com.youme.candid.youmeapp.Activity.model.CardItem;
import com.youme.candid.youmeapp.Activity.model.Navigation;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.GPSTracker;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import link.fls.swipestack.SwipeStack;
import me.relex.circleindicator.CircleIndicator;

import static android.support.constraint.Constraints.TAG;

public class UserProfileActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    CircleImageView image_icon,img_not_available_data;
    ImageView img_like,img_dislike,img_sendcrush,img_reload,chat_icon,dating_icon;
    LinearLayout linear;
    private SwipeStack cardStack;
    private CardsAdapter cardsAdapter;
    private ArrayList<CardItem> cardItems;
    PrettyDialog dialog;
    Dialog send_crush_dialog;
    int currentPosition;
    FrameLayout frameLayout;
     double currentlat,currentlong;
    private final static int INTERVAL = 1000 * 60 * 15; //15 minutes
    Handler mHandler = new Handler();
    Runnable runnable;
    ProgressDialog pd;
    DrawerLayout drawerLayout;
    ListView listView;
    ArrayList<Navigation> nav_list;
    // GiphyDialog giphyDialog;
    RippleBackground rippleBackground;
    Context context;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    ProgressDialog progressDialog;

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
        setContentView(R.layout.profile);

        // Get the widgets reference from XML layout
        initView();

        startLocationUpdates();

        boolean internet = connectionDetector.isConnected(context);
        if (internet)
        {
            getLocation();
            loadUserData();
        }
        else
        {
            rippleBackground.startRippleAnimation();

            Snackbar snackbar = Snackbar
                    .make(linear, "Please check your internet connection. Thankyou!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        // setNavigation();
        // for all the click events
        click();
    }

//.....................................................For initialize the views .......................................................////

    private void initView()
    {


        context = this;
        connectionDetector = new ConnectionDetector();
        sessionManager = new SessionManager(context);


        pd = new ProgressDialog(context);
        cardItems = new ArrayList<>();

        linear = (LinearLayout) findViewById(R.id.relative_user_profile);
        linear.setVisibility(View.VISIBLE);
        cardStack = (SwipeStack) findViewById(R.id.container);

        img_reload =(ImageView)findViewById(R.id.reload_stack);
        img_like = (ImageView)findViewById(R.id.like);
        img_dislike = (ImageView)findViewById(R.id.dislike);
        img_sendcrush = (ImageView)findViewById(R.id.send_crush_request);
        img_not_available_data = (CircleImageView) findViewById(R.id.img_no_data_avel);
        chat_icon = (ImageView)findViewById(R.id.chat_icon);
        dating_icon = (ImageView)findViewById(R.id.dating);

        rippleBackground=(RippleBackground)findViewById(R.id.content);

        img_like.setEnabled(false);
        img_like.setClickable(false);
        img_reload.setEnabled(false);
        img_reload.setClickable(false);
        img_sendcrush.setEnabled(false);
        img_sendcrush.setClickable(false);
        img_dislike.setClickable(false);
        img_dislike.setEnabled(false);


        image_icon = (CircleImageView) findViewById(R.id.profile_image_icon);

        String pr_img = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
        String user_gen = sessionManager.getData(SessionManager.KEY_GENDER_);

       // Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 30);
        // user_choosed_image.setImageBitmap(circularBitmap);
        System.out.println("Selected Image pro" + user_gen);
        System.out.println("Selected Image p" + pr_img);

        if (pr_img!=null)
        {
            Glide.with(context).load(pr_img).into(image_icon);
            Glide.with(context).load(pr_img).into(img_not_available_data);
            // Bitmap bitmap = decodeBase64(pr_img);
            //image_icon.setImageBitmap(bitmap);
        }
        else if(user_gen!=null)
        {
            if (user_gen.equalsIgnoreCase("Male"))
            {
                image_icon.setImageResource(R.drawable.male_blue);
            }
            if(user_gen.equalsIgnoreCase("Female"))
            {
                image_icon.setImageResource(R.drawable.female_pink);
            }
        }

        currentPosition = 0;
        mHandler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                getLocation();
                updateLocation();
                mHandler.postDelayed(runnable, INTERVAL);
            }
        }, INTERVAL);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mSettingsClient = LocationServices.getSettingsClient(context);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                System.out.println("Current Latitude@@@ fused" + mCurrentLocation.getLatitude());
                System.out.println("Current Longitude@@@ fused" + mCurrentLocation.getLongitude());

                // System.out.println("Current Latitude@@@" + mCurrentLocation.getLatitude());
                //System.out.println("Current Longitude@@@" + mCurrentLocation.getLongitude());


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




    }

//.....................................................For getting user location coordinates.......................................................////

    //this method will give us a user current location.

    private void getLocation() {
        GPSTracker gps = new GPSTracker(context);
        if (gps.canGetLocation()) {

            currentlat = gps.getLatitude();
            currentlong = gps.getLongitude();

            System.out.println("Current Latitude@@@" + currentlat);
            System.out.println("Current Longitude@@@" + currentlong);

            sessionManager.setData(SessionManager.KEY_LATITUDE, String.valueOf(currentlat));
            sessionManager.setData(SessionManager.KEY_LONGITUDE, String.valueOf(currentlong));




        }

        else {
            gps.showSettingsAlert();
        }
    }
//.....................................................For getting all users data is here.......................................................////

    //this method will give us a user data
    //who will be in our range of 50kms from our current location.

    private void loadUserData() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(true);
        progressDialog.show();

        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_AllUSERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                        JSONObject jsonObject = new JSONObject(response);
                        Log.i("response..!", "1235" + response);

                        String Status = jsonObject.getString("status");
                        String Status_code = jsonObject.getString("status_code");
                        String msg = jsonObject.getString("message");
                        String result = jsonObject.getString("result");

                    if (result.equalsIgnoreCase("There is no users available."))
                    {
                        progressDialog.dismiss();
                        cardStack.setVisibility(View.GONE);
                        img_not_available_data.setVisibility(View.VISIBLE);
                        rippleBackground.startRippleAnimation();

                        Snackbar snackbar = Snackbar
                                .make(linear, "Trying to find someone for you.", 3000);

                        System.out.println("frst---##11");
                        img_like.setEnabled(false);
                        img_like.setClickable(false);
                        img_dislike.setClickable(false);
                        img_dislike.setEnabled(false);
                        img_sendcrush.setClickable(false);
                        img_sendcrush.setEnabled(false);

                        JSONObject jsonObject2 = jsonObject.getJSONObject("user");
                        String first_name = jsonObject2.getString("first_name");
                        String last_name = jsonObject2.getString("last_name");
                        String pseudonym = jsonObject2.getString("pseudonym");
                        String gender = jsonObject2.getString("gender");
                        String age = jsonObject2.getString("age");
                        String zodiac_sign = jsonObject2.getString("zodiac_sign");
                        String hobbs = jsonObject2.getString("interest_hobby");
                        String looking_for = jsonObject2.getString("looking_for");
                        String profilepic = jsonObject2.getString("profile_pic");
                        String user_type = jsonObject2.getString("user_type");

                        sessionManager.setData(SessionManager.KEY_FIRST_NAME,first_name);
                        sessionManager.setData(SessionManager.KEY_LAST_NAME,last_name);
                        sessionManager.setData(SessionManager.KEY_PSEUDONYM,pseudonym);
                        sessionManager.setData(SessionManager.KEY_GENDER_,gender);
                        sessionManager.setData(SessionManager.KEY_AGE,age);
                        sessionManager.setData(SessionManager.KEY_ZODIAC,zodiac_sign);
                        sessionManager.setData(SessionManager.KEY_HOBBY,hobbs);
                        sessionManager.setData(SessionManager.KEY_LOOKIN_FOR,looking_for);
                        sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE,profilepic);
                        sessionManager.setData(SessionManager.KEY_USER_TYPE,user_type);

                        String pr_img = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
                        String user_gen = sessionManager.getData(SessionManager.KEY_GENDER_);

                        System.out.println("Selected Image pro" + user_gen);
                        System.out.println("Selected Image p" + pr_img);

                        if (!pr_img.equalsIgnoreCase(""))
                        {
                            Glide.with(context).load(pr_img).into(image_icon);

                           // Bitmap bitmap = decodeBase64(pr_img);
                            //image_icon.setImageBitmap(bitmap);
                        }
                       else  if(user_gen!=null)
                        {
                            if (user_gen.equalsIgnoreCase("Male"))
                            {
                                image_icon.setImageResource(R.drawable.male_blue);
                            }
                            if(user_gen.equalsIgnoreCase("Female"))
                            {
                                image_icon.setImageResource(R.drawable.female_pink);
                            }
                        }
                    }
                    else
                    {
                       // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                        if (Status.equals("success")) {


                            img_like.setEnabled(true);
                            img_like.setClickable(true);
                            img_reload.setEnabled(true);
                            img_reload.setClickable(true);
                            img_sendcrush.setEnabled(true);
                            img_sendcrush.setClickable(true);
                            img_dislike.setClickable(true);
                            img_dislike.setEnabled(true);
                            cardStack.setVisibility(View.VISIBLE);
                            img_not_available_data.setVisibility(View.GONE);


                            JSONArray array = jsonObject.getJSONArray("result");
                              for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject1 = array.getJSONObject(i);

                                    String user_id = jsonObject1.getString("user_id");
                                    String pseudonym = jsonObject1.getString("pseudonym");
                                    String age = jsonObject1.getString("age");
                                    String distance = jsonObject1.getString("distance");
                                    String profilepic = jsonObject1.getString("profile_pic");

                                    System.out.println("user id" + user_id);
                                    System.out.println("user id" + pseudonym);
                                    System.out.println("user id" + age);
                                    System.out.println("user id" + distance);
                                    System.out.println("user id" + profilepic);

                                    cardItems.add(new CardItem(user_id, pseudonym, age, distance, profilepic));
                                    setCardStackAdapter();


                                }
                                JSONObject jsonObject2 = jsonObject.getJSONObject("user");
                                String first_name = jsonObject2.getString("first_name");
                                String last_name = jsonObject2.getString("last_name");
                                String pseudonym = jsonObject2.getString("pseudonym");
                                String gender = jsonObject2.getString("gender");
                                String age = jsonObject2.getString("age");
                                String zodiac_sign = jsonObject2.getString("zodiac_sign");
                                String hobbs = jsonObject2.getString("interest_hobby");
                                String looking_for = jsonObject2.getString("looking_for");
                                String profilepic = jsonObject2.getString("profile_pic");
                                String user_type = jsonObject2.getString("user_type");

                                sessionManager.setData(SessionManager.KEY_FIRST_NAME,first_name);
                                sessionManager.setData(SessionManager.KEY_LAST_NAME,last_name);
                                sessionManager.setData(SessionManager.KEY_PSEUDONYM,pseudonym);
                                sessionManager.setData(SessionManager.KEY_GENDER_,gender);
                                sessionManager.setData(SessionManager.KEY_AGE,age);
                                sessionManager.setData(SessionManager.KEY_ZODIAC,zodiac_sign);
                                sessionManager.setData(SessionManager.KEY_HOBBY,hobbs);
                                sessionManager.setData(SessionManager.KEY_LOOKIN_FOR,looking_for);
                                sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE,profilepic);
                                sessionManager.setData(SessionManager.KEY_USER_TYPE,user_type);

                                System.out.println("user id" + first_name+last_name);
                                System.out.println("user id" + pseudonym);
                                System.out.println("user id" + gender + age + zodiac_sign + looking_for);
                                System.out.println("user id" + profilepic + user_type);


                            String pr_img = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
                            String user_gen = sessionManager.getData(SessionManager.KEY_GENDER_);

                            System.out.println("Selected Image pro" + user_gen);
                            System.out.println("Selected Image p" + pr_img);

                            if (pr_img!=null)
                            {
                                Glide.with(context).load(pr_img).into(image_icon);

                                Bitmap bitmap = decodeBase64(pr_img);
                                //image_icon.setImageBitmap(bitmap);
                            }
                            else if(user_gen!=null)
                            {
                                if (user_gen.equalsIgnoreCase("Male"))
                                {
                                    image_icon.setImageResource(R.drawable.male_blue);
                                }
                                if(user_gen.equalsIgnoreCase("Female"))
                                {
                                    image_icon.setImageResource(R.drawable.female_pink);
                                }
                            }
                            //cardItems.add(new CardItem(user_id, pseudonym, age, distance, profilepic));
                                //setCardStackAdapter();

                        }
                    }

                     if (Status.equals("error") && msg.equals("An error occurred while performing an action!"))
                     {
                             progressDialog.dismiss();
                             img_not_available_data.setVisibility(View.VISIBLE);
                     }
                     else if (Status.equals("error") && msg.equals("Login Unsuccessful. An error occurred while performing an action!"))
                     {
                         progressDialog.dismiss();
                         img_not_available_data.setVisibility(View.VISIBLE);
                     }
                        else  if(Status_code.equals("500") && msg.equals("There is no users available."))
                        {
                            img_like.setEnabled(false);
                            img_like.setClickable(false);
                            img_dislike.setClickable(false);
                            img_dislike.setEnabled(false);
                            img_sendcrush.setClickable(false);
                            img_sendcrush.setEnabled(false);
                            cardStack.setVisibility(View.GONE);
                            img_not_available_data.setVisibility(View.VISIBLE);

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
                Toast.makeText(context, "Couldn,t connect to server.", Toast.LENGTH_SHORT).show();
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



  /*  private void getUserData() {

        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        String response = sessionManager.getData(SessionManager.KEY_USERDATA);
        if (response != null) {
        try {
            pd.dismiss();

            JSONObject jsonObject = new JSONObject(response);
            String Status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");

            if (Status.equals("success")) {
                JSONArray array = jsonObject.getJSONArray("result");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject1 = array.getJSONObject(i);
                    String user_id = jsonObject1.getString("user_id");
                    String pseudonym = jsonObject1.getString("pseudonym");
                    String age = jsonObject1.getString("age");
                    String distance = jsonObject1.getString("distance");
                    String profilepic = jsonObject1.getString("profile_pic");
                    cardItems.add(new CardItem(user_id, pseudonym, age, distance, profilepic));

                }
            } else if (Status.equals("error")) {
                pd.dismiss();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    }
*/
//.....................................................update user location is here.......................................................////

    //according to requirment.
    //in every 15 minutes user lat long will be send to server.
    //for this updatelocation method wil invokes here...

    private void updateLocation() {

        final String id = sessionManager.getData(SessionManager.KEY_USER_ID);

         StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATED_LOCATION,
                 new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!","1235"+response);

                    String Status = jsonObject.getString("status");
                    String msg = jsonObject.getString("message");

                    //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success"))
                    {
                        System.out.println("Succesfull update lat long...!!");
                    }
                    else if (Status.equals("error"))
                    {
                        System.out.println("Error update lat long...!!");
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
                System.out.println("error...111"+ error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",id);
                params.put("user_lat", String.valueOf(currentlat));
                params.put("user_long", String.valueOf(currentlong));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

//.....................................................adapter for set data is here.......................................................////

    private void setCardStackAdapter()
    {
        if (cardItems!=null)
        {
            mHandler.postDelayed( runnable = new Runnable() {
                public void run() {
                    // Toast.makeText(context, "delay", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }, 2000);

            cardsAdapter = new CardsAdapter(this, cardItems);
            cardStack.setAdapter(cardsAdapter);
            cardsAdapter.notifyDataSetChanged();



            System.out.println("user id");
        }

    }

//..................................................set data for navigation drawer...............................................///

   /* private void setNavigation() {

        nav_list = new ArrayList<>();
        nav_list.add(new Navigation(R.drawable.male_blue, "Home"));
        nav_list.add(new Navigation(R.drawable.male_blue, "Change Password"));
        nav_list.add(new Navigation(R.drawable.male_blue, "Crush Requests"));
        nav_list.add(new Navigation(R.drawable.male_blue, "Send Crush Requests"));


        NavAdapter navAdapter = new NavAdapter(context, nav_list);
        listView.setAdapter(navAdapter);
    }
*/



//.....................................................click events here.......................................................////

    private void click()
    {

        // listView.setOnItemClickListener(this);

        //Handling swipe event of Cards stack
        cardStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
                currentPosition = position;
                //Toast.makeText(context, "You liked " + currentPosition+ position,
                    //    Toast.LENGTH_SHORT).show();
                 sendDisLikedData();
            }

            @Override
            public void onViewSwipedToRight(int position) {
                currentPosition = position;
                sendLikedData();
            }

            //no user available then this will work
            @Override
            public void onStackEmpty() {
                pd.setMessage("Loading..");
                pd.show();
              //  Toast.makeText(context, "show", Toast.LENGTH_SHORT).show();

                img_dislike.setClickable(false);
                img_like.setClickable(false);
                img_sendcrush.setClickable(false);
                cardStack.setVisibility(View.GONE);
                img_not_available_data.setVisibility(View.VISIBLE);
                rippleBackground.startRippleAnimation();

                mHandler.postDelayed( runnable = new Runnable() {
                    public void run() {
                       // Toast.makeText(context, "delay", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(linear, "Trying to find someone for you.", 3000);

                        snackbar.show();
                    }
                }, 5000);

            }
        });

        //when user clicks on a dislike button
        //image goes in left and method will be invoked.
        img_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDisLikedData();
                cardStack.swipeTopViewToLeft();
            }
        });

        //when user clicks on a like button
        //image goes in right and method will be invoked.
        img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLikedData();
                cardStack.swipeTopViewToRight();
            }
        });

        img_sendcrush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLikeDialog();

            }
        });

        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.resetStack();
            }
        });

        //when user clicks on a like button
        //image goes in left and method will be invoked.
        image_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MyProfileActiivty.class);
                startActivity(intent);
            }
        });

        chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ChatUserList.class);
                startActivity(intent);
            }
        });

       /* dating_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });*/

    }

    private void openLikeDialog() {
        try{
          /* giphyDialog = new GiphyDialog.Builder(UserProfileActivity.this)
                .setTitle("You have CRUSH on "+cardItems.get(currentPosition).getName()+".")
                .setMessage("God bless you.")
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Yes")
                .setNegativeBtnText("No")
                .setNegativeBtnBackground("#FF4081")
                //.setGifResource(R.drawable.giffy)
                .isCancellable(true)
                .OnPositiveClicked(new GiphyDialogListener() {
                    @Override
                    public void OnClick() {
                        sendCrushRequest();
                    }
                })
                .OnNegativeClicked(new GiphyDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();*/


            send_crush_dialog = new Dialog(context);
            send_crush_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            send_crush_dialog.setContentView(R.layout.send_crush_request_dialog);
            send_crush_dialog.setCancelable(true);

            /*initializing all the views of dialog */
            TextView txt_msg = (TextView) send_crush_dialog.findViewById(R.id.tag_line_send_crush);
            Button btn_ok = (Button) send_crush_dialog.findViewById(R.id.send_crush_ok);
            Button btn_canecl = (Button) send_crush_dialog.findViewById(R.id.send_crush_cancel);

            txt_msg.setText("You have CRUSH on "+cardItems.get(currentPosition).getName()+".");

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    send_crush_dialog.dismiss();
                    sendCrushRequest();
                }
            });

            btn_canecl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    send_crush_dialog.dismiss();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        send_crush_dialog.show();

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }




//.....................................................like user api is here.......................................................////


    private void sendLikedData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final String id = String.valueOf(cardItems.get(currentPosition).getUser_id());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_PROFILE_LIKED,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                   progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!","1235"+response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");
                    String msg = jsonObject.getString("message");


                    if (Status.equals("success"))
                    {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                    else if (Status.equals("error"))
                    {
                        progressDialog.dismiss();
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
                progressDialog.dismiss();
                System.out.println("Error occured "+error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                if (id!=null)
                {
                    params.put("liked_user_id",id);
                }
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
//.....................................................Dislike user api is here.......................................................////


    private void sendDisLikedData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final String id = String.valueOf(cardItems.get(currentPosition).getUser_id());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_PROFILE_DISLIKED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("response..!","1235"+response);

                            String Status = jsonObject.getString("status");
                            String Status_code = jsonObject.getString("status_code");
                            String msg = jsonObject.getString("message");


                            if (Status.equals("success"))
                            {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                            else if (Status.equals("error"))
                            {
                                progressDialog.dismiss();
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
                progressDialog.dismiss();
                System.out.println("Error occured "+error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("disliked_user_id",id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


//......................................................for send crush request heree............................................ //

    private void sendCrushRequest() {
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final String id = String.valueOf(cardItems.get(currentPosition).getUser_id());

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_CRUSH_REQUEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("response..!","1235"+response);

                            String Status = jsonObject.getString("status");
                            String Status_code = jsonObject.getString("status_code");
                            String msg = jsonObject.getString("message");


                            if (Status.equals("success"))
                            {
                                send_crush_dialog.dismiss();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                            else if (Status.equals("error"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
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
                Toast.makeText(context, "Couldn,t connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sender_id",user_id);
                params.put("receiver_id",id);
                params.put("status","pending");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

//.....................................................favorite Api invokes here.......................................................////


    private void sendfavouriteData(int position)
    {
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final String id = String.valueOf(cardItems.get(position).getUser_id());

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_PROFILE_ADD_FAV,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("response..!","1235"+response);

                            String Status = jsonObject.getString("status");
                            String Status_code = jsonObject.getString("status_code");
                            String msg = jsonObject.getString("message");


                            if (Status.equals("success"))
                            {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                            else if (Status_code.equalsIgnoreCase("500"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(context, "You already added this profile in you favorites.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Couldn,t connect to server.", Toast.LENGTH_SHORT).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("favorite_user_id",id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

  //...................................................for Spam report to server/................................................//

    private void sendSpamReportData(final String msg)
    {
        pd.setMessage("Loading..");
        pd.show();

        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final String id = String.valueOf(cardItems.get(currentPosition).getUser_id());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SPAM_REPORTED,
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
                                pd.dismiss();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                            else if (Status_code.equals("500"))
                            {
                                pd.dismiss();
                                Toast.makeText(context, "You already reported this user.", Toast.LENGTH_SHORT).show();                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Couldn,t connect to server.", Toast.LENGTH_SHORT).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("reported_user_id",id);
                params.put("reason",msg);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }




//.....................................................Decode method here.......................................................////

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
//.....................................................Destroy activity here.......................................................////

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("destroy activity");
        mHandler.removeCallbacks(runnable);
    }


    //.....................................................Adapter class here.......................................................////
    public class CardsAdapter extends BaseAdapter {

        private Activity activity;
        private final static int AVATAR_WIDTH = 200;
        private final static int AVATAR_HEIGHT = 390;
        private List<CardItem> data;

        ImageView gallery_images;

        public CardsAdapter(Activity activity, List<CardItem> data) {
            this.data = data;
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CardItem getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            // If holder not exist then locate all view from UI file.
            if (convertView == null) {
                // inflate UI from XML file
                convertView = inflater.inflate(R.layout.item_card, parent, false);


                // get all UI view
                holder = new ViewHolder(convertView);


                // set tag for holder
                convertView.setTag(holder);
            } else {
                // if holder created, get tag from view
                holder = (ViewHolder) convertView.getTag();
            }

             //currentPosition = position;
            //setting data to views
            if (getItem(position).getAge().equalsIgnoreCase(""))
            {
                holder.username_age_textvies.setText(getItem(position).getName());
            }
            else
            {
                holder.username_age_textvies.setText(getItem(position).getName()+", "+getItem(position).getAge());
            }

            if (!getItem(position).getLocation().equalsIgnoreCase(""))
            {
                holder.user_kmaway.setText(getItem(position).getLocation()+" km away");
            }

           // holder.user_choosed_image.setImageBitmap(decodeSampledBitmapFromResource(activity.getResources(),
                  //  getItem(position).getDrawableId(), AVATAR_WIDTH, AVATAR_HEIGHT));

            System.out.println("user image "+getItem(position).getName()+", "+getItem(position).getAge());
            System.out.println("user image "+getItem(position).getLocation()+" km away");

            String image = getItem(position).getProfile_pic();
            System.out.println("user image "+ image);

            if (image!=null)
            {
                Glide.with(activity).load(image).into(holder.user_choosed_image);
            }
            else
            {
                Glide.with(context).load(R.drawable.user_profile).into(holder.user_choosed_image);
            }
           /* String image = getItem(position).getDrawableId();
            Bitmap img= decodeSampledBitmapFromResource(BitmapFactory.decodeResource(image)
                    //,AVATAR_WIDTH,AVATAR_HEIGHT);
            if (img!=null) {
                Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(img, 12);
                if (circularBitmap != null) {
                    holder.user_choosed_image.setImageBitmap(circularBitmap);
                } else {
                    holder.user_choosed_image.setImageResource(R.drawable.app_logo);
                }
            }*/

           holder.addfav.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   sendfavouriteData(position);
               }
           });


           gallery_images.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Id is###"+getItem(position).getName());

                  Intent  ii=new Intent(UserProfileActivity.this, GalleryActivity.class);
                  ii.putExtra("id",getItem(position).getUser_id());
                  ii.putExtra("psuedonym",getItem(position).getName()+"'s");
                  startActivity(ii);
                   // startActivity(new Intent(context,GalleryActivity.class));
                }
            });

            holder.user_choosed_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sessionManager.setData(SessionManager.KEY_ANOTHER_USER_ID,getItem(position).getUser_id());
                    Intent intent = new Intent(context,Fullview_User_Profile.class);
                    startActivity(intent);
                }
            });

            holder.report_spam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // sendSpamReportData();
                   setup();
                   dialog.show();
                }
            });


            return convertView;
        }



        private void setup() {

      dialog =   new PrettyDialog(context)
                .setTitle("Spam Report!")
                .setMessage("Why you want to spam this user.")
                .setIcon(R.drawable.pdlg_icon_info,R.color.pdlg_color_red,null)

                // OK button
                .addButton(
                        "Inappropriate Photos.",					// button text
                        R.color.pdlg_color_white,		// button text color
                        R.color.pdlg_color_green,		// button background color
                        new PrettyDialogCallback() {		// button OnClick listener
                            @Override
                            public void onClick() {
                                // Do what you gotta do
                                String msg = "Inappropriate Photos.";
                                 sendSpamReportData(msg);

                            }
                        }
                )

          // Cancel button
                .addButton(
                        "Feels like spam.",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                String msg = "Feels like spam.";
                                 sendSpamReportData(msg);

                            }
                        }
                )

           // 3rd button
                .addButton(
                        "Other (text box).",
                        R.color.pdlg_color_black,
                        R.color.pdlg_color_blue,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                dialog.dismiss();
                               // String msg = "Other (text box).";
                                 //sendSpamReportData(msg);
                              final PanterDialog panterDialog =  new PanterDialog(context);
                                   panterDialog
                                        .setHeaderBackground(R.drawable.pattern_bg_blue)
                                        .setHeaderLogo(R.drawable.app_logo)
                                        .setDialogType(DialogType.INPUT)
                                        .isCancelable(true)
                                        .input("THIS IS HINT FOR INPUT AREA YOU CAN WRITE HERE ANY LONGER TEXTS",
                                                "ERROR MESSAGE IF USER PUT EMPTY INPUT",
                                                new OnTextInputConfirmListener() {
                                                            @Override
                                                            public void onTextInputConfirmed(String text) {
                                                               // Toast.makeText(activity, "helllo", Toast.LENGTH_SHORT).show();
                                                            sendSpamReportData(text);
                                                            }
                                                        })
                                           .show();

                            }
                        }


                );

    }

    private class ViewHolder{
            ImageView user_choosed_image, addfav , report_spam;
            TextView username_age_textvies, user_kmaway;

            public ViewHolder(View view) {
                user_choosed_image = (ImageView)view.findViewById(R.id.user_image1);
                addfav = (ImageView)view.findViewById(R.id.add_favorite);
                gallery_images = (ImageView)view.findViewById(R.id.gallery_image);

                report_spam = (ImageView)view.findViewById(R.id.report_spam);

                username_age_textvies = (TextView)view.findViewById(R.id.username_age);
                user_kmaway = (TextView)view.findViewById(R.id.away_from);


            }
        }

        public  Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(res, resId, options);
        }

        public  int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) >= reqHeight
                        && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
        }
    }

//..........................................onBackpreesed events..................................................................//

   //when user click on back button
   //we will ask him for EXIT through open the dialog.

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        // finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


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


    //.........................................................END here..........................................................//

}
