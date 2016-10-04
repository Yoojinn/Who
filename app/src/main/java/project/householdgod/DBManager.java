package project.householdgod;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 10105-김유진 on 2016-09-06.
 */
public class DBManager {
    //DB명, 테이블명, DB버전 정보 정의
    //==============================================================================
    static final String DB_SensorInfo ="SensorInfo.db";
    static final String TABLE = "SensorInfo";
    static final int DB_VERSION = 1;
    //==============================================================================

    Context mContext = null;

    private static DBManager mDbManager = null;
    private SQLiteDatabase mDatabase = null;

    //==============================================================================

    public static DBManager getInstance(Context context)
    {
        if(mDbManager == null)
        {
            mDbManager = new DBManager(context);
        }
        return mDbManager;
    }

    //==============================================================================

    private DBManager(Context context) {
        mContext = context;

            mDatabase = context.openOrCreateDatabase(DB_SensorInfo, Context.MODE_PRIVATE, null);
        //==============================================================================
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "UserCheck        INTEGER DEFAULT 0,"+ //사용자가 확인했는지 안했는지 체크
                            "DoorOpenTime   TEXT ,"+ //문이 열린 시각
                            "SensorOntime   TEXT ,"+ //센서가 On된 시각
                            "DoorbellRingPicture    TEXT,"+ //초인종을 누른 사람의 사진
                            "DoorbellRingTime   TEXT);" //초인종이 눌린 시각
        );
    }

    public long insert(String table, ContentValues addRowValue)
    {
        if(table.equals("Words")){
            addRowValue.put("correct_num",0);
        }
        return mDatabase.insert(table,null,addRowValue);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return mDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public int delete(String table, String whereClause, String[] whereArgs){
        return mDatabase.delete(table,whereClause,whereArgs);
    }

    public void removeTable(String tableName) {
        String sql = "drop tableName "+tableName;
        mDatabase.execSQL(sql);
    }

    public int update(String table, ContentValues updateRowValue, String whereClause, String[] whereArgs){
        return mDatabase.update(table, updateRowValue,whereClause,whereArgs);
    }

    public Cursor searchAutocomplete(String table, String updateRowValue,String whereClause){
        whereClause = "'%"+whereClause+"%'";
        return mDatabase.rawQuery("SELECT * FROM " + table + " WHERE "+ updateRowValue + " LIKE "+whereClause+";", null);
    }

}
