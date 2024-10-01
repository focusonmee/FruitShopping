package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderVegetableDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Vegetable.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_NAME = "OrderProduct";
    private static final String COLUMN_ORDER_ID = "order_id"; // Foreign key referencing Order table
    private static final String COLUMN_PRODUCT_ID = "product_id"; // Foreign key referencing Product table
    private static final String COLUMN_COUNT = "count";
    private static final String COLUMN_OPTION = "option";
    private static final String COLUMN_PRICE = "price";

    // SQL statement to create the OrderProduct table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ORDER_ID + " INTEGER, " +
            COLUMN_PRODUCT_ID + " INTEGER, " +
            COLUMN_COUNT + " INTEGER, " +
            COLUMN_OPTION + " TEXT, " +
            COLUMN_PRICE + " REAL, " +
            "PRIMARY KEY (" + COLUMN_ORDER_ID + ", " + COLUMN_PRODUCT_ID + "), " +
            "FOREIGN KEY (" + COLUMN_ORDER_ID + ") REFERENCES OrderTable(id), " +
            "FOREIGN KEY (" + COLUMN_PRODUCT_ID + ") REFERENCES Product(id))";

    public OrderVegetableDBHelper(Context context) {
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

    // Insert a new OrderProduct record
    public boolean addOrderProduct(int orderId, int productId, int count, String option, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_ID, orderId);
        values.put(COLUMN_PRODUCT_ID, productId);
        values.put(COLUMN_COUNT, count);
        values.put(COLUMN_OPTION, option);
        values.put(COLUMN_PRICE, price);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    // Get all OrderProduct records for a specific order
    public Cursor getOrderProductByOrderId(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_ORDER_ID + " = ?", new String[]{String.valueOf(orderId)}, null, null, null);
    }

    // Update an OrderProduct record
    public boolean updateOrderProduct(int orderId, int productId, int count, String option, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COUNT, count);
        values.put(COLUMN_OPTION, option);
        values.put(COLUMN_PRICE, price);
        int result = db.update(TABLE_NAME, values, COLUMN_ORDER_ID + " = ? AND " + COLUMN_PRODUCT_ID + " = ?", new String[]{String.valueOf(orderId), String.valueOf(productId)});
        db.close();
        return result > 0;
    }

    // Delete an OrderProduct record
    public void deleteOrderProduct(int orderId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ORDER_ID + " = ? AND " + COLUMN_PRODUCT_ID + " = ?", new String[]{String.valueOf(orderId), String.valueOf(productId)});
        db.close();
    }
}

