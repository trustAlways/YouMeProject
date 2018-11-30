package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.Activity.utils.VolleyMultipartRequest;
import com.youme.candid.youmeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class LookingForActivity extends AppCompatActivity {


    private int previousSelectedPosition =0;
    int postion;
    Bitmap bitmap;
    GridView grid;
    RelativeLayout relativeLayout;
    TextView textView;
    String  str_grey;
    ImageView next,previous;
    ImageView imageView ;
    String Lookin_for;
     CustomGrid adapter;
     FrameLayout frameLayout;
     ProgressBar progressBar;
    String[] web = {
            "Friendship",
            "Dating",
            "Long Term Relationship",
            "Just For Fun",
            "Marriage",

    };
    int[] imageId = {
            R.drawable.friendship,
            R.drawable.dating_gray,
            R.drawable.long_term_gray,
            R.drawable.fun_gray,
            R.drawable.marriage_grey,

    };
    int[] imageid0={
            R.drawable.friendship_gray,
            R.drawable.dating,
            R.drawable.long_term_gray,
            R.drawable.fun_gray,
            R.drawable.marriage_grey,

    };

    int[] imageid1={
            R.drawable.friendship_gray,
            R.drawable.dating_gray,
            R.drawable.long_term,
            R.drawable.fun_gray,
            R.drawable.marriage_grey,

    };

    int[] imageid2={
            R.drawable.friendship_gray,
            R.drawable.dating_gray,
            R.drawable.long_term_gray,
            R.drawable.for_fun,
            R.drawable.marriage_grey,

    };


    int[] imageid3={
            R.drawable.friendship_gray,
            R.drawable.dating_gray,
            R.drawable.long_term_gray,
            R.drawable.fun_gray,
            R.drawable.marriage,
    };

    /*int[] imageid4={
            R.drawable.aries_gray_icon,
            R.drawable.taurus_gray_icon,
            R.drawable.gemini_gray_icon,
            R.drawable.cancer_gray_icon,
            R.drawable.leo_icon,

    };*/

    SessionManager sessionManager;
    Context context;
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_for);

        // Get the widgets reference from XML layout
        initView();

         getData();

       /* boolean register = sessionManager.isLookingfor();
        {
            if (register)
            {
                startActivity(new Intent(context,UserProfileActivity.class));
                finish();
            }
        }*/

       /* final String image = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
        Log.i("response..!","1235"+image);*/

        // for all the click events
        click();
    }

    private void initView() {

        context = this;
        connectionDetector = new ConnectionDetector();
        sessionManager = new SessionManager(context);

        grid = (GridView) findViewById(R.id.gv);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_layoutt);
        previous = (ImageView) findViewById(R.id.looking_for_previous_btn);
        next = (ImageView) findViewById(R.id.looking_for_next_btn);
        textView = (TextView)findViewById(R.id.looking_for);

        frameLayout = (FrameLayout)findViewById(R.id.lookingfor_loading_frame);
        progressBar = (ProgressBar)findViewById(R.id.lookingfor_progress);
        progressBar.setVisibility(View.VISIBLE);

    }

    private void getData()
    {
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USER_LOKKINGFOR, new Response.Listener<String>() {
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
                        String looking_for = jsonObject.getString("result");
                        Log.i("response..!", "life style" + looking_for);
                        sessionManager.setData(SessionManager.KEY_LOOKIN_FOR,looking_for);

                        setData();
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

    private void setData(){

        String str_looking_for=  sessionManager.getData(SessionManager.KEY_LOOKIN_FOR);
        System.out.println("str_looking_for is###" + str_looking_for);

        if (!str_looking_for.equalsIgnoreCase("null"))
        {
            //System.out.println("str_looking_for is###" + str_looking_for);
           // startActivity(new Intent(context,UserProfileActivity.class));
            String str_previousposition = sessionManager.getData(SessionManager.KEY_PREVIOUS_LOOKING_POSITION);
            if (str_previousposition != null) {
                previousSelectedPosition = Integer.parseInt(str_previousposition);
            }
            System.out.println("str_looking_for is###" + str_looking_for);
            System.out.println("str_looking_for Position is###" + previousSelectedPosition);

            if (!str_looking_for.equalsIgnoreCase("null")) {

                if (str_looking_for.equalsIgnoreCase("Friendship")) {
                    adapter = new CustomGrid(LookingForActivity.this, web, imageId);
                    textView.setText("Looking for Friendship.");

                    grid.setAdapter(adapter);
                    startActivity(new Intent(context,UserProfileActivity.class));


                } else if (str_looking_for.equalsIgnoreCase("Dating")) {
                    adapter = new CustomGrid(LookingForActivity.this, web, imageid0);
                    grid.setAdapter(adapter);
                    textView.setText("Looking for Dating.");
                    startActivity(new Intent(context,UserProfileActivity.class));


                } else if (str_looking_for.equalsIgnoreCase("Long Term Relationship")) {
                    textView.setText("Looking for Long Term Relationship.");

                    adapter = new CustomGrid(LookingForActivity.this, web, imageid1);
                    grid.setAdapter(adapter);
                  startActivity(new Intent(context,UserProfileActivity.class));

                } else if (str_looking_for.equalsIgnoreCase("Just For Fun")) {
                    textView.setText("Looking Just For Fun.");

                    adapter = new CustomGrid(LookingForActivity.this, web, imageid2);
                    grid.setAdapter(adapter);
                   startActivity(new Intent(context,UserProfileActivity.class));


                } else if (str_looking_for.equalsIgnoreCase("Marriage")) {
                    textView.setText("Looking for Marriage.");

                    adapter = new CustomGrid(LookingForActivity.this, web, imageid3);
                    grid.setAdapter(adapter);
                   startActivity(new Intent(context,UserProfileActivity.class));


                }
                else {
                    adapter = new CustomGrid(LookingForActivity.this, web, imageId);
                    grid.setAdapter(adapter);
                   startActivity(new Intent(context,UserProfileActivity.class));

                }
            } else {
                adapter = new CustomGrid(LookingForActivity.this, web, imageId);
                grid.setAdapter(adapter);
            }
        }


        else {
            frameLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            Lookin_for = "Friendship";
            sessionManager.setData(SessionManager.KEY_LOOKIN_FOR,Lookin_for);
            adapter = new CustomGrid(LookingForActivity.this, web, imageId);
            textView.setText("Looking for Friendship.");
            grid.setAdapter(adapter);
        }
    }



    private void click() {

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Lookin_for = web[+ position];
                textView.setText("Looking for "+web[+ position]+".");
                imageView = (ImageView)view.findViewById(R.id.grid_image);
                if (position==0)
                {
                    sessionManager.setData(SessionManager.KEY_LOOKINGFOR_CHOOSED, String.valueOf(position));
                    imageView.setImageResource(R.drawable.friendship);

                }
                else if (position==1)
                {
                    sessionManager.setData(SessionManager.KEY_LOOKINGFOR_CHOOSED, String.valueOf(position));
                    imageView.setImageResource(R.drawable.dating);
                }
                else if (position==2)
                {
                    sessionManager.setData(SessionManager.KEY_LOOKINGFOR_CHOOSED, String.valueOf(position));
                    imageView.setImageResource(R.drawable.long_term);

                }
                else if (position==3)
                {
                    sessionManager.setData(SessionManager.KEY_LOOKINGFOR_CHOOSED, String.valueOf(position));
                    imageView.setImageResource(R.drawable.for_fun);
                }
                else
                {
                    imageView.setImageResource(R.drawable.marriage);
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
                        imageView.setImageResource(R.drawable.friendship_gray);

                    }

                    else if (previousSelectedPosition==1)
                    {
                        imageView.setImageResource(R.drawable.dating_gray);
                    }
                    else if (previousSelectedPosition==2)
                    {
                        imageView.setImageResource(R.drawable.long_term_gray);

                    }
                    else if (previousSelectedPosition==3)
                    {
                        imageView.setImageResource(R.drawable.fun_gray);
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.marriage_grey);
                    }

                }

                // Set the current selected view position as previousSelectedPosition
                previousSelectedPosition = position;


            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean internet = connectionDetector.isConnected(context);
                if (internet)
                {
                   Register_detail();
                }
                else
                {
                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "Please check your internet connection. Thankyou!", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (Lookin_for!=null)
                {
                    sessionManager.setData(SessionManager.KEY_LOOKIN_FOR,Lookin_for);
                    sessionManager.setData(SessionManager.KEY_PREVIOUS_LOOKING_POSITION,previousSelectedPosition+"");

                }*/
                   onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(LookingForActivity.this,Upload2.class));
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private void Register_detail() {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

       /* final String first_name = sessionManager.getData(SessionManager.KEY_FIRST_NAME);
        final String last_name = sessionManager.getData(SessionManager.KEY_LAST_NAME);
        final String psydonym = sessionManager.getData(SessionManager.KEY_PSEUDONYM);
        final String age = sessionManager.getData(SessionManager.KEY_AGE);
        final String gender = sessionManager.getData(SessionManager.KEY_GENDER_);
        final String zodiac = sessionManager.getData(SessionManager.KEY_ZODIAC);
        final String Lifestyle = sessionManager.getData(SessionManager.KEY_LIFE_STYLE);
        final String hobbies = sessionManager.getData(SessionManager.KEY_HOBBY);
        final String image = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
         if (image!=null)
         {
             bitmap = decodeBase64(image);
         }*/
        final String looking_for = sessionManager.getData(SessionManager.KEY_LOOKIN_FOR);
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
       // final String token = sessionManager.getData(SessionManager.KEY_TOKEN);

        Log.i("response..!","1235"+looking_for);
        Log.i("response..!","1235"+user_id);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest;
        volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_UPDATE_USER_LOKKINGFOR,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        try{
                           progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            Log.i("response..!","1235"+ response);

                            String Status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                            if (Status.equals("success"))
                            {
                                if (Lookin_for!=null)
                                {
                                    sessionManager.setData(SessionManager.KEY_LOOKIN_FOR,Lookin_for);
                                    sessionManager.setData(SessionManager.KEY_PREVIOUS_LOOKING_POSITION,previousSelectedPosition+"");
                                    sessionManager.Lookingfor(Lookin_for);
                                }
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context,UserProfileActivity.class));
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
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("looking_for",Lookin_for);

                System.out.println("user looking for ### "+Lookin_for);

                params.put("user_id",user_id);
                return params;
            }


        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }


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

                view = inflater.inflate(R.layout.adapter_looking_for_gridview, null);

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
        params.width = getPixelsFromDPs(Lookin_forActivity.this,168);

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
