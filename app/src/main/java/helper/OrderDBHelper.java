package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Vegetable.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_NAME = "OrderTable";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_EMAIL = "userEmail";
    private static final String COLUMN_ADDRESS_ID = "address_id";
    private static final String COLUMN_LIST_PRODUCT_NAME = "listProductName";
    private static final String COLUMN_TOTAL = "total";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DATE_TIME = "dateTime";
    private static final String COLUMN_PAYMENT_METHOD = "paymentMethod";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_REVIEW = "review";
    private static final String COLUMN_RATE = "rate";
    private static final String COLUMN_VOUCHER = "voucher";

    // SQL statement to create the Order table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_EMAIL + " TEXT, " +
            COLUMN_ADDRESS_ID + " INTEGER, " +
            COLUMN_LIST_PRODUCT_NAME + " TEXT, " +
            COLUMN_TOTAL + " REAL, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_DATE_TIME + " TEXT, " +
            COLUMN_PAYMENT_METHOD + " TEXT, " +
            COLUMN_STATUS + " INTEGER, " +
            COLUMN_REVIEW + " TEXT, " +
            COLUMN_RATE + " INTEGER, " +
            COLUMN_VOUCHER + " INTEGER)";

    public OrderDBHelper(Context context) {
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

    // Insert a new order
    public boolean addOrder(String userEmail, int addressId, String listProductName, double total, double price,
                            String dateTime, String paymentMethod, int status, String review, int rate, int voucher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, userEmail);
        values.put(COLUMN_ADDRESS_ID, addressId);
        values.put(COLUMN_LIST_PRODUCT_NAME, listProductName);
        values.put(COLUMN_TOTAL, total);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DATE_TIME, dateTime);
        values.put(COLUMN_PAYMENT_METHOD, paymentMethod);
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_REVIEW, review);
        values.put(COLUMN_RATE, rate);
        values.put(COLUMN_VOUCHER, voucher);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    // Get all orders
    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Get orders by user email
    public Cursor getOrdersByUserEmail(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_USER_EMAIL + " = ?", new String[]{userEmail}, null, null, null);
    }

    // Update an order
    public boolean updateOrder(int id, String userEmail, int addressId, String listProductName, double total,
                               double price, String dateTime, String paymentMethod, int status, String review, int rate, int voucher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, userEmail);
        values.put(COLUMN_ADDRESS_ID, addressId);
        values.put(COLUMN_LIST_PRODUCT_NAME, listProductName);
        values.put(COLUMN_TOTAL, total);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DATE_TIME, dateTime);
        values.put(COLUMN_PAYMENT_METHOD, paymentMethod);
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_REVIEW, review);
        values.put(COLUMN_RATE, rate);
        values.put(COLUMN_VOUCHER, voucher);
        int result = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    // Delete an order
    public void deleteOrder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
