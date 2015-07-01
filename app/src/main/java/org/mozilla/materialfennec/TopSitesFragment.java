package org.mozilla.materialfennec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TopSitesFragment extends Fragment implements HomePanel {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topsites, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.topSites);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
        recyclerView.setAdapter(new TopSitesAdapter());

        int spacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new SpacingDecoration(spacing));
        recyclerView.setPadding(spacing, spacing, spacing, spacing);

        return view;
    }

    @Override
    public String getTitle() {
        return "Top Sites";
    }

    public static class TopSiteViewHolder extends RecyclerView.ViewHolder {
        public final TextView titleView;

        public TopSiteViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public static class TopSitesAdapter extends RecyclerView.Adapter<TopSiteViewHolder> {
        private TopSite[] topSites = new TopSite[] {
            new TopSite("Mozilla Project", 0xffcc4f45),
            new TopSite("Firefox Marketplace", 0xff1897da),
            new TopSite("Customize Firefox", 0xff65bc24),
            new TopSite("Help & Support", 0xfff17c22),
        };


        @Override
        public TopSiteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TopSiteViewHolder(
                LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.topsite, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(TopSiteViewHolder holder, int position) {
            TopSite topSite = topSites[position];

            holder.itemView.setBackgroundColor(topSite.color);
            holder.titleView.setText(topSite.title);
        }

        @Override
        public int getItemCount() {
            return topSites.length;
        }
    }

    public static class TopSite {
        public final String title;
        public final int color;

        public TopSite(String title, int color) {
            this.title = title;
            this.color = color;
        }
    }
}
