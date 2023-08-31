package online.intershipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class DashboardActivity extends AppCompatActivity {
    MeowBottomNavigation meowBottomNavigation;
    int HOME_MENU=1;
    int PROFILE_MENU=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        meowBottomNavigation=findViewById(R.id.dashboard_bottom);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(HOME_MENU, R.drawable.ic_home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(PROFILE_MENU, R.drawable.persone_icon));

         meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                if (item.getId()==HOME_MENU){
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.dashboard_relative,new HomeFragment()).commit();
                    meowBottomNavigation.show(HOME_MENU,true);
                }
                 else if (item.getId()==PROFILE_MENU) {
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.dashboard_relative,new ProfileFragment()).commit();
                    meowBottomNavigation.show(PROFILE_MENU,true);
                }
                    else {
                }
            }
        });
        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });
        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });
           FragmentManager manager = getSupportFragmentManager();
           manager.beginTransaction().replace(R.id.dashboard_relative,new HomeFragment()).commit();
           meowBottomNavigation.show(HOME_MENU,true);
    }
}