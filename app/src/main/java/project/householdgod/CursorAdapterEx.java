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

      //  viewHolder.imageView = (ImageView) itemLayout.findViewById(R.id.doorbell_man);
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
        //현재 커서 위치에 센서종류, 센서가 on된 시간을 가져온다.
        String doorOpenTime = cursor.getString(cursor.getColumnIndex("KindOfSensor"));
        String sensorOntime= cursor.getString(cursor.getColumnIndex("time"));

        if(doorOpenTime.equals("1")){ //전원 On
            viewHolder.log.setText("센서의 전원이 켜졌습니다.");
        }
        else if(doorOpenTime.equals("2")){ //전원 Off
            viewHolder.log.setText("센서의 전원이 꺼졌습니다.");
        }
        else if(doorOpenTime.equals("3")){ //문 열림
            viewHolder.log.setText("문이 열렸습니다.");
        }
        else if(doorOpenTime.equals("4")){ //문 닫힘
            viewHolder.log.setText("문이 닫혔습니다.");
        }
        else if(doorOpenTime.equals("5")){ //센서등 켜짐
            viewHolder.log.setText("센서등이 켜졌습니다.");
        }
        else if(doorOpenTime.equals("6")) {  //초인종, 초인종 사진
            viewHolder.log.setText("초인종이 눌렸습니다.");
            //ToDo : viewHolder.imageView();
        }
        viewHolder.time.setText(sensorOntime);

    }

}
