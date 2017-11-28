package com.example.dxtre.sppen.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dxtre.glam.R;
import com.example.dxtre.sppen.model.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by DXtre on 22/06/16.
 */

public class ApiHelper {

    public static final String TAG = "DXtre";
    public static final String TAG_Pref = "DXtrePreferences";
    public static final String BASE_URL = "http://glamorizeapp.com/images/";

    public static int getPixelsWithDensity(Context context, int pixels){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pixels* scale + 0.5f);
    }

    public static String getFormatDate(String dateInString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateResult = "";

        try {

            Date date = formatter.parse(dateInString);

            dateResult = formatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateResult;
    }

    public static String parseString(Double number){

        String numberFormat="0.00";

        if(number!=null)
            numberFormat = String.format("%.2f",number);

        numberFormat = numberFormat.replace(",", ".");

        return numberFormat;
    }

    public static User getUser(Activity activity){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(ApiHelper.TAG_Pref, Context.MODE_PRIVATE);
        User user = new User();

        user.setIdUser(sharedPreferences.getInt("idUser", 0));
        user.setEmail(sharedPreferences.getString("email", ""));
        user.setPassword(sharedPreferences.getString("password", ""));
        user.setFacebookLogin(sharedPreferences.getInt("facebookLogin", 0));
        user.setIdCity(sharedPreferences.getInt("idCity", 0));

        return user;
    }

    public static User getUser(){
        User user = new User();

        user.setIdUser(687);
        user.setEmail("test@test.com");
        user.setPassword("12345");
        user.setFacebookLogin(0);
        user.setIdCity(1);

        return user;
    }

    public static void changeIdUser(Activity activity, User user){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(ApiHelper.TAG_Pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("idUser", user.getIdUser());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putInt("facebookLogin", user.getFacebookLogin());
        editor.putInt("idCity", user.getIdCity());

        editor.commit();
    }



    public static boolean getNotification(Activity activity){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(ApiHelper.TAG_Pref, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("stateSotification", false);
    }

    public static void changeNotification(Activity activity, boolean value){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(ApiHelper.TAG_Pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("stateSotification", value);

        editor.commit();
    }

    public static void displayImagen(Context context, String url, ImageView v){
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.placeholder_not_found)
                .showImageOnFail(R.drawable.placeholder_not_found)
                .showImageOnLoading(R.drawable.placeholder_loading).build();

        imageLoader.displayImage(url, v, options);
    }

    public static Bitmap decodeFile(File f, int rotate){
        Bitmap b = null;

        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = null;
        try {

            fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();

            int scale = 1;
            if (o.outHeight > Constants.IMAGE_MAX_SIZE || o.outWidth > Constants.IMAGE_MAX_SIZE) {
                scale = (int)Math.pow(2, (int) Math.ceil(Math.log(Constants.IMAGE_MAX_SIZE /
                        (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();

            if (rotate != 0) {
                Matrix mat = new Matrix();
                mat.postRotate(rotate);
                b = Bitmap.createBitmap(b, 0, 0,
                        b.getWidth(), b.getHeight(),
                        mat, true);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static void openBrowserByURL(Activity activity, String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }

    public static void changeCity(Activity activity, User user){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(ApiHelper.TAG_Pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("idCity", user.getIdCity());

        editor.commit();
    }

    public static boolean isOnline(Activity activity) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(EMAIL_REGEX);
    }

    public static void hiddenKeyBoard(Activity activity){
        View v = activity.getCurrentFocus();
        if (v != null){
            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static TextView setBackGround(TextView textView, GradientDrawable gradientDrawable){
        if(Build.VERSION.SDK_INT <= 15) {
            textView.setBackgroundDrawable(gradientDrawable);
        }else {
            textView.setBackground(gradientDrawable);
        }
        return textView;
    }

    public static GradientDrawable createDrawableBackground(int color){
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setColor(color);
        return gd;
    }

    public static String getRealPath(Activity context, Intent data){

        String camRuta = null;

        if (camRuta == null) {

            if (data != null) {

                Uri selectedImage = data.getData();

                if (selectedImage != null) {

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor2 = context.getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);

                    if (cursor2 != null) {

                        cursor2.moveToFirst();

                        int columnIndex = cursor2.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor2.getString(columnIndex);
                        cursor2.close();
                        camRuta = filePath;
                    }
                }
            }
        }

        if (camRuta == null) {

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{
                            MediaStore.Images.Media.DATA,
                            MediaStore.Images.Media.DATE_ADDED,
                            MediaStore.Images.ImageColumns.ORIENTATION},
                    MediaStore.Images.Media.DATE_ADDED, null, "date_added ASC");

            if (cursor != null) {

                while (cursor.moveToNext()) {
                    Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                    camRuta = uri.toString();
                }
                if (camRuta != null) {
                    //LogView.SaveLogDep("camRuta0", camRuta);
                }
                cursor.close();
            } else {
                camRuta = null;
            }
        }

        return camRuta;
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = "";

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }
}