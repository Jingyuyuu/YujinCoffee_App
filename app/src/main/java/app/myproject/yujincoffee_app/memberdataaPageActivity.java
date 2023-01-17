package app.myproject.yujincoffee_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;

import app.myproject.yujincoffee_app.databinding.ActivityMemberdataaPageBinding;
import app.myproject.yujincoffee_app.databinding.ActivityRegisttPageBinding;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class memberdataaPageActivity extends AppCompatActivity {
    ActivityMemberdataaPageBinding binding;
    ExecutorService executorService;

    Handler memberDataHandler =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle=msg.getData();
            if(bundle.getInt("status")==000){
                Toast.makeText(memberdataaPageActivity.this, "註冊成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(memberdataaPageActivity.this, indextPageActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(memberdataaPageActivity.this, bundle.getString("mesg"), Toast.LENGTH_LONG).show();
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMemberdataaPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.memberPointsTXT.setText("會員點數");
        binding.memberNameTXT.setText("會員姓名");
        binding.memberNameTXT.setText("會員信箱");
        binding.memberNameTXT.setText("會員電話");
        binding.memberReviseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //傳送修改資料給Server寫入資料庫

                binding.memberReviseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //請將使用者資料 封裝成JSON格式 回傳給SpringBoot Controller進行驗證
                        //下拉選單範例https://github.com/miscoder002/ReivewApphttps://github.com/miscoder002/ReivewApp
                        JSONObject packet=new JSONObject();
                        try {

                            JSONObject newMemberRegData=new JSONObject();
                            newMemberRegData.put("name",binding.memberNameTXT.getText().toString());
                            newMemberRegData.put("phone",binding.memberPhoneTXT.getText().toString());
                            newMemberRegData.put("email",binding.memberEmailTXT.getText().toString());
                            newMemberRegData.put("pwd",binding.memberPwdTXT.getText().toString());

                            packet.put("NewMemberData",newMemberRegData);
                            Log.e("JSON",packet.toString(4));
                            Toast.makeText(memberdataaPageActivity.this, "送出修改後的會員資料", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        MediaType mType=MediaType.parse("application/json");
                        RequestBody body=RequestBody.create(packet.toString(),mType);
                        //VM IP=20.187.101.131
                        Request request=new Request.Builder()
                                .url("http://192.168.255.123:8216/api/member/reNewMemberData")
                                .post(body)
                                .build();
                        memberdataaPageActivity.SimpleAPIWorker apiCaller=new memberdataaPageActivity.SimpleAPIWorker(request);
                        //產生Task準備給executor執行
                        executorService.execute(apiCaller);

                    }

                });
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
                String responseString=response.body().string();
                Log.e("API回應",responseString);
                //Response也應該是JASON格式回傳 由APP端確認登入結果

                JSONObject result=new JSONObject(responseString);
                Message m=memberDataHandler.obtainMessage();
                Bundle bundle=new Bundle();
                if(result.getInt("status")==000){
                    bundle.putString("mesg",result.getString("mesg"));
                    bundle.putInt("status",result.getInt("status"));
                }else{
                    bundle.putString("mesg","email已經存在");
                    bundle.putInt("status",result.getInt("status"));
                }
                m.setData(bundle);
                memberDataHandler.sendMessage(m);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //製作Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        //用id判斷點了哪個選項
        if(id == R.id.membersetting){
            Intent intent=new Intent(memberdataaPageActivity.this,memberdataaPageActivity.class);
            startActivity(intent);
            return true;
        }
        /*
        else if(id == R.id.myorder){
            Intent intent=new Intent(memberdataaPageActivity.this,我的訂單頁面.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.itemmenu){
            Intent intent=new Intent(memberdataaPageActivity.this,MenuList.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.historyorder){
            Intent intent=new Intent(memberdataaPageActivity.this,歷史訂單頁面.class);
            startActivity(intent);
            return true;
        }
        */
        else if(id == R.id.myfavorite){
            Intent intent=new Intent(memberdataaPageActivity.this,MyFavoriteActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.pointchange){
            Intent intent=new Intent(memberdataaPageActivity.this,PointChangeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}