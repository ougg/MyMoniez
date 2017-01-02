package oug.com.mymoniez;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by Wojtek on 02.01.2017.
 */
public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "items.db";
    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VALUE = "value";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DESCRIPTION = "description";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+TABLE_ITEMS+" ( "+
                        COLUMN_ID +" INTEGER PRIMARY KEY, "+
                        COLUMN_VALUE +" REAL, "+
                        COLUMN_DATE +" INTEGER, "+
                        COLUMN_CATEGORY + " TEXT, "+
                        COLUMN_DESCRIPTION +" TEXT"+
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST "+TABLE_ITEMS);
        onCreate(sqLiteDatabase);
    }

    public void addItem(MoneyEvent event){
        ContentValues values = new ContentValues();
        values.put(COLUMN_VALUE,event.getValue());
        values.put(COLUMN_DATE,event.getDateMillis());
        values.put(COLUMN_CATEGORY, event.getCategory().name());
        values.put(COLUMN_DESCRIPTION,event.getDescription());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ITEMS,null,values);
        db.close();
    }

    public void deleteItem(int id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM "+ TABLE_ITEMS+
                        " WHERE "+ COLUMN_ID +"="+id+";";
        db.execSQL(query);
        db.close();
    }

    public ArrayList<MoneyEvent> getItemsList(long date, @Nullable MoneyEvent.Category filterCategory){
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM "+TABLE_ITEMS+
                        " WHERE "+ COLUMN_DATE + " > " + date;
        if(filterCategory!=null){
            query += " AND "+COLUMN_CATEGORY +" =\""+filterCategory.name()+"\"";
        }
        query += " ORDER BY "+COLUMN_DATE+" DESC;";

        Cursor c = db.rawQuery(query,null);

        ArrayList<MoneyEvent> list = new ArrayList<>();
        c.moveToFirst();
        while (!c.isAfterLast()){
            MoneyEvent item = new MoneyEvent();
            item.setEventId(c.getInt(c.getColumnIndex(COLUMN_ID)));
            item.setValue(c.getDouble(c.getColumnIndex(COLUMN_VALUE)));
            item.setDateMillis(c.getLong(c.getColumnIndex(COLUMN_DATE)));
            item.setCategory(MoneyEvent.Category.valueOf(c.getString(c.getColumnIndex(COLUMN_CATEGORY))));
            item.setDescription(c.getString(c.getColumnIndex(COLUMN_DESCRIPTION)));

            list.add(item);
            c.moveToNext();
        }
        db.close();
        return list;
    }
}
