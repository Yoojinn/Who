package project.householdgod;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 10105-김유진 on 2016-06-24.
 */
public class CursorAdapterEx extends CursorAdapter implements Checkable{

    private static boolean[] mCheckBoxState; //현재 체크박스 상태체크, 실제 생성된 뷰와 숫자의 차이가 있음
    private ArrayList<CheckBox> mCheckBox; //newView로 생성된 체크박스 저장
    private boolean mAllCheck; //전체체크 여부
    private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();
    private long checkBoxId[] ;
    ViewHolder viewHolder;

    private Context     mContext = null;
    private LayoutInflater  mLayoutInflater = null;

    public CursorAdapterEx(Context context, Cursor c, int flags) {
        super(context, c,flags);
        mCheckBoxState = new boolean[c.getCount()];  //초기화시켜줌
        checkBoxId = new long[c.getCount()];
        mCheckBox = new ArrayList<CheckBox>();     //초기화시켜줌
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public void setChecked(boolean checked) {
        if (viewHolder.checkBox.isChecked() != checked) {
            viewHolder.checkBox.setChecked(checked) ;
        }
    }

    @Override
    public boolean isChecked() {
        return viewHolder.checkBox.isChecked() ;
    }

    @Override
    public void toggle() {
        setChecked(viewHolder.checkBox.isChecked() ? false : true) ;
    }

    class ViewHolder{
        CheckBox checkBox;

        TextView time;
        TextView log;

        ImageView imageView; //초인종 사진
        TextView textView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        //새로운 아이템 뷰 생성---------------------------------------------------
        View itemLayout = mLayoutInflater.inflate(R.layout.door_list,null);
        //------------------------------------------------------------------------

        //아이쳄에 뷰 홀더 설정---------------------------------------------------
        viewHolder = new ViewHolder();

        //newView로 생성된 체크박스를 배열에 저장. 전체체크를 위해
        mCheckBox.add((CheckBox)itemLayout.findViewById(R.id.check_box));
        //viewHolder.checkBox = (CheckBox)itemLayout.findViewById(R.id.check_box);
        viewHolder.checkBox = (CheckBox)itemLayout.findViewById(R.id.check_box) ;
        viewHolder.time = (TextView) itemLayout.findViewById(R.id.textview_time);
        viewHolder.log = (TextView) itemLayout.findViewById(R.id.textview_log);
        viewHolder.imageView = (ImageView) itemLayout.findViewById(R.id.imageView);

      //  viewHolder.imageView = (ImageView) itemLayout.findViewById(R.id.doorbell_man);
        viewHolder.textView = (TextView) itemLayout.findViewById(R.id.textview_time);

        itemLayout.setTag(viewHolder);
        //---------------------------------------------------------------------

        //

        return itemLayout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final int position = cursor.getPosition();
        //아이템뷰에 저장된 뷰 홀더를 얻어온다.
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        // radioButton 조작-------------------------------------------------
//        if(mAllCheck)
//        {
//            viewHolder.checkBox.setChecked(mAllCheck); //allCheck에 따라 체크/해제
//        }
//        else
//        {
//            viewHolder.checkBox.setChecked(mCheckBoxState[]);  //체크여부 저장 배열에 따라 체크셋팅
//        }
        //-------------------------------------------------------------------

       // SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
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
            String image;
            try{
                image = cursor.getString(cursor.getColumnIndex("picture"));
                Bitmap bitmap = BitmapFactory.decodeFile(image);
                viewHolder.imageView.setImageBitmap(bitmap);
            }catch (Exception e)
            {
                Log.e("image","image is null");
            }

        }
        viewHolder.time.setText(sensorOntime);

        //-----------------------------------------------------------

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckBoxState[position] = isChecked;

            }
        });
//        viewHolder.checkBox.setChecked(mCheckBoxState[position]);

    }

//    //체크박스 체크 여부 배열에 저장, 이 함수는 listView가 있는 액티비티에서 onItemClick메소드가 실행될때 체크상태를 저장하기 위한 함수
//    public void setCheckBoxState(int id, boolean state)
//    {
//        mCheckBoxState[id] = state;
//    }
//
//    //전체 체크박스 눌렀을 경우
//    public void allCheckBoxSetting(boolean check)
//    {
//        mAllCheck = check;
//        for(int i=0; i<mCheckBoxState.length; i++)
//        {
//            mCheckBoxState[i] = check;
//            mCheckBox.get(i).setChecked(check);
//        }
//    }
//
//    public void onItemlick(int position) //아이템 클릭하면 클릭된 아이템 _id저장
//    {
//        mCheckBoxState[position] = !mCheckBoxState[position];
//    }
//
//    public boolean[] isCheckBoxState(){
//        return mCheckBoxState;
//    }

}
