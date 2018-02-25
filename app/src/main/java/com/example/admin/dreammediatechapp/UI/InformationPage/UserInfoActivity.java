package com.example.admin.dreammediatechapp.UI.InformationPage;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.admin.dreammediatechapp.Entities.User;
import com.example.admin.dreammediatechapp.Entities.Video;
import com.example.admin.dreammediatechapp.Entities.VideoijkBean;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.common.CircleImageView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jph.takephoto.app.TakePhoto;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {
    private CircleImageView  circleImageView;
    private User user;
    private int uId;
    private TextView user_info_name,user_info_char;
    private EditText user_info_nicname_input, user_info_realname_input, user_info_phone_input, user_info_alipay_input;
    private Button user_info_button,local_photo,camera_photo,cancle;
    String mCurrentPhotoPath = null;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        //
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        actionBar.setTitle("个人信息");
        SharedPreferences sharedPreferences=this.getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        uId = sharedPreferences.getInt("uId",0);

        circleImageView = findViewById(R.id.user_info_image);
        user_info_name = findViewById(R.id.user_info_name);
        user_info_char = findViewById(R.id.user_info_char);
        user_info_nicname_input = findViewById(R.id.user_info_nicname_input);
        user_info_realname_input = findViewById(R.id.user_info_realname_input);
        user_info_phone_input = findViewById(R.id.user_info_phone_input);
        user_info_alipay_input = findViewById(R.id.user_info_alipay_input);
        user_info_button = findViewById(R.id.user_info_button);

        circleImageView.setOnClickListener(onClickListener);
        user_info_button.setOnClickListener(onClickListener);
//        circleImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"aa",Toast.LENGTH_LONG).show();
//            }
//        });
        //showPopupWindow();
        GetUserInfo(uId);

    }
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.user_info_image:
                    showPopupWindow();

                break;
                case R.id.user_info_button:
                    Toast.makeText(getApplicationContext(),"aa",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    //监听标题栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopupWindow(){
        View view = View.inflate(this,R.layout.popup_window_layout,null);
        local_photo = view.findViewById(R.id.btn_pop_album);
        camera_photo = view.findViewById(R.id.btn_pop_camera);
        cancle = view.findViewById(R.id.btn_pop_cancel);

        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels*1/3;
        final PopupWindow popupWindow  = new PopupWindow(view,weight,height);
        popupWindow.setAnimationStyle(R.anim.push_up_in);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        local_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        camera_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,1);
                if (Build.VERSION.SDK_INT>=23){
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CAMERA);
                    if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(UserInfoActivity.this,new String[]{Manifest.permission.CAMERA},222);
                        return;
                    }else {
                        File file = new File(Environment.getExternalStorageDirectory(),"icon.jpg");
                        try{
                            if (file.exists()){
                                file.delete();
                            }file.createNewFile();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        imageUri = Uri.fromFile(file);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, 1);
                    }
                }


            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha =1.0f;
                getWindow().setAttributes(lp);
            }
        });
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.alpha =0.5f;
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK) {
//                    // 设置intent为启动裁剪程序
//                    Intent intent = new Intent("com.android.camera.action.CROP");
//                    // 设置Data为刚才的imageUri和Type为图片类型
//                    intent.setDataAndType(imageUri, "image/*");
//                    // 设置可缩放
//                    intent.putExtra("scale", true);
//                    // 设置输出地址为imageUri
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                    // 开启裁剪,设置requestCode为CROP_PHOTO
//                    startActivityForResult(intent, CROP_PHOTO);
                    Toast.makeText(getApplicationContext(),"拍照成功",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    private void GetUserInfo(final int UID){
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{
                    String sendUrl2="http://119.29.114.73/Dream/mobileUserController/getUserInformation.action?uid="+UID;

                    Log.d("UIA",sendUrl2);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder().url(sendUrl2).build();
                    Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String result = response.body().string();
                            JsonElement je = new JsonParser().parse(result);
                            Log.d("UIA","获取返回码"+je.getAsJsonObject().get("status"));
                            Log.d("UIA","获取返回信息"+je.getAsJsonObject().get("data"));
                            // JsonData(je.getAsJsonObject().get("data"));
                            //videoList=JsonData(je.getAsJsonObject().get("data"));
                            user=JsonData(je.getAsJsonObject().get("data"));
                            showResponse(user);

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(){
            public void run(){
                new Handler(Looper.getMainLooper()).post(runnable);
            }
        }.start();
    }
    private User JsonData(JsonElement data){
        Gson gson = new Gson();
        Type type = new TypeToken<User>(){}.getType();
        User user = gson.fromJson(data,type);
        Log.d("UIA",user.getuNickName());
        Log.d("UIA",user.getuType());
        return user ;
    }

    private void showResponse(final User user){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user_info_name.setText(user.getuNickName());
                user_info_char.setText(user.getuType());

                user_info_nicname_input.setText(user.getuNickName());
                if (user.getuName()!=null){
                    user_info_realname_input.setText(user.getuName());
                }
                if(user.getuImageAddress()!=null){
                    Glide.with(getApplicationContext()).load(user.getuImageAddress()).into(circleImageView);
                }
            }
        });
    }
//    private void takeCamera(int num) {
//
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            photoFile = createImageFile();
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(photoFile));
//            }
//        }
//
//        startActivityForResult(takePictureIntent, num);//跳转界面传回拍照所得数据
//    }
//    private File createImageFile() {
//        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//        File image = null;
//        try {
//            image = File.createTempFile(
//                    generateFileName(),  /* prefix */
//                    ".jpg",         /* suffix */
//                    storageDir      /* directory */
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
////
//    public static String generateFileName() {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        return imageFileName;
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK ) {
//            if (requestCode == 1 && null != data) {
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                Cursor cursor = getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                final String picturePath = cursor.getString(columnIndex);
//                //upload(picturePath);
//                cursor.close();
//            }else if (requestCode == 2){
//
//                SimpleTarget target = new SimpleTarget<Bitmap>() {
//
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
////                        upload(saveMyBitmap(resource).getAbsolutePath());
//                    }
//
//                    @Override
//                    public void onLoadStarted(Drawable placeholder) {
//                        super.onLoadStarted(placeholder);
//
//                    }
//
//                    @Override
//                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                        super.onLoadFailed(e, errorDrawable);
//
//                    }
//                };
//
////                Glide.with(UserInfoActivity.this).load(mCurrentPhotoPath)
////                        .asBitmap()
////                        .override(1080, 1920)//图片压缩
////                        .centerCrop()
////                        .dontAnimate()
////                        .into(target);
//
//
//            }
//        }
//    }


//    //将bitmap转化为png格式
//    public File saveMyBitmap(Bitmap mBitmap){
//        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//        File file = null;
//        try {
//            file = File.createTempFile(
//                    UploadAccess.generateFileName(),  /* prefix */
//                    ".jpg",         /* suffix */
//                    storageDir      /* directory */
//            );
//
//            FileOutputStream out=new FileOutputStream(file);
//            mBitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return  file;
//    }




}
