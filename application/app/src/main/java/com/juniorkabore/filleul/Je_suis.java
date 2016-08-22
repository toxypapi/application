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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class Je_suis extends ActionBarActivity {


    private TextView pageEtape1;
    private RadioButton parrain1;
    private RadioButton filleul1;
    private Button bouton2;
    public String statut;
    private String parrain;
    private RadioGroup Statut;
    String idFacebook;
    private String name;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private static final String TAG = "Jesuis";
    Context context;
    // Declaration de la base de donnée
    final DatabaseHandler db = new DatabaseHandler(this);
    Profile profile = Profile.getCurrentProfile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_je_suis);
        final SessionManager mSessionManager = new SessionManager(this);



        pageEtape1 = (TextView) findViewById(R.id.pageEtape1);
        parrain1 = (RadioButton) findViewById(R.id.parrain1);
        filleul1 = (RadioButton) findViewById(R.id.filleul1);
        bouton2 = (Button) findViewById(R.id.bouton2);
        Statut = (RadioGroup) findViewById(R.id.statut);


        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                if(profile != null){



                     idFacebook = profile.getId();

                }
            }
        };



        Statut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                                              @Override
                                              public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                  float f = 0x7f0b0085;
                                                  int p = 0x7f0b0084;

                                                  if (checkedId == R.id.filleul1)
                                                      parrain = "Filleul";

                                                  else if (checkedId == R.id.parrain1)
                                                      parrain = "Parrain";

                                                  else
                                                      parrain = "null";
                                              }
                                          }
        );



        bouton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                statut = parrain;
                idFacebook = profile.getId();
                mSessionManager.setUSERSTATUT(parrain);


                //insertion de donnée dans la base TABLE_STATUT

				 Log.d("Insert STATUT: ", "Inserting ..");
				 db.addStatutModele(new StatutModele(statut, idFacebook));
              /*   db.addStatutModele(new StatutModele("Filleul", "10205296156105790"));
                 db.addStatutModele(new StatutModele("Parrain", "1254478899996969"));
                db.addStatutModele(new StatutModele("Parrain", "234444556666667886543234"));*/

				// Reading all contacts
                Log.d("Reading: ", "Reading all STATUT..");
			        List<StatutModele> statutmodeles = db.getAllStatutModeles();

			        for (StatutModele sm : statutmodeles) {
			            String log = "Id: "+sm.getId()+" ,Statut: " + sm.getStatut() + " ,IdFacebook: " + sm.getIdFacebook();
			                // Writing Contacts to log
			            Log.d("Statut: ", log);
			        }

                //Fin de l'enregistrement dans la table Statut
                ContinuerJeSuis(this);
            }
        });

    }














    //envoi de messsage a une autre activiter
    public void ContinuerJeSuis(View.OnClickListener onClickListener) {
        sendRegistrationIdToBackend();
        Intent besoin = new Intent(this, Besoin.class);
        startActivity(besoin);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_je_suis, menu);
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

            String url = Util.statut_url+"?statut="+statut+"&idFacebook="+idFacebook;
            Log.i("pavan", "url = " + url);

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
