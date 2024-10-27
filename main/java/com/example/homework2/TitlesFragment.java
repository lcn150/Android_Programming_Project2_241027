package com.example.homework2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
//모든것은 프래그먼트에서 처리
public class TitlesFragment extends Fragment {
    final static String TAG="SQLITEDBTEST";
    //RestaurantDetail액티비티 위에서 동작할 TitlesFragment(fragment_titles.xml)
    public TitlesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflator를 사용한 rootView는 프래그먼트에서 화면을 부풀리는 중요한 역할
        View rootView = inflater.inflate(R.layout.fragment_titles, container, false);
        return rootView;
    }
}