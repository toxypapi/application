package com.juniorkabore.filleul;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.ProfilePictureView;

public class AjoutInfo extends AppCompatActivity {
    private ProfilePictureView profilePictureView;
    public TextView Name;
    public TextView textUniversiter;
    public TextView textStatut;
    public TextView addInfo;
    private CallbackManager callbackManager;
    RecyclerView my_recycler_view;
    String idFacebook;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_info);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

// Finir la Class
    
   /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_ajout_info, container, false);

    }*/












}
