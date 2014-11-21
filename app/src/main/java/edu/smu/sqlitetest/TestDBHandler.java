package edu.smu.sqlitetest;

/**
 * Created by NicholasSaulnier on 11/19/2014.
 */
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

public class TestDBHandler extends SQLiteOpenHelper {

    // the default system path for database
    private static String DB_PATH = "/data/data/edu.smu.sqlitetest/databases/";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "testDB.db";
    private static final String TABLE_PATIENTS = "patients";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PATIENTNAME = "patientname";
    public static final String COLUMN_PATIENTDOB = "patientdob";

    private static final String TAG = null;

    private TestDatabase myDataBase;
    private final Context myContext;

    public TestDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.myContext = context;
    }

    // Add Handler method
    public void addPatient(TestDatabase patient) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENTNAME, patient.getPatientName());
        values.put(COLUMN_PATIENTDOB, patient.getPatientDOB());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_PATIENTS, null, values);
        db.close();
    }

    // query Handler method
    // this only returns a single patient match
    public TestDatabase findPatient(String patientname) {
        String query = "Select * FROM " + TABLE_PATIENTS + " WHERE " + COLUMN_PATIENTNAME + " =  \"" + patientname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        TestDatabase patient = new TestDatabase();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            patient.setID(Integer.parseInt(cursor.getString(0)));
            patient.setPatientName(cursor.getString(1));
            patient.setPatientDOB(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else {
            patient = null;
        }
        db.close();
        return patient;
    }

    // delete handler method
    // I do not plan on having this as an option at first
    public boolean deletePatient(String patientname) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_PATIENTS + " WHERE " + COLUMN_PATIENTNAME + " =  \"" + patientname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        TestDatabase patient = new TestDatabase();

        if (cursor.moveToFirst()) {
            patient.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_PATIENTS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(patient.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PATIENTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PATIENTNAME
                + " TEXT," + COLUMN_PATIENTDOB + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // for now, just delete the old database with a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        onCreate(db);
    }

}