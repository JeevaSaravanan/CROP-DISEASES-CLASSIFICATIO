package com.insight.farmlenz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.insight.farmlenz.Adapter.MyCustomPagerAdapter;
import com.insight.farmlenz.Adapter.RecyclerViewAdapter;
import com.insight.farmlenz.Model.certModel;
import com.insight.farmlenz.R;
import com.insight.farmlenz.Upload;

import java.util.ArrayList;
import java.util.Timer;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    private static final int SPLASH_TIME_OUT =3000 ;
    ImageView draw;
    private TabLayout indicator;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    String User="";
    private ViewPager viewPager;
    ArrayList<certModel> models;
    RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        models=new ArrayList<>();

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager=root.findViewById(R.id.viewPager);
        indicator = root.findViewById(R.id.indicator);
        recyclerView = root.findViewById(R.id.recyclerView);

        String s[]={"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQa94j_AMhxPmxA70QPH6nGJyH6Ik5dqncp3OqkmjcNUIU8Fx7M", "https://s1.1zoom.me/b4744/911/Grapes_Closeup_536290_1920x1080.jpg", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSxBkPk7fBAorNzT1uFqvSG2klbJgyLX8h0i4qP-12TLCfU1fAB", "https://wallpapercave.com/wp/wp2570201.jpg", "https://wallpapercave.com/wp/wp2570201.jpg"};
        String name[]={"Tomato","Grape","Potato","Apple","Corn"};


        for (int i=0;i<5;i++)
        {
            certModel obj=new certModel(s[i],"link",name[i],"dec","rels");
            models.add(obj);
        }

        MyCustomPagerAdapter  myCustomPagerAdapter = new MyCustomPagerAdapter(getContext(), models,getActivity());
        viewPager.setAdapter(myCustomPagerAdapter);
        indicator.setupWithViewPager(viewPager, true);



        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                if(models!=null)
                {

                    if (viewPager.getCurrentItem() < models.size() -1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                    else
                    {
                        viewPager.setCurrentItem(0);
                    }

                }
            }
        }, SPLASH_TIME_OUT);



        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), models);
        setAdapter();


        return root;
    }

    private void setAdapter() {

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        recyclerView.setAdapter(recyclerViewAdapter);


        recyclerViewAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, certModel model) {

             //   Toast.makeText(getContext(), ""+model.getName(), Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getContext(), Upload.class);
                i.putExtra("fruit",model.getName());
               // getActivity().finish();
                startActivity(i);

            }
        });

    }
}