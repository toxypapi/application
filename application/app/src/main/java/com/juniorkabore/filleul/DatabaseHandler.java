package com.juniorkabore.filleul;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    private static String TAG = "DatabaseHandler";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    //private static final String DATABASE_NAME = "contactsManager";
    private static final String DATABASE_NAME = "filleul.db";
 
    // Contacts table name
    //private static final String TABLE_CONTACTS = "contacts";
    public static final String TABLE_CONTACTS = "facebookData";
    public static final String TABLE_STATUT = "Statut";
    public static final String TABLE_BESOIN = "besoin";
    public static final String TABLE_KIFFES = "kiffes";
    public static final String TABLE_LIEUX = "lieux";
    public static final String TABLE_ATTRIBUTION = "attribution";
    public static final String TABLE_CHATMESSAGE = "chatMessage";
 
    // Contacts Table Columns names
    /*private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_idFacebook = "phone_number";*/
    // Contacts Table Columns names

    //colonne table contact
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_idFacebookC = "idFacebookC";

    //colonne table statut
    public static final String KEY_STATUT = "statut";
    public static final String KEY_idFacebookS = "idFacebookS";

    //colonne table besoin
    public static final String KEY_INTE = "inte";
    public static final String KEY_AIDE = "aide";
    public static final String KEY_idFacebookB = "idFacebookB";

    //colonne table kiffe
    public static final String KEY_SPORT = "sport";
    public static final String KEY_MUSIQUE = "musique";
    public static final String KEY_GAMING = "gaming";
    public static final String KEY_DANSE = "danse";
    public static final String KEY_VIRER = "virer";
    public static final String KEY_CULTURE = "culture";
    public static final String KEY_idFacebookK = "idFacebookK";

    //colonne table lieux
    public static final String KEY_UNIV = "univ";
    public static final String KEY_idFacebookL = "idFacebookL";


    //colonne table Attribution
    public static final String KEY_PARRAINIDFACEBOOK = "parrainId";
    public static final String KEY_FILLEULIDFACEBOOK = "filleulId";



    //Colonne table ChatMessage
    private static final String FACEBOOK_ID = "facebook_id";
    public static final String CHAT_MESSAGE = "chat_message";
    public static final String MESSAGE_DATE = "message_date";
    public static final String SENDER_ID = "sender_id";
    public static final String RECEIVER_ID = "receiver_id";
    public static final String TYPE = "type";
    SQLiteDatabase db = null;
    


 
   /* public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }*/


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    private final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, "
            + KEY_idFacebookC + " TEXT" + ")";



    //Creation de la table statut
    private final String CREATE_STATUT_TABLE = "CREATE TABLE " + TABLE_STATUT
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_STATUT + " VARCHAR, "
            + KEY_idFacebookS + " VARCHAR"
            + ")";



    //Creation de la table besoin
   private final String CREATE_TABLE_BESOIN = "CREATE TABLE " + TABLE_BESOIN
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_INTE + " TEXT, "
            + KEY_AIDE + " TEXT, "
            + KEY_idFacebookB + " TEXT"
            + ")";


    //Creation de la table kiffes
   private final String CREATE_TABLE_KIFFES = "CREATE TABLE " + TABLE_KIFFES
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_SPORT + " TEXT, "
            + KEY_MUSIQUE + " TEXT, "
            + KEY_GAMING + " TEXT, "
            + KEY_DANSE + " TEXT, "
            + KEY_VIRER + " TEXT, "
            + KEY_CULTURE + " TEXT, "
            + KEY_idFacebookK + " TEXT"
            + ")";


    //Creation de la table lieux
   private final String CREATE_TABLE_LIEUX = "CREATE TABLE " + TABLE_LIEUX
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY , "
            + KEY_UNIV + " TEXT, "
            + KEY_idFacebookL + " TEXT"
            + ")";

    //Creation de la table Attribution
   private final String CREATE_TABLE_ATTRIBUTION = "CREATE TABLE " + TABLE_ATTRIBUTION
           + "("
           + KEY_ID + " INTEGER PRIMARY KEY, "
           + KEY_PARRAINIDFACEBOOK + " TEXT, "
           + KEY_FILLEULIDFACEBOOK + " TEXT"
           + ")";




    //Creation de la table ChatMessage
    private final String CREATE_TABLE_CHATMESSAGE = "CREATE TABLE " + TABLE_CHATMESSAGE
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + FACEBOOK_ID + " TEXT, "
            + SENDER_ID + " TEXT, "
            + RECEIVER_ID + " TEXT, "
            + CHAT_MESSAGE + " TEXT, "
            + TYPE + " TEXT"
            + ")";







    /* *
     * Operations sur la table Contact
     * */


	// Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_STATUT_TABLE);
        db.execSQL(CREATE_TABLE_BESOIN);
        db.execSQL(CREATE_TABLE_KIFFES);
        db.execSQL(CREATE_TABLE_LIEUX);
        db.execSQL(CREATE_TABLE_ATTRIBUTION);
        db.execSQL(CREATE_TABLE_CHATMESSAGE);
    }




    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BESOIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KIFFES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIEUX);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTRIBUTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATMESSAGE);

        // Create tables again
        onCreate(db);
    }












 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    void addContact(Contact contact) {
        db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_idFacebookC, contact.getIdFacebook()); // Contact idFacebook
 
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Contact getContact(int id) {
       db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_idFacebookC}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }
     
    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setIdFacebook(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
    // Updating single contact
    public int updateContact(Contact contact) {
        db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_idFacebookC, contact.getIdFacebook());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }
 
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }







    /**
     * Operations sur la table StatutModele
     * */
    // Adding new StatutModele
    public void addStatutModele(StatutModele statutmodele) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATUT, statutmodele.getStatut()); // Contact Name
        values.put(KEY_idFacebookS, statutmodele.getIdFacebook()); // Contact idFacebook

        // Inserting Row
        db.insert(TABLE_STATUT, null, values);
        db.close(); // Closing database connection
    }


    // Getting single StatutModele
    StatutModele getStatutModele(int id) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STATUT, new String[]{KEY_ID,
                        KEY_STATUT, KEY_idFacebookS}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        StatutModele statutmodele = new StatutModele(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return statutmodele;
    }



    // Getting All StatutModele
    public List<StatutModele> getAllStatutModeles() {
        List<StatutModele> statutmodeleList = new ArrayList<StatutModele>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STATUT;

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StatutModele statutmodele = new StatutModele();
                statutmodele.setId(Integer.parseInt(cursor.getString(0)));
                statutmodele.setStatut(cursor.getString(1));
                statutmodele.setIdFacebook(cursor.getString(2));
                // Adding contact to list
                statutmodeleList.add(statutmodele);
            } while (cursor.moveToNext());
        }

        // return contact list
        return statutmodeleList;
    }

    // Updating single statutmodele
    public int updateStatutModele(StatutModele statutmodele) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATUT, statutmodele.getStatut());
        values.put(KEY_idFacebookS, statutmodele.getIdFacebook());

        // updating row
        return db.update(TABLE_STATUT, values, KEY_ID + " = ?",
                new String[] {String.valueOf(statutmodele.getId())});
    }

    // Deleting single statutmodele
    public void deleteStatutModele(StatutModele statutmodele) {
        db = this.getWritableDatabase();
        db.delete(TABLE_STATUT, KEY_ID + " = ?",
                new String[]{String.valueOf(statutmodele.getId())});
        db.close();
    }


    // Getting statutmodeles Count
    public int getStatutModeleCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STATUT;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }









    /**
     * Operations sur la table BesoinModele
     * */
    // Adding new StatutModele
    public void addBesoinModele(BesoinModele besoinmodele) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AIDE, besoinmodele.getAide());
        values.put(KEY_INTE, besoinmodele.getInte());
        values.put(KEY_idFacebookB, besoinmodele.getIdFacebook()); // Contact idFacebook

        // Inserting Row
        db.insert(TABLE_BESOIN, null, values);
        db.close(); // Closing database connection
    }


    // Getting single StatutModele
    BesoinModele getBesoinModele(int id) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BESOIN, new String[]{KEY_ID,
                        KEY_AIDE, KEY_INTE, KEY_idFacebookB}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        BesoinModele besoinmodele = new BesoinModele(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3));
        // return contact
        return besoinmodele;
    }



    // Getting All StatutModele
    public List<BesoinModele> getAllBesoinModeles() {
        List<BesoinModele> besoinmodeleList = new ArrayList<BesoinModele>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BESOIN;

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BesoinModele besoinmodele = new BesoinModele();
                besoinmodele.setId(Integer.parseInt(cursor.getString(0)));
                besoinmodele.setAide(cursor.getString(1));
                besoinmodele.setInte(cursor.getString(2));
                besoinmodele.setIdFacebook(cursor.getString(3));
                // Adding contact to list
                besoinmodeleList.add(besoinmodele);
            } while (cursor.moveToNext());
        }

        // return contact list
        return besoinmodeleList;
    }

    // Updating single besoinmodele
    public int updateBesoinModele(BesoinModele besoinmodele) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AIDE, besoinmodele.getAide());
        values.put(KEY_INTE, besoinmodele.getInte());
        values.put(KEY_idFacebookB, besoinmodele.getIdFacebook());

        // updating row
        return db.update(TABLE_BESOIN, values, KEY_ID + " = ?",
                new String[] {String.valueOf(besoinmodele.getId())});
    }

    // Deleting single besoinmodele
    public void deleteStatutModele(BesoinModele besoinmodele) {
        db = this.getWritableDatabase();
        db.delete(TABLE_BESOIN, KEY_ID + " = ?",
                new String[]{String.valueOf(besoinmodele.getId())});
        db.close();
    }


    // Getting besoinmodeles Count
    public int getBesoinModeleCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BESOIN;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }



    /**
     * Operation sur la table kiffesmodele
     * */




    // Adding new KiffesModele
    public void addKiffesModele(KiffesModele kiffesmodele) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SPORT, kiffesmodele.getSport());
        values.put(KEY_MUSIQUE, kiffesmodele.getMusique());
        values.put(KEY_GAMING, kiffesmodele.getGaming());
        values.put(KEY_DANSE, kiffesmodele.getDanse());
        values.put(KEY_VIRER, kiffesmodele.getVirer());
        values.put(KEY_CULTURE, kiffesmodele.getCulture());
        values.put(KEY_idFacebookK, kiffesmodele.getIdFacebook());

        // Inserting Row
        db.insert(TABLE_KIFFES, null, values);
        db.close(); // Closing database connection
    }


    // Getting single KiffesModele
    KiffesModele getKiffesModele(int id) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_KIFFES, new String[]{KEY_ID,
                        KEY_SPORT, KEY_MUSIQUE, KEY_GAMING, KEY_DANSE, KEY_VIRER, KEY_CULTURE, KEY_idFacebookK}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        KiffesModele kiffesmodele = new KiffesModele(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
        // return contact
        return kiffesmodele;
    }



    // Getting All KiffesModele
    public List<KiffesModele> getAllKiffesModeles() {
        List<KiffesModele> kiffesmodeleList = new ArrayList<KiffesModele>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_KIFFES;

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KiffesModele kiffesmodele = new KiffesModele();
                kiffesmodele.setId(Integer.parseInt(cursor.getString(0)));
                kiffesmodele.setSport(cursor.getString(1));
                kiffesmodele.setMusique(cursor.getString(2));
                kiffesmodele.setGaming(cursor.getString(3));
                kiffesmodele.setDanse(cursor.getString(4));
                kiffesmodele.setVirer(cursor.getString(5));
                kiffesmodele.setCulture(cursor.getString(6));
                kiffesmodele.setIdFacebook(cursor.getString(7));
                // Adding contact to list
                kiffesmodeleList.add(kiffesmodele);
            } while (cursor.moveToNext());
        }

        // return contact list
        return kiffesmodeleList;
    }

    // Updating single KiffesModele
    public int updateKiffesModele(KiffesModele kiffesmodele) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SPORT, kiffesmodele.getSport());
        values.put(KEY_MUSIQUE, kiffesmodele.getMusique());
        values.put(KEY_GAMING, kiffesmodele.getGaming());
        values.put(KEY_DANSE, kiffesmodele.getDanse());
        values.put(KEY_VIRER, kiffesmodele.getVirer());
        values.put(KEY_CULTURE, kiffesmodele.getCulture());
        values.put(KEY_idFacebookK, kiffesmodele.getIdFacebook());

        // updating row
        return db.update(TABLE_KIFFES, values, KEY_ID + " = ?",
                new String[] {String.valueOf(kiffesmodele.getId())});
    }

    // Deleting single KiffesModele
    public void deleteKiffesModele(KiffesModele kiffesmodele) {
        db = this.getWritableDatabase();
        db.delete(TABLE_KIFFES, KEY_ID + " = ?",
                new String[]{String.valueOf(kiffesmodele.getId())});
        db.close();
    }


    // Getting modeles Count
    public int getKiffesModeleCount() {
        String countQuery = "SELECT  * FROM " + TABLE_KIFFES;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }





    /**
     * Operation sur la table lieuxmodele
     * */




    // Adding new LieuxModele
    public void addLieuxModele(LieuxModele lieuxmodele) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UNIV, lieuxmodele.getUniv());
        values.put(KEY_idFacebookL, lieuxmodele.getIdFacebook());


        // Inserting Row
        db.insert(TABLE_LIEUX, null, values);
        db.close(); // Closing database connection
    }


    // Getting single LieuxModele
    LieuxModele getLieuxModele(int id) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LIEUX, new String[]{KEY_ID,
                        KEY_UNIV, KEY_idFacebookL}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LieuxModele lieuxmodele = new LieuxModele(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return lieuxmodele;
    }



    // Getting All LieuxModele
    public List<LieuxModele> getAllLieuxModeles() {
        List<LieuxModele> lieuxmodeleList = new ArrayList<LieuxModele>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LIEUX;

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LieuxModele lieuxmodele = new LieuxModele();
                lieuxmodele.setId(Integer.parseInt(cursor.getString(0)));
                lieuxmodele.setUniv(cursor.getString(1));
                lieuxmodele.setIdFacebook(cursor.getString(2));
                // Adding contact to list
                lieuxmodeleList.add(lieuxmodele);
            } while (cursor.moveToNext());
        }

        // return contact list
        return lieuxmodeleList;
    }

    // Updating single LieuxModele
    public int updateLieuxModele(LieuxModele lieuxmodele) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UNIV, lieuxmodele.getUniv());
        values.put(KEY_idFacebookL, lieuxmodele.getIdFacebook());

        // updating row
        return db.update(TABLE_LIEUX, values, KEY_ID + " = ?",
                new String[]{String.valueOf(lieuxmodele.getId())});
    }

    // Deleting single LieuxModele
    public void deleteLieuxModele(LieuxModele lieuxmodele) {
        db = this.getWritableDatabase();
        db.delete(TABLE_LIEUX, KEY_ID + " = ?",
                new String[]{String.valueOf(lieuxmodele.getId())});
        db.close();
    }


    // Getting lieuxmodeles Count
    public int getLieuxModeleCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LIEUX;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }



    /**
     * Operation sur la table Attribution
     * */




    // Adding new AttributionModele
    public void addAttributionModele(AttributionModele attributionmodele) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( KEY_FILLEULIDFACEBOOK, attributionmodele.getParrainIdFacebook());
        values.put(KEY_PARRAINIDFACEBOOK, attributionmodele.getFilleulIdFacebook());


        // Inserting Row
        db.insert(TABLE_ATTRIBUTION, null, values);
        db.close(); // Closing database connection
    }


    // Getting single AttributionModele
    AttributionModele getAttributionModele(int id) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ATTRIBUTION, new String[]{KEY_ID,
                        KEY_FILLEULIDFACEBOOK, KEY_PARRAINIDFACEBOOK}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        AttributionModele attributionmodele = new AttributionModele(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return attributionmodele;
    }



    // Getting All AttributionModele
    public List<AttributionModele> getAllAttributionModeles() {
        List<AttributionModele> attributionmodeleList = new ArrayList<AttributionModele>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ATTRIBUTION;

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AttributionModele attributionmodele = new AttributionModele();
                attributionmodele.setId(Integer.parseInt(cursor.getString(0)));
                attributionmodele.setFilleulIdFacebook(cursor.getString(1));
                attributionmodele.setParrainIdFacebook(cursor.getString(2));
                // Adding contact to list
                attributionmodeleList.add(attributionmodele);
            } while (cursor.moveToNext());
        }
        // return contact list
        return attributionmodeleList;
    }




    // Updating single AttributionModele
    public int updateAttributionModele(AttributionModele attributionmodele) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FILLEULIDFACEBOOK, attributionmodele.getFilleulIdFacebook());
        values.put(KEY_PARRAINIDFACEBOOK, attributionmodele.getParrainIdFacebook());

        // updating row
        return db.update(TABLE_ATTRIBUTION, values, KEY_ID + " = ?",
                new String[] { String.valueOf(attributionmodele.getId())});
    }


    // Deleting single AttributionModele
    public void deleteAttributionModele(AttributionModele attributionmodele) {
        db = this.getWritableDatabase();
        db.delete(TABLE_ATTRIBUTION, KEY_ID + " = ?",
                new String[]{String.valueOf(attributionmodele.getId())});
        db.close();
    }




    // Getting AttributionModeles Count
    public int getAttributionModeleCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ATTRIBUTION;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }







    //Insert chat message
    public boolean insertChatMessage(ChatObject messageData, String type){
        boolean dataStored = true;
        db = this.getWritableDatabase();
        try{
            String str = messageData.getStrFBId().toString();
            Log.i(TAG, "insertMessageData str :" + str);

            ContentValues values = new ContentValues();

            values.put(DatabaseHandler.FACEBOOK_ID,str);
            values.put(DatabaseHandler.SENDER_ID, messageData.getStrSenderId());
            Log.i(TAG, "insertMessageData getStrSenderId....." + messageData.getStrSenderId());
            values.put(DatabaseHandler.RECEIVER_ID, messageData.getStrReceiverId());
            Log.d(TAG, "Insertion de RECEIVER_ID dans BDD == == " + messageData.getStrReceiverId());
            values.put(DatabaseHandler.CHAT_MESSAGE, messageData.getMessage());
            Log.d(TAG, "Insertion de chat message dans BDD == == " + messageData.getMessage());
            values.put(DatabaseHandler.TYPE, messageData.getType());
            Log.d(TAG, "Insertion du type de message dans BDD == == " + messageData.getType());
            db.insert(TABLE_CHATMESSAGE, null, values);
        } catch (Exception e) {
        e.printStackTrace();
            dataStored = false;
    } finally {
        if (db != null)
            db.close();
    }

        return dataStored;
    }




    public ArrayList<ChatObject> getChatMessages(String idFacebook){
        ArrayList<ChatObject> listMessage = new ArrayList<ChatObject>();
        Cursor cursor = null;
        try{
            db = this.getReadableDatabase();
            //Requette pour la selection des chatMessage
            String selectQuery = "SELECT  * FROM " + TABLE_CHATMESSAGE + " WHERE FACEBOOK_ID = "+ idFacebook;

            cursor = db.rawQuery(selectQuery,null);

            if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0){
                do {
                    ChatObject objChatMessage = new ChatObject();
                    Log.i(TAG,
                            "getChatMessages senderid FACEBOOK_ID......"
                                    + cursor.getString(cursor.getColumnIndex(DatabaseHandler.FACEBOOK_ID)));
                    objChatMessage.setType(cursor.getString(cursor.getColumnIndex(DatabaseHandler.TYPE)));
                  //  objChatMessage.setStrSenderId(cursor.getString(cursor.getColumnIndex(DatabaseHandler.FACEBOOK_ID)));
                    objChatMessage.setMessage(cursor.getString(cursor.getColumnIndex(DatabaseHandler.CHAT_MESSAGE)));
                    listMessage.add(objChatMessage);
                    System.out.println("Getting favorote heritagspot detail");

                }while(cursor.moveToNext());
            }

        }catch (SQLiteException lSqlEx) {
            Log.e(TAG, "Could not open database");
            Log.e(TAG, "Exception:" + lSqlEx.getMessage());

        } catch (SQLException lEx) {
            Log.e(TAG, "Could not fetch trip data");
            Log.e(TAG, "Exception:" + lEx.getMessage());
        } finally {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
        }
        return listMessage;
    }



    public ArrayList<ProfilUser> getListPers(){
        ArrayList<ProfilUser> users =new ArrayList<ProfilUser>();
        Cursor cursor = null;
        try{

        }catch (SQLiteException lSqlEx) {
            Log.e(TAG, "Could not open database");
            Log.e(TAG, "Exception:" + lSqlEx.getMessage());

        } catch (SQLException lEx) {
            Log.e(TAG, "Could not fetch trip data");
            Log.e(TAG, "Exception:" + lEx.getMessage());
        } finally {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
        }
        return users;
    }





}
