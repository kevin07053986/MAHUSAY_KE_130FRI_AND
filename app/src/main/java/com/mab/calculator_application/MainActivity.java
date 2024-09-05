package com.mab.calculator_application;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private String currentOperator;
    private double firstNumber = Double.NaN;
    private double secondNumber;
    private boolean isFirstNumber = true;
    private boolean operatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);

        setNumberButtonListeners();
        setOperationButtonListeners();
        setClearButtonListener();
    }

    private void setNumberButtonListeners() {
        int[] numberIds = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.buttonDot};
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
            findViewById(id).setOnClickListener(numberClickListener);
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

    private void setOperationButtonListeners() {
        findViewById(R.id.buttonAdd).setOnClickListener(operationClickListener);
        findViewById(R.id.buttonSubtract).setOnClickListener(operationClickListener);
        findViewById(R.id.buttonMultiply).setOnClickListener(operationClickListener);
        findViewById(R.id.buttonDivide).setOnClickListener(operationClickListener);
        findViewById(R.id.buttonEquals).setOnClickListener(equalsClickListener);
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
    private void setClearButtonListener() {
        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
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