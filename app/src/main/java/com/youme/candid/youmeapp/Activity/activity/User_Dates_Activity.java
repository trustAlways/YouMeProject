package com.youme.candid.youmeapp.Activity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youme.candid.youmeapp.R;

public class User_Dates_Activity extends AppCompatActivity {

    LinearLayout linear_send,linear_received;
    ImageView datesend_img_activated,datesend_img_deactivated, imag_backarrrow;
    ImageView datereceive_img_activated,datereceive_img_deactivated;
    View sent_view,receive_view;
    FrameLayout date_frameLayout;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__dates_);

        //initialize all the views
        init();

        //onClick events
        onClick();

        //for set fragment for send and receive crushes
         setView();
    }

    private void init() {

        context = this;

        linear_send = (LinearLayout)findViewById(R.id.ll_date_crushess);
        linear_received = (LinearLayout)findViewById(R.id.ll_received_date);

        date_frameLayout = (FrameLayout)findViewById(R.id.date_framelayout);

        imag_backarrrow = (ImageView)findViewById(R.id.date_backarrow);

        datesend_img_activated = (ImageView)findViewById(R.id.sentdate_activated);
        datesend_img_deactivated = (ImageView)findViewById(R.id.sentdate_deactivated);
        sent_view = (View)findViewById(R.id.sentdate_activatedview);

        datereceive_img_activated = (ImageView)findViewById(R.id.receiveddate_activated);
        datereceive_img_deactivated = (ImageView)findViewById(R.id.receiveddate_deactivated);
        receive_view = (View)findViewById(R.id.receiveddate_view);


    }

    private void onClick() {

        linear_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if (datesend_img_activated.getVisibility()==View.VISIBLE)
              {
                  datesend_img_activated.setVisibility(View.VISIBLE);
                  sent_view.setVisibility(View.VISIBLE);
                  datesend_img_deactivated.setVisibility(View.GONE);
                  datereceive_img_deactivated.setVisibility(View.VISIBLE);
                  datereceive_img_activated.setVisibility(View.GONE);
                  receive_view.setVisibility(View.GONE);
                  setView();
              }
              else
              {
                  datesend_img_activated.setVisibility(View.VISIBLE);
                  sent_view.setVisibility(View.VISIBLE);
                  datesend_img_deactivated.setVisibility(View.GONE);
                  datereceive_img_deactivated.setVisibility(View.VISIBLE);
                  datereceive_img_activated.setVisibility(View.GONE);
                  receive_view.setVisibility(View.GONE);
                  setView();
              }

            }
        });

        linear_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (datereceive_img_deactivated.getVisibility()==View.VISIBLE)
                {
                    datereceive_img_deactivated.setVisibility(View.GONE);
                    datereceive_img_activated.setVisibility(View.VISIBLE);
                    receive_view.setVisibility(View.VISIBLE);
                    datesend_img_activated.setVisibility(View.GONE);
                    datesend_img_deactivated.setVisibility(View.VISIBLE);
                    sent_view.setVisibility(View.GONE);
                    setView();
                }
                else
                {
                    datereceive_img_activated.setVisibility(View.VISIBLE);
                    receive_view.setVisibility(View.VISIBLE);
                    datesend_img_deactivated.setVisibility(View.VISIBLE);
                    datereceive_img_deactivated.setVisibility(View.GONE);
                    datereceive_img_activated.setVisibility(View.GONE);
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
       if (datesend_img_activated.getVisibility()==View.VISIBLE)
       {
           Fragment send_fragment = new SendDateRequest();
           getSupportFragmentManager().beginTransaction().replace(R.id.date_framelayout,send_fragment)
                   .commit();
       }
       else
       {
           Fragment receive_fragment = new ReceiveDateRequest();
           getSupportFragmentManager().beginTransaction().replace(R.id.date_framelayout,receive_fragment)
                   .commit();
       }
    }
}
