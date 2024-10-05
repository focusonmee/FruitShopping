package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedbackDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Vegetable.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_NAME = "Feedback";
    private static final String COLUMN_ID = "id"; // Unique identifier for feedback
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_COMMENT = "comment";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_EMAIL + " TEXT NOT NULL, " +
            COLUMN_PHONE + " TEXT NOT NULL, " +
            COLUMN_COMMENT + " TEXT NOT NULL)";

    public FeedbackDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addFeedback(String name, String email, String phone, String comment) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PHONE, phone);
            values.put(COLUMN_COMMENT, comment);

            long result = db.insert(TABLE_NAME, null, values);
            return result != -1; // Returns true if insert is successful
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }


    public Cursor getAllFeedback() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }


    public boolean updateFeedback(int id, String name, String email, String phone, String comment) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PHONE, phone);
            values.put(COLUMN_COMMENT, comment);

            int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            return rowsAffected > 0; // Returns true if update is successful
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }


    public boolean deleteFeedback(int id) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            int rowsAffected = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            return rowsAffected > 0; // Returns true if deletion is successful
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }
}
