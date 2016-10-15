package project.householdgod;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;


/**
 * Created by 10105-김유진 on 2016-09-01.
 */
@SuppressLint("ValidFragment")
public class RecentlyNotice extends Fragment{

    protected ListView listView;
    protected DBManager mDbManager;
    protected CursorAdapterEx cursorAdapterEx;
    protected Cursor c;
    protected TextView textView;
    protected LinearLayout linearLayout;

    protected Context mContext;

    public RecentlyNotice(Context context){
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                                               ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recently_notice_main, null);

        listView = (ListView)view.findViewById(R.id.list_view);
        textView = (TextView)view.findViewById(R.id.recently_textview);

        Context context = getActivity();
        mDbManager = DBManager.getInstance(context);

        //<임시 데이터>
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("DoorOpenTime","2016-08-20 13:00");
//        contentValues.put("SensorOntime","2016-08-20 13:01");
//        mDbManager.insert(DBManager.TABLE,contentValues);
//        ContentValues contentValues2 = new ContentValues();
//        contentValues2.put("DoorbellRingTime","2016-09-20 13:01");
//        mDbManager.insert(DBManager.TABLE,contentValues);

        String[] Columns = new String[]{"_id","SensorOntime", "DoorOpenTime", "DoorbellRingPicture","DoorbellRingTime","UserCheck"};
        c = mDbManager.query("SensorInfo", Columns, "UserCheck='0'", null, null, null, null);

        c.moveToFirst();
        if(c.getCount()!=0){
            setListview();
        }
        else{
            linearLayout = (LinearLayout)view.findViewById(R.id.recently_linear_layout);
            linearLayout.setGravity(Gravity.CENTER);
            textView.setText("알림 내역이 없습니다.");
        }

        return view;
    }

    //전적 리스트뷰 ------------------------------------------------------
    public void setListview(){
        cursorAdapterEx = new CursorAdapterEx(mContext,c,0);
        listView.setAdapter(cursorAdapterEx);
    }
    //---------------------------------------------------------------------

    public void userCheckUpdate(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserCheck","1");
        mDbManager.update("SensorInfo",contentValues,"UserCheck='0'",null);
    }
}
