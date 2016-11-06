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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.database.Cursor;
import android.widget.TextView;

/**
 * Created by 10105-김유진 on 2016-09-01.
 */
@SuppressLint("ValidFragment")
public class AllInformationCheck extends Fragment implements View.OnClickListener{

    protected TextView textView;
    protected ListView listView;
    protected DBManager mDbManager;
    protected CursorAdapterEx cursorAdapterEx;
    protected Cursor c;
    protected CheckBox allSelectCheckBox;
    protected Button deleteButton;
    protected Button selectClearButton;
    LinearLayout linearLayout;

    private String[] delItemArr; //삭제될 리스트 선택 배열
    private CheckBox mAllCheckBox; //전체 삭제 체크박스

    Context mContext;

    public AllInformationCheck(Context context){
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_information_check_main, null);

        mDbManager = DBManager.getInstance(mContext);

        textView = (TextView)view.findViewById(R.id.all_information_textview);
        listView = (ListView)view.findViewById(R.id.all_information_listview);
        linearLayout = (LinearLayout)view.findViewById(R.id.all_linear_layout);

        allSelectCheckBox = (CheckBox)view.findViewById(R.id.all_check_box);
        deleteButton = (Button) view.findViewById(R.id.delete_button);
        selectClearButton = (Button) view.findViewById(R.id.check_clear_button);

        delItemArr = new String[10000];

        String[] Columns = new String[]{"_id","UserCheck", "KindOfSensor"," time"};
        c = mDbManager.query("SensorInfo", Columns,null, null, null, null, null);

        if(c.getCount()!=0){
            setListview();
        }
        else{
           setNoNotice();
        }

        allSelectCheckBox.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        selectClearButton.setOnClickListener(this);

        return view;
    }

    //리스트뷰 ------------------------------------------------------
    public void setListview(){
        cursorAdapterEx = new CursorAdapterEx(mContext,c,0);
        listView.setAdapter(cursorAdapterEx);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); //checkBox와 연동하기 위함
    }
    //---------------------------------------------------------------------

    public void setNoNotice(){
        linearLayout.setGravity(Gravity.CENTER);
        textView.setText("알림 내역이 없습니다.");
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.delete_button :
                for(int i=0 ; i<c.getCount() ; i++)
                {
                    mDbManager.delete(DBManager.TABLE, "_id"+"="+delItemArr[i],null );
                }
                break;

            case R.id.check_clear_button :
                break;

            case R.id.all_check_box :

                cursorAdapterEx.allCheckBoxSetting(allSelectCheckBox.isChecked());
                //전체 선택시 배열에 저장
                if(allSelectCheckBox.isChecked())
                {
                    c.moveToFirst();
                    for(int i=0; i<c.getCount();i++)
                    {
                        delItemArr[i] = c.getString(0);
                        if(c.getCount()!=i)
                        {
                            c.moveToNext();
                        }
                    }
                }else
                {
                    for(int i=0; i<c.getCount(); i++)
                    {
                        delItemArr[i] = null;
                    }
                }
                break;
        }
    }


    android.widget.AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            allSelectCheckBox.setChecked(!allSelectCheckBox.isChecked());
            c.moveToPosition(position);

            if(allSelectCheckBox.isChecked()){
                delItemArr[position] = c.getString(0); //id값 저장
                cursorAdapterEx.setCheckBoxState(position, true);
                //전체가 다 체크되었는지 체크
                checkBoxAllCheckState();
            }else
            {
                delItemArr[position] = null;
                mAllCheckBox.setChecked(false); //전체 체크박스 해제
                cursorAdapterEx.setCheckBoxState(position, false);
            }
        }
    };

    //전체가 다 체크되었는지 체크
    private void checkBoxAllCheckState()
    {
        int totalCnt = 0;
        for(int i=0; i<delItemArr.length;i++)
        {
            if(delItemArr[i] != null)
            {
                totalCnt++;
            }
        }
        if(totalCnt == delItemArr.length)
        {
            mAllCheckBox.setChecked(true);
        }
    }


}
