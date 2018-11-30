package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.impl.OnItemClickListener;
import com.yanzhenjie.album.util.AlbumUtils;
import com.yanzhenjie.album.widget.divider.Api21ItemDivider;
import com.yanzhenjie.album.widget.divider.Divider;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.Activity.utils.VolleyMultipartRequest;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserPersonalInfo extends AppCompatActivity {

    ImageView img_upload_images, user_profile_pic;
    TextView username;
    GridView gridview;
    RecyclerView recyclerView;
    Context context;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    RelativeLayout relativeLayout;

    private Adapter mAdapter;
    private ArrayList<AlbumFile> mAlbumFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initView();
        // for all the click events
        click();

    }



    //....................................................initialize all the view....................................................//

    private void initView()
    {
         context = this;
         connectionDetector = new ConnectionDetector();
         sessionManager = new SessionManager(context);

         relativeLayout = (RelativeLayout) findViewById(R.id.relative_user_profile2);

         recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
         recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

         mAdapter = new Adapter(this, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                previewImage(position);
            }
        });
        recyclerView.setAdapter(mAdapter);

         img_upload_images = (ImageView)findViewById(R.id.upload_images);

         user_profile_pic = (CircleImageView) findViewById(R.id.user_profile_image);
         gridview = (GridView)findViewById(R.id.user_profile_gv);
         username = (TextView)findViewById(R.id.userpseudonym);

        String pr_img = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
        String user_gen = sessionManager.getData(SessionManager.KEY_GENDER_);
        String user_nm = sessionManager.getData(SessionManager.KEY_PSEUDONYM);
        username.setText(user_nm);
        // Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 30);
        // user_choosed_image.setImageBitmap(circularBitmap);
        System.out.println("Selected Image pro" + user_gen);
        System.out.println("Selected Image p" + pr_img);

        if (pr_img!=null)
        {
            Bitmap bitmap = decodeBase64(pr_img);
            user_profile_pic.setImageBitmap(bitmap);
        }
        else if(user_gen!=null)
        {
            if (user_gen.equalsIgnoreCase("Male"))
            {
                user_profile_pic.setImageResource(R.drawable.male_blue);
            }
            if(user_gen.equalsIgnoreCase("Female"))
            {
                user_profile_pic.setImageResource(R.drawable.female_pink);
            }
        }


     }

//..........................................for all click events...............................................................//
    private void click() {

        img_upload_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });

    }
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    /**
     * Select picture, from album.
     */
    private void selectImage() {
        Album.image(this)
                .multipleChoice()
                .camera(true)
                .columnCount(2)
                .selectCount(6)
                .checkedList(mAlbumFiles)
                .widget(
                        Widget.newDarkBuilder(this)
                                .title("Select images")
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;

                        mAdapter.notifyDataSetChanged(mAlbumFiles);
                        mAdapter.notifyDataSetChanged();
                      // String img =  encodeTobase64(BitmapFactory.decodeFile(String.valueOf(mAlbumFiles)));
                        //mTvMessage.setVisibility(result.size() > 0 ? View.VISIBLE : View.GONE);
                      //  System.out.println("path"+ img);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Toast.makeText(context, "cancel", Toast.LENGTH_LONG).show();
                    }
                })
                .start();
    }

    /**
     * Preview image, to album.
     */
    private void previewImage(int position) {
        if (mAlbumFiles == null || mAlbumFiles.size() == 0) {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show();
        } else {
            Album.galleryAlbum(this)
                    .checkable(true)
                    .checkedList(mAlbumFiles)
                    .currentPosition(position)
                    .widget(
                            Widget.newDarkBuilder(this)
                                    .title("")
                                    .build()
                    )
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            mAlbumFiles = result;
                            mAdapter.notifyDataSetChanged(mAlbumFiles);
                            mAdapter.notifyDataSetChanged();

                            //mTvMessage.setVisibility(result.size() > 0 ? View.VISIBLE : View.GONE);
                        }
                    })
                    .start();
        }
    }

    public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private LayoutInflater mInflater;
        private OnItemClickListener mItemClickListener;
        private List<AlbumFile> mAlbumFiles;

        public Adapter(Context context, OnItemClickListener itemClickListener) {
            this.mInflater = LayoutInflater.from(context);
            this.mItemClickListener = itemClickListener;
        }

        public void notifyDataSetChanged(List<AlbumFile> imagePathList) {
            this.mAlbumFiles = imagePathList;
           // super.notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            AlbumFile albumFile = mAlbumFiles.get(position);
            if (albumFile.getMediaType() == AlbumFile.TYPE_IMAGE) {
                return AlbumFile.TYPE_IMAGE;
            } else {
                return AlbumFile.TYPE_VIDEO;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType) {
                case AlbumFile.TYPE_IMAGE: {
                    return new ImageViewHolder(mInflater.inflate(R.layout.adapter_personalinfo_gridview, parent, false), mItemClickListener);
                }
                case AlbumFile.TYPE_VIDEO: {
                    return new VideoViewHolder(mInflater.inflate(R.layout.adapter_personalinfo_gridview, parent, false), mItemClickListener);
                }
                default: {
                    throw new AssertionError("This should not be the case.");
                }
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            switch (viewType) {
                case AlbumFile.TYPE_IMAGE: {
                    ((ImageViewHolder) holder).setData(mAlbumFiles.get(position));
                    String path = mAlbumFiles.get(position).getPath();
                    System.out.println("pahh "+path);
                    Bitmap bitmap =  BitmapFactory.decodeFile(path);
                    System.out.println("pahh.. "+bitmap);

                    sendImages(bitmap);
                    break;
                }
                case AlbumFile.TYPE_VIDEO: {
                    ((VideoViewHolder) holder).setData(mAlbumFiles.get(position));
                    break;
                }
            }
        }

        @Override
        public int getItemCount() {
            return mAlbumFiles == null ? 0 : mAlbumFiles.size();
        }

        private  class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final OnItemClickListener mItemClickListener;
            private ImageView mIvImage;

            ImageViewHolder(View itemView, OnItemClickListener itemClickListener) {
                super(itemView);
                this.mItemClickListener = itemClickListener;
                this.mIvImage = itemView.findViewById(R.id.grid_image);
                itemView.setOnClickListener(this);
            }

            public void setData(AlbumFile albumFile) {
                Album.getAlbumConfig().
                        getAlbumLoader().
                        load(mIvImage, albumFile);
            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        }

        private class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final OnItemClickListener mItemClickListener;

            private ImageView mIvImage;
            private TextView mTvDuration;

            VideoViewHolder(View itemView, OnItemClickListener itemClickListener) {
                super(itemView);
                this.mItemClickListener = itemClickListener;
                this.mIvImage = itemView.findViewById(R.id.grid_image);
                this.mTvDuration = itemView.findViewById(com.yanzhenjie.album.R.id.tv_duration);
                itemView.setOnClickListener(this);
            }

            void setData(AlbumFile albumFile) {
                Album.getAlbumConfig().
                        getAlbumLoader().
                        load(mIvImage, albumFile);
                mTvDuration.setText(AlbumUtils.convertDuration(albumFile.getDuration()));
            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        }

    }

    private void sendImages(final Bitmap path) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest;
        volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_ADD_GALLERY,
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
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                               // startActivity(new Intent(context,UserProfileActivity.class));
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {



           /*  * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags*/


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",sessionManager.getData(SessionManager.KEY_USER_ID));


                return params;
            }



             // Here we are passing image by renaming it with a unique name


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                if (path!=null)
                {
                    params.put("gallery_image", new DataPart(imagename + ".png", getFileDataFromDrawable(path)));

                }
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
