package com.blundell.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.blundell.adc.R;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    static final String EXTRA_PIN_SELECT = "DIFF_PIN";
    static final String EXTRA_THRESHOLD = "THRESHOLD";

    private ToggleButton toggleReadCompare;
    private RadioGroup channelSelect;
    private View textCompareThreshold;
    private EditText inputCompareThreshold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toggleReadCompare = findViewById(R.id.main_toggle_read_compare);
        textCompareThreshold = findViewById(R.id.main_text_compare_threshold);
        inputCompareThreshold = findViewById(R.id.main_edittext_compare_threshold);
        channelSelect = findViewById(R.id.differential_radio_pin_select);

        toggleReadCompare.setOnCheckedChangeListener(this);
    }

    public void onStartDemo(View view) {
        boolean compare = toggleReadCompare.isChecked();

        Intent intent = new Intent();
        if (compare) {
            intent.setClass(this, ComparatorActivity.class);
            intent.putExtra(EXTRA_THRESHOLD, getThreshold());
            intent.putExtra(EXTRA_PIN_SELECT, channelSelect.getCheckedRadioButtonId());
            startActivity(intent);
        } else {
            intent.setClass(this, ReaderActivity.class);
            intent.putExtra(EXTRA_PIN_SELECT, channelSelect.getCheckedRadioButtonId());
            startActivity(intent);
        }
    }

    private Long getThreshold() {
        String inputValue = inputCompareThreshold.getText().toString();
        if (inputValue.isEmpty()) {
            return 0L;
        }
        try {
            return Long.valueOf(inputValue);
        } catch (NumberFormatException e) {
            inputCompareThreshold.setText("0");
            Toast.makeText(this, "Invalid Threshold " + inputValue, Toast.LENGTH_SHORT).show();
            return 0L;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        inputCompareThreshold.setVisibility(checked ? View.VISIBLE : View.GONE);
        textCompareThreshold.setVisibility(checked ? View.VISIBLE : View.GONE);
    }
}
