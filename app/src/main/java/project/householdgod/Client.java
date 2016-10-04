//package project.householdgod;
//
//import android.widget.Toast;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//
///**
// * Created by 10105-김유진 on 2016-09-07.
// */
//public class Client {
//
////    /** Called when the activity is first created. */
////    @Override
////    public void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.main);
////        Button btnDownload = (Button)findViewById(R.id.btnButton);
////        btnDownload.setOnClickListener(myButtonClick);
////    }
////    Button.OnClickListener myButtonClick = new Button.OnClickListener()
////    {
////        public void onClick(View v)
////        {
////            Bitmap imgBitmap = GetImageFromURL("http://cfile25.uf.tistory.com/image/112CA2274C2220D2B47CB1");
////            if (imgBitmap != null)
////            {
////                ImageView imgView = (ImageView)findViewById(R.id.ImageView);
////                imgView.setImageBitmap(imgBitmap);
////            }
////        }
////    };
//
//    public void HttpPostData() {
//        try {
//            //--------------------------
//            //   URL 설정하고 접속하기
//            //--------------------------
//            URL url = new URL("http://korea-com.org/foxmann/lesson01.php");       // URL 설정
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
//            //--------------------------
//            //   전송 모드 설정 - 기본적인 설정이다
//            //--------------------------
//            http.setDefaultUseCaches(false);
//            http.setDoInput(true);                         // 서버에서 읽기 모드 지정
//            http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
//            http.setRequestMethod("POST");         // 전송 방식은 POST
//
//            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
//            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
//            //--------------------------
//            //   서버에서 전송받기
//            //--------------------------
//            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
//            BufferedReader reader = new BufferedReader(tmp);
//            StringBuilder builder = new StringBuilder();
//            String str;
//            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
//                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
//            }
//            myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
//        } catch (MalformedURLException e) {
//            //
//        } catch (IOException e) {
//            //
//        } // try
//        catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    } // HttpPostData
//
//}
