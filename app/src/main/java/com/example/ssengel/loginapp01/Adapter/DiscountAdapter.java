package com.example.ssengel.loginapp01.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ssengel.loginapp01.DiscountDetailActivity;
import com.example.ssengel.loginapp01.Model.GeneralDiscount;
import com.example.ssengel.loginapp01.R;

import java.util.List;

/**
 * Created by ssengel on 11.04.2018.
 */

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.MyViewHolder> {

    private Context context;
    private List<GeneralDiscount> generalDiscountList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public String discountId;
        public TextView txtTag;
        public TextView txtPrice;
        public TextView txtDiscountRate;
        private RelativeLayout main;

        public MyViewHolder(View view) {
            super(view);
            txtTag = (TextView) view.findViewById(R.id.txtTag);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            txtDiscountRate = (TextView) view.findViewById(R.id.txtDiscountRate);
            //main = view.findViewById(R.id.main);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent discountDetailIntent = new Intent(context, DiscountDetailActivity.class);
                    discountDetailIntent.putExtra("discountId",discountId);

                    context.startActivity(discountDetailIntent);
                }
            });
        }
    }

    public DiscountAdapter(Context context, List<GeneralDiscount> generalDiscountList){
        this.context = context;
        this.generalDiscountList = generalDiscountList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_discount, parent, false);
        return new MyViewHolder(cardItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GeneralDiscount discount = generalDiscountList.get(position);
        holder.txtTag.setText(discount.getTag());
        holder.txtDiscountRate.setText("Indirim Orani: " + discount.getDiscountRate());
        holder.txtPrice.setText("Urun Fiyati: " + discount.getPrice());
        //discountId sini listeden temin et.

        holder.discountId = discount.getId();
        // Card uzerindeki bir componente listener atamak icin burayi kullanmalisin
    }

    @Override
    public int getItemCount() {
        return generalDiscountList.size();
    }
}
