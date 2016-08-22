package com.juniorkabore.filleul;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.juniorkabore.filleul.adapter.miAdapter;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class AttenteParrain extends AppCompatActivity/*FragmentActivity*/ implements SlidingMenu.OnOpenListener, View.OnClickListener  {
    public ImageView logo;
    public ImageView profileimage;
    public TextView AttenteParrain;
    TextView tvTitle;
    public static final String TAG = "AttenteParrain";
    public static final String TAGDEBUG = "Affichage de debug AttenteParrain";
    private CallbackManager callbackManager;
    String idFacebook;
    String idRelationDuParrain;
    public final static String RELATIONDUPARRAIN = "relation du parrain";
    Button btMenu;
    private Button buttonRightMenu;
    // SerchUi
    private EditText etSerchFriend;
    public  ListView message ;
    private LinearLayout profilelayout, homelayout, messages, settinglayout, invitelayout;
    private SlidingMenu menu;
    private ProfilePictureView profilePictureView;
    //Recycleview
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset = {"Cette page est la page du file d'actualité qui sera bientot disponnible"};

    private List<FileNews> profilePerso;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attente_parrain);


        tvTitle = (TextView) findViewById(R.id.activity_main_content_title);
        tvTitle.setTextColor(Color.rgb(255, 255, 255));
        tvTitle.setTextSize(20);

       /* buttonRightMenu = (Button) findViewById(R.id.button_right_menu);
        buttonRightMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                toggleRightMenu(v);
            }
        });*/
        btMenu = (Button) findViewById(R.id.button_menu);
        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show/hide the menu
                toggleMenu(v);
                // menu_right.setVisibility(View.GONE);;
                // menu_left.setVisibility(View.VISIBLE);
            }
        });
        try {

           // profilelayout.setOnClickListener(this);
            homelayout.setOnClickListener(this);
            messages.setOnClickListener(this);
            settinglayout.setOnClickListener(this);
           // invitelayout.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
       // menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        Log.d(TAG, "onCreate  before add menu ");
        menu.setMenu(R.layout.leftmenu);
        menu.setSecondaryMenu(R.layout.rightmenu);
        Log.d(TAG, "onCreate  add menu ");
        menu.setSlidingEnabled(true);
        Log.d(TAG, "onCreate  finish");

        // search
        etSerchFriend = (EditText) menu
                .findViewById(R.id.et_serch_right_side_menu);
        // btnSerch = (Button) menu.findViewById(R.id.btn_serch_right_side);

        View leftmenuview = menu.getMenu();
        View rightmenuview = menu.getSecondaryMenu();

        initLayoutComponent(leftmenuview, rightmenuview);

        menu.setSecondaryOnOpenListner(this);

      /*  try {
            FragmentManager fm = AttenteParrain.this.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            AjoutInfo fragment = new AjoutInfo();
            ft.add(R.id.activity_main_content_fragment, fragment);
            tvTitle.setText(getResources().getString(R.string.app_name));
            ft.commit();

        }catch (Exception e) {
            e.printStackTrace();
        }*/
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new miAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        final Profile profile = Profile.getCurrentProfile();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        final SessionManager mSessionManager = new SessionManager(this);
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
                            mSessionManager.FinLogin();
                            profilePictureView.setProfileId(idFacebook);
                            profilePictureView.setPresetSize(ProfilePictureView.CUSTOM);
                            //Redirection vers la page profil comment faire pour le reste
                            profilePictureView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(AttenteParrain.this,ProfilUser.class);
                                    startActivity(i);
                                }
                            });
                            //Redirection vers listes de message
                            messages.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(AttenteParrain.this,NewChat.class);
                                    startActivity(i);
                                }
                            });
                            //Redirection vers setting
                            settinglayout.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(AttenteParrain.this,ProfilUser.class);
                                    startActivity(i);
                                }
                            });
                            //Redirection vers home
                            homelayout.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(AttenteParrain.this,ProfilUser.class);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });
        request.executeAsync();
    }
//on vera

  /*  private void init(){
        profilePerso = new ArrayList<>();
        profilePerso.add(profilePictureView.setProfileId(idFacebook),);

    }
*/

    private void initLayoutComponent(View leftmenu, View rightmenu) {


        // menu_right=(LinearLayout)rightmenu.findViewById(R.id.menu_right);
        // menu_left=(LinearLayout)leftmenu.findViewById(R.id.menu_left);
        //message = (ListView) rightmenu.findViewById(R.id.menu_right_ListView);

        //test foireux
       // afficherListeMessageDesign();
        //afficherListeTweets();


        //test foireux





        // lvMenu = (ListView) findViewById(R.id.menu_listview);

       // profileimage = (ImageView) leftmenu.findViewById(R.id.profileimage);
        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        // homeimageview=(ImageView)findViewById(R.id.homeimageview);
        // messageimage=(ImageView)findViewById(R.id.messageimage);
        // settingimage=(ImageView)findViewById(R.id.settingimage);
        // inviteimage=(ImageView)findViewById(R.id.inviteimage);
        // profileimage.setBackgroundResource(R.drawable.profile_boader_on);

        //profilelayout = (LinearLayout) leftmenu.findViewById(R.id.profilelayout);
        homelayout = (LinearLayout) leftmenu.findViewById(R.id.homelayout);
        messages = (LinearLayout) leftmenu.findViewById(R.id.messages);
        settinglayout = (LinearLayout) leftmenu.findViewById(R.id.settinglayout);
        //invitelayout = (LinearLayout) leftmenu.findViewById(R.id.invitelayout);

    }

  /*  public void afficherListeMessageDesign(){
        String[] prenoms = new String[]{
                "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
                "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
                "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
                "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier", "Yann", "Zoé"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AttenteParrain.this, android.R.layout.simple_list_item_1, prenoms);
        message.setAdapter(adapter);

    }
    private List<messagedesign> genererTweets(){
        List<messagedesign> tweets = new ArrayList<messagedesign>();
        tweets.add(new messagedesign(profilePictureView, "Florent", "Mon premier tweet !"));
        tweets.add(new messagedesign(profilePictureView, "Kevin", "C'est ici que ça se passe !"));
        tweets.add(new messagedesign(profilePictureView, "Logan", "Que c'est beau..."));
        tweets.add(new messagedesign(profilePictureView, "Mathieu", "Il est quelle heure ??"));
        tweets.add(new messagedesign(profilePictureView, "Willy", "On y est presque"));
        return tweets;
    }

    private void afficherListeTweets(){
        List<messagedesign> tweets = genererTweets();

        messagedesignAdapter adapter = new messagedesignAdapter(AttenteParrain.this, tweets);
        message.setAdapter(adapter);
    }
*/




    public void toggleMenu(View v) {
        // mLayout.toggleMenu();
        menu.toggle();
    }

    public void toggleRightMenu(View v) {
        // mLayout.toggleMenuRight();
        menu.showSecondaryMenu();
    }

    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.toggle();
        } else if (menu.isSecondaryMenuShowing()) {
            menu.showSecondaryMenu();
        } else {
            super.onBackPressed();
        }
    }








/*
    private class SendMessage extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

           // String url = Util.send_chat_url+"?idFacebook_id="+bingo.idFacebookRelation.trim()+"&message="+message;
            String url = Util.selectAttribution+"?idFacebook="+idFacebook;
            Log.i(TAG, "url" + url);

            OkHttpClient client_for_getMyFriends = new OkHttpClient();;

            String response = null;
            // String response=Utility.callhttpRequest(url);

            try {
                url = url.replace(" ", "%20");
                response = callOkHttpRequest(new URL(url),
                        client_for_getMyFriends);
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


    }*/

    // Http request using OkHttpClient
 /*   String callOkHttpRequest(URL url, OkHttpClient tempClient)
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
    }*/





/*    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
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
            idRelationDuParrain = result;
            Log.d("idRelationDuParrain = ", idRelationDuParrain.trim());
            //Intent attenteF = new Intent(AttenteParrain.this,Bingo.class);
           // attenteF.putExtra(RELATIONDUPARRAIN, idRelationDuParrain.trim());
           // startActivity(attenteF);


        }
    }*/


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
        getMenuInflater().inflate(R.menu.menu_attente_parrain, menu);
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

    @Override
    public void onOpen() {

    }

    @Override
    public void onClick(View v) {

    }







// avoir

/*
    private class MatchedDataAdapter extends
            ArrayAdapter<ProfilUser> {
        // RequestQueue mRequestQueue =
        // Volley.newRequestQueue(MainActivity.this);
        private AQuery aQuery;
        // private ImageLoader imageLoader = new ImageLoader(mRequestQueue,
        // new BitmapLruCache(BitmapLruCache.getDefaultLruCacheSize()));
        // private int imageHeigthAndWidth [];
        private Activity mActivity;
        private LayoutInflater mInflater;
        private SessionManager sessionManager;

        public MatchedDataAdapter(Activity context, List<ProfilUser> objects, int imageHeigthAndWidth[]) {
            super(context, R.layout.messagedesign, objects);
            mActivity = context;
            mInflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // this.imageHeigthAndWidth=imageHeigthAndWidth;
            sessionManager = new SessionManager(context);
            aQuery = new AQuery(context);
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public ProfilUser getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.messagedesign, null);
               // holder.imageview = (ImageView) convertView.findViewById(R.id.userimage);
                holder.profilePictureView = (ProfilePictureView) convertView.findViewById(R.id.profilePicture);

                holder.textview = (TextView) convertView.findViewById(R.id.userName);
                holder.lastMasage = (TextView) convertView.findViewById(R.id.lastmessage);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textview.setId(position);
            holder.imageview.setId(position);
            holder.lastMasage.setId(position);
            holder.textview.setText(getItem(position).getUserName());
            // holder.imageview.setImageUrl(getItem(position).getImageUrl(),
            // imageLoader,"CircularImge",MainActivity.this);
            aQuery.id(holder.imageview).image(getItem(position).getImageUrl());
            // holder.imageview.setImageUrl(getItem(position).getImageUrl(),
            // imageLoader);
            try {
                holder.lastMasage.setText(sessionManager.getLastMessage(getItem(position).getFacebookid()));
            } catch (Exception e) {

            }
            return convertView;
        }

        class ViewHolder {
            //ImageView imageview;

            TextView textview;
            TextView lastMasage;

        }
    }*/


}
