package com.mab.bottom_navigation_application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class EditFragment extends Fragment {

    private EditText editTextName;
    private RadioGroup radioGroupGender;
    private CheckBox checkBoxKotlin;
    private CheckBox checkBoxJava;
    private Button buttonSave;

    private OnSaveClickListener onSaveClickListener;

    public interface OnSaveClickListener {
        void onSave(String name, String gender, List<String> preferredLanguages);
    }

    public void setOnSaveClickListener(OnSaveClickListener listener) {
        this.onSaveClickListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        radioGroupGender = view.findViewById(R.id.radioGroupGender);
        checkBoxKotlin = view.findViewById(R.id.checkBoxKotlin);
        checkBoxJava = view.findViewById(R.id.checkBoxJava);
        buttonSave = view.findViewById(R.id.buttonSave);

        // Pre-fill the fields with existing data
        editTextName.setText("My Profile");
        radioGroupGender.check(R.id.radioMale); // Assuming the ID for Male radio button is radioMale
        checkBoxKotlin.setChecked(true);
        checkBoxJava.setChecked(false);

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
            String gender;
            if (selectedGenderId != -1) {
                RadioButton selectedRadioButton = view.findViewById(selectedGenderId);
                gender = selectedRadioButton.getText().toString();
            } else {
                gender = "Not specified";
            }

            List<String> preferredLanguages = new ArrayList<>();
            if (checkBoxKotlin.isChecked()) preferredLanguages.add("Kotlin");
            if (checkBoxJava.isChecked()) preferredLanguages.add("Java");

            // Pass the updated data back to ProfileFragment
            if (onSaveClickListener != null) {
                onSaveClickListener.onSave(name, gender, preferredLanguages);
            }

            // Navigate back to ProfileFragment
            getParentFragmentManager().popBackStack();
        });

        return view;
    }
}