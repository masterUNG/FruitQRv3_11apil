package kiky.beam.lilly.th.ac.rmutk.fruitqr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.ShowListViewHolder>{

    private Context context;
    private ArrayList<String> nameStringArrayList, dateStringArrayList, amountStringArrayList, unitStringArrayList, iconStringArrayList;
    private OnClickItem onClickItem;
    private LayoutInflater layoutInflater;

    public ShowListAdapter(Context context, ArrayList<String> nameStringArrayList, ArrayList<String> dateStringArrayList, ArrayList<String> amountStringArrayList, ArrayList<String> unitStringArrayList, ArrayList<String> iconStringArrayList, OnClickItem onClickItem) {
        this.layoutInflater = LayoutInflater.from(context);
        this.nameStringArrayList = nameStringArrayList;
        this.dateStringArrayList = dateStringArrayList;
        this.amountStringArrayList = amountStringArrayList;
        this.unitStringArrayList = unitStringArrayList;
        this.iconStringArrayList = iconStringArrayList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ShowListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_show_list, viewGroup, false);
        ShowListViewHolder showListViewHolder = new ShowListViewHolder(view);

        return showListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowListViewHolder showListViewHolder, int i) {

        String name = nameStringArrayList.get(i);
        String date = dateStringArrayList.get(i);
        String amount = amountStringArrayList.get(i);
        String unit = unitStringArrayList.get(i);
        String urlIcon = iconStringArrayList.get(i);

        showListViewHolder.nameTextView.setText(name);
        showListViewHolder.dateTextView.setText(date);
        showListViewHolder.amountTextView.setText(amount);
        showListViewHolder.unitTextView.setText(unit);

        Picasso.get().load(urlIcon).into(showListViewHolder.iconImageView);

        showListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickitem(v, showListViewHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return nameStringArrayList.size();
    }

    public class ShowListViewHolder extends RecyclerView.ViewHolder {

        private ImageView iconImageView;
        private TextView nameTextView, dateTextView, amountTextView, unitTextView;


        public ShowListViewHolder(@NonNull View itemView) {
            super(itemView);

            iconImageView = itemView.findViewById(R.id.imvIcon);
            nameTextView = itemView.findViewById(R.id.txtName);
            dateTextView = itemView.findViewById(R.id.txtDate);
            amountTextView = itemView.findViewById(R.id.txtAmount);
            unitTextView = itemView.findViewById(R.id.txtUnit);


        }
    }   // Inside Class

}   // Main Class
