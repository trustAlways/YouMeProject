package com.youme.candid.youmeapp.Activity.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youme.candid.youmeapp.R;

public class User_Crushes_Activity extends AppCompatActivity {

    LinearLayout linear_send,linear_received;
    ImageView crushsend_img_activated,crushsend_img_deactivated, imag_backarrrow;
    ImageView crushreceive_img_activated,crushreceive_img_deactivated;
    View sent_view,receive_view;
    FrameLayout crushes_frameLayout;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__crushes_);

        //initialize all the views
        init();

        //onClick events
        onClick();

        //for set fragment for send and receive crushes
         setView();
    }

    private void init() {

        context = this;

        linear_send = (LinearLayout)findViewById(R.id.ll_sent_crushes);
        linear_received = (LinearLayout)findViewById(R.id.ll_received_crushes);

        crushes_frameLayout = (FrameLayout)findViewById(R.id.crush_framelayout);

        imag_backarrrow = (ImageView)findViewById(R.id.crushes_backarrow);

        crushsend_img_activated = (ImageView)findViewById(R.id.sentcrushes_activated);
        crushsend_img_deactivated = (ImageView)findViewById(R.id.sentcrushes_deactivated);
        sent_view = (View)findViewById(R.id.sentcrushes_activatedview);

        crushreceive_img_activated = (ImageView)findViewById(R.id.receivedcrushes_activated);
        crushreceive_img_deactivated = (ImageView)findViewById(R.id.receivedcrushes_deactivated);
        receive_view = (View)findViewById(R.id.receivedcrushes_view);


    }

    private void onClick() {

        linear_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if (crushsend_img_activated.getVisibility()==View.VISIBLE)
              {
                  crushsend_img_activated.setVisibility(View.VISIBLE);
                  sent_view.setVisibility(View.VISIBLE);
                  crushsend_img_deactivated.setVisibility(View.GONE);
                  crushreceive_img_deactivated.setVisibility(View.VISIBLE);
                  crushreceive_img_activated.setVisibility(View.GONE);
                  receive_view.setVisibility(View.GONE);
                  setView();
              }
              else
              {
                  crushsend_img_activated.setVisibility(View.VISIBLE);
                  sent_view.setVisibility(View.VISIBLE);
                  crushsend_img_deactivated.setVisibility(View.GONE);
                  crushreceive_img_deactivated.setVisibility(View.VISIBLE);
                  crushreceive_img_activated.setVisibility(View.GONE);
                  receive_view.setVisibility(View.GONE);
                  setView();
              }

            }
        });

        linear_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (crushreceive_img_deactivated.getVisibility()==View.VISIBLE)
                {
                    crushreceive_img_deactivated.setVisibility(View.GONE);
                    crushreceive_img_activated.setVisibility(View.VISIBLE);
                    receive_view.setVisibility(View.VISIBLE);
                    crushsend_img_activated.setVisibility(View.GONE);
                    crushsend_img_deactivated.setVisibility(View.VISIBLE);
                    sent_view.setVisibility(View.GONE);
                    setView();
                }
                else
                {
                    crushreceive_img_activated.setVisibility(View.VISIBLE);
                    receive_view.setVisibility(View.VISIBLE);
                    crushsend_img_deactivated.setVisibility(View.VISIBLE);
                    crushreceive_img_deactivated.setVisibility(View.GONE);
                    crushreceive_img_activated.setVisibility(View.GONE);
                    sent_view.setVisibility(View.GONE);
                    setView();
                }
            }
        });

        imag_backarrrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MyProfileActiivty.class);
                startActivity(intent);
            }
        });
    }

    private void setView()
    {
       if (crushsend_img_activated.getVisibility()==View.VISIBLE)
       {
           Fragment send_fragment = new SendCrushRequest();
           getSupportFragmentManager().beginTransaction().replace(R.id.crush_framelayout,send_fragment)
                   .commit();
       }
       else
       {
           Fragment receive_fragment = new ReceivedCrushRequest();
           getSupportFragmentManager().beginTransaction().replace(R.id.crush_framelayout,receive_fragment)
                   .commit();
       }
    }
}
