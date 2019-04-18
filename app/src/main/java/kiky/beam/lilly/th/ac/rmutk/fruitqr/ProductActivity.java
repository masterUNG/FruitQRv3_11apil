package kiky.beam.lilly.th.ac.rmutk.fruitqr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ProductActivity extends AppCompatActivity {

    private String idProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

//        Get Value From Intent
        idProduct = getIntent().getStringExtra("idProduct");
        Log.d("18AprilV2", "idProduct ==> " + idProduct);


    }
}
