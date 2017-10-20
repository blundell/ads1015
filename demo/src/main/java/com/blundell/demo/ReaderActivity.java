package com.blundell.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.widget.TextView;

import com.blundell.adc.Ads1015;
import com.blundell.adc.R;

import java.util.concurrent.TimeUnit;

public class ReaderActivity extends Activity {

    private static final String I2C_BUS = "I2C1";
    private static final int I2C_ADDRESS = 0x48;
    private static final Ads1015.Gain GAIN = Ads1015.Gain.TWO_THIRDS;

    private TextView textInfo;
    private TextView textOutput;

    private Ads1015 ads1015;
    private Ads1015.Channel channel;

    private Handler mainHandler;
    private Handler backgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);

        textInfo = findViewById(R.id.demo_text_info);
        textOutput = findViewById(R.id.demo_text_output);

        channel = getChannel();
        Ads1015.Factory factory = new Ads1015.Factory();
        ads1015 = factory.newAds1015(I2C_BUS, I2C_ADDRESS, GAIN);

        textInfo.setText("Channel " + channel);

        HandlerThread thread = new HandlerThread("BackgroundThread");
        thread.start();
        mainHandler = new Handler(Looper.getMainLooper());
        backgroundHandler = new Handler(thread.getLooper());
        backgroundHandler.post(readAnLoop);
    }

    private final Runnable readAnLoop = new Runnable() {
        @Override
        public void run() {
            final int result = ads1015.read(channel);

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    textOutput.setText("Got: " + result);
                }
            });
            backgroundHandler.postDelayed(readAnLoop, TimeUnit.SECONDS.toMillis(1));
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
