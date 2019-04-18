package kiky.beam.lilly.th.ac.rmutk.fruitqr;


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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProductFragment extends Fragment {

    private String idProduct;
    private String titleToolbar = "รายละเอียด";
    private ArrayList<String> productStringArrayList, farmerStringArrayList, userStringArrayList;
    private Myconstant myconstant = new Myconstant();


    public ShowProductFragment() {
        // Required empty public constructor
    }

    public static ShowProductFragment showProductInstance(String idProduce) {
        ShowProductFragment showProductFragment = new ShowProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString("idProduct", idProduce);
        showProductFragment.setArguments(bundle);
        return showProductFragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        idProduct = getArguments().getString("idProduct");
        Log.d("18AprilV2", "Receive idProduct ==> " + idProduct);

//        Create Toolbar
        createToolbar();

//        Load Data
        loadData();


    }   // Main Method

    private void loadData() {
        productStringArrayList = new ArrayList<>();
        farmerStringArrayList = new ArrayList<>();
        userStringArrayList = new ArrayList<>();

        String[] columnDetailProduct = myconstant.getColumnDetailProduct();

        try {

//            for Product
            GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(getActivity());
            getDataWhereOneColumn.execute("id", idProduct, myconstant.getUrlGetProductWhereId());
            String jsonProduct = getDataWhereOneColumn.get();
            Log.d("18AprilV3", "jsonProduct ==>>> " + jsonProduct);

            JSONArray jsonArray = new JSONArray(jsonProduct);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            for (int i = 0; i < columnDetailProduct.length; i += 1) {
                productStringArrayList.add(jsonObject.getString(columnDetailProduct[i]));
                Log.d("18AprilV3", "productStringArrayList[" + i + "] ==> " + productStringArrayList.get(i));
            }

//            for Former
            GetDataWhereOneColumn getDataWhereOneColumn1 = new GetDataWhereOneColumn(getActivity());
            getDataWhereOneColumn1.execute("id", productStringArrayList.get(4), myconstant.getUrlGetFarmerWhereId());
            String jsonFarmer = getDataWhereOneColumn1.get();
            Log.d("18AprilV4", "jsonFarmer ==>> " + jsonFarmer);
            String[] colunmdetailFarmer = myconstant.getColumnDetailFarmer();

            JSONArray jsonArray1 = new JSONArray(jsonFarmer);
            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
            for (int i = 0; i < colunmdetailFarmer.length; i += 1) {
                farmerStringArrayList.add(jsonObject1.getString(colunmdetailFarmer[i]));
                Log.d("18AprilV4", "farmerStringArrayList[" + i + "] ==> " + farmerStringArrayList.get(i));
            }

            GetDataWhereOneColumn getDataWhereOneColumn2 = new GetDataWhereOneColumn(getActivity());
            getDataWhereOneColumn2.execute("id", productStringArrayList.get(1), myconstant.getUrlGetUserWhereId());
            String jsonUser = getDataWhereOneColumn2.get();
            Log.d("18AprilV5", "jsonUser ==> " + jsonUser);

            JSONArray jsonArray2 = new JSONArray(jsonUser);
            JSONObject jsonObject2 = jsonArray2.getJSONObject(0);
            String[] columnUser = myconstant.getColumnUser();
            for (int i = 0; i < columnUser.length; i += 1) {
                userStringArrayList.add(jsonObject2.getString(columnUser[i]));
                Log.d("18AprilV5", "userStringArrayList[" + i + "] ==> " + userStringArrayList.get(i));
            }

//            Show View
//            Name Product
            TextView nameTextView = getView().findViewById(R.id.txtName);
            nameTextView.setText(productStringArrayList.get(5));

//            Image Product
            ImageView imageView = getView().findViewById(R.id.imvImage);
            Picasso.get().load(productStringArrayList.get(7)).resize(800, 600).into(imageView);


        } catch (Exception e) {
            Log.d("18AprilV3", "e ==>> " + e.toString());
        }


    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarProduct);
        ((ProductActivity) getActivity()).setSupportActionBar(toolbar);
        ((ProductActivity) getActivity()).getSupportActionBar().setTitle(titleToolbar);
        ((ProductActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((ProductActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_product, container, false);
    }

}
