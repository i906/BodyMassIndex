package my.i906.bmi;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    protected TextView mBmiResultView;
    protected TextView mBmiMessageView;
    protected EditText mHeightInputView;
    protected EditText mWeightInputView;
    protected TextView mHeightUnitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBmiResultView = (TextView) findViewById(R.id.tv_results_bmi);
        mBmiMessageView = (TextView) findViewById(R.id.tv_results_text);
        mHeightInputView = (EditText) findViewById(R.id.et_height);
        mWeightInputView = (EditText) findViewById(R.id.et_weight);
        mHeightUnitView = (TextView) findViewById(R.id.tv_height_unit);

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performCalculations();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        mHeightInputView.addTextChangedListener(tw);
        mWeightInputView.addTextChangedListener(tw);
    }

    protected void performCalculations() {
        try {

            double height = Double.valueOf(mHeightInputView.getText().toString());

            if (height > 3) {
                height /= 100;
                mHeightUnitView.setText(R.string.unit_centimeter);
            } else {
                mHeightUnitView.setText(R.string.unit_meter);
            }

            double weight = Double.valueOf(mWeightInputView.getText().toString());
            double bmi = calculateBmi(weight, height);

            if (bmi < 15) {
                mBmiResultView.setText("< 15");
            } else if (bmi > 40) {
                mBmiResultView.setText("> 40");
            } else {
                mBmiResultView.setText(String.format("%.1f", bmi));
            }

            if (bmi < 15) {
                mBmiMessageView.setText(R.string.label_underweight_2);
            } else if (bmi >= 15 && bmi < 16) {
                mBmiMessageView.setText(R.string.label_underweight_1);
            } else if (bmi >= 16 && bmi < 18.5) {
                mBmiMessageView.setText(R.string.label_underweight_0);
            } else if (bmi >= 18.5 && bmi < 25) {
                mBmiMessageView.setText(R.string.label_normal);
            } else if (bmi >= 25 && bmi < 30) {
                mBmiMessageView.setText(R.string.label_overweight);
            } else if (bmi >= 30 && bmi < 35) {
                mBmiMessageView.setText(R.string.label_obese_0);
            } else if (bmi >= 35 && bmi < 40) {
                mBmiMessageView.setText(R.string.label_obese_1);
            } else {
                mBmiMessageView.setText(R.string.label_obese_2);
            }
        } catch (NumberFormatException nfe) {
            mBmiResultView.setText("0.0");
            mBmiMessageView.setText(R.string.label_enter_weight_height);
        }
    }

    protected double calculateBmi(double weightInKg, double heightInMeter) {
        return weightInKg / (heightInMeter * heightInMeter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
