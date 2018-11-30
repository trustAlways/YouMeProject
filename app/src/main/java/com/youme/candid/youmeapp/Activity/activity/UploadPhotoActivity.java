package com.youme.candid.youmeapp.Activity.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.Utility;
import com.youme.candid.youmeapp.R;

import java.awt.font.TextAttribute;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class UploadPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView circleImageView;
    TextView text_upload,text_take;
    ImageView img_next,img_previous;
    private static final int PERMISSION_CAMERA_CODE = 1;
    private static final int PERMISSION_STORE_CODE = 2;
    private static final int REQUEST_CODE_TAKE_IMAGE = 3;
    private static final int REQUEST_CODE_CHOOSE_IMAGE = 4;
    private static final int REQUEST_CODE_CROP_AVATAR = 5;
    private static final int REQUEST_CODE_CROP_COVER = 6;
    Context context;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    File file;
    Uri uri;
    Bitmap btmap;
    Intent CamIntent, GalIntent, CropIntent ;
    public  static final int RequestPermissionCode  = 1 ;
    DisplayMetrics displayMetrics ;
    int width, height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        //for initialize all the views this method will be invoke
        initView();

        //for cheeck the runtime permisson of marshmallow this method will be invoke
            // checkForPermission();

        //for click events this method will be invoked
        clickevents();

    }

    private void initView() {

        context = this;
        sessionManager = new SessionManager(context);
        connectionDetector = new ConnectionDetector();

        circleImageView = (CircleImageView)findViewById(R.id.profile_image);
        text_upload = (TextView) findViewById(R.id.upload_pic);
        text_take =  (TextView) findViewById(R.id.take_pic);

        img_next = (ImageView) findViewById(R.id.upload_next_step);
        img_previous = (ImageView) findViewById(R.id.upload_previous_step);
      //  EnableRuntimePermission();

         String  profileimage =  sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
         String male = sessionManager.getData(SessionManager.KEY_GENDER_);
         if (profileimage!=null)
        {
            // btmap=decodeBase64(profileimage);
            circleImageView.setImageBitmap(btmap);
        }
         if (male.equalsIgnoreCase("Male"))
        {
            circleImageView.setImageResource(R.drawable.male_blue);
        }
         if(male.equalsIgnoreCase("Female"))
        {
            circleImageView.setImageResource(R.drawable.female_pink);
        }

    }

    private  void clickevents()
    {
        text_upload.setOnClickListener(this);
        text_take.setOnClickListener(this);
        img_previous.setOnClickListener(this);
        img_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i==R.id.upload_pic)
        {
            if (Utility.checkPermission(context))
            {
                GetImageFromGallery();

            }
        }

        if (i==R.id.take_pic)

        {
            if (Utility.checkPermission(context))
            {
                ClickImageFromCamera() ;

            }
        }

        if (i==R.id.upload_next_step)
        {
            Toast.makeText(context, "Nothing to Show", Toast.LENGTH_SHORT).show();
        }
        if (i==R.id.upload_previous_step)
        {
            onBackPressed();
        }

    }


    public void ClickImageFromCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);

        file = new File(Environment.getExternalStorageDirectory(), "file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        uri = Uri.fromFile(file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, REQUEST_CODE_TAKE_IMAGE);

    }

    public void GetImageFromGallery(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select image from your gallery"), REQUEST_CODE_CHOOSE_IMAGE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_TAKE_IMAGE && resultCode == Activity.RESULT_OK) {
            cropImage();

        } else if (requestCode == REQUEST_CODE_CHOOSE_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.getData();
                cropImage();

            }

        } else if (requestCode == REQUEST_CODE_CROP_AVATAR && resultCode == Activity.RESULT_OK) {

            if (data != null) {

                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                circleImageView.setImageBitmap(bitmap);
            }

        }
    }

    private void cropImage() {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setDataAndType(uri, "image/*");
        cropIntent.putExtra("crop", true);
        cropIntent.putExtra("outputX", 360);
        cropIntent.putExtra("outputY", 350);

        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("scaleUpIfNeeded", true);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, REQUEST_CODE_CROP_AVATAR);

    }

    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 360);
            CropIntent.putExtra("outputY", 350);
            CropIntent.putExtra("aspectX", 1);
            CropIntent.putExtra("aspectY", 1);
            CropIntent.putExtra("scale", true);
            CropIntent.putExtra("circleCrop", new String(""));
            //CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

        }
    }
    //Image Crop Code End Here


    /* public static String encodeTobase64(Bitmap image) {
        Bitmap bitmap_image = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap_image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }
    // method for base64 to bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }*/






    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,HobbiesActivity.class));
    }

     @Override
    protected void onPause() {
        super.onPause();
       //  sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE, encodeTobase64(btmap));

     }



    @Override
    protected void onResume() {
        super.onResume();
      //  sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE, encodeTobase64(btmap));

    }

}
