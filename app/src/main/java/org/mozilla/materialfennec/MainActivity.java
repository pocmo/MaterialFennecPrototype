package org.mozilla.materialfennec;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private EditText urlView;
    private ImageView menuView;
    private ImageView switchView;
    private CardView cardView;
    private ViewPager pagerView;
    private LinearLayout containerView;
    private ImageView clearView;
    private TabLayout tabsView;

    private int containerPadding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        containerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        containerView = (LinearLayout) findViewById(R.id.container);
        cardView = (CardView) findViewById(R.id.card);
        urlView = (EditText) findViewById(R.id.url);
        menuView = (ImageView) findViewById(R.id.menu);
        switchView = (ImageView) findViewById(R.id.switcher);
        pagerView = (ViewPager) findViewById(R.id.pager);
        clearView = (ImageView) findViewById(R.id.clear);
        tabsView = (TabLayout) findViewById(R.id.tabs);

        clearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlView.clearFocus();
            }
        });

        urlView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                switchView.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
                menuView.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
            }
        });

        LayoutTransition transition = containerView.getLayoutTransition();

        transition.addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                if (!urlView.hasFocus()) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(urlView.getWindowToken(), 0);

                    clearView.setVisibility(View.GONE);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
                    params.setMargins(containerPadding, containerPadding, containerPadding, containerPadding);
                    cardView.setLayoutParams(params);
                }
            }

            @Override
            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                if (urlView.hasFocus()) {
                    clearView.setVisibility(View.VISIBLE);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
                    params.setMargins(0, 0, 0, 0);
                    cardView.setLayoutParams(params);
                }
            }
        });

        pagerView.setAdapter(new HomeAdapter(getSupportFragmentManager()));

        tabsView.setupWithViewPager(pagerView);

        transition.setDuration(100);
    }

    @Override
    public void onBackPressed() {
        if (urlView.hasFocus()) {
            urlView.clearFocus();
        } else {
            super.onBackPressed();
        }
    }

    public static class HomeAdapter extends FragmentPagerAdapter {
        private static HomePanel[] panels = new HomePanel[] {
            new TopSitesFragment(),
            DummyFragment.create("Bookmarks"),
            DummyFragment.create("History"),
            DummyFragment.create("Reading List"),
            DummyFragment.create("Recent Tabs"),
        };

        public HomeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return (Fragment) panels[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return panels[position].getTitle();
        }

        @Override
        public int getCount() {
            return panels.length;
        }
    }
}
