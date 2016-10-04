package project.householdgod;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.database.Cursor;

/**
 * Created by 10105-김유진 on 2016-09-01.
 */

@SuppressLint("ValidFragment")
public class NowState extends Fragment{

     Context mContext;
    protected ImageView imageView;
    protected TextView doorTextView;
    protected TextView bellTextView;

    protected DBManager mDBManager;

    public NowState(Context context){
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.now_state_main, null);

        imageView = (ImageView)view.findViewById(R.id.image_view);
        doorTextView = (TextView)view.findViewById(R.id.door_text_view);
        bellTextView = (TextView)view.findViewById(R.id.bell_text_view);

        doorTextView.setText("123456789");
        mDBManager = DBManager.getInstance(mContext);

//        if(){ //문이 열려있으면
//            //문이 열려있는 그림으로 세팅
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.open_door));
//        }
//        else{
        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.close_door));
//        }

        String[] Columns = new String[]{"_id","SensorOntime", "DoorOpenTime", "DoorbellRingPicture","DoorbellRingTime","UserCheck"};
        Cursor c = mDBManager.query("SensorInfo", Columns, "SensorOntime!=null",null,null,null,null);
        c.moveToLast();

//        Log.e("Time",c.getString(c.getColumnIndex("DoorOpenTime")));
//        Log.e("Time",c.getString(c.getColumnIndex("DoorbellRingTime")));
        try{
            doorTextView.setText(c.getString(c.getColumnIndex("DoorOpenTime")));
        }
        catch (Exception e){
            doorTextView.setText("데이터가 없습니다.");
        }
        c = mDBManager.query("SensorInfo",Columns,"DoorbellRingTime IS NOT NULL",null,null,null,null);
        c.moveToLast();

        try{
            doorTextView.setText(c.getString(c.getColumnIndex("DoorbellRingTime")));
        }
        catch (Exception e){
            bellTextView.setText("데이터가 없습니다.");
        }

        return view;
    }

}
