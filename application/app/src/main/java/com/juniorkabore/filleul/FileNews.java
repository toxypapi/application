package com.juniorkabore.filleul;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

import java.util.List;

public class FileNews extends AppCompatActivity {
    private ProfilePictureView profilePictureView;
    public TextView Name;
    public TextView textUniversiter;
    public TextView textStatut;
    public TextView addInfo;

    public String textName;
    public String tUniversiter;
    public String tStatut;
    public String textaddInfo;




    private CallbackManager callbackManager;
    String idFacebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_news);
        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        Name = (TextView) findViewById(R.id.Name);
        textUniversiter = (TextView) findViewById(R.id.textUniversiter);
        textStatut = (TextView) findViewById(R.id.textStatut);
        addInfo = (TextView) findViewById(R.id.addInfo);



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
                            profilePictureView.setPresetSize(ProfilePictureView.SMALL);
                            Name.setText(profile.getName());
                            textUniversiter.setText(mSessionManager.getUSERUNIV());
                            textStatut.setText(mSessionManager.getUSERSTATUT());

                        }
                    }
                });
        request.executeAsync();





    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_news, menu);
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
