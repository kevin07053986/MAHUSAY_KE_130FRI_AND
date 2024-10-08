package com.mab.bottom_navigation_application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class ProfileFragment extends Fragment implements EditFragment.OnSaveClickListener {

    private TextView textViewName;
    private TextView textViewGender;
    private TextView textViewPreferredLanguages;
    private Button buttonEdit;

    private String name = "My Profile";
    private String gender = "Male";
    private String preferredLanguages = "Java or Kotlin";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewName = view.findViewById(R.id.textName);
        textViewGender = view.findViewById(R.id.textGender);
        textViewPreferredLanguages = view.findViewById(R.id.textPreferredLanguage);
        buttonEdit = view.findViewById(R.id.btnEdit);

        // Set initial values
        updateProfileViews();

        buttonEdit.setOnClickListener(v -> {
            EditFragment editFragment = new EditFragment();
            editFragment.setOnSaveClickListener(ProfileFragment.this); // Set the listener
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, editFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void updateProfileViews() {
        textViewName.setText(name);
        textViewGender.setText(gender);
        textViewPreferredLanguages.setText(preferredLanguages);
    }

    @Override
    public void onSave(String updatedName, String updatedGender, List<String> updatedLanguages) {
        name = updatedName;
        gender = updatedGender;
        preferredLanguages = String.join(", ", updatedLanguages);
        updateProfileViews(); // Update the UI
    }
}