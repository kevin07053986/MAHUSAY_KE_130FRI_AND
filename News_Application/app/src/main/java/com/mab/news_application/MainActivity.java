package com.mab.news_application;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private boolean isLandscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate called");
        setContentView(R.layout.activity_main);

        isLandscape = findViewById(R.id.fragment_detail_container) != null;
        Log.d("MainActivity", "Is Landscape: " + isLandscape);

        if (savedInstanceState == null) {
            Log.d("MainActivity", "SavedInstanceState is null");
            setupFragments();
        } else {
            Log.d("MainActivity", "SavedInstanceState is not null");

            Fragment fragmentHeadlines = getSupportFragmentManager().findFragmentById(R.id.fragment_headline_container);
            Fragment fragmentDetails = getSupportFragmentManager().findFragmentById(R.id.fragment_detail_container);

            if (fragmentHeadlines == null) {
                Log.d("MainActivity", "Adding NewsListFragment");
                NewsListFragment newsListFragment = new NewsListFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_headline_container, newsListFragment)
                        .commit();
            }

            if (fragmentDetails == null && isLandscape) {
                Log.d("MainActivity", "Adding default NewsDetailFragment");
                NewsDetailFragment defaultDetailFragment = NewsDetailFragment.newInstance(
                        "Select a headline", "Details will appear here"
                );
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail_container, defaultDetailFragment)
                        .commit();
            }
        }
    }

    private void setupFragments() {
        NewsListFragment newsListFragment = new NewsListFragment();

        if (isLandscape) {
            Log.d("MainActivity", "Landscape mode detected");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_headline_container, newsListFragment)
                    .commit();
            Log.d("MainActivity", "Added NewsListFragment to fragment_headline_container");

            NewsDetailFragment defaultDetailFragment = NewsDetailFragment.newInstance(
                    "Select a headline", "Details will appear here"
            );
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail_container, defaultDetailFragment)
                    .commit();
            Log.d("MainActivity", "Added NewsDetailFragment to fragment_detail_container");
        } else {
            Log.d("MainActivity", "Portrait mode detected");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, newsListFragment)
                    .commit();
            Log.d("MainActivity", "Loading NewsListFragment into fragment_container");
        }
    }

    public void showDetailsInLandscape(String headline, String content) {
        if (isLandscape) {
            NewsDetailFragment detailFragment = NewsDetailFragment.newInstance(headline, content);

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.fragment_detail_container, detailFragment)
                    .commit();
            Log.d("MainActivity", "Replacing detail fragment in fragment_detail_container with slide animation");
        }
    }


    public static class NewsListFragment extends Fragment {

        private final String[] newsHeadlines = {
                "Super Typhoon Julian Recurves Outside PAR",
                "Panguil Bay Bridge Inauguration: A Game-Changer for Mindanao",
                "Manila Court Convicts 10 in Hazing Case",
                "Philippine Stock Market Surges Due to Tech Sector Growth",
                "Comelec Opens Filing for 2025 Elections"
        };

        private final String[] newsContents = {
                "Super Typhoon Julian exited the Philippine Area of Responsibility (PAR), but Signal No. 1 is still raised over parts of Northern Luzon as the typhoon is expected to bring occasional rainfall and winds to the region. While the storm is no longer directly affecting the country, local governments remain on high alert for any possible return of the typhoon due to changes in its trajectory.",
                "President Ferdinand Marcos Jr. officially inaugurated the Panguil Bay Bridge, the longest sea-crossing bridge in Mindanao, spanning 3.17 kilometers. The bridge, connecting Misamis Occidental and Lanao del Norte, reduces travel time from over two hours to just seven minutes. This landmark infrastructure project is expected to boost regional economic development and improve connectivity in Northern Mindanao\u200B.",
                "Ten members of the Aegis Juris fraternity were convicted by a Manila court for the hazing-related death of law student Horacio \"Atio\" Castillo III. The case, which has been closely followed since 2017, saw the accused sentenced to significant prison terms. The verdict is seen as a critical moment in the fight against violent hazing practices in the Philippines.",
                "The Philippine Stock Exchange reached a new high, driven by strong performances in the technology sector. Key players like PayMaya and GCash reported record-breaking profits, propelling the index upward by 4.8%. The growth reflects the increasing digitalization of businesses in the country and the robust consumer demand for fintech services.",
                "The Commission on Elections (Comelec) officially opened the filing of Certificates of Candidacy (COCs) for the upcoming 2025 national and local elections. Aspiring candidates for over 18,000 positions, including senators, party-list representatives, governors, and councilors, have until October 8 to submit their documents."
        };

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Log.d("NewsListFragment", "onCreateView called");
            View view = inflater.inflate(R.layout.fragment_headlines, container, false);
            ListView listView = view.findViewById(R.id.newsListView);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    newsHeadlines
            );
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("NewsListFragment", "Item clicked: " + newsHeadlines[position]);
                    MainActivity activity = (MainActivity) requireActivity();
                    String selectedHeadline = newsHeadlines[position];
                    String selectedContent = newsContents[position];

                    if (activity.isLandscape) {
                        Log.d("NewsListFragment", "Landscape mode: Loading details for \"" + selectedHeadline + "\"");
                        activity.showDetailsInLandscape(selectedHeadline, selectedContent);
                    } else {
                        Log.d("NewsListFragment", "Portrait mode: Navigating to details for \"" + selectedHeadline + "\"");
                        NewsDetailFragment detailFragment = NewsDetailFragment.newInstance(selectedHeadline, selectedContent);
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, detailFragment)
                                .addToBackStack(null)
                                .commit();
                        Log.d("NewsListFragment", "Replacing fragment in fragment_container");
                    }
                }
            });

            Button closeButton = view.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("NewsListFragment", "Close button clicked");
                    requireActivity().finish();  // Close the app by finishing the activity
                }
            });

            return view;
        }
    }

    public static class NewsDetailFragment extends Fragment {

        private static final String ARG_HEADLINE = "headline";
        private static final String ARG_CONTENT = "content";
        private String headline;
        private String content;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                headline = getArguments().getString(ARG_HEADLINE);
                content = getArguments().getString(ARG_CONTENT);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Log.d("NewsDetailFragment", "onCreateView called");
            View view = inflater.inflate(R.layout.fragment_contents, container, false);

            TextView detailTextView = view.findViewById(R.id.newsDetailTextView);
            detailTextView.setText(headline);

            TextView contentTextView = view.findViewById(R.id.newsContentTextView);
            contentTextView.setText(content);

            Button backButton = view.findViewById(R.id.backButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) requireActivity();
                    if (activity.isLandscape) {
                        // In landscape mode, reset the content fragment to default text
                        detailTextView.setText("Select a headline");
                        contentTextView.setText("Details will appear here");
                    } else {
                        // In portrait mode, navigate back to the previous fragment
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                }
            });

            return view;
        }

        public static NewsDetailFragment newInstance(String headline, String content) {
            NewsDetailFragment fragment = new NewsDetailFragment();
            Bundle args = new Bundle();
            args.putString(ARG_HEADLINE, headline);
            args.putString(ARG_CONTENT, content);
            fragment.setArguments(args);
            return fragment;
        }
    }
}