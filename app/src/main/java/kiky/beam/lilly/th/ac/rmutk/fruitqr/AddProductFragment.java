package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {

    private Myconstant myconstant = new Myconstant();
    private String idRecord, NameRecord, TypeRecord, idFarmer, Name, Detail, Image, Amount, Unit, Date, QRcode;

    private ImageView imageView;
    private Uri uri;


    public AddProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create RecyclerView
        createRecyclerView();

//        Picture Controller
        pictureController();


    }   // Main Method

    private void pictureController() {
        imageView = getView().findViewById(R.id.imvProduct);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "กรุณาเลือกแอพ ดูรูปภาพ"), 1);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == getActivity().RESULT_OK) {

            uri = data.getData();

            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 800, 600, false);
                imageView.setImageBitmap(bitmap1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void createRecyclerView() {

        try {

            RecyclerView recyclerView = getView().findViewById(R.id.recyclerFramer);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            GetAllDataThread getAllDataThread = new GetAllDataThread(getActivity());
            getAllDataThread.execute(myconstant.getUrlGetAllFramer());

            String result = getAllDataThread.get();
            final ArrayList<String> nameStringArrayList = new ArrayList<>();
            ArrayList<String> amountStringArrayList = new ArrayList<>();
            ArrayList<String> dateStringArrayList = new ArrayList<>();
            ArrayList<String> ownerStringArrayList = new ArrayList<>();
            final ArrayList<String> idStringArrayList = new ArrayList<>();
            Log.d("11AprilV1", result);

            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameStringArrayList.add(jsonObject.getString("Name"));
                amountStringArrayList.add(jsonObject.getString("Amount") + " " + jsonObject.getString("Unit"));
                dateStringArrayList.add(jsonObject.getString("Date"));
                ownerStringArrayList.add(jsonObject.getString("idRecord"));
                idStringArrayList.add(jsonObject.getString("id"));

            }

            ShowListFragmentAdapter showListFragmentAdapter = new ShowListFragmentAdapter(getActivity(),
                    nameStringArrayList, amountStringArrayList, dateStringArrayList, ownerStringArrayList,
                    new OnClickItem() {
                        @Override
                        public void onClickitem(View view, int position) {
                            confirmFruit(nameStringArrayList.get(position), idStringArrayList.get(position));
                        }
                    });
            recyclerView.setAdapter(showListFragmentAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void confirmFruit(final String nameFruit, final String idFruit) {
        final TextView textView = getView().findViewById(R.id.txtChooseFruit);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Fruit");
        builder.setMessage("คุณต้องการเลือก " + nameFruit);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idFarmer = idFruit;
                textView.setText("ผลผลิด ที่เลือก " + nameFruit);
                dialog.dismiss();
            }
        });
        builder.show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

}
