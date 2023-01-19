package app.myproject.yujincoffee_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import app.myproject.yujincoffee_app.Modle.Util.SimpleeAPIWorker;
import app.myproject.yujincoffee_app.databinding.ActivityMemberDataChangeBinding;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MemberDataChangeActivity extends AppCompatActivity {
    ActivityMemberDataChangeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMemberDataChangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*
        //傳送修改後的資料給Server寫入資料庫
        binding.memberReviseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //密碼確認正確才會送出修改資料的請求給Server
                String pwd=binding.memberPWDTV.getText().toString();
                String pwd2=binding.memberPWDTV2.getText().toString();
                if(pwd != null && pwd2 !=null && pwd.equals(pwd2)){
                    //請將使用者資料 封裝成JSON格式 回傳給SpringBoot Controller進行驗證
                    JSONObject packet=new JSONObject();
                    try {
                        JSONObject newMemberRegData=new JSONObject();
                        newMemberRegData.put("name",binding.memberNameT.getText().toString());
                        newMemberRegData.put("pwd",binding.memberPWDTV.getText().toString());
                        newMemberRegData.put("phone",binding.memberPhoneT.getText().toString());
                        newMemberRegData.put("email",binding.memberEmailTX.getText().toString());
                        packet.put("NewMemberData",newMemberRegData);
                        Log.e("JSON",packet.toString(4));
                        Toast.makeText(memberdataaPageActivity.this, "已送出修改的會員資料", Toast.LENGTH_SHORT).show();

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
                    SimpleeAPIWorker apiCaller=new SimpleeAPIWorker(request,memberDataHandler);
                    //產生Task準備給executor執行
                    executorService.execute(apiCaller);
                }else {
                    Toast.makeText(memberdataaPageActivity.this, "密碼確認錯誤，請輸入相同密碼", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Spinner
        String [] items =  { "現金付款", "信用卡" , "LINE PAY" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                items);
        binding.presetStoreSpinner.setAdapter(adapter);
        binding.presetStoreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(memberdataaPageActivity.this, "選取項目" + items[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         */
    }
}