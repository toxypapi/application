package com.juniorkabore.filleul;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.Sharer;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ProfilePictureView profilePictureView;
    private TextView greeting;
    private TextView email;
    private TextView gender;
    private TextView birthday;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private ShareDialog shareDialog;
    private ImageView logo;
    private TextView intro;
    private TextView intro2;
    private TextView intro3;
    private Button bouton1;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private static final String DEBUG_TAG = "MainActivityDebug";
    BroadcastReceiver mRegistrationBroadcastReceiver;
    BroadcastReceiver recieve_chat;
    private ProgressBar mRegistrationProgressBar;
    GoogleCloudMessaging gcm;
    String regid;
    SharedPreferences prefs;
    Context context;
    String msg;
    Util util = new Util();
    String Nom = "";
    String Prenom = "";
    String idFacebook = "";
    String message = " ";
    String type = " ";
    public static final String MAIN = "com.juniorkabore.filleul.MAIN";
    // Declaration de la base de donnée
    final DatabaseHandler db = new DatabaseHandler(this);



    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
            Log.d("HelloFacebook", "Canceled");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
            String title = getString(R.string.error);
            String alertMessage = error.getMessage();
            showResult(title, alertMessage);
        }

        @Override
        public void onSuccess(Sharer.Result result) {
            Log.d("HelloFacebook", "Success!");
            if (result.getPostId() != null) {
                String title = getString(R.string.success);
                String id = result.getPostId();
                String alertMessage = getString(R.string.successfully_posted_post, id);
                showResult(title, alertMessage);
            }
        }

        private void showResult(String title, String alertMessage) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(title)
                    .setMessage(alertMessage)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHashKey(this);

        context = getApplicationContext();
        if(isUserRegistered(context)){

            startActivity(new Intent(MainActivity.this,NewChat.class));
            finish();

        }

        // Check device for Play Services APK. If check succeeds, proceed with
        //  GCM registration.

        if (checkPlayServices()) {
          // gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(this);
            if (regid.isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
        //Login facebook

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        updateUI();
                    }

                    @Override
                    public void onCancel() {

                        updateUI();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        updateUI();
                    }
                });

        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, shareCallback);

        setContentView(R.layout.activity_main);

       // showHashKey(this);



        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();




        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                updateUI();
                // It's possible that we were waiting for Profile to be populated in order to
            }
        };

        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        greeting = (TextView) findViewById(R.id.greeting);
        email = (TextView) findViewById(R.id.email);
        gender = (TextView) findViewById(R.id.gender);
        birthday = (TextView) findViewById(R.id.birthday);
        logo = (ImageView) findViewById(R.id.logo);
        intro = (TextView) findViewById(R.id.intro);
        intro2 = (TextView) findViewById(R.id.intro2);
        intro3 = (TextView) findViewById(R.id.intro3);
        bouton1 = (Button) findViewById(R.id.bouton1);


        List<String> PERMISSIONS= Arrays.asList("user_location","public_profile","user_friends");
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(PERMISSIONS);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


        

    }



    @Override
    protected void onResume() {
        super.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onResume methods of the primary Activities that an app may be
        // launched into.
        AppEventsLogger.activateApp(this);

        updateUI();
    }



    @Override
    public void onPause() {
        super.onPause();

        // Call the 'deactivateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onPause methods of the primary Activities that an app may be
        // launched into.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
        accessTokenTracker.stopTracking();
    }


    // A voir comment injecter de la donnée dans les getter de sessionManager
    private void getUserProfile() {
        SessionManager mSessionManager = new SessionManager(this);

        String userFaceBookid = mSessionManager.getFacebookId();
        String userSessionToken = mSessionManager.getUserToken();
        String userDeviceId = Ultilities.getDeviceId(this);

        /*String[] params = { userSessionToken, userDeviceId, userFaceBookid };
        new BackGroundTaskForUserProfile().execute(params);*/
    }
// A voir comment injecter de la donnée dans les getter de sessionManager






    private void updateUI() {
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        final Profile profile = Profile.getCurrentProfile();
        if (enableButtons && profile != null) {
            Nom =  profile.getLastName();
            Prenom = profile.getFirstName();
            idFacebook = ""+profile.getId();

            final SessionManager mSessionManager = new SessionManager(this);

            
            //Injection de data dans les setters SessionManager
            mSessionManager.setFacebookId(idFacebook);
            mSessionManager.setUserName(Prenom);
            /*if(mSessionManager.isLoggedIn()) {
                if(mSessionManager.isChat() != true){
                    recieve_chat = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            message = intent.getStringExtra("message");
                            Log.i(TAG, "in local braod dans MainActivity() " + message);
                            type = "recieve";

                        }
                    };
                   // LocalBroadcastManager.getInstance(this).registerReceiver(recieve_chat, new IntentFilter("message_recieved"));
                    Intent intentMsg = new Intent(MainActivity.this, NewChat.class);
                    intentMsg.putExtra(MAIN, message);
                    Log.i(TAG, "in local braod dans MainActivity() " + message);
                    startActivity(intentMsg);
                }else{
                    Intent i = new Intent(MainActivity.this, AttenteParrain.class);
                    startActivity(i);
                }
*//*
                Intent i = new Intent(MainActivity.this, AttenteParrain.class);
                startActivity(i);*//*
            }*/
            logo.setVisibility(View.INVISIBLE);
            profilePictureView.setVisibility(View.VISIBLE);
            profilePictureView.setProfileId(idFacebook);
            profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
            greeting.setText(getString(R.string.hello_user, profile.getFirstName()));
            logo.setVisibility(View.INVISIBLE);
            intro.setVisibility(View.INVISIBLE);
            intro2.setVisibility(View.INVISIBLE);
            intro3.setVisibility(View.INVISIBLE);
            bouton1.setVisibility(View.VISIBLE);

            final AccessToken accessToken = AccessToken.getCurrentAccessToken();
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            System.out.println(" ..... " + accessToken.getExpires().toString());
                            System.out.println(profile.getProfilePictureUri(30, 30).toString());
                            try {
                                String name=" " + profile.getLastName();
                                mSessionManager.setUserToken(accessToken.getToken());


                                bouton1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Gets the URL from the UI's text field.
                                        /*String stringUrl = Util.selectAttribution+"?idFacebook="+idFacebook;
                                        Log.d("URL D'ATTRIBUTION : ", stringUrl);*/
                                        String stringUrl0 = Util.facebookData_url+"?nom="+Nom+"&prenom="+Prenom+"&idFacebook="+idFacebook;
                                        String stringUrl1 = Util.register_url+"?name="+""+profile.getFirstName()+"&idFacebook="+""+profile.getId()+"&regId="+""+regid;
                                        Log.d("URL D'ATTRIBUTION : ", stringUrl0);
                                        Log.d("URL D'ATTRIBUTION : ", stringUrl1);
                                        ConnectivityManager connMgr = (ConnectivityManager)
                                                getSystemService(Context.CONNECTIVITY_SERVICE);
                                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                                        if (networkInfo != null && networkInfo.isConnected()) {

                                         /*   if(mSessionManager.isLoggedIn()) {
                                                Intent i = new Intent(MainActivity.this, AttenteParrain.class);
                                                startActivity(i);
                                            }else{
                                                //  new DownloadWebpageTask().execute(stringUrl);
                                                new sendRegistrationIdToBackend().execute(stringUrl0);
                                                new sendRegistrationIdToBackend1().execute(stringUrl1);
                                                //sendRegistrationIdToBackend1();
                                                //sendRegistrationIdToBackend();
                                                //mSessionManager.createLoginSession();
                                                Intent je_suis = new Intent(MainActivity.this, Je_suis.class);
                                                startActivity(je_suis);
                                            }*/

                                            //  new DownloadWebpageTask().execute(stringUrl);
                                            new sendRegistrationIdToBackend().execute(stringUrl0);
                                            new sendRegistrationIdToBackend1().execute(stringUrl1);
                                            //sendRegistrationIdToBackend1();
                                            //sendRegistrationIdToBackend();
                                            //mSessionManager.createLoginSession();
                                            Intent je_suis = new Intent(MainActivity.this, Je_suis.class);
                                            startActivity(je_suis);


                                        } else {
                                            Log.d(TAG,"No network connection available.");
                                        }

/*
                                        Intent je_suis = new Intent(MainActivity.this, Je_suis.class);
                                        startActivity(je_suis);*/

                                    }

                                });



                                System.out.println(object.getString("id"));
                                System.out.println(object.getString("email"));
                                System.out.println(object.getString("birthday"));
                                System.out.println(object.getString("name"));
                                System.out.println(object.getString("gender").toUpperCase());

                                email.setText(object.getString("email"));
                                gender.setText(object.getString("gender").toUpperCase());
                                birthday.setText(object.getString("birthday"));



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            request.executeAsync();
        } else {
            logo.setVisibility(View.VISIBLE);
            logo.setVisibility(View.VISIBLE);
            intro.setVisibility(View.VISIBLE);
            intro2.setVisibility(View.VISIBLE);
            intro3.setVisibility(View.VISIBLE);
            bouton1.setVisibility(View.INVISIBLE);
            profilePictureView.setVisibility(View.INVISIBLE);
            profilePictureView.setProfileId(null);
            greeting.setText(null);
            email.setText(null);
            gender.setText(null);
            birthday.setText(null);
        }
    }




/*

    public void ContinuerMain(View view){
            Intent je_suis = new Intent(MainActivity.this, Je_suis.class);
            startActivity(je_suis);

    }
*/

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    public static void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.juniorkabore.filleul.MainActivity", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }



    //chek google play serveice
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Util.PROPERTY_REG_ID, regId);
        editor.putInt(Util.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }



    private void storeUserDetails(Context context) {
        final Profile profile = Profile.getCurrentProfile();
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Util.EMAIL, ""+profile.getId());
        editor.putString(Util.USER_NAME, "" + profile.getFirstName());
        editor.commit();
    }








   private void registerInBackground() {
        new AsyncTask() {



            @Override
            protected String doInBackground(Object[] params) {


                try {

                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
                    }
                    regid = gcm.register(Util.SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;



                    // You should send the registration ID to your server over HTTP,
                    //GoogleCloudMessaging gcm;/ so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    // sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the registration ID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;



            }
        }.execute();

    }


//858434530841704

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(Util.PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = prefs.getInt(Util.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }


    public boolean isUserRegistered(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String User_name = prefs.getString(Util.USER_NAME, "");
        if (User_name.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return false;
        }

        return true;
    }


    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the registration ID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }




    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }






    private class sendRegistrationIdToBackend extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Toast.makeText(context,"response "+result,Toast.LENGTH_LONG).show();

            if (result != null) {
                if (result.equals("success")) {

                    /*storeUserDetails(context);
                    startActivity(new Intent(MainActivity.this, ChatActivity.class));*/
                    Log.d(TAG,"Fini on peut aller discuter maintenant");
                    finish();

                } else {

                    Toast.makeText(context, "Try Again " + result, Toast.LENGTH_LONG).show();
                }


            }else{

                Toast.makeText(context, "Check net connection ", Toast.LENGTH_LONG).show();
            }
        }
    }



    private class sendRegistrationIdToBackend1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl1(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Toast.makeText(context,"response "+result,Toast.LENGTH_LONG).show();

            if (result != null) {
                if (result.equals("success")) {

                    /*storeUserDetails(context);
                    startActivity(new Intent(MainActivity.this, ChatActivity.class));*/
                    Log.d(TAG,"Fini on peut aller discuter maintenant");
                    finish();

                } else {

                    Toast.makeText(context, "Try Again" + result, Toast.LENGTH_LONG).show();
                }


            }else{

                Toast.makeText(context, "Check net connection ", Toast.LENGTH_LONG).show();
            }

        }
    }




    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
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



    private String downloadUrl1(String miurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(miurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 );
            conn.setConnectTimeout(15000);
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
    }


















    //  private RequestQueue mRequestQueue;
   /* public void sendRegistrationIdToBackend() {
        new SendGcmToServer().execute();
    }


    private class SendGcmToServer extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            final Profile profile = Profile.getCurrentProfile();

            //String url = Util.register_url+"?name="+""+profile.getFirstName()+"&idFacebook="+""+profile.getId()+"&regId="+regid;
            String url = Util.facebookData_url+"?nom="+Nom+"&prenom="+Prenom+"&idFacebook="+idFacebook;
            Log.i(TAG, "url " + url);

            OkHttpClient client_for_getMyFriends = new OkHttpClient();

            String response = null;
            // String response=Utility.callhttpRequest(url);

            try {
                url = url.replace(" ", "%20");
                response = callOkHttpRequest(new URL(url),client_for_getMyFriends);
                for (String subString : response.split("<script", 2)) {
                    response = subString;
                    break;
                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //Toast.makeText(context,"response "+result,Toast.LENGTH_LONG).show();

            if (result != null) {
                if (result.equals("success")) {

                    storeUserDetails(context);
                    startActivity(new Intent(MainActivity.this, ChatActivity.class));
                   Log.d(TAG,"Fini on peut aller discuter maintenant");
                    finish();

                } else {

                    Toast.makeText(context, "Try Again" + result, Toast.LENGTH_LONG).show();
                }


            }else{

                Toast.makeText(context, "Check net connection ", Toast.LENGTH_LONG).show();
            }

        }


    }















    //  private RequestQueue mRequestQueue;
    public void sendRegistrationIdToBackend1() {
        // Your implementation here.


        new SendGcmToServer1().execute();

    }


    private class SendGcmToServer1 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            final Profile profile = Profile.getCurrentProfile();

           String url = Util.register_url+"?name="+""+profile.getFirstName()+"&idFacebook="+""+profile.getId()+"&regId="+""+regid;
            //  String url = Util.register_url+"?regID="+""+regid+"&name="+""+profile.getFirstName()+"&idFacebook="+""+profile.getId();
            Log.i(TAG, "url " + url);

            OkHttpClient client_for_getMyFriends = new OkHttpClient();

            String response = null;
            // String response=Utility.callhttpRequest(url);

            try {
                url = url.replace(" ", "%20");
                response = callOkHttpRequest(new URL(url),client_for_getMyFriends);
                for (String subString : response.split("<script", 2)) {
                    response = subString;
                    break;
                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //Toast.makeText(context,"response "+result,Toast.LENGTH_LONG).show();

            if (result != null) {
                if (result.equals("success")) {

                    storeUserDetails(context);
                    startActivity(new Intent(MainActivity.this, ChatActivity.class));
                    Log.d(TAG,"Fini on peut aller discuter maintenant");
                    finish();

                } else {

                    Toast.makeText(context, "Try Again" + result, Toast.LENGTH_LONG).show();
                }


            }else{

                Toast.makeText(context, "Check net connection ", Toast.LENGTH_LONG).show();
            }

        }


    }*/






    // Http request using OkHttpClient
    String callOkHttpRequest(URL url, OkHttpClient tempClient)
            throws IOException {

        HttpURLConnection connection = tempClient.open(url);

        connection.setConnectTimeout(40000);
        InputStream in = null;
        try {
            // Read the response.
            in = connection.getInputStream();
            byte[] response = readFully(in);
            return new String(response, "UTF-8");
        } finally {
            if (in != null)
                in.close();
        }
    }

    byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int count; (count = in.read(buffer)) != -1;) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }











}
