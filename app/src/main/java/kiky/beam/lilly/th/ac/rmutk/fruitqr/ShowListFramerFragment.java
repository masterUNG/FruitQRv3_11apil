package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowListFramerFragment extends Fragment {

    private String typeUserString, idRecordString;
    private Myconstant myconstant = new Myconstant();

    public ShowListFramerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //การสร้าง Create Recycler
        createRecyclerView();


    }

    private void createRecyclerView() {
        try {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(myconstant.getNameFileSharePreference(), Context.MODE_PRIVATE);
            typeUserString = sharedPreferences.getString("TypeUser", "");
            idRecordString = sharedPreferences.getString("idLogin", "");

            int index = Integer.parseInt(typeUserString.trim()); //เอามาเทสก่อน

            String result = null;

            if(index == 1) { //สถานะเท่ากับ 1

                GetAllDataThread getAllDataThread = new GetAllDataThread(getActivity());
                getAllDataThread.execute(myconstant.getUrlGetAllDetailFramer()); //อ่านทุกช่อง
                result = getAllDataThread.get();

            } else{ //สถานะมากกว่า1
                GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(getActivity());
                getDataWhereOneColumn.execute("idRecord", idRecordString, myconstant.getUrlGetDetailFramerWhereIdRecord());
                result = getDataWhereOneColumn.get();

                }

            Log.d("7April", "result ==> " + result);

            JSONArray jsonArray = new JSONArray(result);
            ArrayList<String> nameStringArrayList = new ArrayList<>();
            ArrayList<String> amountStringArrayList = new ArrayList<>();
            ArrayList<String> dateStringArrayList = new ArrayList<>();

            ArrayList<String> nameOwnerStringArrayList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameStringArrayList.add(jsonObject.getString("Name"));
                amountStringArrayList.add(jsonObject.getString("Amount")+ " " +jsonObject.getString("Unit"));
                dateStringArrayList.add(jsonObject.getString("Date"));

                nameOwnerStringArrayList.add(findNameOwer(jsonObject.getString("idRecord")));
            }

            Log.d("7April", "name ==>>> " + nameStringArrayList.toString());
            Log.d("7April", "amount ==>>> " + amountStringArrayList.toString());
            Log.d("7April", "date ==>>> " +dateStringArrayList.toString());

            RecyclerView recyclerView = getView().findViewById(R.id.recyclerFramer);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            ShowListFragmentAdapter showListFragmentAdapter = new ShowListFragmentAdapter(getActivity(), nameStringArrayList,
                    amountStringArrayList, dateStringArrayList, nameOwnerStringArrayList, new OnClickItem() {//กด ctrl+p
                @Override
                public void onClickitem(View view, int position) {

                }
            });
            recyclerView.setAdapter(showListFragmentAdapter);


        }catch (Exception e){
            Log.d("7April", "eror ==> "+e.toString());
        }
    }

    private String findNameOwer(String idRecord) {//ดึงชื่อมาจากฐานข้อมูล
        try{
            GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(getActivity());
            getDataWhereOneColumn.execute("id", idRecord, myconstant.getUrlGetUserWhereId());
            String result = getDataWhereOneColumn.get();

            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            return jsonObject.getString("Name");

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_list_framer, container, false);
    }

}
