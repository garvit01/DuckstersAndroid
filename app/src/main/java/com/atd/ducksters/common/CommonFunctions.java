package com.atd.ducksters.common;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class CommonFunctions {

    public static Boolean checkNetwork(Activity activity) {
        ConnectionDetector conObj = new ConnectionDetector(activity);
        if (!conObj.isConnectingToInternet()) {
            CommonFunctions.showToast(activity, "No Network available, Please connect to Internet.");
            return true;
        }
        return false;
    }

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    public static String getSHA256(String strData) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] passHash = sha256.digest(strData.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passHash.length; i++) {
                sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object toJSON(Object object) throws JSONException {
        if (object == null) {
            return "";
        }
        if (object instanceof Map) {
            JSONObject json = new JSONObject();
            Map map = (Map) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (object instanceof Iterable) {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable) object)) {
                json.put(toJSON(value));
            }
            return json;
        } else {
            return object;
        }
    }

    public static String EncodeFunction(HashMap<String, String> params) {
        Gson gson = new Gson();
        String compactJws = null;
        try {
            compactJws = Jwts.builder()
                    .setSubject(gson.toJson(params))
//					.setPayload(gson.toJson(params))
                    .signWith(SignatureAlgorithm.HS384, CommonVar.key)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compactJws;
    }

    public static String getMd5Hash(String input) {
        if (input == null) return null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32)
                md5 = "0" + md5;

            return md5;
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", e.getLocalizedMessage());
            return null;
        }
    }

    public static void saveAppPref(Context ctx, String key, String value) {
        SharedPreferences preferences = ctx.getSharedPreferences(CommonVar.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getAppPref(Context ctx, String key) {
        SharedPreferences preferences = ctx.getSharedPreferences(CommonVar.PREFS_NAME, Context.MODE_PRIVATE);
        if (preferences.contains(key))
            return preferences.getString(key, "");
        return "";
    }

    public static boolean getAppPrefBoolean(Context ctx, String key) {
        SharedPreferences preferences = ctx.getSharedPreferences(CommonVar.PREFS_NAME, Context.MODE_PRIVATE);
        if (preferences.contains(key))
            return preferences.getBoolean(key, false);
        return false;
    }

    public static int getAppVersionCode(Context ctx) {
        try {
            return (ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static String getAppVersionName(Context ctx) {
        try {
            return (ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void showDateDialog(final TextView text, Activity activity) {
        final Calendar cMax = Calendar.getInstance();
        cMax.add(Calendar.DATE, 365 * 10);
        final Calendar cMin = Calendar.getInstance();
        cMin.add(Calendar.DATE, -0);
        int[] openDate = formateDate(text);
        DatePickerDialog dpd = new DatePickerDialog(activity,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String month = (monthOfYear + 1) + "";
                    String date = dayOfMonth + "";
                    if (month.length() < 2) {
                        month = "0" + month;
                    }
                    if (date.length() < 2) {
                        date = "0" + date;
                    }
                    text.setText(date + "-" + month + "-" + year);
                }, openDate[0], openDate[1], openDate[2]);
        dpd.getDatePicker().setMaxDate(cMax.getTimeInMillis());
        dpd.getDatePicker().setMinDate(cMin.getTimeInMillis());
        dpd.show();
    }

    public static int[] formateDate(TextView text) {
        int[] newDateStr = new int[3];
        if (text.getText().toString().isEmpty()) {
            try {
                String dateStr = getCurrentDate();
                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                newDateStr[0] = cal.get(Calendar.YEAR);
                newDateStr[1] = cal.get(Calendar.MONTH);
                newDateStr[2] = cal.get(Calendar.DAY_OF_MONTH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                String dateStr = text.getText().toString();
                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                newDateStr[0] = cal.get(Calendar.YEAR);
                newDateStr[1] = cal.get(Calendar.MONTH);
                newDateStr[2] = cal.get(Calendar.DAY_OF_MONTH);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newDateStr;
    }

    public static String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(date);
        return formattedDate;
    }

    public static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static String getYesterdayDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(yesterday());
    }

    public static String getFormattedDate(String date, String inputFormat, String outputFormat) {
        String outputDate = "";
        try {
            Date date1 = new SimpleDateFormat(inputFormat).parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat(outputFormat);
            String format = formatter.format(date1);
            Log.d("format date", format);
            outputDate = format;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;
    }

    /*    "HH:mm"        "hh:mm a"             */
    public static String getFormattedTime(String time, String inputFormat, String outputFormat) {
        String outputTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
            Date dateObj = sdf.parse(time);
            outputTime = new SimpleDateFormat(outputFormat).format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }

        return outputTime;
    }

    public static void showToast(Activity activity, String message) {
        try {
            CommonVar.controlledToast.getView().isShown();
            CommonVar.controlledToast.setText(message);
        } catch (Exception e) {
            CommonVar.controlledToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        }
        CommonVar.controlledToast.show();
    }

    public static int getDifferenceInDates(String fromDate, String uptoDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date startDate = formatter.parse(fromDate);
            Date endDate = formatter.parse(uptoDate);
            System.out.println("exception range = " + endDate.compareTo(startDate));
            long duration = endDate.getTime() - startDate.getTime();
            long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
            int difference = (int) diffInDays;
            if (difference < 0) {
                return (difference - 1);
            } else {
                return (difference + 1);
            }
        } catch (ParseException e1) {
            return -1;
        }
    }

    public static boolean isValidDateSelection(String fromDate, String uptoDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date1 = formatter.parse(fromDate);
            Date date2 = formatter.parse(uptoDate);
            if (date2.compareTo(date1) >= 0) {
                return true;
            } else {
                return false;
            }

        } catch (ParseException e1) {
            return false;
        }
    }

}
