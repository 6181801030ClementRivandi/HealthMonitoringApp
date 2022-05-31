package com.example.healthmonitoringwsn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Model.ProfileStaff;

import java.util.ArrayList;
import java.util.List;

public class SqliteStaff extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Staff Profile";

    private static final String TABLE_PROFILE = "Staff";

    private static final String KEY_NAMA_STAFF = "namaStaff";
    private static final String KEY_NAMAKLINIK_STAFF = "namaKlinikStaff";
    private static final String KEY_ID_STAFF = "idStaff";

    public SqliteStaff(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SqliteStaff(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                + KEY_NAMA_STAFF + " TEXT,"
                + KEY_NAMAKLINIK_STAFF + " TEXT,"
                + KEY_ID_STAFF + " INTEGER PRIMARY KEY"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // on Upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }

    public void addRecord(ProfileStaff profileStaff) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAMA_STAFF, profileStaff.getNamaStaff());
        values.put(KEY_NAMAKLINIK_STAFF, profileStaff.getNamaKlinikStaff());
        values.put(KEY_ID_STAFF, profileStaff.getIdStaff());
        db.insert(TABLE_PROFILE, null, values);
        db.close();
    }

    public ProfileStaff getContact(int idUser) {
        ProfileStaff contact = new ProfileStaff();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROFILE, new String[]{KEY_NAMA_STAFF, KEY_NAMAKLINIK_STAFF, KEY_ID_STAFF}, KEY_ID_STAFF + "=?",
                new String[]{String.valueOf(idUser)}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            contact = new ProfileStaff(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
        }
        return contact;
    }

    public int updateContact(ProfileStaff contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();;
        values.put(KEY_NAMA_STAFF, contact.getNamaStaff());
        values.put(KEY_NAMAKLINIK_STAFF, contact.getNamaKlinikStaff());
        values.put(KEY_ID_STAFF, contact.getIdStaff());
        // updating row
        return db.update(TABLE_PROFILE, values, KEY_ID_STAFF + " = ?",
                new String[]{String.valueOf(contact.getIdStaff())});
    }

    public List<ProfileStaff> getAllRecord() {
        List<ProfileStaff> contactList = new ArrayList<ProfileStaff>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProfileStaff ProfileStaff = new ProfileStaff();
                ProfileStaff.setNamaStaff(cursor.getString(0));
                ProfileStaff.setNamaKlinikStaff(cursor.getString(1));
                ProfileStaff.setIdStaff(cursor.getInt(2));
                contactList.add(ProfileStaff);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
}
