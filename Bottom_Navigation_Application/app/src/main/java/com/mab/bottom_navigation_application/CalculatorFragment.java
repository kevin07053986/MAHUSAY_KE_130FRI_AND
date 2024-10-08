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
import java.text.DecimalFormat;
public class CalculatorFragment extends Fragment {

    private TextView resultTextView;
    private String currentOperator;
    private double firstNumber = Double.NaN;
    private double secondNumber;
    private boolean isFirstNumber = true;
    private boolean operatorPressed = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        resultTextView = view.findViewById(R.id.resultTextView);

        setNumberButtonListeners(view);
        setOperationButtonListeners(view);
        setClearButtonListener(view);
    }

    private void setNumberButtonListeners(View view) {
        int[] numberIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonDot
        };

        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                String buttonText = clickedButton.getText().toString();
                if (operatorPressed) {
                    resultTextView.setText("");  // Clear the result only when entering the second number
                    operatorPressed = false;  // Reset the flag
                }
                appendToResult(buttonText);
            }
        };

        for (int id : numberIds) {
            view.findViewById(id).setOnClickListener(numberClickListener);
        }
    }

    private void appendToResult(String value) {
        String currentText = resultTextView.getText().toString();
        if (currentText.equals("0")) {
            resultTextView.setText(value);
        } else {
            resultTextView.append(value);
        }
    }

    private void setOperationButtonListeners(View view) {
        view.findViewById(R.id.buttonAdd).setOnClickListener(operationClickListener);
        view.findViewById(R.id.buttonSubtract).setOnClickListener(operationClickListener);
        view.findViewById(R.id.buttonMultiply).setOnClickListener(operationClickListener);
        view.findViewById(R.id.buttonDivide).setOnClickListener(operationClickListener);
        view.findViewById(R.id.buttonEquals).setOnClickListener(equalsClickListener);
    }

    private final View.OnClickListener operationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button clickedButton = (Button) v;
            currentOperator = clickedButton.getText().toString();

            if (isFirstNumber) {
                firstNumber = Double.parseDouble(resultTextView.getText().toString());
                isFirstNumber = false;
                operatorPressed = true;  // Flag to indicate operator was pressed
            }
        }
    };

    private final View.OnClickListener equalsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!Double.isNaN(firstNumber)) {
                secondNumber = Double.parseDouble(resultTextView.getText().toString());
                double result = performOperation(firstNumber, secondNumber, currentOperator);
                DecimalFormat df = new DecimalFormat("0.00");
                resultTextView.setText(df.format(result));
                isFirstNumber = true;  // Reset for next calculation
                operatorPressed = false;
            }
        }
    };

    private double performOperation(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    resultTextView.setText("Error");
                    return 0;
                }
            default:
                return 0;
        }
    }

    private void setClearButtonListener(View view) {
        view.findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearResult();
                firstNumber = Double.NaN;
                isFirstNumber = true;
            }
        });
    }

    private void clearResult() {
        resultTextView.setText("0");
    }
}