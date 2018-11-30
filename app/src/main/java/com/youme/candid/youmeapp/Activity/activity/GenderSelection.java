package com.youme.candid.youmeapp.Activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.youme.candid.youmeapp.R;

public class GenderSelection extends AppCompatActivity {


    private Animation animation;
    ImageView img_male_hair, img_female_hair, img_eyes, img_mouth, img_mustache, img_boytshrt, img_grltshrt;
    Button btn_male, bt_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genderselection);

        initView();

        click();
    }

    private void click() {

        btn_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ////////female/////
                animation = AnimationUtils.loadAnimation(GenderSelection.this, R.anim.app_name_animation_back);
                img_female_hair.startAnimation(animation);

                animation = AnimationUtils.loadAnimation(GenderSelection.this, R.anim.app_name_animation_back);
                img_grltshrt.startAnimation(animation);


                /////////////male
                animation = AnimationUtils.loadAnimation(GenderSelection.this, R.anim.slide_in_up);
                img_male_hair.startAnimation(animation);

                animation = AnimationUtils.loadAnimation(GenderSelection.this,
                        R.anim.app_name_animation);
                img_boytshrt.startAnimation(animation);

                animation = AnimationUtils.loadAnimation(GenderSelection.this, R.anim.slide_down);
                img_mustache.startAnimation(animation);


            }
        });

        bt_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ////male

                img_grltshrt.setVisibility(View.VISIBLE);
                img_female_hair.setVisibility(View.VISIBLE);


                animation = AnimationUtils.loadAnimation(GenderSelection.this, R.anim.app_name_animation_back);
                img_male_hair.startAnimation(animation);

                animation = AnimationUtils.loadAnimation(GenderSelection.this,
                        R.anim.app_name_animation_back);
                img_boytshrt.startAnimation(animation);

                animation = AnimationUtils.loadAnimation(GenderSelection.this, R.anim.app_name_animation_back);
                img_mustache.startAnimation(animation);

                //......... female.....//

                animation = AnimationUtils.loadAnimation(GenderSelection.this, R.anim.slide_in_up);
                img_female_hair.startAnimation(animation);

                animation = AnimationUtils.loadAnimation(GenderSelection.this,
                        R.anim.slide_in_right);
                img_grltshrt.startAnimation(animation);


            }
        });
    }


    private void initView() {

        img_male_hair = (ImageView) findViewById(R.id.male_hair);
        img_female_hair = (ImageView) findViewById(R.id.female_hair);
        img_eyes = (ImageView) findViewById(R.id.eyes);
        img_mouth = (ImageView) findViewById(R.id.mouth);
        img_mustache = (ImageView) findViewById(R.id.mustache);
        img_boytshrt = (ImageView) findViewById(R.id.boys_tshrt);
        img_grltshrt = (ImageView) findViewById(R.id.girl_tshrt);

        btn_male = (Button) findViewById(R.id.male);
        bt_female = (Button) findViewById(R.id.female);


    }
}


