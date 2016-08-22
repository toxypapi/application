package com.juniorkabore.filleul;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Bingo extends ActionBarActivity {

    private ProfilePictureView pictureAttribution;
    private TextView Nom;
    private ListView donnees;
    private String name;
    String Univ = "";
    String  idFacebookRelation;
    private String statutRelation;
    String parrainIdFacebook;
    String filleulIdFacebook;
    private String parrain = "Parrain";
    private Button valider;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    final MainActivity mainActivity = new MainActivity();
    private static final String TAG = "BingoActivity";
    private static final String TAGDEBUG = "DebugBingo";
    public static final String IDRELATIONFILLEUL = "com.juniorkabore.filleul.Bingo.IDRELATIONFILLEUL";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public Je_suis jesuis;
    String idFacebook;
    public ArrayList<Contact> relation;
    String message = "Voila vous avez la balle dans votre camp";
    GoogleCloudMessaging gcm;
    String regid;
    SharedPreferences prefs;
    String msg;
    Context context;
    String statutAttrib;
    String relationID;
    String url;
    String urlF;
    boolean ok = false;



    final DatabaseHandler db = new DatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SessionManager mSessionManager = new SessionManager(this);
        context = getApplicationContext();
        ArrayList<String> donnee = new ArrayList<String>();
        Random random = new Random();
        setContentView(R.layout.activity_bingo);

        valider =(Button) findViewById(R.id.valider);

        // Find the user's profile picture custom view
        pictureAttribution = (ProfilePictureView) findViewById(R.id.profilePictureRelation);
        pictureAttribution.setCropped(true);
        //ArrayList<String> donnee = new ArrayList<String>();

        Nom = new TextView(this);

        final Profile profile = Profile.getCurrentProfile();

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        if (profile != null) {

                            idFacebook = profile.getId();

                            Intent receive = getIntent();
                             relationID = receive.getStringExtra(AttenteFilleul.IDRELATION);
                            pictureAttribution.setProfileId(relationID);
                        }
                    }
                });
        request.executeAsync();


        /*
         * Bouton permettant l'attribution d'un parrain/filleul et permettant
         * du coup l'enregistrement des idFacebook dans la table TABLE_ATTRIBUTION
         * */

        valider.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("VALIDER", "Je vien de valider la relation");
                loadDictionary();

            }
        });




    }

    public String recupStatut(){


        List<StatutModele> statutmodeles = db.getAllStatutModeles();
        for (StatutModele sm : statutmodeles) {
            String log = "Id: "+sm.getId()+" ,Statut: " + sm.getStatut() + " ,IdFacebook: " + sm.getIdFacebook();
            // Writing Contacts to log
            statutAttrib = sm.getStatut();
            Log.d("JESUIS",statutAttrib);
            Log.d("Statut: ", log);

        }

        return statutAttrib;
    }





    public void registerAttribution(){
        final SessionManager mSessionManager = new SessionManager(this);
        if (recupStatut().equals("Parrain")){
            Log.d("recupStatut", "parrain");
            parrainIdFacebook = idFacebook;
            filleulIdFacebook = relationID;
            Log.d("INSERTION IF :", "DANS LA TABLE ATTRIBUTION");
            db.addAttributionModele(new AttributionModele(filleulIdFacebook, parrainIdFacebook));
            sendAttribution();
        }else{
            Log.d("recupStatut", "filleul");
            parrainIdFacebook = relationID;
            filleulIdFacebook = idFacebook;
            Log.d("INSERTION ELSE :", "DANS LA TABLE ATTRIBUTION");
            db.addAttributionModele(new AttributionModele(filleulIdFacebook, parrainIdFacebook));
            sendAttribution();
        }
        //Reading all Contacts
        Log.d("Reading: ", "Reading all Attributon");
        List<AttributionModele> attributionModeles = db.getAllAttributionModeles();

        for (AttributionModele am : attributionModeles) {
            String log = "Id: " + am.getId() + " ,FilleulIdFacebook: " + am.getFilleulIdFacebook()  + " ,ParrainIdFacebook: " + am.getParrainIdFacebook();
            // Writing Attribution to log
            Log.d("Attribution dans Bingo.class : ", log);
        }


        //send notification relation
        sendPushToBackend();
        sendPushToBackendF();

        Intent chat = new Intent(Bingo.this,AttenteParrain.class);
        //Intent chat = new Intent(Bingo.this,ChatActivity.class);
       // Intent chat = new Intent(Bingo.this,AttenteParrain.class);
        chat.putExtra(Bingo.IDRELATIONFILLEUL, relationID);

        startActivity(chat);
    }






    public void sendAttribution(){
        new SendRelation().execute();
    }



    private class SendRelation extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            final Profile profile = Profile.getCurrentProfile();

            String url = Util.attribution_url+"?parrainIdF="+relationID+"&filleulIdF="+profile.getId();
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
/*
                    storeUserDetails(context);
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



    //push a filleul





    public void sendPushToBackendF() {
        new SendPushF().execute();
    }

    private class SendPushF extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            urlF = Util.send_chat_url+"?idFacebook_id="+idFacebook+"&message="+ message;

            Log.i(TAG, "urlF = " + urlF);
            OkHttpClient client_for_getMyFriends = new OkHttpClient();

            String response = null;
            // String response=Utility.callhttpRequest(url);

            try {
                urlF = urlF.replace(" ", "%20");
//                url = new String(url.trim().replace(" ", "%20")
//                        .replace(",", "%2c").replace("(", "%28").replace(")", "%29")
//                        .replace("!", "%21").replace("=", "%3D").replace("<", "%3C")
//                        .replace(">", "%3E").replace("#", "%23").replace("$", "%24")
//                        .replace("'", "%27").replace("*", "%2A").replace("-", "%2D")
//                        .replace(".", "%2E").replace("/", "%2F").replace(":", "%3A")
//                        .replace(";", "%3B").replace("?", "%3F").replace("@", "%40")
//                        .replace("[", "%5B").replace("\\", "%5C").replace("]", "%5D")
//                        .replace("_", "%5F").replace("`", "%60").replace("{", "%7B")
//                        .replace("|", "%7C").replace("}", "%7D"));
                response = callOkHttpRequest(new URL(urlF), client_for_getMyFriends);
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
        }
    }




    //Push a filleul




    public void sendPushToBackend() {
        new SendPush().execute();
    }

    private class SendPush extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            url = Util.send_chat_url+"?idFacebook_id="+relationID+"&message="+ message;

            Log.i(TAG, "url = " + url);
            OkHttpClient client_for_getMyFriends = new OkHttpClient();

            String response = null;
            // String response=Utility.callhttpRequest(url);

            try {
                url = url.replace(" ", "%20");
//                url = new String(url.trim().replace(" ", "%20")
//                        .replace(",", "%2c").replace("(", "%28").replace(")", "%29")
//                        .replace("!", "%21").replace("=", "%3D").replace("<", "%3C")
//                        .replace(">", "%3E").replace("#", "%23").replace("$", "%24")
//                        .replace("'", "%27").replace("*", "%2A").replace("-", "%2D")
//                        .replace(".", "%2E").replace("/", "%2F").replace(":", "%3A")
//                        .replace(";", "%3B").replace("?", "%3F").replace("@", "%40")
//                        .replace("[", "%5B").replace("\\", "%5C").replace("]", "%5D")
//                        .replace("_", "%5F").replace("`", "%60").replace("{", "%7B")
//                        .replace("|", "%7C").replace("}", "%7D"));
                response = callOkHttpRequest(new URL(url), client_for_getMyFriends);
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
        }
    }

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



    private void loadDictionary() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    registerAttribution();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }




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
            conn.setReadTimeout(10000 /*milliseconds*/);
            conn.setConnectTimeout(15000 /*milliseconds*/);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(TAG, "The response is: " + response);
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
















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bingo, menu);
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


}
