package kiky.beam.lilly.th.ac.rmutk.fruitqr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QRActivity extends AppCompatActivity {

    private boolean loginABoolean;  //true ==> Customer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        loginABoolean = getIntent().getBooleanExtra("Login", true);


        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentQRfragment, QRFragment.qrInstance(loginABoolean))
                    .commit();

        }


    }
}
