package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VegetableDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Vegetable";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_NAME = "Product";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_SALE = "sale";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_BANNER = "banner";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_FEATURED = "featured";

    // SQL statement to create the Drink table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_CATEGORY_ID + " INTEGER, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_QUANTITY + " INTEGER, " +
            COLUMN_SALE + " INTEGER, " +
            COLUMN_BANNER + " TEXT, " +
            COLUMN_IMAGE + " TEXT, " +
            COLUMN_FEATURED + " INTEGER DEFAULT 0)";

    public VegetableDBHelper(Context context) {
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

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        db.close();
    }

    // Insert a new product
    public boolean addProduct(String name, String description, int categoryId, double price, int quantity, int sale, String banner, String image, boolean featured) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_CATEGORY_ID, categoryId);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_SALE, sale);
        values.put(COLUMN_BANNER, banner);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_FEATURED, featured ? 1 : 0);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    // Get all product
    public Cursor getAllProduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Get product by category ID
    public Cursor getProductByCategory(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_CATEGORY_ID + " = ?", new String[]{String.valueOf(categoryId)}, null, null, null);
    }

    // Get product best deal
    public Cursor getBestDealProduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_FEATURED + " = ?", new String[]{String.valueOf("1")}, null, null, null);
    }

    // Update a product
    public boolean updateProduct(int id, String name, String description, int categoryId, double price, int quantity, int sale, String banner, String image, boolean featured) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_CATEGORY_ID, categoryId);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_SALE, sale);
        values.put(COLUMN_BANNER, banner);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_FEATURED, featured ? 1 : 0);
        int result = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    // Delete a product
    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}

