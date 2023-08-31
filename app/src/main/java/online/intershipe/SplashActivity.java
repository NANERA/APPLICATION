package online.intershipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         sp = getSharedPreferences(ConstanSp.PREF,MODE_PRIVATE);

          /*AlphaAnimation animation = new AlphaAnimation(0,1);
           animation.setDuration(3000);
          imageView.startAnimation(animation);
          Glide.with(SplashActivity.this).asGif().load("https://cdn.pixabay.com/animation/2022/11/01/09/21/09-21-24-793_512.gif").into(imageView);*/


         new Handler().postDelayed(new Runnable() {
            @Override 
            public void run() {
                if (sp.getString(ConstanSp.REMEMBER,"").equalsIgnoreCase("YES")) {
                    new CommonMethod(SplashActivity.this, MainActivity.class);
                }
                else
                    new CommonMethod(SplashActivity.this, MainActivity.class);
                }


         },3000);

    }
}