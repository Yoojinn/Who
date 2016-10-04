package project.householdgod;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by 10105-김유진 on 2016-09-01.
 */
public class SettingActivity extends AppCompatActivity{
    CheckBox bellCheckBox;
    CheckBox doorCheckBox;
    RadioButton alarmOn;
    RadioButton alarmOff;
    LinearLayout linearLayout;
    LinearLayout doorTiemLinearLayout;
    EditText editText;

    SPreferences sPreferences;

    //ㅁ초 이상 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_set);

        sPreferences = new SPreferences(this);

        alarmOn = (RadioButton)findViewById(R.id.alarm_on);
        alarmOff = (RadioButton)findViewById(R.id.alarm_off);
        bellCheckBox = (CheckBox)findViewById(R.id.bell_notice);
        doorCheckBox = (CheckBox)findViewById(R.id.door_notice);
        editText = (EditText)findViewById(R.id.door_edittext);
        linearLayout = (LinearLayout)findViewById(R.id.set_linear_layout);
        doorTiemLinearLayout = (LinearLayout) findViewById(R.id.door_time_linear_layout);

        alarmOn.setChecked(sPreferences.getValue("Alarm",true));
        alarmOff.setChecked(!sPreferences.getValue("Alarm",true));

        if(doorCheckBox.isChecked()){
            editText.setText(String.valueOf(sPreferences.getValue("door",5)));
        }
        else{
            doorTiemLinearLayout.setVisibility(View.GONE);
        }

        alarmOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(alarmOn.isChecked()){
                    linearLayout.setVisibility(View.VISIBLE);
                    bellCheckBox.setChecked(sPreferences.getValue("BellAlarm",true));
                    doorCheckBox.setChecked(sPreferences.getValue("DoorAlarm",true));
                }
                else{
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });

        doorCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (doorCheckBox.isChecked()) {
                    doorTiemLinearLayout.setVisibility(View.VISIBLE);
                    editText.setText(String.valueOf(sPreferences.getValue("door",5)));
                } else {
                    doorTiemLinearLayout.setVisibility(View.GONE);
                }
            }
        });

    }

//    @Override
//    protected void onPause(){ //기본값
//        sPreferences.put("Alarm", true);
//        sPreferences.put("BellAlarm", true);
//        sPreferences.put("DoorAlarm", true);
//        sPreferences.put("door",5);
//    }

    protected void ChangeDataValue(){
        sPreferences.put("Alarm", alarmOn.isChecked());
        sPreferences.put("BellAlarm", bellCheckBox.isChecked());
        sPreferences.put("DoorAlarm", doorCheckBox.isChecked());
        Log.e("door",editText.getText().toString());
        if(!editText.getText().toString().equals("")){
            sPreferences.put("door",Integer.parseInt(editText.getText().toString()));
        }
    }

    void ok_button(View view){
        sPreferences.put("door",Integer.parseInt(editText.getText().toString()));
        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ChangeDataValue();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
