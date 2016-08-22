package com.juniorkabore.filleul;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by juniorkabore on 04/09/15.
 */
public class SessionManager {


    public static boolean flag = true;
    private static final String TAG = "SessionManager";
    // Shared Preferences
    private SharedPreferences pref;
    // Editor for Shared preferences
    private SharedPreferences.Editor editor;
    // Context
    private Context _context;
    // Shared pref mode
    private int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "Filleul";
    // All Shared Preferences Keys
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String IS_FIRSTSCREEN = "is_firstscreen";
    public static final String IS_Finish = "IsFinish";
    public  static final String IS_CHAT = "Ischat";


    // User name (make variable public to access from outside)
    public static final String USER_ID = "userid";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String ID_FACEBOOK = "idFacebook";
    public static final String PROFILE_PIC_URL = "prifilePickurl";
    public static final String USER_STATUT = "USER_STATUT";
    public static final String USER_UNIV = "USER_UNIV";
    public static final String USER_BESOIN = "USER_BESOIN";
    public static final String USER_INTERET = "USER_INTERET";
    public static final String USER_FININSCRIPTION = "USER_FININSCRIPTION";
    public static final String USER_SEXE = "USER_SEXE";




    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }





    //SETTER & GETTER


    public void setLastMessage(String key, String lastMessage) {
        editor.putString(key, lastMessage);
        // commit changes
        editor.commit();
    }

    public String getLastMessage(String key) {
        return pref.getString(key, null);
    }




    public boolean isChat() {

        return pref.getBoolean(IS_CHAT, false);

    }

    public void setIsChat(boolean bFlag) {
        editor.putBoolean(IS_CHAT, bFlag);
        editor.commit();
    }



    public boolean isFirstScreen() {

        return pref.getBoolean(IS_FIRSTSCREEN, false);

    }

    public void setFirstScreen(boolean bFlag) {
        editor.putBoolean(IS_FIRSTSCREEN, bFlag);
        editor.commit();
    }

    public void setUserName(String useName) {
        Log.i(TAG, "setUserName.....");
        Log.i(TAG, "setUserName  useName " + useName);
        // Storing login value as TRUE
        // editor.putBoolean(IS_LOGIN, true);
        editor.putString(USER_NAME, useName);
        // commit changes
        editor.commit();
        Log.i(TAG, "" + pref.getString(USER_NAME, null));

    }
    public HashMap<String, String> getUserName() {
        Log.i(TAG, "getUserName.....");
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        Log.i(TAG, "getUserName= " + pref.getString(USER_NAME, null));
        user.put(USER_NAME, pref.getString(USER_NAME, null));

		/*
		 * // user email id user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,
		 * null));
		 *
		 * // return user
		 */
        return user;
    }





    public void setUserToken(String UserToken) {
        editor.putString(USER_TOKEN, UserToken);
        // commit changes
        editor.commit();
    }


    public String getUserToken() {
        return pref.getString(USER_TOKEN, "");
    }



/*

    public void setUserFininscription(String UserFinInscription) {
        editor.putString(USER_FININSCRIPTION, UserFinInscription);
        // commit changes
        editor.commit();
    }


    public String getUserFininscription() {
        return pref.getString(USER_FININSCRIPTION, "");
    }
*/





    public void setUSERSTATUT(String USERSTATUT) {
        editor.putString(USER_STATUT, USERSTATUT);
        // commit changes
        editor.commit();
    }


    public String getUSERSTATUT() {
        return pref.getString(USER_STATUT, "");
    }


    public String getUserBesoin() {
        return pref.getString(USER_BESOIN, "");
    }


    public void setUserBesoin(String USERBESOIN) {
        editor.putString(USER_BESOIN, USERBESOIN);
        // commit changes
        editor.commit();
    }

    public void setUSERUNIV(String USERUNIV) {
        editor.putString(USER_UNIV, USERUNIV);
        // commit changes
        editor.commit();
    }


    public String getUserSexe() {
        return pref.getString(USER_SEXE, "");
    }

    public void setUserSexe(String USER_SEXE) {
        editor.putString(USER_SEXE, USER_SEXE);
        // commit changes
        editor.commit();
    }




    public String getUSERUNIV() {
        return pref.getString(USER_UNIV, "");
    }





    public void setUSERINTERET(String USERINTERET) {
        editor.putString(USER_INTERET, USERINTERET);
        // commit changes
        editor.commit();
    }


    public String getUSERINTERET() {
        return pref.getString(USER_INTERET, "");
    }









    public void setProFilePicture(String picurl) {
        editor.putString(PROFILE_PIC_URL, picurl);
        // commit changes
        editor.commit();
    }

    public String getUserPrifilePck() {
        return pref.getString(PROFILE_PIC_URL, "not found");
    }









    public void setFacebookId(String idFacebook) {
        editor.putString(ID_FACEBOOK, idFacebook);
        // commit changes
        editor.commit();
    }

    public String getFacebookId() {

        return pref.getString(ID_FACEBOOK, "");

    }










    /**
     * Clear session details
     * */
    public void logoutUser() {
        flag = true;
        Log.i(TAG, " logoutUser..........");
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
		/*
		 * Log.i(TAG, "Usersuccessfully logout.........."); // After logout
		 * redirect user to Loing Activity Intent i = new Intent(_context,
		 * LoginActivity.class); // Closing all the Activities
		 * i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Add new Flag to start
		 * new Activity i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Staring
		 * Login Activity _context.startActivity(i);
		 */
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Create login session
     * */
    public void createLoginSession(/*String idFacebook*/ ) {
        Log.i(TAG, "createLoginSession.....");
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // commit changes
        editor.commit();
        Log.i(TAG, "" + pref.getString(USER_ID, null));
        Log.i(TAG, "session created....");
    }



    /**
     *
     * */
    public boolean isFinish(){
        return pref.getBoolean(IS_Finish,false);
    }



    /**
     * Create finish register
     * */
    public void FinLogin() {
        Log.i(TAG, "Finish of register.....");
        editor.putBoolean(IS_Finish, true);
        editor.commit();
        Log.i(TAG, "" + pref.getString(USER_ID, null));
        Log.i(TAG, "register finish....");
    }







    /**
     * Check login method wil check user login status If false it will redirect
     * user to login page Else won't do anything
     * */
    public void checkLogin() {
        Log.i(TAG, "checkLogin......");
        // Check login status
        if (!this.isLoggedIn()) {
            Log.i(TAG, "user not logedin......");
            flag = false;
            // user is not logged in redirect him to Login Activity
            // Intent i = new Intent(_context, HomeActivity.class);
            // // Closing all the Activities
            // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //
            // // Add new Flag to start new Activity
            // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //
            // // Staring Login Activity
            // _context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails() {
        Log.i(TAG, "getUserDetails.....");
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        Log.i(TAG, "user id= " + pref.getString(USER_ID, null));
        user.put(USER_ID, pref.getString(USER_ID, null));

		/*
		 * // user email id user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,
		 * null));
		 *
		 * // return user
		 */
        return user;
    }

    public HashMap<String, String> getUserId() {
        Log.i(TAG, "getUserId.....");
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        Log.i(TAG, "getUserId= " + pref.getString(USER_ID, null));
        user.put(USER_ID, pref.getString(USER_ID, null));

		/*
		 * // user email id user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,
		 * null));
		 *
		 * // return user
		 */
        return user;
    }



}
