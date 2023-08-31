package online.intershipe;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    String[] nameArray = {"ear buds","iphone 15pro Max","Lenovo Idea pad slim 3"};
    int[] imageArray = {R.drawable.burd_1, R.drawable.phone_1, R.drawable.leptope_1};
    String[] priceArray = {"1900","1,10,000","45,500"};
    String[] descArray = {
           "Earbuds are basically a pair of tiny speakers that you wear inside your ears. At low volumes, they're useful little devices. But playing loud music so close to your eardrums can cause permanent hearing loss.",
            "The iPhone is a smartphone made by Apple that combines a computer, iPod, digital camera and cellular phone into one device with a touchscreen interface. The iPhone runs the iOS operating system, and in 2021 when the iPhone 13 was introduced, it offered up to 1 TB of storage and a 12-megapixel camera.",
            "Lenovo ThinkPad is a Windows 10 laptop with a 14.00-inch display that has a resolution of 1920x1080 pixels. It is powered by a Core i7 processor and it comes with 12GB of RAM. The Lenovo ThinkPad packs 256GB of SSD storage. Graphics are powered by Intel HD Graphics 520."
    };
    public HomeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ProductAdapter adapter=new ProductAdapter(getActivity(),nameArray,imageArray,priceArray,descArray);
        recyclerView.setAdapter(adapter);
        return view;
    }
}