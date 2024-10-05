package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class OrderDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Vegetable.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_NAME = "OrderTable";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "user_id"; // Thay thế userEmail bằng user_id
    private static final String COLUMN_ADDRESS_ID = "address_id";
    private static final String COLUMN_TOTAL = "total";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DATE_TIME = "dateTime";
    private static final String COLUMN_PAYMENT_ID = "payment_id";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_REVIEW = "review";
    private static final String COLUMN_RATE = "rate";
    private static final String COLUMN_VOUCHER = "voucher";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_ID + " INTEGER, " + // Thêm user_id
            COLUMN_ADDRESS_ID + " INTEGER, " +
            // COLUMN_LIST_PRODUCT_NAME + " TEXT, " + // Có thể loại bỏ
            COLUMN_TOTAL + " REAL NOT NULL, " +
            COLUMN_PRICE + " REAL NOT NULL, " +
            COLUMN_DATE_TIME + " TEXT NOT NULL, " +
            COLUMN_PAYMENT_ID + " INTEGER, " + // Thêm payment_id
            COLUMN_STATUS + " INTEGER NOT NULL, " +
            COLUMN_REVIEW + " TEXT, " +
            COLUMN_RATE + " INTEGER, " +
            COLUMN_VOUCHER + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES Account(id) ON DELETE CASCADE, " +
            "FOREIGN KEY (" + COLUMN_ADDRESS_ID + ") REFERENCES Address(id) ON DELETE SET NULL, " +
            "FOREIGN KEY (" + COLUMN_PAYMENT_ID + ") REFERENCES Payment(id) ON DELETE SET NULL, " +
            "FOREIGN KEY (" + COLUMN_VOUCHER + ") REFERENCES Voucher(id) ON DELETE SET NULL" + // Giả định có bảng Voucher
            ");";

    public OrderDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        // Nếu bạn muốn tạo bảng khác như Voucher, hãy thêm câu lệnh tạo bảng ở đây
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addOrder(int userId, int addressId, double total, double price,
                            String dateTime, int paymentId, int status, String review, int rate, int voucher) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, userId);
            values.put(COLUMN_ADDRESS_ID, addressId);
            values.put(COLUMN_TOTAL, total);
            values.put(COLUMN_PRICE, price);
            values.put(COLUMN_DATE_TIME, dateTime);
            values.put(COLUMN_PAYMENT_ID, paymentId);
            values.put(COLUMN_STATUS, status);
            values.put(COLUMN_REVIEW, review);
            values.put(COLUMN_RATE, rate);
            values.put(COLUMN_VOUCHER, voucher);

            long result = db.insert(TABLE_NAME, null, values);
            return result != -1;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT o.*, a.name AS user_name, addr.address, p.name AS payment_method " +
                "FROM " + TABLE_NAME + " o " +
                "LEFT JOIN Account a ON o." + COLUMN_USER_ID + " = a.id " +
                "LEFT JOIN Address addr ON o." + COLUMN_ADDRESS_ID + " = addr.id " +
                "LEFT JOIN Payment p ON o." + COLUMN_PAYMENT_ID + " = p.id";
        return db.rawQuery(query, null);
    }

    public Cursor getOrdersByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT o.*, a.name AS user_name, addr.address, p.name AS payment_method " +
                "FROM " + TABLE_NAME + " o " +
                "LEFT JOIN Account a ON o." + COLUMN_USER_ID + " = a.id " +
                "LEFT JOIN Address addr ON o." + COLUMN_ADDRESS_ID + " = addr.id " +
                "LEFT JOIN Payment p ON o." + COLUMN_PAYMENT_ID + " = p.id " +
                "WHERE o." + COLUMN_USER_ID + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }

    public boolean updateOrder(int id, int userId, int addressId, double total, double price,
                               String dateTime, int paymentId, int status, String review, int rate, int voucher) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, userId);
            values.put(COLUMN_ADDRESS_ID, addressId);
            // values.put(COLUMN_LIST_PRODUCT_NAME, listProductName); // Nếu cần
            values.put(COLUMN_TOTAL, total);
            values.put(COLUMN_PRICE, price);
            values.put(COLUMN_DATE_TIME, dateTime);
            values.put(COLUMN_PAYMENT_ID, paymentId);
            values.put(COLUMN_STATUS, status);
            values.put(COLUMN_REVIEW, review);
            values.put(COLUMN_RATE, rate);
            values.put(COLUMN_VOUCHER, voucher);

            int result = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            return result > 0;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public boolean deleteOrder(int id) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            int rowsAffected = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            return rowsAffected > 0;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public Cursor getOrderWithProducts(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT p.*, op.count, op.option, op.price " +
                "FROM OrderProduct op " +
                "INNER JOIN Product p ON op.product_id = p.id " +
                "WHERE op.order_id = ?";
        return db.rawQuery(query, new String[]{String.valueOf(orderId)});
    }
}
