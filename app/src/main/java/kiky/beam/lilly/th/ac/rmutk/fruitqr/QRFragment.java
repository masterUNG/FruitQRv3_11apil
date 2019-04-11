package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QRFragment extends Fragment implements ZXingScannerView.ResultHandler{

    private ZXingScannerView zXingScannerView;
    private  String resultQRcode;
    private boolean loginABoolean;


    public QRFragment() {
        // Required empty public constructor
    }

    public static QRFragment qrInstance(boolean b) {
        QRFragment qrFragment = new QRFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("Login", b);
        qrFragment.setArguments(bundle);
        return qrFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

    }

    @Override
    public void onPause() {
        super.onPause();
        zXingScannerView.stopCamera(); // ปิดหน้ากล้อง
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        zXingScannerView = new ZXingScannerView(getActivity());
        return zXingScannerView;

    }

    @Override
    public void handleResult(Result result) {

        resultQRcode = result.toString().trim();
        Log.d("6janV2","result ==> "+ resultQRcode);

        loginABoolean = getArguments().getBoolean("Login");

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentQRfragment,DetailFragment.detailFragment(resultQRcode, loginABoolean)).commit();

        Handler handler = new Handler();  //Handler ระบบน่วงเวลา
        handler.postDelayed(new Runnable() {//postDelayed ทำให้ดีเล ช้าลง 2000=0วิ
            @Override
            public void run() {
                zXingScannerView.resumeCameraPreview(QRFragment.this);
            }
        }, 2000);

    }
}
