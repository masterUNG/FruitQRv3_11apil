package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private  String qrCode, nameString, imageString, amountString, unitString,
            dateString, idShopString, nameShopString, addressString, phoneString;
    private boolean loginABoolean;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment detailFragment(String resuliQR, boolean b) {

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("QRcode", resuliQR);
        bundle.putBoolean("Login", b);
        detailFragment.setArguments(bundle);
        return detailFragment;



    }

    //รับค่า QR เพื่อจะเอาไป where
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginABoolean = getArguments().getBoolean("Login");
        Log.d("3FebV2", "LoginBool at Detail ==> " + loginABoolean);

//        Create Toolbar
        createToolbar();

        showView();


    } //Main Method

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarDetail);
        ((QRActivity)getActivity()).setSupportActionBar(toolbar);
        ((QRActivity) getActivity()).getSupportActionBar().setTitle("Detail");
        ((QRActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((QRActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginABoolean) {
                    Intent intent = getActivity().getIntent();
                    getActivity().finish();
                    startActivity(intent);
                } else {
                    getActivity().finish();
                }

            }
        });


    }

    private void showView() {
        qrCode = getArguments().getString("QRcode");
        if (!qrCode.isEmpty()) {

//            Have QR codeValue
            try {

                Myconstant myconstant = new Myconstant();
                GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(getActivity());
                getDataWhereOneColumn.execute("QR", qrCode, myconstant.getUrlGetDataWhereQR());

                String json = getDataWhereOneColumn.get();
                Log.d("2FebV1", "json ==> " + json);

                JSONArray jsonArray = new JSONArray(json);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                nameString = jsonObject.getString("Name");
                imageString = jsonObject.getString("Image");
                idShopString = jsonObject.getString("idUser");
                amountString = jsonObject.getString("Amount");
                unitString = jsonObject.getString("Unit");
                dateString = jsonObject.getString("Date");

                TextView nameTextView = getView().findViewById(R.id.txtName);
                nameTextView.setText(nameString);

                ImageView imageView = getView().findViewById(R.id.imvDetail);
                Picasso.get().load(imageString).into(imageView);

                TextView amountTextView = getView().findViewById(R.id.txtAmount);
                amountTextView.setText(amountString);

                TextView unitTextView = getView().findViewById(R.id.txtUnit);
                unitTextView.setText(unitString);

                TextView dateTextView = getView().findViewById(R.id.txtDate);
                dateTextView.setText(dateString);

                GetDataWhereOneColumn getDataWhereOneColumn1 = new GetDataWhereOneColumn(getActivity());
                getDataWhereOneColumn1.execute("id", idShopString, myconstant.getUrlGetUserWhereId());
                String json2 = getDataWhereOneColumn1.get();
                Log.d("2FebV2", "json2 ==> " + json2);

                JSONArray jsonArray1 = new JSONArray(json2);
                JSONObject jsonObject1 = jsonArray1.getJSONObject(0);

                TextView nameShopTextView = getView().findViewById(R.id.txtShop);
                nameShopTextView.setText(jsonObject1.getString("Name"));

                TextView addressTextView = getView().findViewById(R.id.txtAddress);
                addressTextView.setText(jsonObject1.getString("Address"));

                TextView phoneTextView = getView().findViewById(R.id.txtPhone);
                phoneTextView.setText(jsonObject1.getString("Phone"));

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

}