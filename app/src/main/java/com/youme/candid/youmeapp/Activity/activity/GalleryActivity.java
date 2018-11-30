package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.app.gallery.PreviewAlbumAdapter;
import com.yanzhenjie.album.app.gallery.PreviewPathAdapter;
import com.youme.candid.youmeapp.Activity.adapter.CustomAdapter;
import com.youme.candid.youmeapp.Activity.model.Usergalleryimages;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewPager viewPager;
    ImageView imageView;
    TextView txtname;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    ProgressBar pb;
    Context c;
    CustomAdapter customAdapter;
    String idd,name;
    ArrayList<Usergalleryimages> image_list;
    private ArrayList<String> mAlbumFiles;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getBundleData();

        initView();

        //init();
        setTvAdapter();

        clickListner();

    }

    private void getBundleData() {
         Intent intent = getIntent();
         idd = intent.getStringExtra("id");
         name = intent.getStringExtra("psuedonym");
         System.out.println("Idd is@@@"+idd + name);

         txtname = (TextView)findViewById(R.id.activity_named);
         txtname.setText(name+ " Gallery");
         sessionManager = new SessionManager(this);
    }


    private void initView() {
        c = this;

        image_list = new ArrayList<>();
        mAlbumFiles = new ArrayList<>();

        connectionDetector = new ConnectionDetector();
        pb = (ProgressBar)findViewById(R.id.progressbar);


        imageView = (ImageView)findViewById(R.id.gallery_back);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        viewPager = (ViewPager)findViewById(R.id.pager);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);


    }

    private void clickListner() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setTvAdapter()
    {
        pb.setVisibility(View.VISIBLE);

       /* final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Loading..");
        pd.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_All_GALLERY_IMAGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pd.dismiss();
                try {
                    Log.i("response..!", "Images%%%" + response);
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    String Status = obj.getString("status");
                    String msg = obj.getString("message");

                    if (Status.equals("success") ) {

                        JSONArray array = obj.getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject jsonObject =array.getJSONObject(i);
                            String image_id = jsonObject.getString("id");
                            String user_id = jsonObject.getString("user_id");
                            String gallerys_image = jsonObject.getString("gallery_image");

                            image_list.add(new Usergalleryimages(gallerys_image,image_id,user_id));
                            mAlbumFiles.add(gallerys_image);
                        }

                         customAdapter = new CustomAdapter(c, image_list);
                         recyclerView.setAdapter(customAdapter);

                         viewPager.setAdapter(new PreviewPathAdapter(c,mAlbumFiles));
                         pb.setVisibility(View.GONE);



                    }
                    else if (Status.equals("error")) {
                      //  pd.dismiss();
                        pb.setVisibility(View.GONE);
                        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                      //  pd.dismiss();
                        pb.setVisibility(View.GONE);
                        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.setVisibility(View.GONE);;
                Toast.makeText(c, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                params.put("gallery_user",idd);
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(c);
        requestQueue.add(stringRequest);

    }



}
