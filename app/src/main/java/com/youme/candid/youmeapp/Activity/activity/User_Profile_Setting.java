package com.youme.candid.youmeapp.Activity.activity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.youme.candid.youmeapp.Activity.adapter.SlidingImage_Adapter;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class User_Profile_Setting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Context context;
    ConnectionDetector connectionDetector;
    SessionManager sessionManager;
    ProgressDialog progressDialog;
    Dialog mydialog,upgradedialog,contactus_dialog;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    //all linearlayout here
    LinearLayout upgrade_package,linear_contactus,linear_change_password,

    linear_dating_packages,linear_crushes_packages,linear_combo_packages;//all popup linear
   //for all switch view...
    Switch hide_age_Switch,distance_invisible,show_me_men_switch,show_me_women_switch
           ,new_crush_switch,message_switch,date_invitation_switch,daily_packs_switch,other_notification_switch,
    show_distance_in_kms,show_distance_in_miles;
   //all the edittext here...
    EditText dialog_enter_email;
   // all the imageviews here
    ImageView img_backarrow,img_send_arrow,img_hide_dialog,img_hide_upgrade_dialog,img_hide_contactus_dialog,img_sucess,img_error;
   //all the textview here
    TextView edt_email_popup,edt_added_user_email,selected_age_range,selected_distance,show_me_text,mobile_nuber,

    crush_pack_txtview,combo_packge_txtview,date_packge_txtview;

    //range bar and seek bar
    CrystalRangeSeekbar rangeSeekbar;
    CrystalSeekbar crystalSeekbar;
    Button logout;
    SeekBar seekBar;
    String user_type;
    //Upgrade Popup views
    static TextView mDotsText[];
    private int mDotsCount;
    private LinearLayout mDotsLayout;
    long flt_strt=16,flt_end=26;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_settings);

        //for initialize all view this method will invoked
        initView();

        //for cliok events this method will be invoke
        onClick();

        //for cliok events this method will be invoke
        setData();

    }

    private void setData()
    {
        //switch
        try
        {
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.logo_animation);

            String user_email = sessionManager.getData(SessionManager.KEY_EMAIL);
            System.out.println("Emal is###"+user_email);

            if(!user_email.equalsIgnoreCase("null")) {

                    img_error.setVisibility(View.GONE);
                    img_sucess.setVisibility(View.VISIBLE);
                    img_sucess.startAnimation(animation);
                    edt_email_popup.setVisibility(View.GONE);
                    edt_added_user_email.setVisibility(View.VISIBLE);
                    edt_added_user_email.setText(user_email);
            }
            else
            {
                img_error.startAnimation(animation);
            }

            if (user_type.equalsIgnoreCase("premium"))
            {
                flt_strt = Long.parseLong((sessionManager.getData(SessionManager.KEY_MIN_RANGEAGE)));
                flt_end = Long.parseLong((sessionManager.getData(SessionManager.KEY_MAX_RANGEAGE)));
                System.out.println("range age "+flt_strt + flt_end);
            }
            System.out.println("range age "+flt_strt + flt_end);
            rangeSeekbar.setMinStartValue(flt_strt).setMaxStartValue(flt_end).apply();


            String user_mobile = sessionManager.getData(SessionManager.KEY_MOBILE);
            System.out.println("Mobile is###"+user_mobile);

            if(!user_mobile.equalsIgnoreCase("")) {
                mobile_nuber.setText(user_mobile);
            }


            int kms = Integer.parseInt(sessionManager.getData(SessionManager.KEY_RANGEKMS));
            if (String.valueOf(kms)!=null)
            {
                System.out.println("kms"+kms);
                selected_distance.setText(kms+" Kms");

               /* ProgressBarAnimation anim = new ProgressBarAnimation(seekBar, 50, 100);
                anim.setDuration(5000);
                seekBar.startAnimation(anim);*/
                seekBar.setProgress(kms);
            }



            //hide age switch view
            Boolean hide_age_switch_value = Boolean.valueOf(sessionManager.getData(SessionManager.KEY_AGE_HIDE_SWITCH));
            System.out.println("hide age switch"+ hide_age_switch_value);


            if (hide_age_switch_value==true)
            {
                hide_age_Switch.setChecked(true);
            }
            else
            {
                hide_age_Switch.setChecked(false);
            }

            boolean hide_distance_switch_value = Boolean.parseBoolean(sessionManager.getData(SessionManager.KEY_DISTANCE_HIDE_SWITCH));
            System.out.println("distance switch"+ hide_distance_switch_value);

            if (hide_distance_switch_value==true)
            {
                distance_invisible.setChecked(true);
            }
            else
            {
                distance_invisible.setChecked(false);
            }



            String show_men_switch_value = sessionManager.getData(SessionManager.KEY_SHOW_MEN_SWITCH);
            String show_me_men = sessionManager.getData(SessionManager.KEY_SHOW_ME_TEXT);
            System.out.println("switch men"+ show_men_switch_value + show_me_men);

            if (show_men_switch_value.equalsIgnoreCase("true"))
            {
                show_me_text.setText(show_me_men);
                show_me_men_switch.setChecked(true);
            }
            else
            {
                show_me_men_switch.setChecked(false);
            }

            String show_women_switch_value = sessionManager.getData(SessionManager.KEY_SHOW_WOMEN_SWITCH);
            String show_women_men = sessionManager.getData(SessionManager.KEY_SHOW_ME_TEXT);

            System.out.println("switch women"+ show_women_switch_value + show_women_men);

            if (show_women_switch_value.equalsIgnoreCase("true"))
            {
                show_me_text.setText(show_me_men);
                show_me_women_switch.setChecked(true);
            }
            else
            {
                show_me_women_switch.setChecked(false);
            }


            boolean hide_new_crush_value = Boolean.parseBoolean(sessionManager.getData(SessionManager.KEY_NEW_CRUSH_NOTIFY_SWITCH));
            System.out.println("new crush notify switch"+ hide_new_crush_value);

               if (hide_new_crush_value==true)
                {
                    new_crush_switch.setChecked(true);
                }
                else
                {
                    new_crush_switch.setChecked(false);
                }

            boolean hide_date_invitation_value = Boolean.parseBoolean(sessionManager.getData(SessionManager.KEY_DATE_INVITION_NOTIFY_SWITCH));
            System.out.println("new date invitition switch"+ hide_date_invitation_value);

            if (hide_date_invitation_value==true)
            {
                date_invitation_switch.setChecked(true);
            }
            else
            {
                date_invitation_switch.setChecked(false);
            }


            boolean hide_message_value = Boolean.parseBoolean(sessionManager.getData(SessionManager.KEY_MESSAGE_NOTIFY_SWITCH));
            System.out.println("hide_message_value switch"+ hide_message_value);

            if (hide_message_value==true)
            {
                message_switch.setChecked(true);
            }
            else
            {
                message_switch.setChecked(false);
            }

            boolean hide_daily_pack_value = Boolean.parseBoolean(sessionManager.getData(SessionManager.KEY_DAILY_PACKS_NOTIFY_SWITCH));
            System.out.println("hide_daily_pack_value switch"+ hide_daily_pack_value);

            if (hide_daily_pack_value==true)
            {
                daily_packs_switch.setChecked(true);
            }
            else
            {
                daily_packs_switch.setChecked(false);
            }

            boolean hide_other_notify_value = Boolean.parseBoolean(sessionManager.getData(SessionManager.KEY_OTHER_NOTIFY_SWITCH));
            System.out.println("hide_other_notify_value switch"+ hide_other_notify_value);

            if (hide_other_notify_value==true)
            {
                other_notification_switch.setChecked(true);
            }
            else
            {
                other_notification_switch.setChecked(false);
            }

            String show_kms_switch_value = sessionManager.getData(SessionManager.KEY_DIS_IN_KMS);
            System.out.println("switch show_kms_switch_value"+ show_kms_switch_value );

            if (show_kms_switch_value.equalsIgnoreCase("true"))
            {
                show_distance_in_kms.setChecked(true);
            }
            else
            {
                show_distance_in_kms.setChecked(false);
            }

            String show_miles_switch_value = sessionManager.getData(SessionManager.KEY_DIS_IN_MILE);
            System.out.println("switch show_miles_switch_value"+ show_miles_switch_value );

            if (show_miles_switch_value.equalsIgnoreCase("true"))
            {
                show_distance_in_miles.setChecked(true);
            }
            else
            {
                show_distance_in_miles.setChecked(false);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void initView() {

        context = this;
        sessionManager = new SessionManager(context);
        connectionDetector = new ConnectionDetector();
        progressDialog = new ProgressDialog(context);

        upgrade_package = (LinearLayout)findViewById(R.id.setting_upgrade_package);
        linear_contactus = (LinearLayout)findViewById(R.id.ll_contactus);
        linear_change_password = (LinearLayout)findViewById(R.id.ll_changepassword);

        img_backarrow = (ImageView)findViewById(R.id.setting_arrow);

        img_sucess = (ImageView)findViewById(R.id.sucess_img);
        img_error = (ImageView)findViewById(R.id.error_img);

        //textviews
        edt_email_popup = (TextView)findViewById(R.id.Add_email_popup);
        edt_added_user_email = (TextView)findViewById(R.id.Added_email);
        selected_distance = (TextView)findViewById(R.id.selected_distance);
        selected_age_range = (TextView)findViewById(R.id.search_age_btw);
        show_me_text = (TextView)findViewById(R.id.show_me);
        mobile_nuber =  (TextView)findViewById(R.id.mobile_user);


        // get seekbar from view
        rangeSeekbar = (CrystalRangeSeekbar)findViewById(R.id.age_rangeSeekbar);
        crystalSeekbar = (CrystalSeekbar)findViewById(R.id.distance_rangeSeekbar1);
        seekBar = (SeekBar)findViewById(R.id.kms_seekbar);

        //all switch viewss are there
        hide_age_Switch = (Switch)findViewById(R.id.hide_age_switch);
        distance_invisible = (Switch)findViewById(R.id.hide_distance_switch);
        show_me_men_switch = (Switch)findViewById(R.id.men_switch);
        show_me_women_switch = (Switch)findViewById(R.id.female_switch);

        new_crush_switch = (Switch)findViewById(R.id.new_crush_notification_switch);
        message_switch = (Switch)findViewById(R.id.message_notification_switch);
        date_invitation_switch = (Switch)findViewById(R.id.date_invitation_notification_switch);
        daily_packs_switch = (Switch)findViewById(R.id.daily_packs_notification_switch);
        other_notification_switch = (Switch) findViewById(R.id.other_notification_switch);

        show_distance_in_kms = (Switch)findViewById(R.id.distance_shoe_in_kms);
        show_distance_in_miles = (Switch) findViewById(R.id.distance_shoe_in_miles);

        logout = (Button)findViewById(R.id.btn_logout);

        user_type = sessionManager.getData(SessionManager.KEY_USER_TYPE);
        if (user_type.equalsIgnoreCase("basic"))
        {
            //openUpgradePopup();
        }

    }
    private void onClick()
    {

        img_sucess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(context,R.anim.logo_animation);
                img_sucess.startAnimation(animation);
            }
        });
        img_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(context,R.anim.logo_animation);
                img_error.startAnimation(animation);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alertDialog();
            }
        });

        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edt_email_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopup();
            }
        });

        upgrade_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpgradePopup();
            }
        });

        linear_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactUsPopup();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress <= 0){
                    seekBar.setProgress(0);
                }
                if(progress >= 0) {
                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        selected_distance.setText(progress+" Kms range.");
                        sessionManager.setData(SessionManager.KEY_RANGEKMS, String.valueOf(progress));
                        System.out.println("kms"+progress);
                    }

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (user_type.equalsIgnoreCase("basic"))
                {
                    openUpgradePopup();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        linear_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Changepassword.class);
                startActivity(intent);
            }
        });


        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                if (user_type.equalsIgnoreCase("premium"))
                {
                    selected_age_range.setText(String.valueOf(minValue)+"-"+String.valueOf(maxValue)+" Years");
                    flt_strt= (long) minValue;
                    flt_end= (long) maxValue;
                    System.out.println("range###"+flt_strt);
                    System.out.println("range###"+flt_end);
                }
                else
                {
                    openUpgradePopup();
                }

            }
        });

        // set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                if (user_type.equalsIgnoreCase("premium"))
                {
                    selected_age_range.setText(String.valueOf(minValue)+"-"+String.valueOf(maxValue)+" Years");
                    flt_strt= (long) minValue;
                    flt_end= (long) maxValue;
                    System.out.println("range###"+flt_strt);
                    System.out.println("range###"+flt_end);
                }
            }
        });

        crystalSeekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                selected_distance.setText(String.valueOf(value)+" Kms");
            }
        });

        //for switch click Events
        //1. hide user age switch
       hide_age_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               if (isChecked)
               {
                   System.out.println("hide age"+ isChecked);
                   if (user_type.equalsIgnoreCase("premium"))
                   {
                       hide_age_Switch.setChecked(true);
                       sessionManager.setData(SessionManager.KEY_AGE_HIDE_SWITCH, String.valueOf(isChecked));
                       String yes = "yes";
                       updateHideAge(yes);
                   }
                   else
                   {
                       hide_age_Switch.setChecked(false);
                       openUpgradePopup();
                   }
               }
               else
               {
                   String no = "no";
                   System.out.println("hide age"+ isChecked);
                   sessionManager.setData(SessionManager.KEY_AGE_HIDE_SWITCH, String.valueOf(isChecked));
                   updateHideAge(no);
               }
           }
       });

       //2. hide user distance
        distance_invisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    System.out.println("hide dstnce"+ !isChecked);
                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        distance_invisible.setChecked(true);
                        sessionManager.setData(SessionManager.KEY_DISTANCE_HIDE_SWITCH, String.valueOf(isChecked));
                        String yes = "yes";
                        updateHideDistance(yes);
                    }
                    else
                    {
                        distance_invisible.setChecked(false);
                        openUpgradePopup();
                    }
                }
                else
                {
                    String value = "no";
                    System.out.println("hide d"+ isChecked);
                    sessionManager.setData(SessionManager.KEY_DISTANCE_HIDE_SWITCH, String.valueOf(isChecked));
                    updateHideDistance(value);

                }
            }
        });

        //3.show me men switch
        show_me_men_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        show_me_men_switch.setChecked(true);
                        String value = "yes";

                        System.out.println("####ischeck men value if"+value);
                        System.out.println("####ischeck men"+isChecked);

                        show_me_women_switch.setChecked(false);
                        sessionManager.setData(SessionManager.KEY_SHOW_WOMEN_SWITCH, String.valueOf(!isChecked));
                        show_me_text.setText("Men");
                        sessionManager.setData(SessionManager.KEY_SHOW_ME_TEXT, "Men");
                        sessionManager.setData(SessionManager.KEY_SHOW_MEN_SWITCH, String.valueOf(isChecked));
                        updateShowMeMen(value);
                    }
                    else
                    {
                        show_me_men_switch.setChecked(false);
                        openUpgradePopup();
                    }

                }
                else
                {
                    System.out.println("####ischeck men"+isChecked);
                    String value = "no";
                    System.out.println("####ischeck men value if"+value);

                    show_me_women_switch.setChecked(true);
                    sessionManager.setData(SessionManager.KEY_SHOW_WOMEN_SWITCH, String.valueOf(isChecked));
                    show_me_text.setText("women");
                    sessionManager.setData(SessionManager.KEY_SHOW_ME_TEXT, "");
                    sessionManager.setData(SessionManager.KEY_SHOW_MEN_SWITCH, String.valueOf(isChecked));
                    updateShowMeMen(value);

                }
            }
        });

        //4.show me women switch
        show_me_women_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        System.out.println("####ischeck women"+isChecked);
                        String value = "yes";
                        System.out.println("####ischeck women value if"+isChecked);

                        show_me_women_switch.setChecked(true);
                        show_me_men_switch.setChecked(false);
                        sessionManager.setData(SessionManager.KEY_SHOW_MEN_SWITCH, String.valueOf(!isChecked));
                        show_me_text.setText("Women");
                        sessionManager.setData(SessionManager.KEY_SHOW_ME_TEXT, "Women");
                        sessionManager.setData(SessionManager.KEY_SHOW_WOMEN_SWITCH, String.valueOf(isChecked));
                        updateShowMeWomen(value);
                    }
                    else
                    {
                        show_me_women_switch.setChecked(false);
                        openUpgradePopup();
                    }
                }
                else
                {
                    System.out.println("####ischeck women"+isChecked);
                    String value = "no";
                    System.out.println("####ischeck women value else"+isChecked);

                    show_me_men_switch.setChecked(true);
                    sessionManager.setData(SessionManager.KEY_SHOW_MEN_SWITCH, String.valueOf(isChecked));

                    show_me_text.setText("men");
                    sessionManager.setData(SessionManager.KEY_SHOW_ME_TEXT, "");
                    sessionManager.setData(SessionManager.KEY_SHOW_WOMEN_SWITCH, String.valueOf(isChecked));
                    updateShowMeWomen(value);

                }
            }
        });
        //5. set new crush notification setting  switch
        new_crush_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    System.out.println("hide new crush notification"+ isChecked);

                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        new_crush_switch.setChecked(true);
                        String yes = "yes";
                        sessionManager.setData(SessionManager.KEY_NEW_CRUSH_NOTIFY_SWITCH, String.valueOf(isChecked));
                        // updateHideAge(yes);
                    }
                }
                else
                {
                    System.out.println("hide new crush notification"+ isChecked);

                    if (user_type.equalsIgnoreCase("premium"))
                    {
                         String no = "no";
                         sessionManager.setData(SessionManager.KEY_NEW_CRUSH_NOTIFY_SWITCH, String.valueOf(isChecked));
                         // updateHideAge(no);
                         new_crush_switch.setChecked(false);
                    }
                    else
                    {
                        // sessionManager.setData(SessionManager.KEY_NEW_CRUSH_NOTIFY_SWITCH, String.valueOf(isChecked));
                        // updateHideAge(no);
                        new_crush_switch.setChecked(true);
                        openUpgradePopup();
                    }


                }
            }
        });

        //6. set DAte Invitation setting  switch
        date_invitation_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    System.out.println("hide date invitition notification"+ isChecked);

                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        String yes = "yes";
                        sessionManager.setData(SessionManager.KEY_DATE_INVITION_NOTIFY_SWITCH, String.valueOf(isChecked));
                        // updateHideAge(yes);
                        date_invitation_switch.setChecked(true);
                    }

                }
                else
                {
                    System.out.println("hide date invitition notification"+ isChecked);
                    //sessionManager.setData(SessionManager.KEY_DATE_INVITION_NOTIFY_SWITCH, String.valueOf(isChecked));
                    // updateHideAge(no);

                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        String no = "no";
                        System.out.println("hide date invitition notification"+ isChecked);
                        date_invitation_switch.setChecked(false);
                        sessionManager.setData(SessionManager.KEY_DATE_INVITION_NOTIFY_SWITCH, String.valueOf(isChecked));
                    }
                    else
                    {
                        date_invitation_switch.setChecked(true);
                        openUpgradePopup();
                    }

                }
            }
        }); //7. set message notification setting  switch
        message_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        String yes = "yes";
                        sessionManager.setData(SessionManager.KEY_MESSAGE_NOTIFY_SWITCH, String.valueOf(isChecked));
                        // updateHideAge(yes);
                        message_switch.setChecked(true);
                    }
                    System.out.println("hide messsages notification"+ isChecked);
                }
                else
                {
                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        String no = "no";
                        System.out.println("hide messsages notification"+ isChecked);
                        sessionManager.setData(SessionManager.KEY_MESSAGE_NOTIFY_SWITCH, String.valueOf(isChecked));
                        message_switch.setChecked(false);
                    }
                    else
                    {
                        message_switch.setChecked(true);
                        openUpgradePopup();
                    }
                }
            }
        });
        //8. set new crush notification setting  switch
        daily_packs_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    System.out.println("hide daily pack notification"+ isChecked);

                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        String yes = "yes";
                        sessionManager.setData(SessionManager.KEY_DAILY_PACKS_NOTIFY_SWITCH, String.valueOf(isChecked));
                        daily_packs_switch.setChecked(true);
                    }

                    // updateHideAge(yes);
                }
                else
                {
                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        String no = "no";
                        System.out.println("hide daily pack notification"+ isChecked);
                        sessionManager.setData(SessionManager.KEY_DAILY_PACKS_NOTIFY_SWITCH, String.valueOf(isChecked));
                        // updateHideAge(no);
                        daily_packs_switch.setChecked(false);

                    }
                    else
                    {
                        daily_packs_switch.setChecked(true);
                        openUpgradePopup();
                    }

                }
            }
        });
        //9. set new crush notification setting  switch
        other_notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        System.out.println("hide other notification"+ isChecked);
                        String yes = "yes";
                        sessionManager.setData(SessionManager.KEY_OTHER_NOTIFY_SWITCH, String.valueOf(isChecked));
                        // updateHideAge(yes);
                        other_notification_switch.setChecked(true);
                    }

                }
                else
                {
                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        String no = "no";
                        System.out.println("hide other notification"+ isChecked);
                        sessionManager.setData(SessionManager.KEY_OTHER_NOTIFY_SWITCH, String.valueOf(isChecked));
                        // updateHideAge(no);
                        other_notification_switch.setChecked(false);

                    }
                    else
                    {
                        other_notification_switch.setChecked(true);
                        openUpgradePopup();
                    }
                }
            }
        });

        //10.show me men switch
        show_distance_in_kms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        String value = "yes";
                        System.out.println("####ischeck kms"+isChecked);
                        show_distance_in_miles.setChecked(false);
                        sessionManager.setData(SessionManager.KEY_DIS_IN_KMS, String.valueOf(isChecked));
                        sessionManager.setData(SessionManager.KEY_DIS_IN_MILE, String.valueOf(!isChecked));
                        //updateShowMeMen(value);
                    }

                }
                else
                {
                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        String value = "no";
                        show_distance_in_kms.setChecked(false);
                        sessionManager.setData(SessionManager.KEY_DIS_IN_MILE, String.valueOf(!isChecked));
                        show_distance_in_miles.setChecked(true);
                        sessionManager.setData(SessionManager.KEY_DIS_IN_KMS, String.valueOf(isChecked));

                    }
                    else
                    {
                        show_distance_in_kms.setChecked(true);
                        openUpgradePopup();
                    }
                    System.out.println("####ischeck kms"+!isChecked);

                    //sessionManager.setData(SessionManager.KEY_DIS_IN_MILE, String.valueOf(isChecked));
                    //sessionManager.setData(SessionManager.KEY_DIS_IN_KMS, String.valueOf(isChecked));
                    //updateShowMeMen(value);

                }
            }
        });

        //11.show me women switch
        show_distance_in_miles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    System.out.println("#### if ischeck mile"+!isChecked);

                    if (user_type.equalsIgnoreCase("premium"))
                    {
                        String value = "yes";
                        System.out.println("####ischeck mile"+isChecked);
                        show_distance_in_miles.setChecked(true);
                        show_distance_in_kms.setChecked(false);
                        sessionManager.setData(SessionManager.KEY_DIS_IN_KMS, String.valueOf(!isChecked));
                        sessionManager.setData(SessionManager.KEY_DIS_IN_MILE, String.valueOf(isChecked));
                    }
                    else
                    {
                        show_distance_in_miles.setChecked(false);
                        openUpgradePopup();
                    }

                    System.out.println("####ischeck mile"+isChecked);

                   // show_distance_in_kms.setChecked(false);
                    //sessionManager.setData(SessionManager.KEY_DIS_IN_KMS, String.valueOf(!isChecked));
                    //sessionManager.setData(SessionManager.KEY_DIS_IN_MILE, String.valueOf(isChecked));

                }
                else
                {

                    System.out.println("#### else ischeck mile"+!isChecked);
                    String value = "no";
                    show_distance_in_kms.setChecked(true);
                    sessionManager.setData(SessionManager.KEY_DIS_IN_KMS, String.valueOf(!isChecked));
                    sessionManager.setData(SessionManager.KEY_DIS_IN_MILE, String.valueOf(isChecked));
                    //updateShowMeMen(value);

                }
            }
        });



    }

    //UPDATION METHODS WILL be CALLED HERE//
//..............................................                                         ....................................................
    private void updateHideAge(final String no)
    {
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_HIDE_AGE_SETTING, new Response.Listener<String>() {
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
                        progressDialog.dismiss();
                    }
                    else
                    {
                        hide_age_Switch.setChecked(false);
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
                Toast.makeText(getApplicationContext(), "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",sessionManager.getData(SessionManager.KEY_USER_ID));
                System.out.println("#11##"+ no);

                params.put("age_show", no);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
//.....................
private void updateHideDistance(final String value) {

    progressDialog.setMessage("Loading..");
    progressDialog.setCancelable(false);
    progressDialog.show();

    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_HIDE_DISTANCE_SETTING, new Response.Listener<String>() {
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
                    progressDialog.dismiss();
                }
                else
                {
                    distance_invisible.setChecked(false);
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
            Toast.makeText(getApplicationContext(), "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params = new HashMap<>();
            params.put("user_id",sessionManager.getData(SessionManager.KEY_USER_ID));
            System.out.println("#22##"+ value);
            params.put("distance_show", value);
            return params;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(stringRequest);


}
//..............................................update show me...........................................................................
private void updateShowMeMen(final String value) {

    progressDialog.setMessage("Loading..");
    progressDialog.setCancelable(false);
    progressDialog.show();

    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SHOW_ME_MEN_SETTING, new Response.Listener<String>() {
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
                    progressDialog.dismiss();
                }
                else
                {
                    show_me_men_switch.setChecked(false);
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
            Toast.makeText(getApplicationContext(), "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params = new HashMap<>();
            params.put("user_id",sessionManager.getData(SessionManager.KEY_USER_ID));
            System.out.println("#22male##"+ value);
            params.put("gender_male_show", value);
            return params;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(stringRequest);


}

//..........................................................//update show me female/...........................................................
  public void updateShowMeWomen(final String value){


    progressDialog.setMessage("Loading..");
    progressDialog.setCancelable(false);
    progressDialog.show();

    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SHOW_ME_WOMEN_SETTING, new Response.Listener<String>() {
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
                    progressDialog.dismiss();
                }
                else
                {
                    show_me_women_switch.setChecked(false);
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
            Toast.makeText(getApplicationContext(), "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params = new HashMap<>();
            params.put("user_id",sessionManager.getData(SessionManager.KEY_USER_ID));
            System.out.println("#33female##"+ value);
            params.put("gender_female_show", value);
            return params;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(stringRequest);
  }
    //....................................................\\Email verification popup//..............................................................
    private void openPopup() {
        try{
            mydialog = new Dialog(context);
            mydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mydialog.setContentView(R.layout.setemail_dialog);

            /*initializing all the views of dialog */
            dialog_enter_email = (EditText)mydialog.findViewById(R.id.enter_email);
            img_send_arrow = (ImageView) mydialog.findViewById(R.id.addemail);
            img_hide_dialog = (ImageView)mydialog.findViewById(R.id.hideDialog);

            img_hide_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mydialog.dismiss();
                }
            });
            img_send_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Validate1();
                    }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        mydialog.show();
    }

    private void Validate1()
    {
        String email = dialog_enter_email.getText().toString().trim();
        boolean iserror=false;

        if(email.equalsIgnoreCase(""))
        {
            iserror=true;
             dialog_enter_email.setError("Please enter your email.");

        }
        else
        {
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                iserror=true;
                dialog_enter_email.setError("Valid email is required.");

            }
            else
            {
                System.out.println("Valid Email.");
            }

        }

        if(!iserror)
        {
            RegisterEmail();
        }

    }
    private void RegisterEmail()
    {
        final Animation animation = AnimationUtils.loadAnimation(context,R.anim.logo_animation);
        final Animation animation2 = AnimationUtils.loadAnimation(context,R.anim.logo_animation_back);

        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String user_email = dialog_enter_email.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_EMAIL, new Response.Listener<String>() {
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
                        mydialog.dismiss();

                        img_sucess.startAnimation(animation2);
                        img_error.setVisibility(View.GONE);

                        edt_email_popup.setVisibility(View.GONE);

                        img_sucess.setVisibility(View.VISIBLE);
                        img_sucess.startAnimation(animation);

                        edt_added_user_email.setVisibility(View.VISIBLE);
                        edt_added_user_email.setText(user_email);
                        sessionManager.setData(SessionManager.KEY_EMAIL,user_email);
                        Toast.makeText(context, "Successfully registerd email.", Toast.LENGTH_SHORT).show();
                        //JSONObject result_object = jsonObject.getJSONObject("result");
                       // JSONObject object = result_object.getJSONObject("user");
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
                Toast.makeText(getApplicationContext(), "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                 params.put("user_id",sessionManager.getData(SessionManager.KEY_USER_ID));
                 params.put("email",user_email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
//..................................................................\\upgrade popup is here//.....................................................
    private void openUpgradePopup() {

        try{
            upgradedialog = new Dialog(context);
            upgradedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            upgradedialog.setContentView(R.layout.upgrade_package_popup);

            /*initializing all the views of dialog */
            String crush = sessionManager.getData(SessionManager.KEY_CRUSH_MIN_RANGE);
            String date = sessionManager.getData(SessionManager.KEY_DATE_MIN_RANGE);
            String combo = sessionManager.getData(SessionManager.KEY_COMBO_MIN_RANGE);

            img_hide_upgrade_dialog = (ImageView)upgradedialog.findViewById(R.id.hide_upgradeeDialog);
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
            }, 3000, 2000);

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
            gallery.setAdapter(new ImageAdapter(this));
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

    private void openContactUsPopup() {

        try{
            contactus_dialog = new Dialog(context);
            contactus_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            contactus_dialog.setContentView(R.layout.setcontactus_dialog);

            /*initializing all the views of dialog */
            img_hide_contactus_dialog = (ImageView)contactus_dialog.findViewById(R.id.contactus_hideDialog);

            img_hide_contactus_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactus_dialog.dismiss();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        contactus_dialog.show();
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

        if (user_type.equalsIgnoreCase("premium"))
        {
            sessionManager.setData(SessionManager.KEY_RANGEKMS, String.valueOf(seekBar.getProgress()));
            System.out.println("age@@@"+flt_strt+flt_end);

            sessionManager.setData(SessionManager.KEY_MIN_RANGEAGE, String.valueOf(flt_strt));
            sessionManager.setData(SessionManager.KEY_MAX_RANGEAGE, String.valueOf(flt_end));

            updateKms(seekBar.getProgress());
            updateAgeRange(flt_strt,flt_end);
        }

        Intent intent = new Intent(User_Profile_Setting.this,MyProfileActiivty.class);
        startActivity(intent);
    }

    private void updateKms(final int progress)
    {
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        System.out.println("responseagerange34242 ---"+progress);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_KMS_SETTING, new Response.Listener<String>() {
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
                        progressDialog.dismiss();
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
                Toast.makeText(getApplicationContext(), "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",sessionManager.getData(SessionManager.KEY_USER_ID));
                System.out.println("#range ageee##"+ progress);
                params.put("distance_under", String.valueOf(progress));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    private void updateAgeRange(final long flt_strt, final long flt_end)
    {
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        System.out.println("responseagerange34242 ---"+flt_strt +""+ flt_end);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_AGE_RANGE_SETTING, new Response.Listener<String>() {
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
                        progressDialog.dismiss();
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
                Toast.makeText(getApplicationContext(), "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",sessionManager.getData(SessionManager.KEY_USER_ID));
                System.out.println("#range ageee##"+ flt_strt + flt_end);
                params.put("min_age_range", String.valueOf(flt_strt));
                params.put("max_age_range", String.valueOf(flt_end));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void alertDialog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout with YouME?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Logout();
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

    private void Logout()
    {
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String token = sessionManager.getData(SessionManager.KEY_TOKEN);
        System.out.println("tokennn "+token);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_LOGOUT+"/"+token, new Response.Listener<String>() {
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
                        sessionManager.logoutUser();
                        progressDialog.dismiss();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Logout Successfully.", Toast.LENGTH_SHORT).show();
                        sessionManager.logoutUser();

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
                sessionManager.logoutUser();
                Toast.makeText(getApplicationContext(), "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }


    public class ProgressBarAnimation extends Animation{
        private ProgressBar progressBar;
        private float from;
        private float  to;

        public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
        }

    }
}
