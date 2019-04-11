package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowListFragment extends Fragment {


    private int typeUserAnInt;
    private String idLoginString;


    public ShowListFragment() {
        // Required empty public constructor
    }

    public static ShowListFragment showListInstance(int index, String idLogin) {
        ShowListFragment showListFragment = new ShowListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TypeUser", index);
        bundle.putString("Login", idLogin);
        showListFragment.setArguments(bundle);
        return showListFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        idLoginString = getArguments().getString("Login");

        typeUserAnInt = getArguments().getInt("TypeUser", 0);
        Log.d("3FebV1", "typeUser ==> " + typeUserAnInt);

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerShowList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Myconstant myconstant = new Myconstant();
        final ArrayList<String> nameStringArrayList = new ArrayList<>();
        final ArrayList<String> dateStringArrayList = new ArrayList<>();
        final ArrayList<String> amountStringArrayList = new ArrayList<>();
        final ArrayList<String> unitStringArrayList = new ArrayList<>();
        final ArrayList<String> imageStringArrayList = new ArrayList<>();
        final ArrayList<String> idUserStringArrayList = new ArrayList<>();


        switch (typeUserAnInt) {
            case 1:

                try {

                    GetAllDataThread getAllDataThread = new GetAllDataThread(getActivity());
                    getAllDataThread.execute(myconstant.getUrlGetAllDetail());
                    String json = getAllDataThread.get();
                    Log.d("3FebV1", "json ==> " + json);

                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i += 1) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        nameStringArrayList.add(jsonObject.getString("Name"));
                        dateStringArrayList.add(jsonObject.getString("Date"));
                        amountStringArrayList.add(jsonObject.getString("Amount"));
                        unitStringArrayList.add(jsonObject.getString("Unit"));
                        imageStringArrayList.add(jsonObject.getString("Image"));
                        idUserStringArrayList.add(jsonObject.getString("idUser"));

                    }

                    ShowListAdapter showListAdapter = new ShowListAdapter(getActivity(), nameStringArrayList,
                            dateStringArrayList, amountStringArrayList,
                            unitStringArrayList, imageStringArrayList, new OnClickItem() {
                        @Override
                        public void onClickitem(View view, int position) {

                            Intent intent = new Intent(getActivity(), DetailServiceActivity.class);
                            intent.putExtra("Name", nameStringArrayList.get(position));
                            intent.putExtra("Date", dateStringArrayList.get(position));
                            intent.putExtra("Amount", amountStringArrayList.get(position));
                            intent.putExtra("Unit", unitStringArrayList.get(position));
                            intent.putExtra("Image", imageStringArrayList.get(position));
                            intent.putExtra("IdUser", idUserStringArrayList.get(position));
                            startActivity(intent);

                        }
                    });

                    recyclerView.setAdapter(showListAdapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case 2:

                try {

                    GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(getActivity());
                    getDataWhereOneColumn.execute("idUser", idLoginString, myconstant.getUrlGetDetailWhereIdUser());
                    String json = getDataWhereOneColumn.get();
                    Log.d("3FebV1", "json ==> " + json);

                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i += 1) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        nameStringArrayList.add(jsonObject.getString("Name"));
                        dateStringArrayList.add(jsonObject.getString("Date"));
                        amountStringArrayList.add(jsonObject.getString("Amount"));
                        unitStringArrayList.add(jsonObject.getString("Unit"));
                        imageStringArrayList.add(jsonObject.getString("Image"));
                        idUserStringArrayList.add(jsonObject.getString("idUser"));

                    }

                    ShowListAdapter showListAdapter = new ShowListAdapter(getActivity(), nameStringArrayList,
                            dateStringArrayList, amountStringArrayList,
                            unitStringArrayList, imageStringArrayList, new OnClickItem() {
                        @Override
                        public void onClickitem(View view, int position) {

                            Intent intent = new Intent(getActivity(), DetailServiceActivity.class);
                            intent.putExtra("Name", nameStringArrayList.get(position));
                            intent.putExtra("Date", dateStringArrayList.get(position));
                            intent.putExtra("Amount", amountStringArrayList.get(position));
                            intent.putExtra("Unit", unitStringArrayList.get(position));
                            intent.putExtra("Image", imageStringArrayList.get(position));
                            intent.putExtra("IdUser", idUserStringArrayList.get(position));
                            startActivity(intent);

                        }
                    });

                    recyclerView.setAdapter(showListAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case 3:

                break;


        }   // Switch


    }   // Main Method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_list, container, false);
    }

}
