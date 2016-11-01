package project.householdgod;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 10105-김유진 on 2016-10-11.
 */
public class ReceiveDataFromServer{
    String myJSON;

    private static final String TAG_RESULTS="result";
    private static final String TAG_TIME = "time";
    private static final String TAG_KIND_OF_SENSOR = "kindOfSensor";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;
    Context mContext;

    ReceiveDataFromServer(Context c){
        mContext = c;
    }

    public void putDB() {
        if(myJSON == null){ //myJSON이 null이면 데이터를 못가져왔다는 뜻.
            return;
        }
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            SPreferences sPreferences = new SPreferences(mContext);
            int n =sPreferences.getValue("readedRow",0);
            for (int i = 0; i < peoples.length(); i++) {
                if(i >= n) {
                    JSONObject c = peoples.getJSONObject(i);
                    String time = c.getString(TAG_TIME);
                    String kindOfSensor = c.getString(TAG_KIND_OF_SENSOR);

                    HashMap<String, String> persons = new HashMap<String, String>();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("KindOfSensor", kindOfSensor);
                    contentValues.put("time", time);

                    DBManager dbManager = DBManager.getInstance(mContext);
                    dbManager.insert(contentValues);
                }
            }
            sPreferences.put("readedRow",peoples.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

//    protected void showList(){
//        try {
//                    JSONObject jsonObj = new JSONObject(myJSON);
//                    peoples = jsonObj.getJSONArray(TAG_RESULTS);
//
//                    for(int i=0;i<peoples.length();i++){
//                        JSONObject c = peoples.getJSONObject(i);
//                        String time = c.getString(TAG_TIME);
//                        String kindOfSensor = c.getString(TAG_KIND_OF_SENSOR);
//
//                        HashMap<String,String> persons = new HashMap<String,String>();
//
//                persons.put(TAG_TIME,time);
//                persons.put(TAG_KIND_OF_SENSOR,kindOfSensor);
//
//                personList.add(persons);
//            }
//
//            ListAdapter adapter = new SimpleAdapter(
//                    mContext, personList, R.layout.door_list,
//                    new String[]{TAG_TIME,TAG_KIND_OF_SENSOR},
//                    new int[]{R.id.textview_time, R.id.textview_log}
//            );
//
//            list.setAdapter(adapter);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                putDB();
                //showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
