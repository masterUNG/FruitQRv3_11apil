package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {

    private Myconstant myconstant = new Myconstant();
    private String idRecord, NameRecord, TypeRecord, idFarmer = "", Name, Detail, Image, Amount, Unit, Date, QRcode;

    private ImageView imageView;
    private Uri uri;
    private boolean picABoolean = true;


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

//        Date Controller
        dateController();

//        Unit Controller
        unitController();

//        Add Product
        addProduct();

//        QR Controller
        QRController();


    }   // Main Method

    private void QRController() {
        TextView textView = getView().findViewById(R.id.txtQRcode);
        Random random = new Random();
        int i = random.nextInt(10000);
        QRcode = "product" + Integer.toString(i);
        textView.setText(QRcode);
    }

    private void unitController() {
        Spinner spinner = getView().findViewById(R.id.spnUnit);
        final String[] strings = myconstant.getUnits();
        Unit = strings[0];
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Unit = strings[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Unit = strings[0];
            }
        });
    }

    private void dateController() {
        Button button = getView().findViewById(R.id.btnSetDate);
        final TextView textView = getView().findViewById(R.id.txtDate);
        final Calendar calendar = Calendar.getInstance();
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date = dateFormat.format(calendar.getTime());
        textView.setText(Date);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(year, month, dayOfMonth);
                        Date = dateFormat.format(calendar1.getTime());
                        textView.setText(Date);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }

    private void addProduct() {
        Button button = getView().findViewById(R.id.btnAddProduct);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(myconstant.getNameFileSharePreference(), Context.MODE_PRIVATE);
                idRecord = sharedPreferences.getString("idLogin", "");
                NameRecord = sharedPreferences.getString("Name", "");
                TypeRecord = sharedPreferences.getString("TypeUser", "");

                MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
                EditText nameProductEditText = getView().findViewById(R.id.edtProduct);
                Name = nameProductEditText.getText().toString().trim();
                EditText detailEditText = getView().findViewById(R.id.edtDetailProduct);
                Detail = detailEditText.getText().toString().trim();
                EditText amountEditText = getView().findViewById(R.id.edtAmount);
                Amount = amountEditText.getText().toString().trim();

                if (idFarmer.length() == 0) {
                    myAlertDialog.normalDialog("ยังไม่ได้เลือก ผลผลิด", "โปรดเลือกผลผลิด");
                } else if (Name.isEmpty()) {
                    myAlertDialog.normalDialog("ไม่มีชื่อผลิดภัณฑ์", "กรุณาพิมพ์ชื่อผลิตภัณฑ์");
                } else if (Detail.isEmpty()) {
                    myAlertDialog.normalDialog("รายละเอียดไม่มี", "กรุณากรอกรายละเอียด");
                } else if (Amount.isEmpty()) {
                    myAlertDialog.normalDialog("รายละเอียดจำนวน", "กรุณากรอกจำนวน");
                } else if (picABoolean) {
                    Image = myconstant.getUrlProductPic();
                } else {

                    String path = null;
                    String[] strings = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(uri, strings, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        path = cursor.getString(index);
                    } else {
                        path = uri.getPath();
                    }

                    Log.d("11AprilV2", "path ==>> " + path);

                    String nameImage = path.substring(path.indexOf("/"));

                    Image = "https://www.androidthai.in.th/rmutk/Picture" + nameImage;
                    Log.d("11AprilV2", "Image ==>> " + Image);

                    File file = new File(path);
                    FTPClient ftpClient = new FTPClient();
                    String tag = "18AprilV1";

                    StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                            .Builder().permitAll().build();
                    StrictMode.setThreadPolicy(threadPolicy);

                    try {

                        ftpClient.connect("ftp.androidthai.in.th", 21);
                        ftpClient.login("rmutk@androidthai.in.th", "Abc12345");
                        ftpClient.setType(FTPClient.TYPE_BINARY);
                        ftpClient.changeDirectory("Picture");
                        ftpClient.upload(file, new UploadListener());

//                        Add Data
                        AddDetailProductThread addDetailProductThread = new AddDetailProductThread(getActivity());
                        addDetailProductThread.execute(idRecord, NameRecord, TypeRecord, idFarmer, Name,
                                Detail, Image, Amount, Unit, Date, QRcode, myconstant.getUrlAddDetailProduct());
                        String result = addDetailProductThread.get();
                        Log.d(tag, "result ==>>> " + result);


                    } catch (Exception e) {
                        Log.d(tag, "e ==> " + e.toString());

                        try {
                            ftpClient.disconnect(true);
                        } catch (Exception e1) {
                            Log.d(tag, "e1 ==> " + e1.toString());
                        }

                    }




                } // if






            }
        });



    }

    public class UploadListener implements FTPDataTransferListener {

        @Override
        public void started() {
            Toast.makeText(getActivity(), "Start Upload", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void transferred(int i) {
            Toast.makeText(getActivity(), "Continue Upload", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void completed() {
            Toast.makeText(getActivity(), "Complete Upload", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void aborted() {
            Toast.makeText(getActivity(), "Aborted Upload", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failed() {
            Toast.makeText(getActivity(), "False Upload", Toast.LENGTH_SHORT).show();
        }
    }


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
            picABoolean = false;

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
