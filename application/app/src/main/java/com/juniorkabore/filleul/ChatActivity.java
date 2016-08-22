//package com.juniorkabore.filleul;
//
//import android.app.Activity;
//import android.app.NotificationManager;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.graphics.Bitmap;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v4.content.LocalBroadcastManager;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//
//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookSdk;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.Profile;
//import com.squareup.okhttp.OkHttpClient;
//
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//
//public class ChatActivity extends Activity {
//
//    private EditText editText_chat_message;
//    private ListView listView_chat_messages;
//    private Button button_send_chat;
//    public List<ChatObject> chat_list;
//    String message = "";
//    Bingo bingo = new Bingo();
//    Je_suis jesuis = new Je_suis();
//    AttenteParrain attenteParrain = new AttenteParrain();
//    public static final String TAG = "ChatActivity";
//    public static final String TAGDEBUG = "Affichage de debug ChatActivity";
//    BroadcastReceiver recieve_chat;
//    final DatabaseHandler db = new DatabaseHandler(this);
//    private CallbackManager callbackManager;
//    String idFacebook;
//    String idRelationDuParrain = "";
//    String idRelationDuFilleul;
//    public final static String RELATIONDUPARRAIN = "relation du parrain";
//    String type = "";
//    public ArrayList<ChatObject> listChatData = new ArrayList<ChatObject>();
//    List<String> messageMulti = new ArrayList<>();
//    private ChatAdabter chatAdabter;
//    private Bitmap userImage;
//    private Bitmap friendImage;
//    IntentFilter filter;
//
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//        final ChatObject chatM = new ChatObject();
//        final SessionManager mSessionManager = new SessionManager(this);
//        editText_chat_message= (EditText) findViewById(R.id.editText_chat_message);
//        listView_chat_messages= (ListView) findViewById(R.id.listView_chat_messages);
//        button_send_chat= (Button) findViewById(R.id.button_send_chat);
//        final Profile profile = Profile.getCurrentProfile();
//
//        filter = new IntentFilter();
//        filter.addAction("message_recieved");
//        FacebookSdk.sdkInitialize(this.getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();
//        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        GraphRequest request = GraphRequest.newMeRequest(
//                AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        if (profile != null) {
//                            idFacebook = profile.getId();
//
//                            Intent intent = getIntent();
//                            idRelationDuFilleul = intent.getStringExtra(Bingo.IDRELATIONFILLEUL);
//
//                            // Gets the URL from the UI's text field.
//                            String stringUrl = Util.selectAttribution+"?idFacebook="+idFacebook;
//                            Log.d("URL D'ATTRIBUTION : ", stringUrl);
//                            ConnectivityManager connMgr = (ConnectivityManager)
//                                    getSystemService(Context.CONNECTIVITY_SERVICE);
//                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//                            if (networkInfo != null && networkInfo.isConnected()) {
//                                new DownloadWebpageTask().execute(stringUrl);
//                            } else {
//                                Log.d(TAG,"No network connection available.");
//                            }
//                        }
//                    }
//                });
//        request.executeAsync();
//
//
//
//        button_send_chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//             public void onClick(View v) {
//            // send chat message to server
//                message = editText_chat_message.getText().toString().trim();
//               // messageMulti.add(message);
//                showChat("sent", message);
//                new SendMessage().execute();
//                editText_chat_message.setText("");
//            }
//        });
//
//
//        recieve_chat=new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                message = intent.getStringExtra("message");
//                Log.d(TAG, "in local braod " + message);
//                showChat("recieve", message);
//            }
//        };
//
//        LocalBroadcastManager.getInstance(this).registerReceiver(recieve_chat, filter);
//
//        //On supprime la notification de la liste de notification comme dans la méthode cancelNotify de l'Activity principale
//        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(GcmIntentService.NOTIFICATION_ID);
//    }
//
//
//
//
//
//    /**
//     * Ajouter ici l'historique des messages de la bdd Sqlite.
//     **/
//
//    private void showChat(String Type, String message){
//          type = Type;
//        if(chat_list==null || chat_list.size()==0){
//            chat_list= new ArrayList<ChatObject>();
//            chat_list.add(new ChatObject(message, type));
//            chatAdabter=new ChatAdabter(ChatActivity.this,R.layout.chat_view,chat_list);
//            listView_chat_messages.setAdapter(chatAdabter);
//            getData();
//            //chatAdabter.notifyDataSetChanged();
//        }else{
//            //test de recup de donnee ChatMessage
//        }
//        //On supprime la notification de la liste de notification comme dans la méthode cancelNotify de l'Activity principale
//        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(GcmIntentService.NOTIFICATION_ID);
//    }
//
//
//    public void getData(){
//        new BackgroundForGetDataFromDB().execute();
//    }
//
//    private class BackgroundForGetDataFromDB extends
//            AsyncTask<String, Void, ArrayList<ChatObject>> {
//        @Override
//        protected ArrayList<ChatObject> doInBackground(String... params) {
//            ArrayList<ChatObject> listChatDataDb = db.getChatMessages(idFacebook);
//            Log.i(TAG, "BackgroundForGetDataFromDB listChatDataDb size....."
//                    + listChatDataDb.size());
//            // listChatData.addAll(listChatDataDb);
//            Log.d(TAGDEBUG," message dans BDD = " + listChatData);
//            return listChatDataDb;
//        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//        @Override
//        protected void onPostExecute(ArrayList<ChatObject> result) {
//            super.onPostExecute(result);
//            Log.i(TAG,
//                    "BackgroundForGetDataFromDB result size....."
//                            + result.size());
//            Collections.reverse(result);
//            // Collections.reverse(listChatDataDb);
//            listChatData.addAll(0, result);
//            chatAdabter.notifyDataSetChanged();
//            if (listChatData != null && listChatData.size() > 0) {
//               // getListView().setSelection(listChatData.size());
//                for(ChatObject listchat : listChatData){
//                    listchat.getMessage();
//                    Log.d(TAGDEBUG,"Je suis dans BackgroundForGetDataFromDB et voila");
//                    Log.d(TAGDEBUG, " recup chat message dans BDD ====== ======== " + listchat.getMessage() + " "+ listchat.getType());
//
//                }
//              //  getListView().setSelection(listChatData.size());
//            }
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//    }
//
//    private class SendMessage extends AsyncTask<String, Void, String> {
//        String url;
//        //ChatObject chat = new ChatObject(message,type);
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//        }
//        @Override
//        protected String doInBackground(String... params) {
//            // TODO Auto-generated method stub
//            //Recherche dans la table statut
//            Log.d("Reading: ", "Reading all STATUT..");
//            List<StatutModele> statutmodeles = db.getAllStatutModeles();
//            for (StatutModele sm : statutmodeles) {
//                String log = "Id: "+sm.getId()+" ,Statut: " + sm.getStatut() + " ,IdFacebook: " + sm.getIdFacebook();
//                // Writing Contacts to log
//                Log.d("Statut dans chatActicity: ", log);
//                if(sm.getStatut().equals("Parrain")){
//                    Log.d(TAGDEBUG, "je fais du chat en tant que Parrain");
//                    Log.d(TAGDEBUG, idRelationDuParrain);
//                    url = Util.send_chat_url + "?idFacebook_id=" + idRelationDuParrain + "&message=" + message;
//                    Log.i(TAG, "url chat Parrain = " + url);
//                    //Enregistrement des message dans la bdd
//                     ChatObject chat = new ChatObject(message,type);
//                     chat.setStrFBId(idFacebook);
//                     chat.setMessage(message);
//                     chat.setStrSenderId(sm.getIdFacebook());
//                     chat.setStrReceiverId(idRelationDuParrain);
//                     chat.setType(type);
//                     Log.d("InsertChatMess = ", "On vas inserer les messages dans la bdd Sqlite");
//                     db.insertChatMessage(chat,type);
//                }else {
//                    Log.d(TAGDEBUG, "je fais du chat en tant que FILLEUL");
//                    Log.d(TAGDEBUG, idRelationDuFilleul);
//                    url = Util.send_chat_url + "?idFacebook_id=" + idRelationDuFilleul + "&message=" + message;
//                    Log.i(TAG, "url chat Filleul = " + url);
//                    //Enregistrement des message dans la bdd
//                    ChatObject chat = new ChatObject(message,type);
//                    chat.setStrFBId(idFacebook);
//                    chat.setMessage(message);
//                    chat.setStrSenderId(sm.getIdFacebook());
//                    chat.setStrReceiverId(idRelationDuFilleul);
//                    chat.setType(type);
//                    Log.d("InsertChatMess = ", "On vas inserer les messages dans la bdd Sqlite");
//                    db.insertChatMessage(chat,type);
//                }
//            }
//            //Fin de recherche de la table statut
//
//            OkHttpClient client_for_getMyFriends = new OkHttpClient();
//            String response = null;
//            try {
//                url = url.replace("\n",".").replace(" ", "%20");
//                Log.d("dans le try SendMessage", url);
//                response = callOkHttpRequest(new URL(url), client_for_getMyFriends);
//                for (String subString : response.split("<script", 2)) {
//                    response = subString;
//                    break;
//                }
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return response;
//
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//            //Toast.makeText(context,"response "+result,Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//
//            // params comes from the execute() call: params[0] is the url.
//            try {
//                return downloadUrl(urls[0]);
//            } catch (IOException e) {
//                return "Unable to retrieve web page. URL may be invalid.";
//            }
//        }
//        // onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//            idRelationDuParrain = result.trim();
//            Log.d("idRelationDuParrain = ", idRelationDuParrain);
//           // Intent attenteF = new Intent(AttenteParrain.this,Bingo.class);
//             //attenteF.putExtra(RELATIONDUPARRAIN, idRelationDuParrain.trim());
//             //startActivity(attenteF);
//        }
//    }
//
//
//    // Given a URL, establishes an HttpUrlConnection and retrieves
//    // the web page content as a InputStream, which it returns as
//    // a string.
//    public String downloadUrl(String myurl) throws IOException {
//        InputStream is = null;
//        // Only display the first 500 characters of the retrieved
//        // web page content.
//        int len = 500;
//        try {
//            URL url = new URL(myurl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(10000 /*milliseconds*/);
//            conn.setConnectTimeout(15000 /*milliseconds*/);
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            // Starts the query
//            conn.connect();
//            int response = conn.getResponseCode();
//            Log.d(TAG, "The response is: " + response);
//            is = conn.getInputStream();
//            // Convert the InputStream into a string
//            String contentAsString = readIt(is, len);
//            return contentAsString;
//            // Makes sure that the InputStream is closed after the app is
//            // finished using it.
//        } finally {
//            if (is != null) {
//                is.close();
//            }
//        }
//    }
//
//
//
//    // Reads an InputStream and converts it to a String.
//    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
//        Reader reader = null;
//        reader = new InputStreamReader(stream, "UTF-8");
//        char[] buffer = new char[len];
//        reader.read(buffer);
//        return new String(buffer);
//    }
//
//
//
//    // Http request using OkHttpClient
//    String callOkHttpRequest(URL url, OkHttpClient tempClient)
//            throws IOException {
//
//        HttpURLConnection connection = tempClient.open(url);
//
//        connection.setConnectTimeout(40000);
//        InputStream in = null;
//        try {
//            // Read the response.
//            in = connection.getInputStream();
//            byte[] response = readFully(in);
//            return new String(response, "UTF-8");
//        } finally {
//            if (in != null)
//                in.close();
//        }
//    }
//
//    byte[] readFully(InputStream in) throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        for (int count; (count = in.read(buffer)) != -1;) {
//            out.write(buffer, 0, count);
//        }
//        return out.toByteArray();
//    }
//
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (recieve_chat != null) {
//            registerReceiver(recieve_chat, filter);
//        } else {
//
//        }
//        SessionManager session = new SessionManager(ChatActivity.this);
//        session.setFirstScreen(true);
//
//        // chatList.setAdapter(messageAdapter);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        SessionManager session = new SessionManager(ChatActivity.this);
//        session.setFirstScreen(false);
//        unregisterReceiver(recieve_chat);
//        // unregisterReceiver(receiver);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        // unregisterReceiver(deliveryBroadcastReceiver);
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
package com.juniorkabore.filleul;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
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
import java.util.Collections;
import java.util.List;


public class ChatActivity extends Activity {

    private EditText editText_chat_message;
    private ListView listView_chat_messages;
    private Button button_send_chat;
    public List<ChatObject> chat_list;
    private String message = "";
    Bingo bingo = new Bingo();
    Je_suis jesuis = new Je_suis();
    AttenteParrain attenteParrain = new AttenteParrain();
    public static final String TAG = "ChatActivity";
    public static final String TAGDEBUG = "Affichage de debug ChatActivity";
    BroadcastReceiver recieve_chat;
    final DatabaseHandler db = new DatabaseHandler(this);
    private CallbackManager callbackManager;
    String idFacebook;
    String idRelationDuParrain = "";
    String idRelationDuFilleul;
    public final static String RELATIONDUPARRAIN = "relation du parrain";
    String type = "";
    public ArrayList<ChatObject> listChatData = new ArrayList<ChatObject>();
    List<String> messageMulti = new ArrayList<>();
    private ChatAdabter chatAdabter;
    private Bitmap userImage;
    private Bitmap friendImage;
    IntentFilter filter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final ChatObject chatM = new ChatObject();
        final SessionManager mSessionManager = new SessionManager(this);
        editText_chat_message= (EditText) findViewById(R.id.editText_chat_message);
        listView_chat_messages= (ListView) findViewById(R.id.listView_chat_messages);
        button_send_chat= (Button) findViewById(R.id.button_send_chat);
        final Profile profile = Profile.getCurrentProfile();

        filter = new IntentFilter();
        filter.addAction("message_recieved");
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

                            Intent intent = getIntent();
                            idRelationDuFilleul = intent.getStringExtra(Bingo.IDRELATIONFILLEUL);

                            // Gets the URL from the UI's text field.
                            String stringUrl = Util.selectAttribution+"?idFacebook="+idFacebook;
                            Log.d("URL D'ATTRIBUTION : ", stringUrl);
                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {
                                new DownloadWebpageTask().execute(stringUrl);
                            } else {
                                Log.d(TAG,"No network connection available.");
                            }
                        }
                    }
                });
        request.executeAsync();



        button_send_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send chat message to server
                message = editText_chat_message.getText().toString().trim();
                // messageMulti.add(message);
                type = "sent";
                showChat(type, message);
                new SendMessage().execute();
                editText_chat_message.setText("");
            }
        });


        recieve_chat=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                message = intent.getStringExtra("message");
                Log.d(TAG, "in local braod " + message);
                type = "recieve";
                showChat(type, message);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(recieve_chat, new IntentFilter("message_recieved"));


      //  LocalBroadcastManager.getInstance(this).registerReceiver(recieve_chat, filter);

        //On supprime la notification de la liste de notification comme dans la méthode cancelNotify de l'Activity principale
          NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
          notificationManager.cancel(GcmIntentService.NOTIFICATION_ID);
    }





    /**
     * Ajouter ici l'historique des messages de la bdd Sqlite.
     **/

//    private void showChat(String Type, String message){
//        //On supprime la notification de la liste de notification comme dans la méthode cancelNotify de l'Activity principale
////        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
////        notificationManager.cancel(GcmIntentService.NOTIFICATION_ID);
//        type = Type;
//        if(chat_list==null || chat_list.size()==0){
//            chat_list= new ArrayList<ChatObject>();
//            chat_list.add(new ChatObject(message, type));
//            chatAdabter=new ChatAdabter(ChatActivity.this,R.layout.chat_view,chat_list);
//            listView_chat_messages.setAdapter(chatAdabter);
//            //chatAdabter.notifyDataSetChanged();
//        }else{
//            //test de recup de donnee ChatMessage
//        }
//    }



    private void showChat(String type, String message){
        if(chat_list==null || chat_list.size()==0){
            chat_list= new ArrayList<ChatObject>();
        }

        chat_list.add(new ChatObject(message,type));

        ChatAdabter chatAdabter=new ChatAdabter(ChatActivity.this,R.layout.chat_view,chat_list);
        listView_chat_messages.setAdapter(chatAdabter);
        chatAdabter.notifyDataSetChanged();

        //On supprime la notification de la liste de notification comme dans la méthode cancelNotify de l'Activity principale
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(GcmIntentService.NOTIFICATION_ID);
    }


    public void getData(){
        new BackgroundForGetDataFromDB().execute();
    }

    private class BackgroundForGetDataFromDB extends
            AsyncTask<String, Void, ArrayList<ChatObject>> {

        @Override
        protected ArrayList<ChatObject> doInBackground(String... params) {
            ArrayList<ChatObject> listChatDataDb = db.getChatMessages(idFacebook);
            Log.i(TAG, "BackgroundForGetDataFromDB listChatDataDb size....."
                    + listChatDataDb.size());
            // listChatData.addAll(listChatDataDb);
            Log.d(TAGDEBUG, " message dans BDD = " + listChatData);
            return listChatDataDb;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<ChatObject> result) {
            super.onPostExecute(result);
            Log.i(TAG,
                    "BackgroundForGetDataFromDB result size....."
                            + result.size());
            Collections.reverse(result);
            // Collections.reverse(listChatDataDb);
            listChatData.addAll(0, result);
            chatAdabter.notifyDataSetChanged();
            if (listChatData != null && listChatData.size() > 0) {
                // getListView().setSelection(listChatData.size());
                for(ChatObject listchat : listChatData){
                    listchat.getMessage();
                    Log.d(TAGDEBUG,"Je suis dans BackgroundForGetDataFromDB et voila");
                    Log.d(TAGDEBUG, " recup cht message dans BDD ====== ======== " + listchat.getMessage() + " "+ listchat.getType());

                }
                //  getListView().setSelection(listChatData.size());
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }







    private class SendMessage extends AsyncTask<String, Void, String> {
        String url;


        //ChatObject chat = new ChatObject(message,type);
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            //Recherche dans la table statut
            Log.d("Reading: ", "Reading all STATUT..");
            List<StatutModele> statutmodeles = db.getAllStatutModeles();

            for (StatutModele sm : statutmodeles) {
                String log = "Id: "+sm.getId()+" ,Statut: " + sm.getStatut() + " ,IdFacebook: " + sm.getIdFacebook();
                // Writing Contacts to log
                Log.d("Statut dans chatActicity: ", log);

                if(sm.getStatut().equals("Parrain")){
                    Log.d(TAGDEBUG, "je fais du chat en tant que Parrain");
                    Log.d(TAGDEBUG, idRelationDuParrain);
                    url = Util.send_chat_url + "?idFacebook_id=" + idRelationDuParrain + "&message=" + message;

                    //Enregistrement des message dans la bdd
                    ChatObject chat = new ChatObject(message,type);
                    chat.setStrFBId(idFacebook);
                    chat.setMessage(message);
                    chat.setStrSenderId(sm.getIdFacebook());
                    chat.setStrReceiverId(idRelationDuParrain);
                    chat.setType(type);
                    Log.d("InsertChatMess = ", "On vas inserer les messages dans la bdd Sqlite");
                    db.insertChatMessage(chat,type);
                }else {
                    Log.d(TAGDEBUG, "je fais du chat en tant que FILLEUL");
                    Log.d(TAGDEBUG, idRelationDuFilleul);
                    url = Util.send_chat_url + "?idFacebook_id=" + idRelationDuFilleul + "&message=" + message;
                    //Enregistrement des message dans la bdd
                    ChatObject chat = new ChatObject(message,type);
                    chat.setStrFBId(idFacebook);
                    chat.setMessage(message);
                    chat.setStrSenderId(sm.getIdFacebook());
                    chat.setStrReceiverId(idRelationDuFilleul);
                    chat.setType(type);
                    Log.d("InsertChatMess = ", "On vas inserer les messages dans la bdd Sqlite");
                    db.insertChatMessage(chat,type);

                }
            }
            //Fin de recherche de la table statut

            Log.i(TAG, "url = " + url);

            OkHttpClient client_for_getMyFriends = new OkHttpClient();
            String response = null;
            String responseUrlSave = null;
            try {
                url = url.replace("\n",".").replace(" ", "%20");


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


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
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
            idRelationDuParrain = result.trim();
            Log.d("idRelationDuParrain = ", idRelationDuParrain);
            // Intent attenteF = new Intent(AttenteParrain.this,Bingo.class);
            //attenteF.putExtra(RELATIONDUPARRAIN, idRelationDuParrain.trim());
            //startActivity(attenteF);
        }
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






    @Override
    protected void onResume() {
        super.onResume();
        if (recieve_chat != null) {
            registerReceiver(recieve_chat, filter);
        } else {

        }
        SessionManager session = new SessionManager(ChatActivity.this);
        session.setFirstScreen(true);

        // chatList.setAdapter(messageAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SessionManager session = new SessionManager(ChatActivity.this);
        session.setFirstScreen(false);
        unregisterReceiver(recieve_chat);
        // unregisterReceiver(receiver);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // unregisterReceiver(deliveryBroadcastReceiver);

    }






}
