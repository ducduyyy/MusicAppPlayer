package com.example.musicappplayer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.SearchBaiHatAdapter;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearch extends Fragment {
    View view;
    Toolbar toolbar;
    RecyclerView recyclerViewsearchbaihat;
    TextView txtkhongcodulieu;
    SearchBaiHatAdapter searchBaiHatAdapter;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    @Nullable
    @Override
    public View onCreateView(@NonNull @androidx.annotation.NonNull LayoutInflater inflater, @Nullable @androidx.annotation.Nullable ViewGroup container, @Nullable @androidx.annotation.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);
        toolbar = view.findViewById(R.id.toolbarsearchbaihat1);
        recyclerViewsearchbaihat = view.findViewById(R.id.recyclelerviewsearchbaihat1);
        txtkhongcodulieu = view.findViewById(R.id.textviewkhongcodulieu1);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Khám Phá");
        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@androidx.annotation.NonNull Menu menu, @androidx.annotation.NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_view,menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchTuKhoaBaiHat(query);
                menuItem.collapseActionView();
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void SearchTuKhoaBaiHat(String query){
        Dataservice dataservice = APIService.getService();
        Call<List<Songs>> callback = dataservice.GetSearchBaihat(query);
        callback.enqueue(new Callback<List<Songs>>() {
            @Override
            public void onResponse(Call<List<Songs>> call, Response<List<Songs>> response) {
                ArrayList<Songs> mangbaihat = (ArrayList<Songs>) response.body();
                if(mangbaihat.size()>0){
                    searchBaiHatAdapter = new SearchBaiHatAdapter(getActivity(),mangbaihat);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewsearchbaihat.setLayoutManager(linearLayoutManager);
                    recyclerViewsearchbaihat.setAdapter(searchBaiHatAdapter);
                    txtkhongcodulieu.setVisibility(View.GONE);
                    recyclerViewsearchbaihat.setVisibility(View.VISIBLE);
                }else {
                    recyclerViewsearchbaihat.setVisibility(View.GONE);
                    txtkhongcodulieu.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<List<Songs>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        firebaseUser = firebaseAuth.getCurrentUser();
    }
}
