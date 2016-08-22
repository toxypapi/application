package com.juniorkabore.filleul;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

public class ProfilUser extends AppCompatActivity {

    public ProfilePictureView profilePictureView;
    public TextView Name;
    public TextView relation;
    public TextView textStatut;
    public TextView textUniversiter;
  //  public TextView kiffe;
    //public TextView interet;
    private CallbackManager callbackManager;
    private String idFacebook;
    public Button validerprofile;
    String nameText;
    String firstnameText;
    String statutText;
    String univText;
   // CollectionPagerAdapter mCollectionPagerAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        Name = (TextView) findViewById(R.id.Name);
        relation = (TextView) findViewById(R.id.relation);
        textStatut = (TextView) findViewById(R.id.textStatut);
        textUniversiter = (TextView) findViewById(R.id.textUniversiter);
        //interet = (TextView) findViewById(R.id.interet);
       // kiffe = (TextView) findViewById(R.id.kiffe);
      //  validerprofile = (Button) findViewById(R.id.validerprofile);
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
                            profilePictureView.setProfileId(idFacebook);
                            profilePictureView.setPresetSize(ProfilePictureView.CUSTOM);
                            Name.setText(profile.getName());
                            relation.setText(mSessionManager.getUserBesoin());
                            textUniversiter.setText(mSessionManager.getUSERUNIV());
                            textStatut.setText(mSessionManager.getUSERSTATUT());
                          //  interet.setText(mSessionManager.getUSERINTERET());

                           /* validerprofile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("VALIDER", "Je vien de valider la relation");
                                    Intent i = new Intent(ProfilUser.this,AttenteParrain.class);
                                    startActivity(i);

                                }
                            });*/


                        }
                    }
                });
        request.executeAsync();



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profil_user, menu);
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






    public String getUnivText() {
        return univText;
    }

    public void setUnivText(String univText) {
        this.univText = univText;
    }

    public String getStatutText() {
        return statutText;
    }

    public void setStatutText(String statutText) {
        this.statutText = statutText;
    }

    public String getFirstnameText() {
        return firstnameText;
    }

    public void setFirstnameText(String firstnameText) {
        this.firstnameText = firstnameText;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }



    public TextView getName() {
        return Name;
    }

    public void setName(TextView name) {
        Name = name;
    }

    public TextView getRelation() {
        return relation;
    }

    public void setRelation(TextView relation) {
        this.relation = relation;
    }

    public TextView getTextStatut() {
        return textStatut;
    }

    public void setTextStatut(TextView textStatut) {
        this.textStatut = textStatut;
    }

    public TextView getTextUniversiter() {
        return textUniversiter;
    }

    public void setTextUniversiter(TextView textUniversiter) {
        this.textUniversiter = textUniversiter;
    }



/*
    public static class CollectionPagerAdapter extends FragmentStatePagerAdapter {

        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1); // Our object is just an integer :-P
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // For this contrived example, we have a 100-object collection.
            return 100;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }


    *//**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     *//*
    public static class DemoObjectFragment extends Fragment {

        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_collection_object, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }*/





}
