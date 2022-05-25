package com.example.healthmonitoringwsn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.healthmonitoringwsn.Model.Profile;

import java.util.ArrayList;
import java.util.List;

public class Sqlite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "User Profile";

    private static final String TABLE_PROFILE = "Profile";

    private static final String KEY_NAMA_USER = "namaUser";
    private static final String KEY_USIA_USER = "usiaUser";
    private static final String KEY_TANGGALLAHIR_USER = "tanggalLahirUser";
    private static final String KEY_ID_USER = "idUser";

    public Sqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Sqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                + KEY_NAMA_USER + " TEXT,"
                + KEY_USIA_USER + " TEXT,"
                + KEY_TANGGALLAHIR_USER + " TEXT,"
                + KEY_ID_USER + " INTEGER PRIMARY KEY" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // on Upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }

    public void addRecord(Profile Profile) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAMA_USER, Profile.getNamaUser());
        values.put(KEY_USIA_USER, Profile.getUsiaUser());
        values.put(KEY_TANGGALLAHIR_USER, Profile.getTanggalLahirUser());
        values.put(KEY_ID_USER, Profile.getIdUser());
        db.insert(TABLE_PROFILE, null, values);
        db.close();
    }

    public Profile getContact(int idUser) {
        Profile contact = new Profile();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROFILE, new String[]{KEY_NAMA_USER,
                        KEY_USIA_USER, KEY_TANGGALLAHIR_USER, KEY_ID_USER}, KEY_ID_USER + "=?",
                new String[]{String.valueOf(idUser)}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            contact = new Profile(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        }
        return contact;
    }

    public int updateContact(Profile contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAMA_USER, contact.getNamaUser());
        values.put(KEY_USIA_USER, contact.getUsiaUser());
        values.put(KEY_TANGGALLAHIR_USER,contact.getTanggalLahirUser());
        values.put(KEY_ID_USER,contact.getIdUser());
        // updating row
        return db.update(TABLE_PROFILE, values, KEY_ID_USER + " = ?",
                new String[]{String.valueOf(contact.getIdUser())});
    }

    public List<Profile> getAllRecord() {
        List<Profile> contactList = new ArrayList<Profile>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Profile Profile = new Profile();
                Profile.setNamaUser(cursor.getString(0));
                Profile.setUsiaUser(cursor.getString(1));
                Profile.setTanggalLahirUser(cursor.getString(2));
                Profile.setIdUser(Integer.parseInt(cursor.getString(3)));
                contactList.add(Profile);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
}
