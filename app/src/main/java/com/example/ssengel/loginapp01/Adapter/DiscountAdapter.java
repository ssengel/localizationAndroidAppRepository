package com.example.ssengel.loginapp01.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ssengel.loginapp01.Activity.DiscountDetailActivity;
import com.example.ssengel.loginapp01.Model.Discount;
import com.example.ssengel.loginapp01.R;

import java.util.List;

/**
 * Created by ssengel on 11.04.2018.
 */

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.MyViewHolder> {

    private Context context;
    private List<Discount> discountList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public String discountId;
        public TextView txtTag;
        public TextView txtPrice;
        public TextView txtDiscountRate;
        public ImageView imgView;

        public MyViewHolder(View view) {
            super(view);
            txtTag = (TextView) view.findViewById(R.id.txtTag);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            txtDiscountRate = (TextView) view.findViewById(R.id.txtDiscountRate);
            imgView = (ImageView) view.findViewById(R.id.imgView);

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

    public DiscountAdapter(Context context, List<Discount> discountList){
        this.context = context;
        this.discountList = discountList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_discount, parent, false);
        return new MyViewHolder(cardItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Discount discount = discountList.get(position);
        holder.txtTag.setText(discount.getTag());
        holder.txtDiscountRate.setText("Indirim Orani: " + discount.getDiscountRate());
        holder.txtPrice.setText("Urun Fiyati: " + discount.getPrice());

        if(discount.getImagePath().equals("indirim01")){
            holder.imgView.setImageResource(R.drawable.indirim01);
        }else if(discount.getImagePath().equals("indirim02")){
            holder.imgView.setImageResource(R.drawable.indirim02);
        }else{
            holder.imgView.setImageResource(R.drawable.indirim03);
        }
        //discountId sini listeden temin et.

        holder.discountId = discount.getId();
        // Card uzerindeki bir componente listener atamak icin burayi kullanmalisin
    }

    @Override
    public int getItemCount() {
        return discountList.size();
    }
}
