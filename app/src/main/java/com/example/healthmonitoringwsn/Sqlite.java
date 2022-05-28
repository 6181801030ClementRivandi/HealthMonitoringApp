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

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "User Profile";

    private static final String TABLE_PROFILE = "Profile";

    private static final String KEY_NIK_USER = "NIKUser";
    private static final String KEY_NAMA_USER = "namaUser";
    private static final String KEY_USIA_USER = "usiaUser";
    private static final String KEY_TANGGALLAHIR_USER = "tanggalLahirUser";
    private static final String KEY_ID_USER = "idUser";
    private static final String KEY_NOMORHP_USER = "nomorHPUser";
    private static final String KEY_EMAIL_USER = "emailUser";
    private static final String KEY_TANGGALDAFTAR_USER = "tanggalDaftarUser";
    private static final String KEY_NAMAKLINIK_USER = "namaKlinikUser";

    public Sqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Sqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                + KEY_NIK_USER + " INTEGER,"
                + KEY_NAMA_USER + " TEXT,"
                + KEY_USIA_USER + " TEXT,"
                + KEY_TANGGALLAHIR_USER + " TEXT,"
                + KEY_NOMORHP_USER + " TEXT,"
                + KEY_EMAIL_USER + " TEXT,"
                + KEY_TANGGALDAFTAR_USER + " TEXT,"
                + KEY_NAMAKLINIK_USER + " TEXT,"
                + KEY_ID_USER + " INTEGER PRIMARY KEY"
                + ")";
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
        values.put(KEY_NIK_USER, Profile.getNIK());
        values.put(KEY_NAMA_USER, Profile.getNamaUser());
        values.put(KEY_USIA_USER, Profile.getUsiaUser());
        values.put(KEY_TANGGALLAHIR_USER, Profile.getTanggalLahirUser());
        values.put(KEY_NOMORHP_USER, Profile.getNomorHP());
        values.put(KEY_EMAIL_USER, Profile.getEmail());
        values.put(KEY_TANGGALDAFTAR_USER, Profile.getTanggalDaftar());
        values.put(KEY_NAMAKLINIK_USER, Profile.getNamaKlinik());
        values.put(KEY_ID_USER, Profile.getIdUser());
        db.insert(TABLE_PROFILE, null, values);
        db.close();
    }

    public Profile getContact(int idUser) {
        Profile contact = new Profile();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROFILE, new String[]{KEY_NIK_USER, KEY_NAMA_USER,
                        KEY_USIA_USER, KEY_TANGGALLAHIR_USER,KEY_NOMORHP_USER, KEY_EMAIL_USER, KEY_TANGGALDAFTAR_USER, KEY_NAMAKLINIK_USER, KEY_ID_USER}, KEY_ID_USER + "=?",
                new String[]{String.valueOf(idUser)}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            contact = new Profile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8));
        }
        return contact;
    }

    public int updateContact(Profile contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NIK_USER, contact.getNIK());
        values.put(KEY_NAMA_USER, contact.getNamaUser());
        values.put(KEY_USIA_USER, contact.getUsiaUser());
        values.put(KEY_TANGGALLAHIR_USER, contact.getTanggalLahirUser());
        values.put(KEY_NOMORHP_USER, contact.getNomorHP());
        values.put(KEY_EMAIL_USER, contact.getEmail());
        values.put(KEY_TANGGALDAFTAR_USER, contact.getTanggalDaftar());
        values.put(KEY_NAMAKLINIK_USER, contact.getNamaKlinik());
        values.put(KEY_ID_USER, contact.getIdUser());
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
