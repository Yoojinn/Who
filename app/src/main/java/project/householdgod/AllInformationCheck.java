package project.householdgod;

import android.annotation.SuppressLint;
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
import android.database.Cursor;
import android.widget.TextView;

/**
 * Created by 10105-김유진 on 2016-09-01.
 */
@SuppressLint("ValidFragment")
public class AllInformationCheck extends Fragment {

    protected TextView textView;
    protected ListView listView;
    protected DBManager mDbManager;
    protected CursorAdapterEx cursorAdapterEx;
    protected Cursor c;
    LinearLayout linearLayout;

    Context mContext;

    public AllInformationCheck(Context context){
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_information_check_main, null);

//        Context context = getActivity();
        mDbManager = DBManager.getInstance(mContext);

        textView = (TextView)view.findViewById(R.id.all_information_textview);
        listView = (ListView)view.findViewById(R.id.all_information_listview);

        String[] Columns = new String[]{"_id","SensorOntime", "DoorOpenTime", "DoorbellRingPicture","DoorbellRingTime","UserCheck"};
        c = mDbManager.query("SensorInfo", Columns,null, null, null, null, null);

        if(c.getCount()!=0){
            setListview();
        }
        else{
            linearLayout = (LinearLayout)view.findViewById(R.id.all_linear_layout);
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
}
