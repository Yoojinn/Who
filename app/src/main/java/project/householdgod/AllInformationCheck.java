package project.householdgod;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.database.Cursor;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 10105-김유진 on 2016-09-01.
 */
@SuppressLint("ValidFragment")
public class AllInformationCheck extends Fragment implements View.OnClickListener {

    protected TextView textView;
    protected ListView listView;
    protected DBManager mDbManager;
    protected CursorAdapterEx cursorAdapterEx;
    protected Cursor c;
    protected CheckBox allSelectCheckBox;
    protected Button deleteButton;
    protected Button selectClearButton;
    protected Spinner spinner;
    protected boolean[] isChecked;
    LinearLayout linearLayout;

    private String[] Columns;

    Context mContext;

    public AllInformationCheck(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_information_check_main, null);

        mDbManager = DBManager.getInstance(mContext);

        textView = (TextView) view.findViewById(R.id.all_information_textview);
        listView = (ListView) view.findViewById(R.id.all_information_listview);
        linearLayout = (LinearLayout) view.findViewById(R.id.all_linear_layout);

        allSelectCheckBox = (CheckBox) view.findViewById(R.id.all_check_box);
        deleteButton = (Button) view.findViewById(R.id.delete_button);
        selectClearButton = (Button) view.findViewById(R.id.check_clear_button);
        spinner = (Spinner) view.findViewById(R.id.spinner);

        Columns = new String[]{"_id", "UserCheck", "KindOfSensor", " time"};
        c = mDbManager.query("SensorInfo", Columns, null, null, null, null, null);

        isChecked = new boolean[c.getCount()];

        //spinner 세팅
        ArrayAdapter adapter = ArrayAdapter.createFromResource(mContext, R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        if (c.getCount() != 0) {
            setListview();
        } else {
            setNoNotice();
        }

        allSelectCheckBox.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        selectClearButton.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isChecked[position]=true;
                long _id = cursorAdapterEx.getItemId(position); // This is the _ID value
                //     Object vo = (Object) parent.getAdapter().getItem(position);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerItem = (String) spinner.getSelectedItem();
                String selection;
                if (spinnerItem.equals("전체")) {
                    c = mDbManager.query("SensorInfo", Columns, null, null, null, null, null);
                }
                else if (spinnerItem.equals("전원 ON/OFF")) {
                    c = mDbManager.query("SensorInfo", Columns, "KindOfSensor = 1 or KindOfsensor=2", null, null, null, null);
                } else if (spinnerItem.equals("문 열림/닫힘")) {
                    c = mDbManager.query("SensorInfo", Columns, "KindOfSensor = 3 or KindOfsensor=4", null, null, null, null);
                } else if (spinnerItem.equals("센서등 켜짐")) {
                    c = mDbManager.query("SensorInfo", Columns, "KindOfSensor = 5", null, null, null, null);
                } else if (spinnerItem.equals("초인종")) {
                    c = mDbManager.query("SensorInfo", Columns, "KindOfSensor = 6", null, null, null, null);
                }
                try{
                    cursorAdapterEx.changeCursor(c);
                }catch (Exception e)
                {

                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_button:
                /* 삭제 */
//                boolean[] checkBoxState = new boolean[c.getCount()];
//                checkBoxState = cursorAdapterEx.isCheckBoxState();
//
//                if (allSelectCheckBox.isChecked()) {
//                    mDbManager.delete(DBManager.TABLE, null, null);
//                }
//                else
//                {
//                    for (int i = 0; i < c.getCount(); i++) {
//                        if(checkBoxState[i]) {
//                            mDbManager.delete(DBManager.TABLE, "_id" + "=" + String.valueOf(i), null);
//                        }
//                    }
//                }
//                long [] num = listView.getCheckedItemIds(); //체크된 아이템 가져오기
                SparseBooleanArray checkedItemPositions = this.listView.getCheckedItemPositions();
                Log.e("checkbox ID",String.valueOf(checkedItemPositions.size()));
                for(int i=0 ; i<checkedItemPositions.size() ; i++)
                {
                    if(checkedItemPositions.get(i))
                    {
                        mDbManager.delete(DBManager.TABLE, "_id" + "=" + cursorAdapterEx.getItemId(i), null);
                        Log.e("checkbox ID",String.valueOf(cursorAdapterEx.getItemId(i)));
                    }

                }

                Toast.makeText(mContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                c = mDbManager.query("SensorInfo", Columns,null, null, null, null, null);
                cursorAdapterEx.changeCursor(c);
                break;

//            case R.id.check_clear_button:
//                /*선택 해제*/
//                try {
//                    cursorAdapterEx.allCheckBoxSetting(false);
//                    allSelectCheckBox.setChecked(false);
//                } catch (Exception e) {
//                    Log.e("CheckClearButtonError",String.valueOf(e));
//
//                }
//                break;
//
//            case R.id.all_check_box:
//                //전체 선택 클릭
//                cursorAdapterEx.allCheckBoxSetting(allSelectCheckBox.isChecked());
//                break;
        }
    }

}
