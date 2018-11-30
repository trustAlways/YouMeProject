package com.youme.candid.youmeapp.Activity.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.jackandphantom.circularimageview.CircleImage;
import com.youme.candid.youmeapp.Activity.Constants.BitmapUtils;
import com.youme.candid.youmeapp.Activity.Constants.CircularImageView;
import com.youme.candid.youmeapp.Activity.activity.Compatible_test.Compatible_test_one;
import com.youme.candid.youmeapp.Activity.activity.Test_activities.Test_one;
import com.youme.candid.youmeapp.Activity.adapter.SlidingImage_Adapter;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

import static com.youme.candid.youmeapp.Activity.activity.UserProfileActivity.decodeBase64;

public class MyProfileActiivty extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

     CircularImageView circleImageView;
     TextView user_psudonm,user_real_name,user_crush_blnce,user_date_blnce,
              crush_pack_txtview,combo_packge_txtview,date_packge_txtview;
     LinearLayout linear_user_profile_edit,linear_setting,ll_crushes,ll_dates,
            ll_msgs,ll_favourite,ll_compatability_test, ll_personality_test,

             linear_dating_packages,linear_crushes_packages,linear_combo_packages;
     ImageView img_subscription,img_hide_upgrade_dialog,img_backarrow;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    Context context;
    //for upgrade dialog
    Dialog upgradedialog;
    static TextView mDotsText[];
    private int mDotsCount;
    private LinearLayout mDotsLayout;
    String pr_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        System.out.println("Checkbox ");

        //for initiialize all the view this method will be invoked
        initView();

        //for set some data of user
        setData();
        boolean internet = connectionDetector.isConnected(context);
        if (internet)
        {
            loadData();
            GetMinimumPckageRange();
        }
        else
        {
            Toast.makeText(context, "Check your internet connection.", Toast.LENGTH_SHORT).show();
        }

        //for click events this methos will be invoked
        onClickEvents();
    }

    //.........................................................//.........................................................................//
    //now we will initiallize all the view for type cast
    //and use in perform action
    private void initView() {
        System.out.println("Checkbox ");

        context = this;
        sessionManager = new SessionManager(context);
        connectionDetector = new ConnectionDetector();

       // boolean internet = connectionDetector.isConnected(context);

        //all the imageviews initialioze here
        circleImageView = (CircularImageView) findViewById(R.id.user_profile_img);

        img_subscription = (ImageView)findViewById(R.id.imag_subscription);

        img_backarrow = (ImageView)findViewById(R.id.myProfile_arrow);

        //all the textview will be initialize here
        user_psudonm = (TextView)findViewById(R.id.user_pseudonam);
        user_real_name = (TextView)findViewById(R.id.user_Realname);

        user_crush_blnce = (TextView)findViewById(R.id.crush_balance);
        user_date_blnce = (TextView)findViewById(R.id.date_balance);

        //all the linearLayout will be initialize here
        linear_user_profile_edit = (LinearLayout)findViewById(R.id.ll_edit_user_profile);
        linear_setting = (LinearLayout)findViewById(R.id.my_profile_settings);
        ll_crushes = (LinearLayout)findViewById(R.id.ll_crushes);
        ll_dates = (LinearLayout)findViewById(R.id.ll_dates);
        ll_msgs = (LinearLayout)findViewById(R.id.ll_messages);
        ll_favourite = (LinearLayout)findViewById(R.id.ll_my_favourites);
        ll_compatability_test = (LinearLayout)findViewById(R.id.ll_compatibility_test);
        ll_personality_test = (LinearLayout)findViewById(R.id.ll_personality_test);

    }
    //.........................................................//.........................................................................//

    //now all the click events will be invoked here.
    private void onClickEvents() {

        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_subscription.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openUpgradePopup();

           }
       });

      //for Edit User Profile
      linear_user_profile_edit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             Intent intent = new Intent(context,Edit_UserProfile_Actiivty.class);
             startActivity(intent);
          }
      });

    //for Open settings
    linear_setting.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,User_Profile_Setting.class);
            startActivity(intent);
        }
    });

    ll_crushes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,User_Crushes_Activity.class);
            startActivity(intent);
        }
    });

    ll_dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,User_Dates_Activity.class);
                startActivity(intent);
            }
        });

        ll_msgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ChatUserList.class);
                startActivity(intent);
            }
        });

    ll_favourite.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,User_Favourite_Actiivty.class);
            startActivity(intent);
        }
    });
    ll_personality_test.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,Test_one.class);
            startActivity(intent);
        }
    });

        ll_compatability_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Compatible_test_one.class);
                startActivity(intent);
            }
        });

    }
//....................................upgrade subscription popup ................................................................
    private void openUpgradePopup()
    {
        try{
            upgradedialog = new Dialog(context);
            upgradedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            upgradedialog.setContentView(R.layout.upgrade_package_popup);

            String crush = sessionManager.getData(SessionManager.KEY_CRUSH_MIN_RANGE);
            String date = sessionManager.getData(SessionManager.KEY_DATE_MIN_RANGE);
            String combo = sessionManager.getData(SessionManager.KEY_COMBO_MIN_RANGE);


            /*initializing all the views of dialog */
            linear_dating_packages = (LinearLayout)upgradedialog.findViewById(R.id.ll_dating_packages);
            linear_crushes_packages = (LinearLayout)upgradedialog.findViewById(R.id.ll_crushes_package);
            linear_combo_packages = (LinearLayout)upgradedialog.findViewById(R.id.ll_combo_package);

            crush_pack_txtview = (TextView)upgradedialog.findViewById(R.id.crush_package_min);
            combo_packge_txtview = (TextView)upgradedialog.findViewById(R.id.combo_package_min);
            date_packge_txtview = (TextView)upgradedialog.findViewById(R.id.date_package_min);

            if (!crush.equalsIgnoreCase(""))
            {
                crush_pack_txtview.setText("Starting at \u20B9"+crush);
            }
            if (!date.equalsIgnoreCase(""))
            {
                date_packge_txtview.setText("Starting at \u20B9"+date);
            }
            if (!combo.equalsIgnoreCase(""))
            {
                combo_packge_txtview.setText("Starting at \u20B9"+combo);
            }
            img_hide_upgrade_dialog = (ImageView)upgradedialog.findViewById(R.id.hide_upgradeeDialog);

            final ViewPager viewpager = (ViewPager)upgradedialog.findViewById(R.id.pager);
            CircleIndicator indicator = (CircleIndicator)upgradedialog.findViewById(R.id.indicator);
            viewpager.setAdapter(new SlidingImage_Adapter(context));
            indicator.setViewPager(viewpager);
            //Set circle indicator radius

            NUM_PAGES =viewpager.getAdapter().getCount();
            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    viewpager.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3000, 3000);

            // Pager listener over indicator
            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;

                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int pos) {

                }
            });

            Gallery gallery = (Gallery)upgradedialog.findViewById(R.id.gallery);
            gallery.setAdapter(new MyProfileActiivty.ImageAdapter(this));
            gallery.setOnItemSelectedListener(this);

            mDotsLayout = (LinearLayout)upgradedialog.findViewById(R.id.image_count);
            // here we count the number of images we have to know how many dots we
            // need
            mDotsCount = gallery.getAdapter().getCount();

            // here we create the dots
            // as you can see the dots are nothing but "." of large size
            mDotsText = new TextView[mDotsCount];

            // here we set the dots
            for (int i = 0; i < mDotsCount; i++) {
                mDotsText[i] = new TextView(this);
                mDotsText[i].setText(".");
                mDotsText[i].setTextSize(45);
                mDotsText[i].setTypeface(null, Typeface.BOLD);
                mDotsText[i].setTextColor(getResources().getColor(R.color.blue));
                mDotsLayout.addView(mDotsText[i]);
            }

            img_hide_upgrade_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    upgradedialog.dismiss();
                }
            });

            linear_dating_packages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Dating_Packages.class);
                    startActivity(intent);
                }
            });

            linear_crushes_packages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Crushes_Packages.class);
                    startActivity(intent);
                }
            });

            linear_combo_packages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Combo_Packages.class);
                    startActivity(intent);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        upgradedialog.show();
    }

//.........................................................//.........................................................................//

    private void setData()
    {
        String user_pseudonym_name = sessionManager.getData(SessionManager.KEY_PSEUDONYM);
        String user_fname = sessionManager.getData(SessionManager.KEY_FIRST_NAME);
        String user_lname = sessionManager.getData(SessionManager.KEY_LAST_NAME);

         pr_img = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);

        user_psudonm.setText(user_pseudonym_name);
        user_real_name.setText(user_fname+" "+user_lname);

        System.out.println("Checkbox" +user_fname + user_lname);
        System.out.println("Checkbox" +user_pseudonym_name);
        System.out.println("Checkbox "+ pr_img);

        if (pr_img!=null)
        {
            //Bitmap bitmap = decodeBase64(pr_img);
           Glide.with(context).load(pr_img).into(circleImageView);

            /*try {
                Bitmap returned_bitmap = new myTask().execute().get();
                // Bitmap bm = BitmapUtils.loadBitmap(pr_img);
                System.out.println("bm "+ returned_bitmap);
                roundImage = new RoundImage(returned_bitmap);
                circleImageView.setImageDrawable(roundImage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }*/

           // Glide.with(context).load(roundImage).into(circleImageView);
        }


        Animation animation = AnimationUtils.loadAnimation(context,R.anim.logo_animation);
        img_subscription.setAnimation(animation);
    }

   //---------------------------------------------------------------------------------------------

    //get the packages start range data
    private void GetMinimumPckageRange() {

        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.hide();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_MINIMUM_PACKAGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response@@@ Crush..!", "user crush balance" + response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");
                    // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("result");

                        String crush_min_price = jsonObject1.getString("crush");
                        String dating_min_price = jsonObject1.getString("dating");
                        String combo_min_price = jsonObject1.getString("combo");

                        sessionManager.setData(SessionManager.KEY_CRUSH_MIN_RANGE,crush_min_price);
                        sessionManager.setData(SessionManager.KEY_DATE_MIN_RANGE, dating_min_price);
                        sessionManager.setData(SessionManager.KEY_COMBO_MIN_RANGE,combo_min_price);

                        dialog.dismiss();

                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                System.out.println("##----Couldn't connect to server.");
                Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    //-------------------------------------------------------------------------------------------------

    //get the crush balance data
    private void loadData() {

        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_CRUSH_BALANCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response@@@ Crush..!", "user crush balance" + response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");
                   // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success")) {
                        String result = jsonObject.getString("result");
                        user_crush_blnce.setText(result+" left");
                        loadDateBalance();

                        dialog.dismiss();

                    }
                    else {
                        loadDateBalance();
                        dialog.dismiss();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadDateBalance();
                dialog.dismiss();
                System.out.println("##----Couldn't connect to server.");
              //  Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

//---------------------------for get date balance
private void loadDateBalance() {

    final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
    final ProgressDialog dialog = new ProgressDialog(context);
    dialog.setMessage("Loading..");
    dialog.setCancelable(false);
    dialog.hide();
    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_DATE_BALANCE, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {

                JSONObject jsonObject = new JSONObject(response);
                Log.i("response@@@ Crush..!", "user crush balance" + response);

                String Status = jsonObject.getString("status");
                String Status_code = jsonObject.getString("status_code");

                String msg = jsonObject.getString("message");
                //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                if (Status.equals("success")) {
                    dialog.dismiss();
                    String result = jsonObject.getString("result");
                    user_date_blnce.setText(result+" left");
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            dialog.dismiss();
            System.out.println("##----Couldn't connect to server.");
           // Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("user_id", user_id);
            return params;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(stringRequest);
}


    //------------------------------------------------------------------------------------
    private class myTask extends AsyncTask<Void,Void,Bitmap> {


        protected Bitmap doInBackground(Void... params) {

            //do stuff
            Bitmap bitmap = BitmapUtils.loadBitmap(pr_img);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            //do stuff

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        for (int i = 0; i < mDotsCount; i++) {
            mDotsText[i].setTextColor(getResources().getColor(R.color.blue));
        }

        mDotsText[position].setTextColor(getResources().getColor(R.color.pink));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


//............................................Adapter for set iMages...........................................................................
public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    // array of integers for images IDs
    private Integer[] mImageIds = {
            R.drawable.real_identity,
            R.drawable.search_across_globe, R.drawable.ads_free,
            R.drawable.chat_unlimited, R.drawable.bookmarks,R.drawable.go_backs

    };

    // constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mImageIds.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(mContext);

        imageView.setImageResource(mImageIds[i]);
        imageView.setLayoutParams(new Gallery.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }

}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context,UserProfileActivity.class);
        startActivity(intent);
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
//.........................................................//.........................................................................//
