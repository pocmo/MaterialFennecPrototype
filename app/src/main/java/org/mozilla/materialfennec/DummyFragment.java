package org.mozilla.materialfennec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DummyFragment extends Fragment implements HomePanel {
    public static DummyFragment create(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);

        DummyFragment fragment = new DummyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public String getTitle() {
        return getArguments().getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_dummy, container, false);
    }
}
