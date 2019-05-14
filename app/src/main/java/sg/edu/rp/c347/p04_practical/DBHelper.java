package sg.edu.rp.c347.p04_practical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cars.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CAR = "car";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_LITRE = "litre";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_CAR + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BRAND + " TEXT, "
                + COLUMN_LITRE + " REAL )";
        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);
        onCreate(db);
    }
    public void insertCar(String brand, double litre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_LITRE, litre);
        db.insert(TABLE_CAR, null, values);
        db.close();
    }
    public ArrayList<Car> getAllCars() {
        ArrayList<Car> car = new ArrayList<Car>();
        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_BRAND + ", "
                + COLUMN_LITRE
                + " FROM " + TABLE_CAR;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String brand = cursor.getString(1);
                double litre = cursor.getInt(2);
                Car obj = new Car(id, brand, litre);
                car.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return car;
    }

    public ArrayList<String> getCarContent() {
        //TODO return records in Strings

        // Create an ArrayList that holds String objects
        ArrayList<String> notes = new ArrayList<String>();
        // Select all the notes' content
        String selectQuery = "SELECT "
                + COLUMN_BRAND +
                " FROM " + TABLE_CAR;

        // Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        // Run the SQL query and get back the Cursor object
        Cursor cursor = db.rawQuery(selectQuery, null);
        // moveToFirst() moves to first row
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row and returns true;
            // moveToNext() returns false when no more next row to move to
            do {
                notes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return notes;
    }
}
