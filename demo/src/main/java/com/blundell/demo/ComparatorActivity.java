package com.blundell.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.blundell.adc.Ads1015;
import com.blundell.adc.R;

public class ComparatorActivity extends Activity {

    private static final String I2C_BUS = "I2C1";
    private static final int I2C_ADDRESS = 0x48;
    private static final Ads1015.Gain GAIN = Ads1015.Gain.TWO_THIRDS;
    private static final String ALERT_GPIO_PIN = "GP0128";

    private TextView textInfo;
    private TextView textOutput;

    private Ads1015 ads1015;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);

        textInfo = findViewById(R.id.demo_text_info);
        textOutput = findViewById(R.id.demo_text_output);

        int threshold = getIntent().getIntExtra(MainActivity.EXTRA_THRESHOLD, 0);

        Ads1015.Channel channel = getChannel();
        Ads1015.Factory factory = new Ads1015.Factory();
        ads1015 = factory.newAds1015(I2C_BUS, I2C_ADDRESS, GAIN, ALERT_GPIO_PIN);
        ads1015.startComparator(channel, threshold, comparatorCallback);

        textInfo.setText("Channel " + channel + " \n" + "Threshold " + threshold);
        textOutput.setText("Waiting for threshold to be hit..");
    }

    private final Ads1015.ComparatorCallback comparatorCallback = new Ads1015.ComparatorCallback() {
        @Override
        public void onThresholdHit(float valueInMv) {
            Log.d("TUT", "Threshold hit, with value " + valueInMv);

            textOutput.setText("Threshold hit " + valueInMv);
        }
    };

    private Ads1015.Channel getChannel() {
        int id = getIntent().getIntExtra(MainActivity.EXTRA_PIN_SELECT, 0);
        switch (id) {
            case R.id.diff_pins_0:
                return Ads1015.Channel.ZERO;
            case R.id.diff_pins_1:
                return Ads1015.Channel.ONE;
            case R.id.diff_pins_2:
                return Ads1015.Channel.TWO;
            case R.id.diff_pins_3:
                return Ads1015.Channel.THREE;
            case R.id.diff_pins_0_1:
                return Ads1015.Channel.ZERO_AND_ONE;
            case R.id.diff_pins_0_3:
                return Ads1015.Channel.ZERO_AND_THREE;
            case R.id.diff_pins_1_3:
                return Ads1015.Channel.ONE_AND_THREE;
            case R.id.diff_pins_2_3:
                return Ads1015.Channel.TWO_AND_THREE;
            default:
                throw new IllegalStateException("unknown " + id);
        }
    }

    @Override
    protected void onDestroy() {
        ads1015.close();
        super.onDestroy();
    }
}
