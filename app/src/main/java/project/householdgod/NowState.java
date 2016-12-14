package project.householdgod;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.VideoView;

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
    private static final String DOORBELL_URL = "http://10.42.0.24:8080/stream.html";

    public NowState(Context context){
        mContext = context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.now_state_main, null);

        doorTextView = (TextView)view.findViewById(R.id.door_text_view);
        bellTextView = (TextView)view.findViewById(R.id.bell_text_view);

        VideoView videoView = (VideoView)view.findViewById(R.id.VideoView);
        MediaController mediaController = new MediaController(mContext);
        mediaController.setAnchorView(videoView);
        // Set video link (mp4 format )
        Uri video = Uri.parse(DOORBELL_URL);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);
        videoView.requestFocus();
        videoView.start();


        doorTextView.setText("123456789"); //이거 지우면 왜 안될까?
        mDBManager = DBManager.getInstance(mContext);



//        if(){ //문이 열려있으면
//            //문이 열려있는 그림으로 세팅
//        imageView.setImageDrawable(getResources().getDrawable(R.drawable.open_door));
//        }
//        else{
        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.close_door));
//        }

        String[] Columns = new String[]{"UserCheck", "KindOfSensor"," time"};

//        Log.e("Time",c.getString(c.getColumnIndex("DoorOpenTime")));
//        Log.e("Time",c.getString(c.getColumnIndex("DoorbellRingTime")));
        try{
            Cursor c = mDBManager.query(DBManager.TABLE, Columns, "KindOfSensor==3 or KindOfSensor==4",null,null,null,null);
            c.moveToLast();

            doorTextView.setText(c.getString(c.getColumnIndex("time")));
        }
        catch (Exception e){
            doorTextView.setText("데이터가 없습니다.");
        }


        try{
            Cursor c = mDBManager.query("SensorInfo",Columns,"KindofSensor==6",null,null,null,null);
            c.moveToLast();
            doorTextView.setText(c.getString(c.getColumnIndex("time")));
        }
        catch (Exception e){
            bellTextView.setText("데이터가 없습니다.");
        }

        //ToDo : 알림 On, Off 나타냄

        return view;
    }

}
