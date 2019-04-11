package kiky.beam.lilly.th.ac.rmutk.fruitqr;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class AddDetailFramerThread extends AsyncTask<String, Void, String> {

    private Context context;

    public AddDetailFramerThread(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd","true")
                    .add("idRecord", strings[0])
                    .add("Name", strings[1])
                    .add("Amount", strings[2])
                    .add("Unit", strings[3])
                    .add("Date", strings[4])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[5]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return  response.body().string();


        } catch (Exception e) {
            e.printStackTrace();
            return null; //ถ้าทำงานไม่สำเร็จจะรีเทิน null
        }

    }
}
