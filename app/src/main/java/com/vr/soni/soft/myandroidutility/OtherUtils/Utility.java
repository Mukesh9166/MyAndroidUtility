package com.vr.soni.soft.myandroidutility.OtherUtils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.vr.soni.soft.myandroidutility.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;



public class Utility {

    private static Dialog dialogBuilder;
    private static final String LOG_TAG = Utility.class.getSimpleName();


    public static boolean rotateFab(final View v, boolean rotate) {
        v.animate().setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .rotation(rotate ? 135f : 0f);
        return rotate;
    }

    public static void showIn(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(0f);
        v.setTranslationY(v.getHeight());
        v.animate()
                .setDuration(200)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1f)
                .start();
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static boolean isMotorNumber(String str) {
        return str.matches("^[A-Z]{2}[0-9]{1,2}[A-Z]{1,2}[0-9]{3,4}$");
    }

    public static float aFloatRoundOff(float value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Float.parseFloat(df.format(value));
    }

    public static void showOut(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(1f);
        v.setTranslationY(0);
        v.animate()
                .setDuration(200)
                .translationY(v.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(0f)
                .start();
    }

    public static void init(final View v) {
        v.setVisibility(View.GONE);
        v.setTranslationY(v.getHeight());
        v.setAlpha(0f);
    }

    public static void log(String tag, String message, int code) {
        switch (code) {
            case 0:
                Log.d(tag, message);
                break;
            case 1:
                Log.i(tag, message);
                break;
            case 2:
                Log.e(tag, message);
                break;
            default:
                Log.d(tag, message);
                break;
        }
    }

    public static String currencyFormatter(String num) {

        String num1 = "";
        try {
            double m = Double.parseDouble(num);
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            num1 = formatter.format(m);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "currencyFormatter:Exception: " + e.getLocalizedMessage());
        }
        return num1;
    }

    public static void blinkView(View view) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(200);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);

    }

    public static String changeDateFormat(String inputFormat, String displayFormat, String time) {

        SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat, Locale.US);
        SimpleDateFormat displayDateFormat = new SimpleDateFormat(displayFormat, Locale.US);

        String date = "";

        try {
            date = displayDateFormat.format(inputDateFormat.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "changeDateFormat:ParseException: " + e.getLocalizedMessage());
        }
        return date;
    }

    public static void hideInputKeypad(Activity activity) {

        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (activity.getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Method to check email is valid or not
     *
     * @param strEmailAddress Email Address String to be checked or validated
     * @return true if email is valid else false
     */
    public static boolean emailValidator(String strEmailAddress) {
        if (strEmailAddress == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(strEmailAddress).matches();
        }
    }

    public static Date convertStringToDate(String string, String DateFormat) {

        SimpleDateFormat format = new SimpleDateFormat(DateFormat, Locale.US);
        Date date = null;
        try {
            date = format.parse(string);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /** Generate Random Key */
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static void toastMsg(String string, Context context) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static void showProgressDialog(Context mContext, String strMessageOnProgressDialog) {
        log(LOG_TAG, "showProgressDialog()", 0);
        hideProgressDialog(mContext);

        dialogBuilder = new Dialog(mContext);
        dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBuilder.setContentView(R.layout.circular_dialog);
        dialogBuilder.setCancelable(false);
        try {
            dialogBuilder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressDialog(Context context) {
        try {
            if (dialogBuilder != null && dialogBuilder.isShowing()) {
                dialogBuilder.dismiss();
            }
        } catch (Exception e) {

        }
    }

    public static TranslateAnimation shake() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    public static TranslateAnimation bailShake() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(2500);
        shake.setInterpolator(new CycleInterpolator(30));
        return shake;
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static long convertToMileSecond(String startDate, String endDate) {

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date stDate = null;
        Date enDate = null;

        try {
            enDate = df.parse(endDate);
            stDate = df.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = enDate.getTime() - stDate.getTime();

        return difference;
    }

    public static String getHours(long difference) {

        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);
        Log.d("Hours>>", " :: " + hours + "::" + min);

        String totalHours = String.format("%02d:%02d", hours, min);


        return totalHours;
    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static long hourToMili(long hours) {
        return hours * 60 * 60 * 1000;
    }

    public static long minToMili(long min) {
        return min * 60 * 1000;
    }

    public static void alertDialog(Context context, String msg, String Title, Drawable img, String btnPosTitle, String btnNegTitle, boolean cancelable, DialogInterface.OnClickListener btnPos, DialogInterface.OnClickListener btnNeg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Title);

        if (img != null) {
            builder.setIcon(img);
        }

        builder.setMessage(msg);

        try {
            builder.setCancelable(cancelable);
        } catch (Exception e) {

        }

        if (btnPos != null) {
            builder.setPositiveButton(btnPosTitle, btnPos);
        }

        if (btnNeg != null) {
            builder.setNegativeButton(btnNegTitle, btnNeg);
        }

        try {

            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RequestBody convertPartBodyText(String string) {

        try {
            if (string == null) {
                string = "";
            }
            return RequestBody.create(
                    MediaType.parse("text/plain"),
                    string
            );

        } catch (Exception e) {
            return RequestBody.create(MediaType.parse("text/plain"), "");
        }
    }

    public static void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);

    }

    public static void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
    }

    public static double roundDecimal(double value, final int decimalPlace) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        value = bd.doubleValue();
        return value;
    }

    public static double convertSpeed(double speed) {
        return ((speed * 3600) * 0.001);
    }

}
