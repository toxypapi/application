package com.juniorkabore.filleul;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Util {
    private static String TAG="Util";
    private static final String DEBUG_TAG = "Http";

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String EMAIL = "idFacebook";
    public static final String USER_NAME = "user_name";
    public final static String SENDER_ID = "214185124039";
    public static String base_url = "http://192.168.0.42:8888/Filleul/save/";
    public final static String  register_url= base_url+"register.php";
    public final static String  send_chat_url= base_url+"sendChatmessage.php";
    public final static String  sendPush_url= base_url+"send_message.php";



    //Enregistrement data
    public final static String facebookData_url= base_url+"facebookData.php";
    public final static String user_url= base_url+"user.php";
    public final static String lieux_url= base_url+"lieux.php";
    public final static String statut_url= base_url+"statut.php";
    public final static String kiffes_url= base_url+"kiffes.php";
    public final static String attribution_url= base_url+"attribution_auto.php";
    public final static String attribution= base_url+"attribution.php";
    public final static String selectAttribution= base_url+"selectionAttribution.php";
    public final static String saveChat = base_url+"SaveChat.php";
    public final static String getAttribution = base_url+"getAttribution.php";
    public final static String selectAttributionP= base_url+"selectionAttributionP.php";
    public final static String getChatMessage = base_url + "getChatMessage.php";
    public final static String getChatTYPEMessage = base_url + "getChatTYPEMessage.php";






    public static ProgressDialog GetProcessDialog(Activity activity) {
        // prepare the dialog box
        ProgressDialog dialog = new ProgressDialog(activity);
        // make the progress bar cancelable
        dialog.setCancelable(true);
        // set a message text
        dialog.setMessage("Loading...");

        // show it
        return dialog;
    }
    public static String getCurrentDateTimeString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentDateTimeString=dateFormat.format(date);
        return currentDateTimeString;
    }
    public static String getCurrentDateTimeStringGMT() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentDateTimeString=dateFormat.format(date);
        String currentDateTimeWithformat=Util.changeDateTimeFormate(currentDateTimeString,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss");
        Log.i("", "onActivityResult flight currentDateTimeWithformat..." + currentDateTimeWithformat);
        Date currentDateTimeDate=Util.convertStringIntoDate(currentDateTimeWithformat, "yyyy-MM-dd hh:mm:ss");
        Log.i("", "onActivityResult flight currentDateTimeDate..." + currentDateTimeDate);
        String gmtDateTime=Util.getLocalTimeToGMT(currentDateTimeDate);
        Log.i("", "onActivityResult gmtDateTime..............."+gmtDateTime);
        String currentDateTimeGMT=Util.changeDateFormate(gmtDateTime,"MM/dd/yyyy HH:mm:ss","yyyy-MM-dd HH:mm:ss");
        Log.i("", "onActivityResult currentDateTimeGMT..............."+currentDateTimeGMT);
        return currentDateTimeGMT;
    }

    /*public static String getCurrentDateTimeStringGMTForImage() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentDateTimeString=dateFormat.format(date);
        String currentDateTimeWithformat=Utility.changeDateTimeFormate(currentDateTimeString,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss");
        Log.i("", "onActivityResult flight currentDateTimeWithformat..." + currentDateTimeWithformat);
        Date currentDateTimeDate=Utility.convertStringIntoDate(currentDateTimeWithformat, "yyyy-MM-dd hh:mm:ss");
        Log.i("", "onActivityResult flight currentDateTimeDate..." + currentDateTimeDate);
        String gmtDateTime=Utility.getLocalTimeToGMT(currentDateTimeDate);
        Log.i("", "onActivityResult gmtDateTime..............."+gmtDateTime);
        String currentDateTimeGMT=Utility.changeDateFormate(gmtDateTime,"MM/dd/yyyy HH:mm:ss","yyyy-MM-dd HH:mm:ss");
        Log.i("", "onActivityResult currentDateTimeGMT..............."+currentDateTimeGMT);
        return currentDateTimeGMT;
    }*/

    public static String getCurrentDateYearMonthString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String currentDateTimeString=dateFormat.format(date);
        return currentDateTimeString;
    }
    public static Date convertStringIntoDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //String dateInString = "7-Jun-2013";
        System.out.println("dateString......."+dateString);
        Date date=null;


        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(date);
        System.out.println(formatter.format(date));


        return date;
    }
    public static Date convertStringIntoDate(String dateString,String inputFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(inputFormat);
        //String dateInString = "7-Jun-2013";
        System.out.println("dateString......."+dateString);
        Date date=null;


        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(date);
        System.out.println(formatter.format(date));


        return date;
    }
    public static String getLocalTimeToGMT(Date localTime,String outputFormat) {
        //Date will return local time in Java
        //Date localTime = new Date();

        //creating DateFormat for converting time from local timezone to GMT
        DateFormat converter = new SimpleDateFormat(outputFormat);

        //getting GMT timezone, you can get any timezone e.g. UTC
        converter.setTimeZone(TimeZone.getTimeZone("GMT"));

        System.out.println("local time : " + localTime);;
        System.out.println("time in GMT : " + converter.format(localTime));
        return converter.format(localTime);
        //Read more: http://javarevisited.blogspot.com/2012/04/how-to-convert-local-time-to-gmt-in.html#ixzz2i5QriBRI
    }
    public static String convertDateIntoString(Date date) {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // Get the date today using Calendar object.
        //Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(date);
        return reportDate;
    }
    public static String changeDateFormate(String inputDate,String inputFormate,String outputFormate) {
        //String dateStr = "Jul 27, 2011 8:35:29 AM";
        DateFormat readFormat = new SimpleDateFormat(inputFormate);
        DateFormat writeFormat = new SimpleDateFormat(outputFormate);
        Date date = null;
        try
        {
            date = readFormat.parse( inputDate );
        }
        catch ( ParseException e )
        {
            e.printStackTrace();
        }
        if( date != null )
        {
            String formattedDate = writeFormat.format( date );
        }
        return writeFormat.format( date );

    }
    public static String changeDateTimeFormate(String inputDate,String inputFormat,String outFormate) {

        String time24 =null;
        try {
            //String now = new SimpleDateFormat("hh:mm aa").format(new java.util.Date().getTime());
            System.out.println("onActivityResult time in 12 hour format : " + inputDate);
            SimpleDateFormat inFormat = new SimpleDateFormat(inputFormat);
            SimpleDateFormat outFormat = new SimpleDateFormat(outFormate);
            time24 = outFormat.format(inFormat.parse(inputDate));
            System.out.println("onActivityResult time in 24 hour format : " + time24);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return time24;

    }
    public int getWithOFAppRatreDialoge(Context activity)
    {
        //Log.i(TAG, "getImageHeightAndWidth");

        //int imageHeightAndWidth[]= new int[2];
        int screenHeight = getHeight(activity);
        int screenWidth=getWidth(activity);
        //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
        //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
        int topMatgin=0;

        if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 && screenWidth >= 300))
        {
            //Log.i(TAG, "getImageHeightAndWidth mdpi");
            topMatgin=240;

        }

        else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <= 240 && screenWidth >= 220))

        {

            //Log.i(TAG, "getImageHeightAndWidth ldpi");
            topMatgin=200;

        }

        else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <= 500 && screenWidth >= 440))
        {

            //Log.i(TAG, "getImageHeightAndWidth hdpi");
            topMatgin=400;

        }
        else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <= 720 && screenWidth >= 500))
        {
            topMatgin=600;

        }
        else if((screenHeight <= 1920&& screenHeight >= 1280)&& (screenWidth <= 1080 && screenWidth >= 720))
        {
            topMatgin=700;
        }
        else
        {
            topMatgin=700;
        }
        return topMatgin;
    }
    @SuppressLint("NewApi")
    public static int getWidth(Context mContext){
        int width=0;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if(Build.VERSION.SDK_INT>12){
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        }
        else{
            width = display.getWidth();  // deprecated
        }
        return width;
    }

    @SuppressLint("NewApi")
    public static int getHeight(Context mContext)
    {
        int height=0;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if(Build.VERSION.SDK_INT>12){
            Point size = new Point();
            display.getSize(size);
            height = size.y;
        }else
        {
            height = display.getHeight();  // deprecated
        }
        return height;
    }
    public static String getLocalTimeToGMT(Date localTime) {
        //Date will return local time in Java
        //Date localTime = new Date();

        //creating DateFormat for converting time from local timezone to GMT
        DateFormat converter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        //getting GMT timezone, you can get any timezone e.g. UTC
        converter.setTimeZone(TimeZone.getTimeZone("GMT"));

        System.out.println("local time : " + localTime);;
        System.out.println("time in GMT : " + converter.format(localTime));
        return converter.format(localTime);
        //Read more: http://javarevisited.blogspot.com/2012/04/how-to-convert-local-time-to-gmt-in.html#ixzz2i5QriBRI
    }

    public static Bitmap decodeUri(Uri selectedImage,Context con) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(con.getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(con.getContentResolver().openInputStream(selectedImage), null, o2);

    }
    public Bitmap getBitmapFromString(String image_URL)
    {
        Log.i(TAG, "getBitmapFromString ");
        Log.i(TAG, "getBitmapFromString  "+  image_URL);
        image_URL=image_URL.replaceAll(" ", "%20");
        Bitmap srcBitmap;
        if (image_URL == null)
            return null;
        BitmapFactory.Options bmOptions;
        bmOptions = new BitmapFactory.Options();
        Log.i(TAG, "getBitmapFromString bmOptions "+bmOptions);
        bmOptions.inSampleSize = 1;
        return srcBitmap = LoadImage(image_URL, bmOptions);
    }
    private Bitmap LoadImage(String URL, BitmapFactory.Options options)
    {
        Log.i(TAG, "LoadImage");
        Log.i(TAG, "LoadImage  URL "+URL);
        Log.i(TAG, "LoadImage  options "+options);
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedInputStream bufferedInputStream=null;
        try {
            in = OpenHttpConnection(URL);
            Log.i(TAG, "LoadImage  in "+in);
            bufferedInputStream = new BufferedInputStream(in);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream, null, options);
            Log.i(TAG, "LoadImage  bitmap "+bitmap);
            if (in!=null)
            {
                in.close();
            }

        } catch (IOException e1)
        {
            e1.printStackTrace();
            Log.i(TAG, "LoadImage  IOException "+e1);
        }

        return bitmap;
    }
    /**
     * Gets the bitmap from string.
     *
     * @param image_URL
     *            the image_ url
     * @return the bitmap from string
     */
		/*public Bitmap getBitmapFromString(String image_URL)
		{
			Bitmap srcBitmap;
			if (image_URL == null)
				return null;
			BitmapFactory.Options bmOptions;
			bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;
			return srcBitmap = LoadImage(image_URL, bmOptions);
		}*/
    /**
     * Open http connection.
     *
     * @param strURL
     *            the str url
     * @return the input stream
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private InputStream OpenHttpConnection(String strURL) throws IOException {
        InputStream inputStream = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();

        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }
        } catch (Exception ex) {
        }

        return inputStream;
    }
    /**
     * Gets the resized bitmap.
     *
     * @param bm
     *            the bm
     * @param newHeight
     *            the new height
     * @param newWidth
     *            the new width
     * @return the resized bitmap
     */
    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth)
    {

        if (bm == null)
            return null;
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }











/*

    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
    public String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 *//*milliseconds*//*);
            conn.setConnectTimeout(15000 *//*milliseconds*//*);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }



    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }*/



}
