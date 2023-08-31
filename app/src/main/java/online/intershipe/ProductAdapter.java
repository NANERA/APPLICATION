package online.intershipe;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {
    Context context;
    String [] nameArray;
    int[] imageArray;
    String[] priceArray;
    SharedPreferences sp;
    String[] descArray;
    public ProductAdapter(Context context, String[] nameArray, int[] imageArray, String[] priceArray, String[] descArray) {
        this.context=context;
        this.imageArray=imageArray;
        this.nameArray=nameArray;
        this.priceArray=priceArray;
        this.descArray=descArray;
        sp= context.getSharedPreferences(ConstanSp.PREF,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product,parent,false);
        return new MyHolder(view);
    }
    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,price;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.custom_product_image);
            name=itemView.findViewById(R.id.custom_product_name);
            price=itemView.findViewById(R.id.custom_product_price);

        }
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.imageView.setImageResource(imageArray[position]);
        holder.name.setText(nameArray[position]);
        holder.price.setText(ConstanSp.PRICE_SYMBOL+priceArray[position]);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(ConstanSp.PRODUCT_PRICE,priceArray[position]).commit();
                sp.edit().putString(ConstanSp.PRODUCT_NAME,nameArray[position]).commit();
                sp.edit().putInt(ConstanSp.PRODUCT_IMAGE,imageArray[position]).commit();
                sp.edit().putString(ConstanSp.PRODUCT_DESC,descArray[position]).commit();
                new CommonMethod(context, ProductDetailActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameArray.length;
    }
}
