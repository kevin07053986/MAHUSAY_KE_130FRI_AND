package com.mab.bottom_navigation_application.ui.ToDoList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mab.bottom_navigation_application.R;

import java.util.ArrayList;
import java.util.List;


public class ToDoListFragment extends Fragment {

    private EditText edtSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        edtSearch = view.findViewById(R.id.edtSearch);

        List<ToDoListItem> itemList = new ArrayList<>();
        itemList.add(new ToDoListItem(R.drawable.img1, "Rodrigo Roa Duterte", true));
        itemList.add(new ToDoListItem(R.drawable.img1, "Benigno Simeon Aquino III", false));
        itemList.add(new ToDoListItem(R.drawable.img1, "Maria Gloria Macaraeg Macapagal-Arroyo", false));
        itemList.add(new ToDoListItem(R.drawable.img1, "Joseph Ejercito Estrada", false));
        itemList.add(new ToDoListItem(R.drawable.img1, "Fidel Valdez Ramos", false));
        itemList.add(new ToDoListItem(R.drawable.img1, "Maria Corazon Sumulong Cojuangco-Aquino", false));

        // Find the ListView and set the adapter
        ListView listView = view.findViewById(R.id.toDoListView);
        ToDoList_Adapter adapter = new ToDoList_Adapter(requireContext(), itemList);
        listView.setAdapter(adapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // No action needed here
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
        });

        return view;
    }
}