package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.youme.candid.youmeapp.Activity.activity.Test_activities.Test_one;
import com.youme.candid.youmeapp.Activity.adapter.CustomGrid;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class LifeStyleActivity extends AppCompatActivity {

    int previousSelectedPosition0 =-1;
    int previousSelectedPosition1 =2;
    int previousSelectedPosition2 =4;
    int previousSelectedPosition3 =6;
    int previousSelectedPosition4 =8;
    int previousSelectedPosition5= 10;
    int previousSelectedPosition6 =12;
    int previousSelectedPosition7 =14;

    String str_veg="",
            str_smoke="",
            str_drink="",
            str_non_veg="",
            str_healthy="",
            str_traditional="",
            str_organised="",
            str_discipline="";

    GridView grid;
    RelativeLayout relativeLayout;
    SessionManager sessionManager;
    Context context;
    ImageView  imageView2;
    int position2;
    ConnectionDetector connectionDetector;
    TextView textView;
    ImageView img_next,img_previous;
    String[] web = {
            "Cigarette",
            "Drinking",
            "Non-veg",
            "Veg",
            "Healthy",
            "Traditional",
            "Organised",
            "Discipline",


    } ;
    int[] imageId = new int[8];
    FrameLayout frameLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style);

        // Get the widgets reference from XML layout
        initView();

        getData();
    /*  boolean register = sessionManager.isLifestyle();
        {
            if (register)
            {
                startActivity(new Intent(context,HobbiesActivity.class));
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

        imageId[0]= R.drawable.cigarette_grey;
        imageId[1]= R.drawable.drinking_grey;
        imageId[2]= R.drawable.non_veg_grey;
        imageId[3]= R.drawable.veg_grey;
        imageId[4]= R.drawable.healthy_grey;
        imageId[5]= R.drawable.traditional_grey;
        imageId[6]= R.drawable.organised_grey;
        imageId[7]= R.drawable.discipline_grey;


        grid=(GridView)findViewById(R.id.grid);
        textView = (TextView)findViewById(R.id.lifestyle);
        img_next = (ImageView) findViewById(R.id.lifestyle_next_btn);
        img_previous = (ImageView)findViewById(R.id.lifestyle_previous_btn);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativ_lay);

        frameLayout = (FrameLayout)findViewById(R.id.lifestyle_loading_frame);
        progressBar = (ProgressBar)findViewById(R.id.lifestyle_progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void getData()
    {
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USER_LIFESTYLE, new Response.Listener<String>() {
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
                        String life_style = jsonObject.getString("result");
                        Log.i("response..!", "life style" + life_style);
                        sessionManager.setData(SessionManager.KEY_LIFE_STYLE,life_style);
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
                dialog.dismiss();
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

    private void setData() {

        String str_lifestyle = sessionManager.getData(SessionManager.KEY_LIFE_STYLE);
        Log.i("str lifestyle..!", "lifestyle" + str_lifestyle);

        try{
           /* StringTokenizer st = new StringTokenizer(str_lifestyle, ",");
            String smoker = st.nextToken();
            String drinker = st.nextToken();
            String non_veg = st.nextToken();
            String veg = st.nextToken();
            String healthy = st.nextToken();
            String traditional = st.nextToken();
            String organised = st.nextToken();
//            String dicipline = st.nextToken();*/



        String[] items = str_lifestyle.split(",");
        for (String item : items)
        {

         System.out.println("item = " + item);

        if (item.equalsIgnoreCase("Smoker"))
        {
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition0, String.valueOf(1));
        }
        else if (item.equalsIgnoreCase("Drinker"))
        {
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition1, String.valueOf(3));
        }
        else if (item.equalsIgnoreCase("Non-Vegetarian"))
        {
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition2, String.valueOf(5));

        }else if (item.equalsIgnoreCase("Vegetarian"))
        {
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition3, String.valueOf(7));

        }else if (item.equalsIgnoreCase("Healthy"))
        {
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition4, String.valueOf(9));

        }else if (item.equalsIgnoreCase("Traditional"))
        {
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition5, String.valueOf(11));

        }else if (item.equalsIgnoreCase("Organised"))
        {
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition6, String.valueOf(13));
        }
        else if (item.equalsIgnoreCase("Discipline"))
        {
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition7, String.valueOf(15));
        }

    }
        if (!str_lifestyle.equalsIgnoreCase("null")) {

           // startActivity(new Intent(context,HobbiesActivity.class));
            String str_position = sessionManager.getData(SessionManager.KEY_Position);
            System.out.println("Str position &&&" + str_position);

               // int in_str_pos = Integer.parseInt(str_position);

                String str_position0 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition0);
                // After Click Position
                System.out.println("Str position previous &&&" + str_position0);

                if (str_position0 != null) {
                    System.out.println("Str position0 &&&" + str_position0);


                    int in_str_pos0 = Integer.parseInt(str_position0);

                    if (in_str_pos0 == 1) {
                        imageId[0] = R.drawable.cigarette;
                        previousSelectedPosition0 = 1;
                        str_smoke = "Smoker, ";
                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                        startActivity(new Intent(context,HobbiesActivity.class));

                    }
                    else {
                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                    }


                }//...position 0

                String str_position1 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition1);
                if (str_position1 != null) {

                    System.out.println("Str position1 &&&" + str_position1);
                    int in_str_pos1 = Integer.parseInt(str_position1);

                    if (in_str_pos1 == 3) {
                        imageId[1] = R.drawable.drinking;
                        previousSelectedPosition1 = 3;
                        str_drink = "Drinker, ";

                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                      startActivity(new Intent(context,HobbiesActivity.class));

                    } else {
                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                    }

                }//...position 1


                String str_position2 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition2);
                if (str_position2 != null) {
                    System.out.println("Str position2 &&&" + str_position2);


                    int in_str_pos2 = Integer.parseInt(str_position2);

                    if (in_str_pos2 == 5) {
                        imageId[2] = R.drawable.non_veg;
                        previousSelectedPosition2 = 5;
                        str_non_veg = "Non-Vegetarian, ";

                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                        startActivity(new Intent(context,HobbiesActivity.class));

                    } else {
                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }//...position 2

                String str_position3 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition3);
                if (str_position3 != null) {
                    System.out.println("Str position3 &&&" + str_position3);
                    int in_str_pos3 = Integer.parseInt(str_position3);
                    if (in_str_pos3 == 7) {
                        imageId[3] = R.drawable.veg;
                        str_veg = "Vegetarian, ";

                        previousSelectedPosition3 = 7;
                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                       startActivity(new Intent(context,HobbiesActivity.class));

                    }
                }//...position 3

                String str_position4 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition4);
                if (str_position4 != null) {
                    System.out.println("Str position4 &&&" + str_position4);
                    int in_str_pos4 = Integer.parseInt(str_position4);
                    if (in_str_pos4 == 9) {
                        imageId[4] = R.drawable.healthy;
                        str_healthy = "Healthy, ";

                        previousSelectedPosition4 = 9;
                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                        startActivity(new Intent(context,HobbiesActivity.class));

                    }
                }//...position 4

                String str_position5 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition5);
                if (str_position5 != null) {
                    System.out.println("Str position5 &&&" + str_position5);
                    int in_str_pos5 = Integer.parseInt(str_position5);
                    if (in_str_pos5 == 11) {
                        imageId[5] = R.drawable.traditional;
                        str_traditional = "Traditional, ";

                        previousSelectedPosition5 = 11;
                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                        startActivity(new Intent(context,HobbiesActivity.class));

                    }
                }//..position 5

                String str_position6 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition6);
                if (str_position6 != null) {
                    System.out.println("Str position6 &&&" + str_position6);
                    int in_str_pos6 = Integer.parseInt(str_position6);
                    if (in_str_pos6 == 13) {
                        imageId[6] = R.drawable.organised;
                        str_organised = "Organised, ";

                        previousSelectedPosition6 = 13;
                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                        startActivity(new Intent(context,HobbiesActivity.class));

                    }
                }//...position 6

                String str_position7 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition7);
                if (str_position7 != null) {
                    System.out.println("Str position7 &&&" + str_position7);
                    int in_str_pos7 = Integer.parseInt(str_position7);
                    if (in_str_pos7 == 15) {
                        imageId[7] = R.drawable.discipline;
                        str_discipline = "Discipline.";

                        previousSelectedPosition7 = 15;
                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                        startActivity(new Intent(context,HobbiesActivity.class));

                    } else {
                        CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }
                else {
                    CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
                    grid.setAdapter(adapter);
                }//...positon 7




            textView.setText("I am " + str_smoke + "" + str_drink + "" + str_non_veg + "" + str_veg + "" + str_healthy + "" + str_traditional
                    + "" + str_organised + "" + str_discipline);

        }//....str_lifestyle
        else
        {
            progressBar.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);
            CustomGridView adapter = new CustomGridView(LifeStyleActivity.this, web, imageId);
            grid.setAdapter(adapter);
        }
    }//....try blck
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    private void click() {

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                ImageView imageView = (ImageView)view.findViewById(R.id.grid_image);
                textView.setTextColor(getResources().getColor(R.color.grey_light));
                textView.setGravity(Gravity.CENTER);

                position2=position;
                if (position==0)
                {
                    position2=position;

                    if (previousSelectedPosition0 == 0)
                    {
                        imageView.setImageResource(R.drawable.cigarette);
                        previousSelectedPosition0=1;
                        str_smoke="Smoker, ";

                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.cigarette_grey);
                        previousSelectedPosition0=0;
                        str_smoke="";

                    }
                }
                else if (position==1)
                {
                    position2=position;


                    if (previousSelectedPosition1==2)
                    {
                        imageView.setImageResource(R.drawable.drinking);
                        previousSelectedPosition1=3;
                        str_drink="Drinker, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.drinking_grey);
                        previousSelectedPosition1=2;
                        str_drink="";
                    }

                }
                else if (position==2)
                {
                    position2=position;

                    if (previousSelectedPosition2==4)
                    {
                        imageView.setImageResource(R.drawable.non_veg);
                        previousSelectedPosition2=5;
                        str_non_veg="Non-Vegetarian, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.non_veg_grey);
                        previousSelectedPosition2=4;
                        str_non_veg="";

                    }


                }
                else if (position==3)
                {
                    position2=position;


                    if (previousSelectedPosition3==6)
                    {
                        imageView.setImageResource(R.drawable.veg);
                        previousSelectedPosition3=7;
                        str_veg="Vegetarian, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.veg_grey);
                        previousSelectedPosition3=6;
                        str_veg="";
                    }


                }
                else if (position==4)
                {
                    position2=position;


                    if (previousSelectedPosition4==8)
                    {
                        imageView.setImageResource(R.drawable.healthy);
                        previousSelectedPosition4=9;
                        str_healthy="Healthy, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.healthy_grey);
                        previousSelectedPosition4=8;
                        str_healthy="";

                    }


                }
                else if (position==5)
                {
                    position2=position;


                    if (previousSelectedPosition5==10)
                    {
                        imageView.setImageResource(R.drawable.traditional);
                        previousSelectedPosition5=11;
                        str_traditional="Traditional, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.traditional_grey);
                        previousSelectedPosition5=10;
                        str_traditional="";
                    }

                }
                else if (position==6)
                {

                    position2=position;

                    if (previousSelectedPosition6==12)
                    {
                        imageView.setImageResource(R.drawable.organised);
                        previousSelectedPosition6=13;
                        str_organised="Organized, ";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.organised_grey);
                        previousSelectedPosition6=12;
                        str_organised="";
                    }

                }
                else
                {
                    position2=7;

                    if (previousSelectedPosition7==14)
                    {
                        imageView.setImageResource(R.drawable.discipline);
                        previousSelectedPosition7=15;
                        str_discipline="Disciplined.";
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.discipline_grey);
                        previousSelectedPosition7=14;
                        str_discipline="";
                    }

                }

                textView.setText("I am "+ str_smoke+""+str_drink+""+str_non_veg+""+str_veg+""+str_healthy+""+str_traditional
                        +""+str_organised+""+str_discipline);



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

                if (str_smoke=="" && str_drink=="" && str_veg=="" && str_non_veg=="" && str_healthy=="" && str_traditional==""
                        && str_organised=="" && str_discipline=="")
                {

                    textView.setText("I am...");
                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "You have to choose one of these Lifestyle.", Snackbar.LENGTH_LONG);

                    snackbar.show();
                    //textView.setText("You have to choose one of these Lifestyle?");
                    // textView.setTextColor(Color.RED);

                }
                else {

                    String lifestyle = str_smoke+str_drink+str_non_veg+str_veg+str_healthy+str_traditional+str_organised+str_discipline;

                    sessionManager.setData(SessionManager.KEY_LIFE_STYLE,lifestyle);
                    System.out.println("lifestyleee"+lifestyle);

                    sessionManager.setData(SessionManager.KEY_Position,position2+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition0,previousSelectedPosition0+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition1,previousSelectedPosition1+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition2,previousSelectedPosition2+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition3,previousSelectedPosition3+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition4,previousSelectedPosition4+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition5,previousSelectedPosition5+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition6,previousSelectedPosition6+"");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition7,previousSelectedPosition7+"");

                    update_Life_style();

                }
            }
        });

        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

              //  startActivity(new Intent(LifeStyleActivity.this, ZodiacActivity.class));
            }
        });


    }

    private void update_Life_style() {

        final String life_style = sessionManager.getData(SessionManager.KEY_LIFE_STYLE);
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_LIFESTYLE, new Response.Listener<String>() {
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
                        sessionManager.lifestyle(life_style);
                        Intent intent = new Intent(context,HobbiesActivity.class);
                        startActivity(intent);
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
                params.put("life_style",life_style);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    @Override
    public void onBackPressed() {
        String lifestyle = str_smoke+str_drink+str_non_veg+str_veg+str_healthy+str_traditional+str_organised+str_discipline;
        startActivity(new Intent(LifeStyleActivity.this, HobbiesActivity.class));

        sessionManager.setData(SessionManager.KEY_LIFE_STYLE,lifestyle);

        sessionManager.setData(SessionManager.KEY_Position,position2+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPosition0,previousSelectedPosition0+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPosition1,previousSelectedPosition1+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPosition2,previousSelectedPosition2+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPosition3,previousSelectedPosition3+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPosition4,previousSelectedPosition4+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPosition5,previousSelectedPosition5+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPosition6,previousSelectedPosition6+"");
        sessionManager.setData(SessionManager.KEY_previousSelectedPosition7,previousSelectedPosition7+"");

        startActivity(new Intent(LifeStyleActivity.this,ZodiacActivity.class));
    }

    private class CustomGridView extends BaseAdapter {
        private Context mContext;
        private final String[] web;
        private final int[] Imageid;

        public CustomGridView(LifeStyleActivity lifeStyleActivity, String[] web, int[] imageId)
        {
            mContext = lifeStyleActivity;
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
            view = inflater.inflate(R.layout.adapter_lifestyle_gridview, null);
            TextView textView = (TextView) view.findViewById(R.id.grid_text);
             imageView2 = (ImageView) view.findViewById(R.id.grid_image);
            textView.setText(web[position]);
            imageView2.setImageResource(Imageid[position]);



          /*  String str_position=sessionManager.getData(SessionManager.KEY_Position);


                int in_str_pos = Integer.parseInt(str_position);
                System.out.println("Str position &&&"+in_str_pos);

                String str_position0 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition0);
                System.out.println("Str position0 &&&"+str_position0);

            if(str_position0!=null) {
                int in_str_pos0 = Integer.parseInt(str_position0);
                String str_position1 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition1);
                System.out.println("Str position1 &&&"+str_position1);

                int in_str_pos1 = Integer.parseInt(str_position1);

                for (int i=0;i<Imageid.length;i++)
                {
                    if (in_str_pos0 == 0) {
                        imageView2.setImageResource(R.drawable.cigarette);
                        previousSelectedPosition0 = 1;
                        str_smoke = "Smoker, ";

                    }
                    else {
                        // imageView2.setImageResource(Imageid[in_str_pos]);
                        imageView2.setImageResource(R.drawable.cigarette_grey);
                        previousSelectedPosition0 = 0;
                        str_smoke = "";

                    }

                    if (in_str_pos1 == 0) {
                        imageView2.setImageResource(R.drawable.drinking);
                        previousSelectedPosition1 = 1;
                        str_drink = "Drinker, ";
                    }
                    else {
                        imageView2.setImageResource(R.drawable.cigarette);
                        previousSelectedPosition1 = 0;
                        str_drink = "";
                    }

                }

                }

                else {

                imageView2.setImageResource(Imageid[position]);
                  }



*/
            return view;


            }
    }
}
