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

    Handler loginResultHandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle=msg.getData();
            if(bundle.getInt("status")==666){
                Toast.makeText(logPageActivity.this, "歡迎回來", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(logPageActivity.this, indextPageActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(logPageActivity.this, bundle.getString("mesg"), Toast.LENGTH_LONG).show();
            }


        }
    };
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
                //VM IP=20.187.101.131
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

                /*ShoppingCart 練習

                ShoppingCart a= ShoppingCart.newInstance();
                Drink coffee = new Drink("奶茶","123","冰","0.5");
                a.drinkItems.add(coffee);
                Log.e("購物車",a.drinkItems.get(0).getName()
                +" "+a.drinkItems.get(0).getIce()+
                        " "+a.drinkItems.get(0).getSugar()
                +""+a.drinkItems.get(0).getPrice());
                 */
            }
        });

//wu@gmail.co
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
                String responseString=response.body().string();
                Log.e("API回應",responseString);
                //Response也應該是JASON格式回傳 由APP端確認登入結果

                JSONObject result=new JSONObject(responseString);
                Message m=loginResultHandler.obtainMessage();
                Bundle bundle=new Bundle();
                if(result.getInt("status")==666){
                    bundle.putString("mesg",result.getString("mesg"));
                    bundle.putInt("status",result.getInt("status"));
                }else{
                    bundle.putString("mesg","登入失敗 請確認帳號及密碼是否正確");
                    bundle.putInt("status",result.getInt("status"));
                }
                m.setData(bundle);
                loginResultHandler.sendMessage(m);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}