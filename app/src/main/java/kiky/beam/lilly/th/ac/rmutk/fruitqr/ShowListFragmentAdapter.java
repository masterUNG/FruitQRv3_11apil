package kiky.beam.lilly.th.ac.rmutk.fruitqr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowListFragmentAdapter extends  RecyclerView.Adapter<ShowListFragmentAdapter.ShowListFramerViewHolder>{

    private Context context;
    private ArrayList<String> nameStringArrayList, amountStringArrayList, dateStringArrayList, nameOwnerStringArrayList;
    private OnClickItem onClickItem;
    private LayoutInflater layoutInflater;

    public ShowListFragmentAdapter(Context context, ArrayList<String> nameStringArrayList, ArrayList<String> amountStringArrayList, ArrayList<String> dateStringArrayList, ArrayList<String> nameOwnerStringArrayList, OnClickItem onClickItem) {
        this.layoutInflater = LayoutInflater.from(context);
        this.nameStringArrayList = nameStringArrayList;
        this.amountStringArrayList = amountStringArrayList;
        this.dateStringArrayList = dateStringArrayList;
        this.nameOwnerStringArrayList = nameOwnerStringArrayList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ShowListFramerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_show_list_framer, viewGroup, false);
        ShowListFramerViewHolder showListFramerViewHolder = new ShowListFramerViewHolder(view);


        return showListFramerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowListFramerViewHolder showListFramerViewHolder, int i) {
        //ดึงเดต้ามาเป็นตัวอักษร
        String name = nameStringArrayList.get(i);
        String amountAndUnit = amountStringArrayList.get(i);
        String date = dateStringArrayList.get(i);
        String nameOwer = nameOwnerStringArrayList.get(i);


        showListFramerViewHolder.nameTextView.setText(name);
        showListFramerViewHolder.amountTextView.setText("Amount = " + amountAndUnit);
        showListFramerViewHolder.dateTextView.setText(date);
        showListFramerViewHolder.nameOwnerTextView.setText("Owner : " + nameOwer);

    }

    @Override
    public int getItemCount() {
        return nameStringArrayList.size();
    }

    public  class ShowListFramerViewHolder extends RecyclerView.ViewHolder{

        private TextView nameTextView, amountTextView, dateTextView, nameOwnerTextView;

        public ShowListFramerViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.txtName);
            amountTextView = itemView.findViewById(R.id.txtAmount);
            dateTextView = itemView.findViewById(R.id.txtDate);
            nameOwnerTextView = itemView.findViewById(R.id.txtNameOwner);
        }
    }

}
