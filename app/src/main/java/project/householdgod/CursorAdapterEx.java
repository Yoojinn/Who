package project.householdgod;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 10105-김유진 on 2016-06-24.
 */
public class CursorAdapterEx extends CursorAdapter{

    private Context     mContext = null;
    private LayoutInflater  mLayoutInflater = null;

    public CursorAdapterEx(Context context, Cursor c, int flags) {
        super(context, c,flags);

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    class ViewHolder{
        TextView time;
        TextView log;

        ImageView imageView;
        TextView textView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        //새로운 아이템 뷰 생성---------------------------------------------------

        View itemLayout = mLayoutInflater.inflate(R.layout.door_list,null);
        //------------------------------------------------------------------------
        //아이쳄에 뷰 홀더 설정---------------------------------------------------
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.time = (TextView) itemLayout.findViewById(R.id.textview_time);
        viewHolder.log = (TextView) itemLayout.findViewById(R.id.textview_log);

        viewHolder.imageView = (ImageView) itemLayout.findViewById(R.id.doorbell_man);
        viewHolder.textView = (TextView) itemLayout.findViewById(R.id.textview_time);

        itemLayout.setTag(viewHolder);
        //---------------------------------------------------------------------
        return itemLayout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //아이템뷰에 저장된 뷰 홀더를 얻어온다.
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        //--------------------------------------------------------------------
        //현재 커서 위치에 문열린 시각, 센서감지시각, 초인종누른사람, 초인종 울린 시각을 가져온다.
        try{
            String doorOpenTime = cursor.getString(cursor.getColumnIndex("DoorOpenTime"));
            String sensorOntime= cursor.getString(cursor.getColumnIndex("SensorOntime")); viewHolder.time.setText(doorOpenTime);
            viewHolder.time.setText(doorOpenTime);
            try {
                Date doorTime = dateFormat.parse(doorOpenTime);
                Date sensorTime = dateFormat.parse(sensorOntime);

                //센서 인식후, 문열림 : 누군가 나감
                if(doorTime.after(sensorTime)){
                    viewHolder.log.setText("누군가 나갔습니다.");
                }
                //문이 먼저 열리고 센서인식 : 누군가 들어옴
                else{
                    viewHolder.log.setText("누군가 들어왔습니다.");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){

            String doorbellRingPicture = cursor.getString(cursor.getColumnIndex("DoorbellRingPicture"));
            String doorbellRingTime = cursor.getString(cursor.getColumnIndex("DoorbellRingTime"));

            //이미지 불러오기---------------------------------------------------------------------------------
//Todo :            int resID = mContext.getResources().getIdentifier(doorbellRingPicture, "drawable", mContext.getPackageName());
            //---------------------------------------------------------------------------------
            //이미지는 파일네임 것-----------------------------------------------------
            //Todo : viewHolder.imageView.setImageResource(resID); // 이미지뷰의 이미지를 설정한다;
            viewHolder.textView.setText(doorbellRingPicture);

            //viewHolder.imageView.setImageDrawable("URL");-------------------------------------
            viewHolder.log.setText("초인종이 눌렸습니다.");
            viewHolder.time.setText(doorbellRingTime);
        }

        //-------------------------------------------------------------------
        //이름, 학번, 학과 데이터를 뷰에 적용



    }

}
