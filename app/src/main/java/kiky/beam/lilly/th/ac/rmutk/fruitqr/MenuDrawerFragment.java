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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuDrawerFragment extends Fragment {

    private int typeDataInt;
    private String idLogin;

    //    Admin
    private int[] iconAdmin = {
            R.drawable.ic_action_home,
            R.drawable.ic_action_qrb,
            R.drawable.ic_action_listframer,
            R.drawable.ic_action_framer,
            R.drawable.ic_action_listframer,
            R.drawable.ic_action_product,
            R.drawable.ic_action_addregister,
            R.drawable.ic_action_register,
            R.drawable.ic_action_aboutme,
            R.drawable.ic_action_exitt
    };
    private String[] titleAdmin = {
            "หน้าหลัก1",
            "สแกน QR Code",
            "รายการผลผลิต",
            "เพิ่มผลผลิต",
            "รายการผลิตภัณฑ์",
            "เพิ่มผลิตภัณฑ์",
            "เพิ่มสมาชิก",
            "ข้อมูลส่วนตัว",
            "เกี่ยวกับเรา",
            "ออกจากระบบ"
    };

    //    Framer
    private int[] iconFramer = {
            R.drawable.ic_action_home,
            R.drawable.ic_action_qrb,
            R.drawable.ic_action_framer,
            R.drawable.ic_action_listframer,
            R.drawable.ic_action_register,
            R.drawable.ic_action_aboutme,
            R.drawable.ic_action_exitt};

    private String[] titleFramer = {
            "หน้าหลัก2",
            "สแกน QR Code",
            "รายการผลผลิต",
            "เพิ่มรายการผลผลิต",
            "ข้อมูลส่วนตัว",
            "เกี่ยวกับเรา",
            "ออกจากระบบ"};

    //    Product
    private int[] iconProduce = {
            R.drawable.ic_action_home,
            R.drawable.ic_action_qrb,
            R.drawable.ic_action_framer,
            R.drawable.ic_action_listframer,
            R.drawable.ic_action_register,
            R.drawable.ic_action_aboutme,
            R.drawable.ic_action_exitt};

    private String[] titleProduct = {
            "หน้าหลัก3",
            "สแกน QR Code",
            "รายการผลิตภัณฑ์",
            "เพิ่มรายการผลิตภัณฑ์",
            "ข้อมูลส่วนตัว",
            "เกี่ยวกับเรา",
            "ออกจากระบบ"};

    //    Customer
    private int[] iconCustomer = {
            R.drawable.ic_action_home,
            R.drawable.ic_action_qrb,
            R.drawable.ic_action_listframer,
            R.drawable.ic_action_framer,
            R.drawable.ic_action_listframer,
            R.drawable.ic_action_product,
            R.drawable.ic_action_register,
            R.drawable.ic_action_aboutme,
            R.drawable.ic_action_exitt};

    private String[] titleCustomer = {
            "หน้าหลัก4",
            "สแกน QR Code",
            "รายการผลผลิต",
            "เพิ่มผลผลิต",
            "รายการผลิตภัณฑ์",
            "เพิ่มผลิตภัณฑ์",
            "ข้อมูลส่วนตัว",
            "เกี่ยวกับเรา",
            "ออกจากระบบ"
    };

    public MenuDrawerFragment() {
        // Required empty public constructor
    }

    public static MenuDrawerFragment menuDrawerInstance(String typeDataString, String idLogin) {
        MenuDrawerFragment menuDrawerFragment = new MenuDrawerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TypeData", typeDataString);
        bundle.putString("TypeLogin", idLogin);//เพิ่ม
        menuDrawerFragment.setArguments(bundle);
        return menuDrawerFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String typeDataString = getArguments().getString("TypeData").trim();
        typeDataInt = Integer.parseInt(typeDataString);
        idLogin = getArguments().getString("TypeLogin");//

        switch (typeDataInt) {
            case 1:
                showMenu(iconAdmin, titleAdmin);
                break;
            case 2:
                showMenu(iconFramer, titleFramer);
                break;
            case 3:
                showMenu(iconProduce, titleProduct);
                break;
            case 4:
                showMenu(iconCustomer, titleCustomer);
                break;
        }


    }

    private void showMenu(int[] iconInts, String[] titleStrings) {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        for (int i = 0; i < titleStrings.length; i += 1) {
            integerArrayList.add(iconInts[i]);
            stringArrayList.add(titleStrings[i]);
        }

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerDrawerMenu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        MenuDrawerAdapter menuDrawerAdapter = new MenuDrawerAdapter(getActivity(), integerArrayList, stringArrayList, new OnClickItem() {
            @Override
            public void onClickitem(View view, int position) {
                Log.d("6AprilV1", "You Click Position at ===>>> " + position);
                ((ServiceActivity)getActivity()).serviceCloseDrawer();

                switch (typeDataInt) { //มี4ประเภท Amin
                    case 1:
                        chooseCase1(position);
                        break;
                    case 2:
                        chooseCase2(position);
                        break;
                    case 3:
                        chooseCase3(position);
                        break;
                    case 4:
                        chooseCase4(position);
                        break;
                }

            }
        });
        recyclerView.setAdapter(menuDrawerAdapter);

    }

    //For Login Admin Yype
    private void chooseCase1(int position) { //Admin
        switch (position){
//              หน้าหลัก
            case 0:// Fragment
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new TutorialFragment()).commit();
                break;
//            สแกน QR Code
            case 1:
                Intent intent = new Intent(getActivity(), QRActivity.class);
                intent.putExtra("Login", false);
                startActivity(intent);

                break;
//              รายการผลผลิต
            case 2:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new ShowListFramerFragment()).commit();

                break;

//              เพิ่มรายการผลผลิต
            case 3:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new AddFramerFragment()).commit();
                break;

//                รายการผลิตภัณฑ์
            case 4:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new ShowListProductFragment()).commit();

                break;

//                เพิ่มรายการผลิตภัณฑ์
            case 5:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new AddProductFragment()).commit();
                break;

//                เพิ่มสมาชิก
            case 6:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new RegisterFragment()).commit();
                break;

//              ข้อมูลสมาชิก
            case 7:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new ShowListMemberFragment()).commit();
                break;

//              ผู้พัฒนา
            case 8:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new AboutMeFragment()).commit();
                break;

//              ออกจากระบบ
            case 9:
                getActivity().finish();
                break;
        }
    }


    //For Login Framer Yype
    private void chooseCase2(int position) {
        switch(position){
//              หน้าหลัก
            case 0:// Fragment
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new TutorialFragment()).commit();
                break;
//            สแกน QR Code
            case 1:
                Intent intent = new Intent(getActivity(), QRActivity.class);
                intent.putExtra("Login", false);
                startActivity(intent);
                break;

//              รายการผลผลิต
            case 2:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new ShowListFramerFragment()).commit();
                break;

//                เพิ่มผลผลิต
            case 3:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new AddFramerFragment()).commit();
                break;

//                ข้อมูลส่วนตัว
            case 4:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new InfoLoginFragment()).commit();
                break;

//                เกี่ยวกับเรา
            case 5:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new AboutMeFragment()).commit();
                break;

//                ออกจากระบบ
            case 6:
                getActivity().finish();
                break;
        }
    }

    //For Login Product Yype
    private void chooseCase3(int position) {
        switch(position){
//              หน้าหลัก
            case 0:// Fragment
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new TutorialFragment()).commit();
                break;

//            สแกน QR Code
            case 1:
                Intent intent = new Intent(getActivity(), QRActivity.class);
                intent.putExtra("Login", false);
                startActivity(intent);
                break;

//              รายการผลิตภัณฑ์
            case 2:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,ShowListFragment.showListInstance(typeDataInt,idLogin)).commit();
                break;

//               เพิ่มรายการผลิตภัณฑ์
            case 3:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new AddFramerFragment()).commit();

                break;

//                ข้อมูลส่วนตัว
            case 4:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new InfoLoginFragment()).commit();
                break;

//              เกี่ยวกับเรา
            case 5:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment,new AboutMeFragment()).commit();
                break;
//              ออกจากระบบ
            case 6:
                getActivity().finish();
                break;
        }
    }

    private void chooseCase4(int position) {
    }

    private void goTutorial(){}

    private void goScanQRcode(){}

    private void goListFramer(){}

    private void goAddFramer(){}

    private void goListProduct(){}

    private void goAddProduct(){}

    private void goAddRegister(){}

    private void goInfo(){}

    private void goAboutMe(){}

    private void goExit(){}




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_drawer, container, false);
    }

}