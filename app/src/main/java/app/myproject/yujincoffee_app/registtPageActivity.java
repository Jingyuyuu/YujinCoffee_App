package app.myproject.yujincoffee_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.myproject.yujincoffee_app.databinding.ActivityRegisttPageBinding;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class registtPageActivity extends AppCompatActivity {
    ActivityRegisttPageBinding binding;
    ExecutorService executorService;

    Handler registerResultHandler =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisttPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService= Executors.newSingleThreadExecutor();

        binding.backLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.regSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //請將使用者資料 封裝成JSON格式 回傳給SpringBoot Controller進行驗證
                //下拉選單範例https://github.com/miscoder002/ReivewApphttps://github.com/miscoder002/ReivewApp
                JSONObject packet=new JSONObject();
                try {

                    JSONObject memberRegData=new JSONObject();
                    memberRegData.put("name",binding.regName.getText().toString());
                    memberRegData.put("pwd",binding.regPwd01.getText().toString());
                    memberRegData.put("email",binding.regEmail.getText().toString());
                    memberRegData.put("phone",binding.regPhone.getText().toString());

                    packet.put("regData",memberRegData);
                    Log.e("JSON",packet.toString(4));
                    Toast.makeText(registtPageActivity.this, "送出註冊資訊", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MediaType mType=MediaType.parse("application/json");
                RequestBody body=RequestBody.create(packet.toString(),mType);
                //VM IP=20.187.101.131
                Request request=new Request.Builder()
                        .url("http://192.168.255.123:8216/api/member/register")
                        .post(body)
                        .build();
                registtPageActivity.SimpleAPIWorker apiCaller=new registtPageActivity.SimpleAPIWorker(request);
                //產生Task準備給executor執行
                executorService.execute(apiCaller);

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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}