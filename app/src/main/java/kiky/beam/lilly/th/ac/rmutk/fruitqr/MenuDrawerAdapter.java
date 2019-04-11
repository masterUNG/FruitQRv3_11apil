package kiky.beam.lilly.th.ac.rmutk.fruitqr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuDrawerAdapter extends RecyclerView.Adapter<MenuDrawerAdapter.MenuDrawerViewHolder>{

    private Context context;
    private ArrayList<Integer> integerArrayList;
    private ArrayList<String> stringArrayList;
    private OnClickItem onClickItem;
    private LayoutInflater layoutInflater;

    public MenuDrawerAdapter(Context context, ArrayList<Integer> integerArrayList, ArrayList<String> stringArrayList, OnClickItem onClickItem) {
        this.layoutInflater = LayoutInflater.from(context);
        this.integerArrayList = integerArrayList;
        this.stringArrayList = stringArrayList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MenuDrawerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_drawer_menu, viewGroup, false);
        MenuDrawerViewHolder menuDrawerViewHolder = new MenuDrawerViewHolder(view);

        return menuDrawerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuDrawerViewHolder menuDrawerViewHolder, int i) {

        int iconInt = integerArrayList.get(i);
        String titleString = stringArrayList.get(i);

        menuDrawerViewHolder.imageView.setImageResource(iconInt);
        menuDrawerViewHolder.textView.setText(titleString);

        menuDrawerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickitem(v, menuDrawerViewHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class MenuDrawerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public MenuDrawerViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imvIcon);
            textView = itemView.findViewById(R.id.txtTitle);


        }


    }

}
