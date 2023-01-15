package app.myproject.yujincoffee_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.myproject.yujincoffee_app.databinding.ActivityLogPageBinding;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class logPageActivity extends AppCompatActivity {
    ActivityLogPageBinding binding;
    ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLogPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService= Executors.newSingleThreadExecutor();

        binding.logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //請將使用者資料 封裝成JSON格式 回傳給SpringBoot Controller進行驗證
                JSONObject packet=new JSONObject();
                try {

                    JSONObject memberLogData=new JSONObject();
                    memberLogData.put("acc",binding.loginAccTV.getText().toString());
                    memberLogData.put("pwd",binding.loginPwdTV.getText().toString());
                    packet.put("logData",memberLogData);

                    Log.e("JSON",packet.toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MediaType mType=MediaType.parse("application/json");
                RequestBody body=RequestBody.create(packet.toString(),mType);

                Request request=new Request.Builder()
                        .url("http://192.168.43.21:8216/api/member/login")
                        .post(body)
                        .build();
                SimpleAPIWorker apiCaller=new SimpleAPIWorker(request);
                //產生Task準備給executor執行
                executorService.execute(apiCaller);

            }
        });

        binding.RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(logPageActivity.this, registtPageActivity.class);
                Bundle datas = new Bundle();
                startActivity(intent);
            }
        });


    }

    class SimpleAPIWorker implements Runnable{
        OkHttpClient client;
        Request request;

        public SimpleAPIWorker(Request request){
            client=new OkHttpClient();
            this.request=request;
        }
        @Override
        public void run() {
            try {
                Response response=client.newCall(request).execute();
                //Response也應該是JASON格式回傳 由APP端確認登入結果
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}