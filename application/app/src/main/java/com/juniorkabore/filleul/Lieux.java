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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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


public class Lieux extends ActionBarActivity {


    private AutoCompleteTextView univ;
    private TextView pageEtape4;
    private Button bouton5;
   public String Univ;
  ///  private String idFacebook;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    public final static String PHOTO = "com.juniorkabore.filleul.PHOTO";
    public final static String LESTATUT = "com.juniorkabore.filleul.LESTATUT";
    public final static String UNIV = "com.juniorkabore.filleul.UNIV";
    public Je_suis jesuis;
    private static final String TAG = "Jesuis";
    String idFacebook;

    String  idFacebookRelation;
    private String statutRelation;
    String parrainIdFacebook;
    String filleulIdFacebook;
    Context context;
    String statutAttrib;
    // Declaration de la base de donnée
    final DatabaseHandler db = new DatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_lieux);
        final SessionManager mSessionManager = new SessionManager(this);

        final Profile profile = Profile.getCurrentProfile();

        pageEtape4 = (TextView) findViewById(R.id.pageEtape4);
        bouton5 =(Button) findViewById(R.id.bouton5);
        // Get a reference to the AutoCompleteTextView in the layout
        univ = (AutoCompleteTextView) findViewById(R.id.univ);
        // Get the string array
        String[] fac = getResources().getStringArray(R.array.fac);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fac);
        univ.setAdapter(adapter);



        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                if(profile != null){

                    String idFacebookF = " " + profile.getId();


                }
            }
        };



        bouton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                idFacebook = profile.getId();
                Univ = ""+univ.getText().toString();

                mSessionManager.setUSERUNIV(Univ);
                mSessionManager.createLoginSession();


                //insertion de donnée dans la base TABLE_LIEUX

                Log.d("Insert LIEUX: ", "Inserting ..");
                db.addLieuxModele(new LieuxModele(Univ, idFacebook));
               /* db.addLieuxModele(new LieuxModele("Universite de Toulon", "10205296156105790"));
                db.addLieuxModele(new LieuxModele("Universite de Rouen", "234444556666667886543234"));
                db.addLieuxModele(new LieuxModele("Universite de Strasbourg", "2344467886543234"));
                db.addLieuxModele(new LieuxModele("Universite de Toulon", "2344543234"));*/
                // Reading all contacts
                Log.d("Reading: ", "Reading all LIEUX..");
                List<LieuxModele> lieuxmodeles = db.getAllLieuxModeles();

                for (LieuxModele lm : lieuxmodeles) {
                    String log = "Id: "+lm.getId()+" ,Lieux: " + lm.getUniv() + " ,IdFacebook: " + lm.getIdFacebook();
                    // Writing Contacts to log
                    Log.d("Lieux: ", log);
                }

                //Fin de l'enregistrement dans la table Lieux

                sendRegistrationIdToBackend();



                //Debut de la requette de selection et d'attribution de Filleul ou parrain.

                Log.d("ATTRIBUTION : ", "Debut de la requette de selection");

                List<StatutModele> statutmodeles = db.getAllStatutModeles();

                for (StatutModele sm : statutmodeles) {
                    String log = "Id: "+sm.getId()+" ,Statut: " + sm.getStatut() + " ,IdFacebook: " + sm.getIdFacebook();
                    // Writing Contacts to log
                    Log.d("Statut: ", log);
                    if(sm.getStatut().equals("Parrain")){
                        Intent AttenteParrain = new Intent(Lieux.this, AttenteParrain.class);
                        startActivity(AttenteParrain);

                    }else{

                        Intent AttenteFilleul = new Intent(Lieux.this, AttenteFilleul.class);
                        startActivity(AttenteFilleul);
                        //rectification pour enlever les barrieres a l'entrer
                        //apres connection attribution de Parrain sera effectuer en background avec envoi de notif
                        /*Intent AttenteParrain = new Intent(Lieux.this, AttenteParrain.class);
                        startActivity(AttenteParrain);*/
                    }

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lieux, menu);
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
            String url = Util.lieux_url+"?universiter= "+Univ+"&idFacebook= "+idFacebook;
            Log.i(TAG, "url = " + url);

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
                   Log.d(TAG, "Fini on peut aller discuter maintenant");
                   // sendAttente();
                    //finish();

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
