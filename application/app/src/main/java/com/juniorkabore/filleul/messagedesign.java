package com.juniorkabore.filleul;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

/**
 * Created by juniorkabore on 16/03/2016.
 */
public class messagedesign extends AppCompatActivity{

    private ProfilePictureView profilePictureView;
    private String userName;
    private String derniermessages = "On parle tranquille";
    private String idFacebook;
    private CallbackManager callbackManager;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
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
                            userName = profile.getLastName();

                        }
                    }
                });
        request.executeAsync();

    }



    public messagedesign(ProfilePictureView profilePictureView, String derniermessages, String userName) {
        this.profilePictureView = profilePictureView;
        this.derniermessages = derniermessages;
        this.userName = userName;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDerniermessages() {
        return derniermessages;
    }

    public void setDerniermessages(String derniermessages) {
        this.derniermessages = derniermessages;
    }
}
