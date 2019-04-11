package kiky.beam.lilly.th.ac.rmutk.fruitqr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailServiceActivity extends AppCompatActivity {

    private String nameString, dateString, amountString, unitString, imageString, idUserString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service);


        nameString = getIntent().getStringExtra("Name");
        dateString = getIntent().getStringExtra("Date");
        amountString = getIntent().getStringExtra("Amount");
        unitString = getIntent().getStringExtra("Unit");
        imageString = getIntent().getStringExtra("Image");
        idUserString = getIntent().getStringExtra("IdUser");


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentDetailServiceFragment, DetailServiceFragment.detailServiceInstance(nameString, dateString, amountString, unitString, imageString, idUserString))
                .commit();

    }
}
