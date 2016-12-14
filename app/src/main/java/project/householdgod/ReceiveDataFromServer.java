package project.householdgod;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
    private static final String TAG_PICTURE = "image";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;
    Context mContext;
    String SAVE_FOLDER = "who";

    ReceiveDataFromServer(Context c){
        mContext = c;
    }

    /* 서버에서 새로운 데이터를 가져와 DB에 넣음 */
    public void putDB() {
        if(myJSON == null){ //myJSON이 null이면 데이터를 못가져왔다는 뜻.
            return;
        }
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            //SPreferences에서 전에 저장했던 줄 번호를 꺼내옴
            SPreferences sPreferences = new SPreferences(mContext);
            int n =sPreferences.getValue("readedRow",0);
            for (int i = 0; i < peoples.length(); i++) {

                //전에 저장했던 이후부터 DB에 저장
                if(i >= n) {

                    JSONObject c = peoples.getJSONObject(i);
                    String time = c.getString(TAG_TIME);
                    String kindOfSensor = c.getString(TAG_KIND_OF_SENSOR);
                    String picture = c.getString(TAG_PICTURE);

                    //DB에 넣을 데이터를 HashMap으로 저장
                    HashMap<String, String> persons = new HashMap<String, String>();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("KindOfSensor", kindOfSensor);
                    contentValues.put("time", time);

                    // KindOfSensor == 6, 초인종이 눌렸다는 뜻, 받아올 데이터중 사진도 있음
                    if(kindOfSensor.equals("6") && !TextUtils.isEmpty(picture))
                    {
                        Bitmap bitmap = getBitmapFromString(picture);
                        String filepath = Environment.getExternalStorageDirectory().toString() +"/"+ SAVE_FOLDER;
                        String filename = filepath + i+".jpg";

                        saveBitmapToFileCache(bitmap,filepath,filename);

//                //        String filepath = "/sdcard/Images/"+i+".jpg";
//                        File file = new File(filepath);
//                        if (!file.exists()) {
//
//                            boolean check = file.mkdir();
//                            Log.e("file make",check+"");
//                        }
//
//                        try {
//                            final FileOutputStream filestream = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 0, filestream);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }

                        contentValues.put("picture",filepath); //경로 저장
                    }

                    DBManager dbManager = DBManager.getInstance(mContext);
                    dbManager.insert(contentValues);
                }
            }
            sPreferences.put("readedRow",peoples.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void saveBitmapToFileCache(Bitmap bitmap, String strFilePath, String filename) {

        File file = new File(strFilePath); //상위 폴더

        if (!file.exists())
            file.mkdirs();

        File fileCacheItem = new File(strFilePath +"/"+ filename);
        OutputStream out = null;

        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /* 데이터를 파싱*/
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
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    //스트링 -> 비트맵 형식으로 변환
    private Bitmap getBitmapFromString(String jsonString) {
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
