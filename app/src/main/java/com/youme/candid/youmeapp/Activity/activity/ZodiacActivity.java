package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.youme.candid.youmeapp.Activity.activity.edit_profile_activities.Edit_User_ZodiacSign;
import com.youme.candid.youmeapp.Activity.adapter.CustomGrid;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ZodiacActivity extends AppCompatActivity {


    private int previousSelectedPosition =0;
    int postion;
    GridView grid;
    RelativeLayout relativeLayout;
    TextView textView;
    String  str_grey;
    ImageView next,previous;
    ImageView imageView ;
    String zodiac;
     CustomGrid adapter;
    String[] web = {
            "Aries",
            "Taurus",
            "Gemini",
            "Cancer",
            "Leo",
            "Virgo",
            "Libra",
            "Scorpio",
            "Sagittarius",
            "Capricorn",
            "Aquarius",
            "Pieces",


    } ;
    int[] imageId = {
            R.drawable.aries_nm,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_gray_icon,

    };
    int[] imageid0={
            R.drawable.aries_nm,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_gray_icon,
    };

    int[] imageid1={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_gray_icon,
    };

    int[] imageid2={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_gray_icon,
    };


    int[] imageid3={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_gray_icon,
    };
    int[] imageid4={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_gray_icon,
    };

    int[] imageid5={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_gray_icon,
    };

    int[] imageid6={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_gray_icon,
    };
    int[] imageid7={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_gray_icon,
    };
    int[] imageid8={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_gray_icon,
    };
    int[] imageid9={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_gray_icon,
    };
    int[] imageid10={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquarius_icon,
            R.drawable.pisces_gray_icon,
    };
    int[] imageid11={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_gray_icon,
            R.drawable.virgo_gray_icon,
            R.drawable.libra_gray_icon,
            R.drawable.scorpio_gray_icon,
            R.drawable.sagittarius_gray_icon,
            R.drawable.capricorn_gray_icon,
            R.drawable.aquaius_gray_icon,
            R.drawable.pisces_icon,
    };

    SessionManager sessionManager;
    Context context;
    ConnectionDetector connectionDetector;
    FrameLayout frameLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zodiac);

        // Get the widgets reference from XML layout
        initView();

        getData();


        /* boolean register = sessionManager.isZodiac();
        {
            if (register)
            {
                startActivity(new Intent(context,LifeStyleActivity.class));
            }
        }*/



        // for all the click events
        click();
    }

    private void initView() {

        context = this;
        connectionDetector = new ConnectionDetector();
        sessionManager = new SessionManager(context);

        grid = (GridView) findViewById(R.id.gv);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_lay);
        previous = (ImageView) findViewById(R.id.zodiac_previous_btn);
        next = (ImageView) findViewById(R.id.zodiac_next_btn);
        textView = (TextView) findViewById(R.id.zodiac);

        frameLayout = (FrameLayout)findViewById(R.id.zodiac_loading_frame);
        progressBar = (ProgressBar)findViewById(R.id.zodiac_pic_progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void getData()
    {
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USER_ZODIAC, new Response.Listener<String>() {
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
                        String zodiac_sign = jsonObject.getString("result");
                        Log.i("response..!", "res zodiac" + zodiac_sign);
                        sessionManager.setData(SessionManager.KEY_ZODIAC,zodiac_sign);
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
        String str_zodiac = sessionManager.getData(SessionManager.KEY_ZODIAC);
        Log.i("str zodiasc..!", "zodiac" + str_zodiac);

        if (!str_zodiac.equalsIgnoreCase("null"))
        {
        String str_previousposition = sessionManager.getData(SessionManager.KEY_PREVIOUS_POSITION);
        if (str_previousposition != null)
        {
            previousSelectedPosition = Integer.parseInt(str_previousposition);
        }
        System.out.println("Zodia is###" + str_zodiac);
        System.out.println("Zodia Position is###" + previousSelectedPosition);

        if (!str_zodiac.equalsIgnoreCase("null")) {

            if (str_zodiac.equalsIgnoreCase("Aries")) {
                adapter = new CustomGrid(ZodiacActivity.this, web, imageid0);
                textView.setText("I am Aries.");
                grid.setAdapter(adapter);
                startActivity(new Intent(context,LifeStyleActivity.class));

            } else if (str_zodiac.equalsIgnoreCase("Taurus")) {
                adapter = new CustomGrid(ZodiacActivity.this, web, imageid1);
                grid.setAdapter(adapter);
                textView.setText("I am Taurus.");
                startActivity(new Intent(context,LifeStyleActivity.class));

            } else if (str_zodiac.equalsIgnoreCase("Gemini")) {
                textView.setText("I am Gemini.");
                adapter = new CustomGrid(ZodiacActivity.this, web, imageid2);
                grid.setAdapter(adapter);
                startActivity(new Intent(context,LifeStyleActivity.class));


            } else if (str_zodiac.equalsIgnoreCase("Cancer")) {
                textView.setText("I am Cancer.");

                adapter = new CustomGrid(ZodiacActivity.this, web, imageid3);
                grid.setAdapter(adapter);
                startActivity(new Intent(context,LifeStyleActivity.class));

            } else if (str_zodiac.equalsIgnoreCase("Leo")) {
                textView.setText("I am Leo.");

                adapter = new CustomGrid(ZodiacActivity.this, web, imageid4);
                grid.setAdapter(adapter);
                startActivity(new Intent(context,LifeStyleActivity.class));

            } else if (str_zodiac.equalsIgnoreCase("Virgo")) {
                textView.setText("I am Virgo.");

                adapter = new CustomGrid(ZodiacActivity.this, web, imageid5);
                grid.setAdapter(adapter);
                startActivity(new Intent(context,LifeStyleActivity.class));

            } else if (str_zodiac.equalsIgnoreCase("Libra")) {
                textView.setText("I am Libra.");

                adapter = new CustomGrid(ZodiacActivity.this, web, imageid6);
                grid.setAdapter(adapter);
                startActivity(new Intent(context,LifeStyleActivity.class));

            } else if (str_zodiac.equalsIgnoreCase("Scorpio")) {
                textView.setText("I am Scorpio.");
                adapter = new CustomGrid(ZodiacActivity.this, web, imageid7);
                grid.setAdapter(adapter);
                startActivity(new Intent(context,LifeStyleActivity.class));

            } else if (str_zodiac.equalsIgnoreCase("Sagittarius")) {
                textView.setText("I am Scorpio.");

                adapter = new CustomGrid(ZodiacActivity.this, web, imageid8);
                grid.setAdapter(adapter);
                startActivity(new Intent(context,LifeStyleActivity.class));

            } else if (str_zodiac.equalsIgnoreCase("Capricorn")) {
                textView.setText("I am Capricorn.");

                adapter = new CustomGrid(ZodiacActivity.this, web, imageid9);
                grid.setAdapter(adapter);
                startActivity(new Intent(context,LifeStyleActivity.class));

            } else if (str_zodiac.equalsIgnoreCase("Aquarius")) {
                textView.setText("I am Aquarius.");

                adapter = new CustomGrid(ZodiacActivity.this, web, imageid10);
                grid.setAdapter(adapter);
                startActivity(new Intent(context,LifeStyleActivity.class));

            } else if (str_zodiac.equalsIgnoreCase("Pieces")) {
                textView.setText("I am Pieces.");

                adapter = new CustomGrid(ZodiacActivity.this, web, imageid11);
                grid.setAdapter(adapter);
                startActivity(new Intent(context,LifeStyleActivity.class));

            } else {
                adapter = new CustomGrid(ZodiacActivity.this, web, imageId);
                grid.setAdapter(adapter);
            }
        } else {
            frameLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            adapter = new CustomGrid(ZodiacActivity.this, web, imageId);
            grid.setAdapter(adapter);
        }
    }
        else
        {
            frameLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            zodiac = "Aries";
            sessionManager.setData(SessionManager.KEY_ZODIAC,zodiac);
            adapter = new CustomGrid(ZodiacActivity.this, web, imageid0);
            textView.setText("I am Aries.");
            grid.setAdapter(adapter);

        }

    }



    private void click() {

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                zodiac = web[+ position];
                sessionManager.setData(SessionManager.KEY_ZODIAC,zodiac);

                textView.setText("I am "+web[+ position]+".");
                imageView = (ImageView)view.findViewById(R.id.grid_image);

                if (position==0)

                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.aries_nm);
                }
                else if (position==1)
                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.taurus_icon);

                }
                else if (position==2)
                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.gemini_icon);

                }
                else if (position==3)
                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.cancer_icon);

                }
                else if (position==4)
                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.leo_icon);

                }
                else if (position==5)
                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.virgo_icon);

                }
                else if (position==6)
                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.libra_icon);

                }
                else if (position==7)
                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.scorpio_icon);

                }
                else if (position==8)
                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.sagittarius_icon);

                }
                else if (position==9)
                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.capricorn_icon);

                }
                else if (position==10)
                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.aquarius_icon);

                }

                else
                {
                    sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                    imageView.setImageResource(R.drawable.pisces_icon);
                }

                // Get the last selected View from GridView
                imageView = (ImageView)grid.getChildAt(previousSelectedPosition);
                // If there is a previous selected view exists
                if (previousSelectedPosition != position)
                {

                    // Set the last selected View to deselect
                    imageView.setSelected(false);

                    if (previousSelectedPosition==0)
                    {
                        // Set the last selected View background color as deselected item
                        imageView.setImageResource(R.drawable.aries_gray_icon);

                    }

                    else if (previousSelectedPosition==1)
                    {
                        imageView.setImageResource(R.drawable.taurus_gray_icon);
                    }
                    else if (previousSelectedPosition==2)
                    {
                        imageView.setImageResource(R.drawable.gemini_gray_icon);

                    }
                    else if (previousSelectedPosition==3)
                    {
                        imageView.setImageResource(R.drawable.cancer_gray_icon);

                    }
                    else if (previousSelectedPosition==4)
                    {
                        imageView.setImageResource(R.drawable.leo_gray_icon);

                    }
                    else if (previousSelectedPosition==5)
                    {
                        imageView.setImageResource(R.drawable.virgo_gray_icon);

                    }
                    else if (previousSelectedPosition==6)
                    {
                        imageView.setImageResource(R.drawable.libra_gray_icon);

                    }
                    else if (previousSelectedPosition==7)
                    {
                        imageView.setImageResource(R.drawable.scorpio_gray_icon);

                    }
                    else if (previousSelectedPosition==8)
                    {
                        imageView.setImageResource(R.drawable.sagittarius_gray_icon);

                    }
                    else if (previousSelectedPosition==9)
                    {
                        imageView.setImageResource(R.drawable.capricorn_gray_icon);

                    }
                    else if (previousSelectedPosition==10)
                    {
                        imageView.setImageResource(R.drawable.aquaius_gray_icon);

                    }

                    else
                    {
                        imageView.setImageResource(R.drawable.pisces_gray_icon);

                    }

                }

                // Set the current selected view position as previousSelectedPosition
                previousSelectedPosition = position;


            }
        });




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update_zodiac_sign();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ZodiacActivity.this,GenderActivity.class));
            }
        });
    }

    private void update_zodiac_sign()
    {

        final String zodiac_sign = sessionManager.getData(SessionManager.KEY_ZODIAC);
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_ZODIAC, new Response.Listener<String>() {
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
                    Log.i("response..!","key zodiac"+zodiac);

                    if (Status.equals("success"))
                    {
                        if (zodiac!=null)
                        {
                            sessionManager.setData(SessionManager.KEY_ZODIAC,zodiac);
                            sessionManager.setData(SessionManager.KEY_PREVIOUS_POSITION,previousSelectedPosition+"");
                            sessionManager.zodiacsign(zodiac);
                        }
                        Intent intent = new Intent(context,LifeStyleActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(context, "Select Zodiac", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                Log.i("response..!","key zodiac"+ zodiac_sign);
                params.put("zodiac_sign",zodiac_sign);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }





    @Override
    protected void onResume() {
        super.onResume();
       // loadData();

    }

    private void loadData() {

        String zodic = sessionManager.getData(SessionManager.KEY_ZODIAC);
       // Toast.makeText(context, zodic, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }

     /*// Method for converting DP value to pixels
    public static int getPixelsFromDPs(Activity activity, int dps){
        Resources r = activity.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }*/


    public class CustomGrid extends BaseAdapter {
        private Context mContext;
        private final String[] web;
        private final int[] Imageid;

        public CustomGrid(Context c, String[] web, int[] Imageid) {
            mContext = c;
            this.Imageid = Imageid;
            this.web = web;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return web.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        public class Holder
        {
            TextView tv;
            ImageView img;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            // TODO Auto-generated method stub
           Holder holder=new Holder();

            View view;

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                view = inflater.inflate(R.layout.adapter_zodiac_gridview, null);

                holder.tv = (TextView) view.findViewById(R.id.grid_text);
                holder.img = (ImageView) view.findViewById(R.id.grid_image);


                holder.img.setImageResource(Imageid[position]);
                holder.tv.setText(web[position]);




            return holder.img;
        }
    }
}


/*
// Populate a List from Array elements

    final ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(plants));

                // Data bind GridView with ArrayAdapter (String Array elements)
                gv.setAdapter(new ArrayAdapter<String>(
        this, android.R.layout.simple_list_item_1, arrayList){
public View getView(int position, View convertView, ViewGroup parent) {

        // Return the GridView current item as a View
        View view = super.getView(position,convertView,parent);

        // Convert the view as a TextView widget
        TextView tv = (TextView) view;

        // set the TextView text color (GridView item color)
        tv.setTextColor(Color.DKGRAY);

        // Set the layout parameters for TextView widget
        RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        tv.setLayoutParams(lp);

        // Get the TextView LayoutParams
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();

        // Set the width of TextView widget (item of GridView)
        params.width = getPixelsFromDPs(ZodiacActivity.this,168);

        // Set the TextView layout parameters
        tv.setLayoutParams(params);

        // Display TextView text in center position
        tv.setGravity(Gravity.CENTER);

        // Set the TextView text font family and text size
        tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);

        // Set the TextView text (GridView item text)
        //tv.setText((Integer) arrayList.get(position));

        // Set the TextView background color
        tv.setBackgroundColor(Color.parseColor("#FFFF4F25"));

        // Return the TextView widget as GridView item
        return tv;
        }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Get the selected item text
        String selectedItem = parent.getItemAtPosition(position).toString();

        // Display the selected item text to app interface
        tv_message.setText("Selected item : " + selectedItem);

        // Get the current selected view as a TextView
        TextView tv = (TextView) view;

        // Set the current selected item background color
        tv.setBackgroundColor(Color.parseColor("#FF9AD082"));

        // Set the current selected item text color
        tv.setTextColor(Color.BLUE);

        // Get the last selected View from GridView
        TextView previousSelectedView = (TextView) gv.getChildAt(previousSelectedPosition);

        // If there is a previous selected view exists
        if (previousSelectedPosition != -1)
        {
        // Set the last selected View to deselect
        previousSelectedView.setSelected(false);

        // Set the last selected View background color as deselected item
        previousSelectedView.setBackgroundColor(Color.parseColor("#FFFF4F25"));

        // Set the last selected View text color as deselected item
        previousSelectedView.setTextColor(Color.DKGRAY);
        }

        // Set the current selected view position as previousSelectedPosition
        previousSelectedPosition = position;
        }
        });
*/
