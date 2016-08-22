package com.juniorkabore.filleul;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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


public class Kiffes extends ActionBarActivity {

    private TextView pageEtape3;
    private CheckBox sport1;
    private CheckBox musique1;
    private CheckBox gaming1;
    private CheckBox danse1;
    private CheckBox virer1;
    private CheckBox culture1;
    private Button bouton4;
    private String sport;
    private String musique;
    private String gaming;
    private String danse;
    private String virer;
    private String culture;
    String idFacebook;
    private static final String TAG = "Kiffes";
    Context context;
    String kiffeText;

    // Declaration de la base de donnée
    final DatabaseHandler db = new DatabaseHandler(this);

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SessionManager mSessionManager = new SessionManager(this);
        context = getApplicationContext();
        setContentView(R.layout.activity_kiffes);

        final Profile profile = Profile.getCurrentProfile();

        pageEtape3 = (TextView) findViewById(R.id.pageEtape3);
        sport1 = (CheckBox) findViewById(R.id.sport1);
        musique1 = (CheckBox) findViewById(R.id.musique1);
        gaming1 = (CheckBox) findViewById(R.id.gaming1);
        danse1 = (CheckBox) findViewById(R.id.danse1);
        virer1 = (CheckBox) findViewById(R.id.virer1);
        culture1 = (CheckBox) findViewById(R.id.culture1);
        bouton4 =(Button) findViewById(R.id.bouton4);



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


        bouton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sport1 = sport.toString();
                String musique1 = musique.toString();
                String gaming1 = gaming.toString();
                String danse1 = danse.toString();
                String virer1 = virer.toString();
                String culture1 = culture.toString();
                idFacebook = profile.getId();
                mSessionManager.setUSERINTERET(kiffeText);


                //insertion de donnée dans la base TABLE_KIFFES

                Log.d("Insert STATUT: ", "Inserting ..");
                db.addKiffesModele(new KiffesModele(sport1, musique1,gaming1,danse1,virer1,culture1,idFacebook));
                // Reading all contacts
                Log.d("Reading: ", "Reading all STATUT..");
                List<KiffesModele> kiffesmodeles = db.getAllKiffesModeles();

                for (KiffesModele km : kiffesmodeles) {
                    String log = "Id: "+km.getId()+" ,Sport: " + km.getSport() + " ,Musique: " + km.getMusique() + " ,Gaming: " + km.getGaming() + " ,Danse: " + km.getDanse() + " ,Virer: " + km.getVirer() + " ,Culture: " + km.getCulture() + " ,IdFacebook: " + km.getIdFacebook();
                    // Writing Contacts to log
                    Log.d("Kiffes: ", log);
                }

                //Fin de l'enregistrement dans la table Statut

                ContinuerKif(this);

            }
        });







        View.OnClickListener check1 = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //test une seul case cocher

                if(sport1.isChecked() ){
                    sport="sport";
                    musique="null";
                    gaming="null";
                    danse="null";
                    virer="null";
                    culture="null";
                    kiffeText = sport;
                    mSessionManager.setUSERINTERET(kiffeText);


                }
                if(musique1.isChecked() ){
                    sport="null";
                    musique="musique";
                    gaming="null";
                    danse="null";
                    virer="null";
                    culture="null";
                    kiffeText = musique;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if(gaming1.isChecked() ){
                    sport="null";
                    musique="null";
                    gaming="gaming";
                    danse="null";
                    virer="null";
                    culture="null";
                    kiffeText = gaming;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if(danse1.isChecked() ){
                    sport="null";
                    musique="null";
                    gaming="null";
                    danse="danse";
                    virer="null";
                    culture="null";
                    kiffeText = danse;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if(virer1.isChecked() ){
                    sport="null";
                    musique="null";
                    gaming="null";
                    danse="null";
                    virer="virer";
                    culture="null";
                    kiffeText = virer;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if(culture1.isChecked()){
                    sport="null";
                    musique="null";
                    gaming="null";
                    danse="null";
                    virer="null";
                    culture="culture";
                    kiffeText = culture;
                    mSessionManager.setUSERINTERET(kiffeText);
                }

                if( musique1.isChecked() && (gaming1.isChecked()) ){
                    sport="null";
                    musique="musique";
                    gaming="gaming";
                    danse="null";
                    virer="null";
                    culture="null";
                    kiffeText = musique+","+gaming;
                    mSessionManager.setUSERINTERET(kiffeText);

                }
                if( musique1.isChecked() && (sport1.isChecked()) ){
                    sport="sport";
                    musique="musique";
                    gaming="null";
                    danse="null";
                    virer="null";
                    culture="null";
                    kiffeText = sport+","+musique;
                    mSessionManager.setUSERINTERET(kiffeText);

                }
                else if( musique1.isChecked() && (danse1.isChecked()) ){
                    sport="null";
                    musique="musique";
                    gaming="null";
                    danse="danse";
                    virer="null";
                    culture="null";
                    kiffeText = musique+","+danse;
                    mSessionManager.setUSERINTERET(kiffeText);

                }
                if( musique1.isChecked() && (virer1.isChecked()) ){
                    sport="null";
                    musique="musique";
                    gaming="null";
                    danse="null";
                    virer="virer";
                    culture="null";
                    kiffeText = musique+","+virer;
                    mSessionManager.setUSERINTERET(kiffeText);

                }
                if( musique1.isChecked() && (culture1.isChecked()) ){
                    sport="null";
                    musique="musique";
                    gaming="null";
                    danse="null";
                    virer="null";
                    culture="culture";
                    kiffeText = sport+","+musique;
                    mSessionManager.setUSERINTERET(kiffeText);

                }

                if( virer1.isChecked() && (culture1.isChecked()) ){
                    sport="null";
                    musique="null";
                    gaming="null";
                    danse="null";
                    virer="virer";
                    culture="culture";
                    kiffeText = virer+","+culture ;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( virer1.isChecked() && (danse1.isChecked()) ){
                    sport="null";
                    musique="null";
                    gaming="null";
                    danse="danse";
                    virer="virer";
                    culture="null";
                    kiffeText = danse+","+virer;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( virer1.isChecked() && (gaming1.isChecked()) ){
                    sport="null";
                    musique="null";
                    gaming="gaming";
                    danse="null";
                    virer="virer";
                    culture="null";
                    kiffeText = gaming+","+virer;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( virer1.isChecked() && (sport1.isChecked()) ){
                    sport="sport";
                    musique="null";
                    gaming="null";
                    danse="null";
                    virer="virer";
                    culture="null";
                    kiffeText = sport+","+virer;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( sport1.isChecked() && (gaming1.isChecked()) ){
                    sport="sport";
                    musique="null";
                    gaming="gaming";
                    danse="null";
                    virer="null";
                    culture="null";
                    kiffeText = sport+","+gaming;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( sport1.isChecked() && (danse1.isChecked()) ){
                    sport="sport";
                    musique="null";
                    gaming="null";
                    danse="danse";
                    virer="null";
                    culture="null";
                    kiffeText = sport+","+danse;
                    mSessionManager.setUSERINTERET(kiffeText);

                }
                if( danse1.isChecked() && (gaming1.isChecked()) ){
                    sport="null";
                    musique="null";
                    gaming="gaming";
                    danse="null";
                    virer="null";
                    culture="null";
                    kiffeText = gaming+","+danse;
                    mSessionManager.setUSERINTERET(kiffeText);

                }
                if( gaming1.isChecked() && (culture1.isChecked()) ){
                    sport="null";
                    musique="null";
                    gaming="gaming";
                    danse="null";
                    virer="null";
                    culture="culture";
                    kiffeText = gaming+","+culture ;
                    mSessionManager.setUSERINTERET(kiffeText);

                }
                //trois choix

                if( musique1.isChecked() && (gaming1.isChecked()) && (virer1.isChecked())){
                    sport="null";
                    musique="musique";
                    gaming="gaming";
                    danse="null";
                    virer="virer";
                    culture="null";
                    kiffeText = musique+","+gaming+","+virer;
                    mSessionManager.setUSERINTERET(kiffeText);

                }

                if( musique1.isChecked() && (gaming1.isChecked()) && (danse1.isChecked())){
                    sport="null";
                    musique="musique";
                    gaming="gaming";
                    danse="danse";
                    virer="null";
                    culture="null";
                    kiffeText = musique+","+gaming+","+danse;
                    mSessionManager.setUSERINTERET(kiffeText);

                }
                if( musique1.isChecked() && (gaming1.isChecked()) && (culture1.isChecked())){
                    sport="null";
                    musique="musique";
                    gaming="gaming";
                    danse="null";
                    virer="null";
                    culture="culture";
                    kiffeText = musique+","+gaming+","+culture ;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( musique1.isChecked() && (gaming1.isChecked()) && (sport1.isChecked())){
                    sport="sport";
                    musique="musique";
                    gaming="gaming";
                    danse="null";
                    virer="null";
                    culture="null";
                    kiffeText = sport+","+musique+","+gaming;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( musique1.isChecked() && (culture1.isChecked()) && (sport1.isChecked())){
                    sport="sport";
                    musique="musique";
                    gaming="null";
                    danse="null";
                    virer="null";
                    culture="culture";
                    kiffeText = sport+","+musique+","+culture ;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( musique1.isChecked() && (danse1.isChecked()) && (sport1.isChecked())){
                    sport="sport";
                    musique="musique";
                    gaming="null";
                    danse="danse";
                    virer="null";
                    culture="null";
                    kiffeText = sport+","+musique+","+danse;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( musique1.isChecked() && (virer1.isChecked()) && (sport1.isChecked())){
                    sport="sport";
                    musique="musique";
                    gaming="null";
                    danse="null";
                    virer="virer";
                    culture="null";
                    kiffeText = sport+","+musique+","+virer;
                    mSessionManager.setUSERINTERET(kiffeText);
                }


                //quatre choix
                if( musique1.isChecked() && (gaming1.isChecked()) && (danse1.isChecked()) && (virer1.isChecked())){
                    sport="null";
                    musique="musique";
                    gaming="gaming";
                    danse="danse";
                    virer="virer";
                    culture="null";
                    kiffeText = musique+","+gaming+","+danse+","+virer;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( musique1.isChecked() && (gaming1.isChecked()) && (danse1.isChecked()) && (culture1.isChecked())){
                    sport="null";
                    musique="musique";
                    gaming="gaming";
                    danse="danse";
                    virer="null";
                    culture="culture";
                    kiffeText = musique+","+gaming+","+danse+","+culture ;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( musique1.isChecked() && (gaming1.isChecked()) && (danse1.isChecked()) && (sport1.isChecked())){
                    sport="sport";
                    musique="musique";
                    gaming="gaming";
                    danse="danse";
                    virer="null";
                    culture="null";
                    kiffeText = sport+","+musique+","+gaming+","+danse;
                    mSessionManager.setUSERINTERET(kiffeText);
                }

                if( musique1.isChecked() && (gaming1.isChecked()) && (danse1.isChecked()) && (virer1.isChecked()) && (sport1.isChecked())){
                    sport="sport";
                    musique="musique";
                    gaming="gaming";
                    danse="danse";
                    virer="virer";
                    culture="null";
                    kiffeText = sport+","+musique+","+gaming+","+danse+","+virer;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( musique1.isChecked() && (gaming1.isChecked()) && (danse1.isChecked()) && (virer1.isChecked()) && (culture1.isChecked())){
                    sport="null";
                    musique="musique";
                    gaming="gaming";
                    danse="danse";
                    virer="virer";
                    culture="culture";
                    kiffeText = musique+","+gaming+","+danse+","+virer+","+culture ;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( musique1.isChecked() && (gaming1.isChecked()) && (danse1.isChecked()) && (culture1.isChecked()) && (sport1.isChecked())){
                    sport="sport";
                    musique="musique";
                    gaming="gaming";
                    danse="danse";
                    virer="null";
                    culture="culture";
                    kiffeText = sport+","+musique+","+gaming+","+danse+","+culture ;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                if( musique1.isChecked() && (gaming1.isChecked()) && (virer1.isChecked()) && (culture1.isChecked()) && (sport1.isChecked())){
                    sport="sport";
                    musique="musique";
                    gaming="gaming";
                    danse="null";
                    virer="virer";
                    culture="culture";
                    kiffeText = sport+","+musique+","+gaming+","+virer+","+culture ;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
                //test case multiple cocher

                if(musique1.isChecked() && (gaming1.isChecked()) && (danse1.isChecked()) && (virer1.isChecked()) && (culture1.isChecked()) && (sport1.isChecked())){
                    sport="sport";
                    musique="musique";
                    gaming="gaming";
                    danse="danse";
                    virer="virer";
                    culture="culture";
                    kiffeText = sport+","+musique+","+gaming+","+danse+","+virer+","+culture ;
                    mSessionManager.setUSERINTERET(kiffeText);
                }
            }

        };

        sport1.setOnClickListener(check1);
        musique1.setOnClickListener(check1);
        gaming1.setOnClickListener(check1);
        danse1.setOnClickListener(check1);
        virer1.setOnClickListener(check1);
        culture1.setOnClickListener(check1);








    }



    //envoi de messsage a une autre activiter
    public void ContinuerKif(View.OnClickListener onClickListener) {
        sendRegistrationIdToBackend();
        Intent lieu = new Intent(this, Lieux.class);
        startActivity(lieu);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kiffes, menu);
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
            String url = Util.kiffes_url+"?sport= "+sport+"&musique= "+musique+"&gaming= "+gaming+"&danse= "+danse+"&virer= "+virer+"&culture= "+culture+"&idFacebook= "+idFacebook;
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
