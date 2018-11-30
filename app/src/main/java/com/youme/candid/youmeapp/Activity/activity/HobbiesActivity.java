package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HobbiesActivity extends AppCompatActivity {

    int previousSelectedPosition0 =0;
    int previousSelectedPosition1 =2;
    int previousSelectedPosition2 =4;
    int previousSelectedPosition3 =6;
    int previousSelectedPosition4 =8;
    int previousSelectedPosition5= 10;
    int previousSelectedPosition6 =12;
    int previousSelectedPosition7 =14;

    int previousSelectedPosition8 =16;
    int previousSelectedPosition9 =18;
    int previousSelectedPosition10 =20;
    int previousSelectedPosition11 =22;
    int previousSelectedPosition12 =24;
    int previousSelectedPosition13= 26;
    int previousSelectedPosition14 =28;
    int previousSelectedPosition15 =30;
    int previousSelectedPosition16 =32;
    int previousSelectedPosition17 =34;

    String Singing="",
            Dancing="",
            Swiming="",
            Riding="",
            Travelling="",
            Cooking="",
            Reading="",
            movie="",
            acting="",adventure="",sports="",yoga="",weightlifting="",painting="",outing="",dating="",shopping="",modeling="";

    GridView grid;
    RelativeLayout relativeLayout;
    SessionManager sessionManager;
    Context context;
    int position2;
    FrameLayout frameLayout;
    ProgressBar progressBar;
    ConnectionDetector connectionDetector;
    TextView textView;
    ImageView img_next,img_previous;
    String[] web = {
            "Singing",
            "Dancing",
            "Swiming",
            "Riding",
            "Travelling",
            "Cooking",
            "Reading",
            "Movie",
            "Acting",
            "Adventure",
            "Modeling",
            "Outing",
            "Painting",
            "Dating",
            "Yoga",
            "Weightlifting",
            "Sports",
            "Shopping",


    } ;
    int[] imageId = new int[18];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobbies);

        // Get the widgets reference from XML layout
        initView();

        getData();


      /*  boolean register = sessionManager.isHobby();
        {
            if (register)
            {
                startActivity(new Intent(context,Upload2.class));
            }
        }*/


        // for all the click events
        click();
    }

    private void initView()
    {
        context = this;
        sessionManager = new SessionManager(context);
        connectionDetector = new ConnectionDetector();

        imageId[0]= R.drawable.singing_gray;
        imageId[1]= R.drawable.dance_gray;
        imageId[2]= R.drawable.swimming_gray;
        imageId[3]= R.drawable.riding_gray;
        imageId[4]= R.drawable.traveling_gray;
        imageId[5]= R.drawable.cook_gray;
        imageId[6]= R.drawable.reading_gray;
        imageId[7]= R.drawable.movie_gray;
        imageId[8]= R.drawable.acting_gray;
        imageId[9]= R.drawable.adventure_gray;
        imageId[10]= R.drawable.modeling_gray;
        imageId[11]= R.drawable.outing_gray;
        imageId[12]= R.drawable.painting_gray;
        imageId[13]= R.drawable.dating_gray;
        imageId[14]= R.drawable.yoga_gray;
        imageId[15]= R.drawable.weightlifting_gray;
        imageId[16]= R.drawable.sports_gray;
        imageId[17]= R.drawable.shopping_gray;

        grid=(GridView)findViewById(R.id.grid);
        textView = (TextView)findViewById(R.id.hobbies);
        img_next = (ImageView) findViewById(R.id.hobby_next_btn);
        img_previous = (ImageView)findViewById(R.id.hobby__previous_btn);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativ_lay);

        frameLayout = (FrameLayout)findViewById(R.id.hobbies_loading_frame);
        progressBar = (ProgressBar)findViewById(R.id.hobbies_progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void getData()
    {
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USER_HOBBIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!", "1235" + response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success")) {
                        String hobbies = jsonObject.getString("result");
                        Log.i("response..!", "hobbies" + hobbies);
                        sessionManager.setData(SessionManager.KEY_HOBBY,hobbies);

                        setData();
                    }
                    else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    private void setData()
    {
        String hobbies = sessionManager.getData(SessionManager.KEY_HOBBY);
        if (!hobbies.equalsIgnoreCase("null")) {

            String[] items = hobbies.split(",");
            for (String item : items)
            {
                System.out.println("item = " + item);

                if (item.equalsIgnoreCase("Singing"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH0, String.valueOf(1));
                }
                else if (item.equalsIgnoreCase("Dancing"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH1, String.valueOf(3));
                }
                else if (item.equalsIgnoreCase("Swiming"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH2, String.valueOf(5));

                }else if (item.equalsIgnoreCase("Riding"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH3, String.valueOf(7));

                }else if (item.equalsIgnoreCase("Travelling"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH4, String.valueOf(9));

                }else if (item.equalsIgnoreCase("Cooking"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH5, String.valueOf(11));

                }else if (item.equalsIgnoreCase("Reading"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH6, String.valueOf(13));
                }
                else if (item.equalsIgnoreCase("Movie"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH7, String.valueOf(15));
                }
                else if (item.equalsIgnoreCase("Acting"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH8, String.valueOf(17));

                }else if (item.equalsIgnoreCase("Adventure"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH9, String.valueOf(19));

                }else if (item.equalsIgnoreCase("Modeling"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH10, String.valueOf(21));

                }else if (item.equalsIgnoreCase("Outing"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH11, String.valueOf(23));

                }else if (item.equalsIgnoreCase("Painting"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH12, String.valueOf(25));
                }
                else if (item.equalsIgnoreCase("Dating"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH13, String.valueOf(27));
                }
                else if (item.equalsIgnoreCase("Yoga"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH14, String.valueOf(29));
                }
                else if (item.equalsIgnoreCase("Weightlifting"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH15, String.valueOf(31));
                }
                else if (item.equalsIgnoreCase("Sports"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH16, String.valueOf(33));
                }
                else if (item.equalsIgnoreCase("Shopping"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH17, String.valueOf(35));
                }

            }

           // String str_position=sessionManager.getData(SessionManager.KEY_POSITION_HOBBIES);
            //System.out.println("Str position &&&" + str_position);


         // Clicked Position
         // if(str_position!=null) {
            // int in_str_pos = Integer.parseInt(str_position);
            //System.out.println("Str position &&&" + in_str_pos);

            String str_position0 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH0);
            // After Click Position

            if(str_position0!=null) {
                System.out.println("Str position0 &&&" + str_position0);


                int in_str_pos0 = Integer.parseInt(str_position0);

                if (in_str_pos0 == 1) {
                    imageId[0] = R.drawable.singing;
                    previousSelectedPosition0 = 1;
                    Singing="Singing, ";
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }

                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }


            }
            String str_position1 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH1);
            if(str_position1!=null) {

                System.out.println("Str position1 &&&" + str_position1);
                int in_str_pos1 = Integer.parseInt(str_position1);

                if (in_str_pos1 == 3) {
                    imageId[1] = R.drawable.dance;
                    previousSelectedPosition1 = 3;
                    Dancing="Dancing, ";

                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }

                else
                {
                    CustomGridView adapter = new CustomGridView(HobbiesActivity.this, web, imageId);
                    grid.setAdapter(adapter);
                }

            }
            String str_position2 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH2);
            if(str_position2!=null) {
                System.out.println("Str position2 &&&" + str_position2);


                int in_str_pos2 = Integer.parseInt(str_position2);

                if (in_str_pos2 == 5) {
                    imageId[2] = R.drawable.swimming;
                    previousSelectedPosition2 = 5;
                    Swiming="Swiming, ";

                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }

                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }
            String str_position3 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH3);
            if(str_position3!=null) {
                System.out.println("Str position3 &&&" + str_position3);
                int in_str_pos3 = Integer.parseInt(str_position3);
                if (in_str_pos3 == 7) {
                    imageId[3] = R.drawable.riding;
                    Riding="Riding, ";

                    previousSelectedPosition3 = 7;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }
            }
            String str_position4 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH4);
            if(str_position4!=null) {
                System.out.println("Str position4 &&&" + str_position4);
                int in_str_pos4 = Integer.parseInt(str_position4);
                if (in_str_pos4 == 9) {
                    imageId[4] = R.drawable.traveling;
                    Travelling="Travelling, ";

                    previousSelectedPosition4 = 9;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }
            }
            String str_position5 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH5);
            if(str_position5!=null) {
                System.out.println("Str position5 &&&" + str_position5);
                int in_str_pos5 = Integer.parseInt(str_position5);
                if (in_str_pos5 == 11) {
                    imageId[5] = R.drawable.cook;
                    Cooking="Cooking, ";

                    previousSelectedPosition5 = 11;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }
            }
            String str_position6 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH6);
            if(str_position6!=null) {
                System.out.println("Str position6 &&&" + str_position6);
                int in_str_pos6 = Integer.parseInt(str_position6);
                if (in_str_pos6 == 13) {
                    imageId[6] = R.drawable.reading;
                    Reading="Reading, ";

                    previousSelectedPosition6 = 13;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }
            }
            String str_position7 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH7);
            if(str_position7!=null) {
                System.out.println("Str position7 &&&" + str_position7);
                int in_str_pos7 = Integer.parseInt(str_position7);
                if (in_str_pos7 == 15) {
                    imageId[7] = R.drawable.movie;
                    movie="movie, ";

                    previousSelectedPosition7 = 15;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }


                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }

            String str_position8 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH8);
            if(str_position8!=null) {
                System.out.println("Str position7 &&&" + str_position8);
                int in_str_pos8 = Integer.parseInt(str_position8);
                if (in_str_pos8 == 17) {
                    imageId[8] = R.drawable.acting;
                    acting="acting, ";

                    previousSelectedPosition8 = 17;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }


                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }

            String str_position9 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH9);
            if(str_position9!=null) {
                System.out.println("Str position7 &&&" + str_position9);
                int in_str_pos7 = Integer.parseInt(str_position9);
                if (in_str_pos7 == 19) {
                    imageId[9] = R.drawable.movie;
                    adventure="adventure, ";

                    previousSelectedPosition9 = 19;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }


                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }

            String str_position10 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH10);
            if(str_position10!=null) {
                System.out.println("Str position10 &&&" + str_position10);
                int in_str_pos10 = Integer.parseInt(str_position10);
                if (in_str_pos10 == 19) {
                    imageId[10] = R.drawable.modeling_copy;
                    modeling="modeling, ";

                    previousSelectedPosition10 = 21;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }


                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }

            String str_position11 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH11);
            if(str_position11!=null) {
                System.out.println("Str position7 &&&" + str_position11);
                int in_str_pos11 = Integer.parseInt(str_position11);
                if (in_str_pos11 == 23) {
                    imageId[11] = R.drawable.outing;
                    outing="outing, ";

                    previousSelectedPosition11 = 23;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }
                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }

            String str_position12 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH12);
            if(str_position12!=null) {
                System.out.println("Str position7 &&&" + str_position12);
                int in_str_pos12 = Integer.parseInt(str_position12);
                if (in_str_pos12 == 25) {
                    imageId[12] = R.drawable.painting;
                    painting="painting, ";

                    previousSelectedPosition12 = 25;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }


                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }

            String str_position13 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH13);
            if(str_position13!=null) {
                System.out.println("str_position13 &&&" + str_position13);
                int in_str_pos13 = Integer.parseInt(str_position13);
                if (in_str_pos13 == 27) {
                    imageId[13] = R.drawable.datingg;
                    dating="dating, ";

                    previousSelectedPosition13 = 27;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }


                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }

            String str_position14 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH14);
            if(str_position14!=null) {
                System.out.println("str_position13 &&&" + str_position14);
                int in_str_pos14 = Integer.parseInt(str_position14);
                if (in_str_pos14 == 29) {
                    imageId[14] = R.drawable.yoga;
                    yoga="yoga, ";

                    previousSelectedPosition14 = 29;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }


                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }


            String str_position15 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH15);
            if(str_position15!=null) {
                System.out.println("str_position13 &&&" + str_position15);
                int in_str_pos15 = Integer.parseInt(str_position15);
                if (in_str_pos15 == 31) {
                    imageId[15] = R.drawable.weightlifting;
                    weightlifting="weightlifting, ";

                    previousSelectedPosition15 = 31;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }


                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }

            String str_position16 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH16);
            if(str_position16!=null) {
                System.out.println("str_position16 &&&" + str_position16);
                int in_str_pos16 = Integer.parseInt(str_position16);
                if (in_str_pos16 == 33) {
                    imageId[16] = R.drawable.sports;
                    sports="sports, ";

                    previousSelectedPosition16 = 33;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }


                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }

            String str_position17 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH17);
            if(str_position17!=null) {
                System.out.println("str_position17 &&&" + str_position17);
                int in_str_pos17 = Integer.parseInt(str_position17);
                if (in_str_pos17 == 27) {
                    imageId[17] = R.drawable.shopping_cart;
                    shopping="shopping.";

                    previousSelectedPosition17 = 35;
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,Upload2.class));

                }
                else
                {
                    CustomGridView adapter = new CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }
            }


            else
            {
                CustomGridView adapter = new CustomGridView(context, web, imageId);
                grid.setAdapter(adapter);
            }



        
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);
            CustomGridView adapter = new CustomGridView(context, web, imageId);
            grid.setAdapter(adapter);
        }

        textView.setText("My Hobbies "+ Singing +""+Dancing+""+Swiming+""+Riding+""+Travelling+""+Cooking
                +""+Reading+""+movie+""+acting+""+adventure+""+modeling+""+outing+""+painting+""+dating+""+
                yoga+""+weightlifting+""+sports+""+shopping);

    }



        private void click() {

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                ImageView imageView = (ImageView)view.findViewById(R.id.grid_image);
                textView.setTextColor(getResources().getColor(R.color.grey_light));

                position2=position;
                if (position==0)
                {
                    position2=position;

                    if (previousSelectedPosition0 == 0)
                    {
                        imageView.setImageResource(R.drawable.singing);
                        previousSelectedPosition0=1;
                        Singing="Singing, ";

                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.singing_gray);
                        previousSelectedPosition0=0;
                        Singing="";

                    }
                }
                else if (position==1)
                {
                    position2=position;


                    if (previousSelectedPosition1==2)
                    {
                        imageView.setImageResource(R.drawable.dance);
                        previousSelectedPosition1=3;
                        Dancing="Dancing, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.dance_gray);
                        previousSelectedPosition1=2;
                        Dancing="";
                    }

                }
                else if (position==2)
                {
                    position2=position;

                    if (previousSelectedPosition2==4)
                    {
                        imageView.setImageResource(R.drawable.swimming);
                        previousSelectedPosition2=5;
                        Swiming="Swiming, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.swimming_gray);
                        previousSelectedPosition2=4;
                        Swiming="";

                    }


                }
                else if (position==3)
                {
                    position2=position;


                    if (previousSelectedPosition3==6)
                    {
                        imageView.setImageResource(R.drawable.riding);
                        previousSelectedPosition3=7;
                        Riding="Riding, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.riding_gray);
                        previousSelectedPosition3=6;
                        Riding="";
                    }


                }
                else if (position==4)
                {
                    position2=position;


                    if (previousSelectedPosition4==8)
                    {
                        imageView.setImageResource(R.drawable.traveling);
                        previousSelectedPosition4=9;
                        Travelling="Travelling, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.traveling_gray);
                        previousSelectedPosition4=8;
                        Travelling="";

                    }


                }
                else if (position==5)
                {
                    position2=position;


                    if (previousSelectedPosition5==10)
                    {
                        imageView.setImageResource(R.drawable.cook);
                        previousSelectedPosition5=11;
                        Cooking="Cooking, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.cook_gray);
                        previousSelectedPosition5=10;
                        Cooking="";
                    }

                }
                else if (position==6)
                {

                    position2=position;

                    if (previousSelectedPosition6==12)
                    {
                        imageView.setImageResource(R.drawable.reading);
                        previousSelectedPosition6=13;
                        Reading="Reading, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.reading_gray);
                        previousSelectedPosition6=12;
                        Reading="";
                    }
                }
                else if (position==7)
                {
                    position2=position;

                    if (previousSelectedPosition7==14)
                    {
                        imageView.setImageResource(R.drawable.movie);
                        previousSelectedPosition7=15;
                        movie="Movie, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.movie_gray);
                        previousSelectedPosition7=14;
                        movie="";
                    }

                }
                else if (position==8)
                {
                    position2=position;

                    if (previousSelectedPosition8==16)
                    {
                        imageView.setImageResource(R.drawable.acting);
                        previousSelectedPosition8=17;
                        acting="Acting, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.acting_gray);
                        previousSelectedPosition8=16;
                        acting="";
                    }

                }
                else if (position==9)
                {
                    position2=position;

                    if (previousSelectedPosition9==18)
                    {
                        imageView.setImageResource(R.drawable.adventure);
                        previousSelectedPosition9=19;
                        adventure="Adventure, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.adventure_gray);
                        previousSelectedPosition9=18;
                        adventure="";
                    }

                } else if (position==10)
                {
                    position2=position;

                    if (previousSelectedPosition10==20)
                    {
                        imageView.setImageResource(R.drawable.modeling_copy);
                        previousSelectedPosition10=21;
                        modeling="Modeling, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.modeling_gray);
                        previousSelectedPosition10=20;
                        modeling="";
                    }
                }
                else if (position==11)
                {
                    position2=position;

                    if (previousSelectedPosition11==22)
                    {
                        imageView.setImageResource(R.drawable.outing);
                        previousSelectedPosition11=23;
                        outing="Outing, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.outing_gray);
                        previousSelectedPosition11=22;
                        outing="";
                    }

                }
                else if (position==12)
                {
                    position2=position;

                    if (previousSelectedPosition12==24)
                    {
                        imageView.setImageResource(R.drawable.painting);
                        previousSelectedPosition12=25;
                        painting="Painting, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.painting_gray);
                        previousSelectedPosition12=24;
                        painting="";
                    }

                }
                else if (position==13)
                {
                    position2=position;

                    if (previousSelectedPosition13==26)
                    {
                        imageView.setImageResource(R.drawable.datingg);
                        previousSelectedPosition13=27;
                        dating="Dating, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.dating_gray);
                        previousSelectedPosition13=26;
                        dating="";
                    }

                }
                else if (position==14)
                {
                    position2=position;

                    if (previousSelectedPosition14==28)
                    {
                        imageView.setImageResource(R.drawable.yoga);
                        previousSelectedPosition14=29;
                        yoga="Yoga, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.yoga_gray);
                        previousSelectedPosition14=28;
                        yoga="";
                    }

                }
                else if (position==15)
                {
                    position2=position;

                    if (previousSelectedPosition15==30)
                    {
                        imageView.setImageResource(R.drawable.weightlifting);
                        previousSelectedPosition15=31;
                        weightlifting="Weightlifting, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.weightlifting_gray);
                        previousSelectedPosition15=30;
                        weightlifting="";
                    }

                }
                else if (position==16)
                {
                    position2=position;

                    if (previousSelectedPosition16==32)
                    {
                        imageView.setImageResource(R.drawable.sports);
                        previousSelectedPosition16=33;
                        sports="Sports, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.sports_gray);
                        previousSelectedPosition16=32;
                        sports="";
                    }

                }
                else
                {
                    position2=17;

                    if (previousSelectedPosition17==34)
                    {
                        imageView.setImageResource(R.drawable.shopping_cart);
                        previousSelectedPosition17=35;
                        shopping="Shopping.";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.shopping_gray);
                        previousSelectedPosition17=34;
                        shopping="";
                    }

                }


                textView.setText("My Hobbies "+Singing+""+Dancing+""+Swiming+""+Riding+""+Travelling+""+Cooking
                        +""+Reading+""+movie+""+acting+""+adventure+""+modeling+""+outing+""+painting+""+dating+""+
                        yoga+""+weightlifting+""+sports+""+shopping);




               /* ImageView previousSelectedView = (ImageView) grid.getChildAt(previousSelectedPosition);


                if (previousSelectedPosition !=-1)
                {

                    if (previousSelectedPosition==0)
                    {
                        previousSelectedView.setImageResource(R.drawable.cigarette);

                    }
                    else if (previousSelectedPosition==1)
                    {
                        previousSelectedView.setImageResource(R.drawable.drinking);

                    }
                    else if (previousSelectedPosition==2)
                    {
                        previousSelectedView.setImageResource(R.drawable.veg_grey);

                    }
                    else if (previousSelectedPosition==3)
                    {
                        imageView.setImageResource(R.drawable.non_veg_grey);

                    }
                    else if (previousSelectedPosition==4)
                    {
                        imageView.setImageResource(R.drawable.healthy_grey);

                    }
                    else if (previousSelectedPosition==5)
                    {
                        imageView.setImageResource(R.drawable.traditional_grey);

                    }
                    else if (previousSelectedPosition==6)
                    {
                        imageView.setImageResource(R.drawable.organised_grey);

                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.discipline_grey);

                    }

                }
                previousSelectedPosition = position;
*/
            }
        });

        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Singing=="" && Dancing=="" && Swiming=="" && Riding=="" && Travelling=="" && Cooking==""
                        && Reading=="" && movie=="" && acting=="" && adventure==""
                        && modeling=="" && outing=="" && painting=="" && yoga=="" && weightlifting==""
                        && sports=="" && shopping=="")
                {

                    textView.setText("My Hobbies..");
                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "You have to choose one of these Hobbies.", Snackbar.LENGTH_LONG);

                    snackbar.show();
                    //textView.setText("You have to choose one of these Lifestyle?");
                    // textView.setTextColor(Color.RED);

                }
                else {

                    String hobbies = Singing+Dancing+Swiming+Riding+Travelling+Cooking+Reading+movie+acting+painting+yoga
                            +weightlifting+sports+shopping;
                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, hobbies, Snackbar.LENGTH_LONG);

                    //snackbar.show();

                    sessionManager.setData(SessionManager.KEY_HOBBY,hobbies);
                    sessionManager.setData(SessionManager.KEY_POSITION_HOBBIES,position2+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH0,previousSelectedPosition0+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH1,previousSelectedPosition1+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH2,previousSelectedPosition2+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH3,previousSelectedPosition3+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH4,previousSelectedPosition4+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH5,previousSelectedPosition5+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH6,previousSelectedPosition6+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH7,previousSelectedPosition7+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH8,previousSelectedPosition8+"");

                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH9,previousSelectedPosition9+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH10,previousSelectedPosition10+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH11,previousSelectedPosition11+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH12,previousSelectedPosition12+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH13,previousSelectedPosition13+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH14,previousSelectedPosition14+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH15,previousSelectedPosition15+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH16,previousSelectedPosition16+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionH17,previousSelectedPosition17+"");

                    update_Hobbies();



                    //sessionManager.setData(SessionManager.KEY_LIFE_STYLE,lifestyle);
                }
            }
        });

        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void update_Hobbies() {
        final String hobbies = sessionManager.getData(SessionManager.KEY_HOBBY);
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_HOBBIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!","1235"+response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success"))
                    {
                        sessionManager.hobbies(hobbies);
                        startActivity(new Intent(context,Upload2.class));

                    }
                    else
                    {
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
                dialog.dismiss();
                Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("hobbies_interest",hobbies);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    @Override
    public void onBackPressed() {

        String hobbies = Singing+Dancing+Swiming+Riding+Travelling+Cooking+Reading+movie+acting+adventure+modeling+outing+painting
                +dating+yoga+weightlifting+sports+shopping;
        sessionManager.setData(SessionManager.KEY_HOBBY,hobbies);

        sessionManager.setData(SessionManager.KEY_POSITION_HOBBIES,position2+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH0,previousSelectedPosition0+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH1,previousSelectedPosition1+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH2,previousSelectedPosition2+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH3,previousSelectedPosition3+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH4,previousSelectedPosition4+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH5,previousSelectedPosition5+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH6,previousSelectedPosition6+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH7,previousSelectedPosition7+"");

        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH8,previousSelectedPosition8+"");

        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH9,previousSelectedPosition9+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH10,previousSelectedPosition10+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH11,previousSelectedPosition11+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH12,previousSelectedPosition12+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH13,previousSelectedPosition13+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH14,previousSelectedPosition14+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH15,previousSelectedPosition15+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH16,previousSelectedPosition16+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH17,previousSelectedPosition17+"");


        startActivity(new Intent(context,LifeStyleActivity.class));
    }

    private class CustomGridView extends BaseAdapter {
        private Context mContext;
        private final String[] web;
        private final int[] Imageid;


        public CustomGridView(Context context, String[] web, int[] imageId) {
            mContext = context;
            this.Imageid = imageId;
            this.web = web;
        }

        @Override
        public int getCount() {
            return web.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // view = new View(mContext);
            view = inflater.inflate(R.layout.adapter_hobbies_gridview, null);
            TextView textView = (TextView) view.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) view.findViewById(R.id.grid_image);
            textView.setText(web[position]);
            imageView.setImageResource(Imageid[position]);

            return view;


        }
    }

}
