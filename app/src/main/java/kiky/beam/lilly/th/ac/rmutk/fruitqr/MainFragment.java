package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private Myconstant myconstant = new Myconstant();
    private String typeUser;

    public MainFragment() {
        // Required empty public constructor //กำหนดค่าเริ่มต้น
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Check Status
        checkStatus();

//        Register Controllor //เป็นการคลิกปุ่ม
//        registerController();

        //  Login Controller
        loginController();




    }   //Main Method เมธอดแรกในการทำงาน

    private void checkStatus() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Choose Work");
        builder.setMessage("Please Choose Work ?");
        builder.setNegativeButton("QR code", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getActivity(), QRActivity.class));
                getActivity().finish();
            }
        });
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();


    }

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                String user = userEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
                if (user.isEmpty() || password.isEmpty()) {
                    myAlertDialog.normalDialog("Have Space", "Plase Fill Every Blank");
                } else {

                 //   Check Authen
                    try {

                        Myconstant myconstant = new Myconstant();
                        GetAllDataThread getAllDataThread = new GetAllDataThread(getActivity());
                        getAllDataThread.execute(myconstant.getUrlGetAllData());

                        String jsonString = getAllDataThread.get();
                        Log.d("6janV1","json ==> " + jsonString);

                        boolean b = true;
                        String truePassword = null, name = null, idString = null;

                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i=0; i<jsonArray.length(); i += 1){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (user.equals(jsonObject.getString("User"))){
                                b = false;
                                truePassword = jsonObject.getString("Password");
                                name = jsonObject.getString("Name");
                                idString = jsonObject.getString("id");
                                typeUser = jsonObject.getString("TypeUser"); //ประเภท
                            }
                        }

                        if (b){
                            myAlertDialog.normalDialog("User False", "No This User in My Database");
                        }else if (password.equals(truePassword)) {
                            Toast.makeText(getActivity(),"Welcome"+name,Toast.LENGTH_SHORT).show();;
                            //ฝัง idlogin ในแอพ
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(myconstant.getNameFileSharePreference(), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("idLogin", idString);
                            editor.putString("TypeUser", typeUser);
                            editor.putString("Name", name);
                            editor.commit();


                            Intent intent = new Intent(getActivity(), ServiceActivity.class);
                            intent.putExtra("id", idString);
                            startActivity(intent);
                            getActivity().finish(); //คำสั่งปิดแอพ

                        }else{
                            myAlertDialog.normalDialog("Password False","Please Try Again Password False");

                        }



                    }catch (Exception e){
                        e.printStackTrace();

                    }




                }


            }
        });
    }

//    private void registerController() {
//        TextView textView = getView().findViewById(R.id.txtRegister); //กด shift+ctrl+enter เติมส่วนที่ขาด //alt+1 กดปิดหน้าโปรเจค
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
////                Replace Fragment
//
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMainFragment, new RegisterFragment()).addToBackStack(null).commit();
//            }
//        });
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment //สร้างหน้ากาก หรือ textview
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}//Main Class
