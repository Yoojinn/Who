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
    static final String DB_WHO ="WHO.db";
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

        mDatabase = context.openOrCreateDatabase(DB_WHO, Context.MODE_PRIVATE, null);
        //==============================================================================
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "UserCheck        INTEGER DEFAULT 0,"+ //사용자가 확인했는지 안했는지 체크
                            "KindOfSensor   TEXT ,"+ //센서의 종류
                            "picture    TEXT, " +//초인종 사진 저장 경로
                            "time   TEXT " +
                            ");" //센서 시간
                             /*  KindOfSensor / time / DoorBellRingPicture
                                1 : 전원 ON
                                2 : 전원 OFF
                                3 : 문 열림
                                4 : 문 닫힘
                                5 : 센서등 켜짐
                                6 : 초인종 초인종 사진 */

        );
    }

    public long insert(ContentValues addRowValue)
    {
        return mDatabase.insert(TABLE,null,addRowValue);
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

}
