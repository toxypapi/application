package com.juniorkabore.filleul;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.squareup.okhttp.OkHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class Besoin extends ActionBarActivity {



    private TextView pageEtape2;
    private CheckBox integration;
    private CheckBox aideScolair;
    private Button bouton3;
    private String inte;
    private String aide;
    private String idFacebook;
    private String idFacebookF;
    private static final String TAG = "Besoin";
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    String integr;
    String aideScolaire;
    String besoinText;
    Context context;
    // Declaration de la base de donnée
    final DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SessionManager mSessionManager = new SessionManager(this);
        context = getApplicationContext();
        setContentView(R.layout.activity_besoin);
        final Profile profile = Profile.getCurrentProfile();

        pageEtape2 = (TextView) findViewById(R.id.pageEtape2);
        integration = (CheckBox) findViewById(R.id.integration);
        aideScolair = (CheckBox) findViewById(R.id.aideScolair);
        bouton3 =(Button) findViewById(R.id.bouton3);





        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                if(profile != null){

                    idFacebookF = " " + profile.getId();

                }
            }
        };


        bouton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                integr = " "+inte.toString();
                aideScolaire = " "+aide.toString();
                idFacebook = " "+profile.getId().toString();
                mSessionManager.setUserBesoin(besoinText);



                //insertion de donnée dans la base TABLE_BESOIN

                Log.d("Insert STATUT: ", "Inserting ..");
                db.addBesoinModele(new BesoinModele(aideScolaire, integr, idFacebook));
                // Reading all contacts
                Log.d("Reading: ", "Reading all STATUT..");
                List<BesoinModele> besoinmodeles = db.getAllBesoinModeles();

                for (BesoinModele bm : besoinmodeles) {
                    String log = "Id: "+bm.getId()+" ,Integration: " + bm.getInte()+ " ,Aide: " + bm.getAide() + " ,IdFacebook: " + bm.getIdFacebook();
                    // Writing Contacts to log
                    Log.d("Besoin: ", log);
                }

                //Fin de l'enregistrement dans la table Statut

                ContinuerBesoin(this) ;
            }
        });



        View.OnClickListener check = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(integration.isChecked() ){
                    inte="integration";
                    aide="null";
                    besoinText = inte;
                    mSessionManager.setUserBesoin(besoinText);

                }
                if(aideScolair.isChecked() ){
                    aide="aidescolaire";
                    inte="null";
                    besoinText = aide;
                    mSessionManager.setUserBesoin(besoinText);
                }

                if ((integration.isChecked()) && (aideScolair.isChecked())){
                    inte="integration";
                    aide="aidescolaire";
                    besoinText = inte+","+aide;
                    mSessionManager.setUserBesoin(besoinText);
                }
				/*else if(!(integration.isChecked())){
					inte="null";

				}
				else if(!(aideScolair.isChecked())){
					aide="null";
				}*/

            }


        };

        integration.setOnClickListener(check);
        aideScolair.setOnClickListener(check);



    }



    //envoi de messsage a une autre activiter
    public void ContinuerBesoin(View.OnClickListener onClickListener) {
        sendRegistrationIdToBackend();
        Intent kiffe = new Intent(this, Kiffes.class);
        startActivity(kiffe);
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_besoin, menu);
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















    //  private RequestQueue mRequestQueue;
    public void sendRegistrationIdToBackend() {
        // Your implementation here.


        new SendGcmToServer().execute();

        // Access the RequestQueue through your singleton class.
        // AppController.getInstance().addToRequestQueue(jsObjRequest, "jsonRequest");

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
            String url = Util.user_url+"?inte="+integr+"&aide="+aideScolaire+"&idFacebook="+idFacebook;
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
