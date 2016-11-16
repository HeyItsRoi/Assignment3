package com.example.heyitsroi.databaseactiviry;

/**
 * Created by cstuser on 11/10/2016.
 */

        import android.app.Fragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

public class FragPictSound extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //---Inflate (or expand or fill) the layout for this fragment---
        return inflater.inflate(R.layout.frag_pictsound, container, false);
    }
}

