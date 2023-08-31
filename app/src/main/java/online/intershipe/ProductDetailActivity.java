package online.intershipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class ProductDetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView name,price,desc;
    SharedPreferences sp;
    Button BuyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        sp=getSharedPreferences(ConstanSp.PREF,MODE_PRIVATE);
        price=findViewById(R.id.product_detail_price);
        desc=findViewById(R.id.product_detail_detail);
        name=findViewById(R.id.product_detail_Name);
        BuyNow=findViewById(R.id.product_detail_buynow);
        imageView=findViewById(R.id.product_detail_image);
        name.setText(sp.getString(ConstanSp.PRODUCT_NAME,""));
        imageView.setImageResource(sp.getInt(ConstanSp.PRODUCT_IMAGE,0));
        price.setText(ConstanSp.PRICE_SYMBOL+sp.getString(ConstanSp.PRODUCT_PRICE,""));
        desc.setText(sp.getString(ConstanSp.PRODUCT_DESC,""));

    }
}