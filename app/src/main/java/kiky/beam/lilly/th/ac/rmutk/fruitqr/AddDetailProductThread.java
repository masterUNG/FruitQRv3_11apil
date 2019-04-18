package kiky.beam.lilly.th.ac.rmutk.fruitqr;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class AddDetailProductThread extends AsyncTask<String, Void, String> {

    private Context context;

    public AddDetailProductThread(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {


        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd","true")
                    .add("idRecord",strings[0])
                    .add("NameRecord",strings[1])
                    .add("TypeRecord",strings[2])
                    .add("idFarmer",strings[3])
                    .add("Name",strings[4])
                    .add("Detail",strings[5])
                    .add("Image",strings[6])
                    .add("Amount",strings[7])
                    .add("Unit",strings[8])
                    .add("Date",strings[9])
                    .add("QRcode",strings[10])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[11]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return  response.body().string();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }




    }
}