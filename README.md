# ADS1015 Analog to Digital Converter Driver

This driver supports ADS1015 peripherals using the I2C protocol.

See the [/library](/library) module for the implementation

See the [/demo](/demo) module for a working example

AndroidThings port of https://github.com/adafruit/Adafruit_ADS1X15

How to use the driver
---------------------

### Gradle dependency

To use the `ads1015` driver, simply add the line below to your project's `build.gradle`,
where `<version>` matches the last version of the driver available on [jcenter][jcenter].

// TODO NOT RELEASED YET

```
dependencies {
    compile 'com.blundell:driver-ads1015:<version>'
}
```

### Sample usage

You can use the ADS1015 in two ways

As a reader:

```
Ads1015.Factory factory = new Ads1015.Factory();
ads1015 = factory.newAds1015("I2C1", 0x48, Ads1015.Gain.TWO_THIRDS);
        
int valueInMv = ads1015.read(Ads1015.Channel.ONE)
Log.d("TUT", "Read value " + valueInMv);;
```

As a comparator:

```
Ads1015.Factory factory = new Ads1015.Factory();
ads1015 = factory.newAds1015("I2C1", 0x48, Ads1015.Gain.TWO_THIRDS, "GPIO23");
ads1015.startComparator(Ads1015.Channel.ONE_AND_THREE, 2000, comparatorCallback);

private final Ads1015.ComparatorCallback comparatorCallback = new Ads1015.ComparatorCallback() {
    @Override
    public void onThresholdHit(float valueInMv) {
        Log.d("TUT", "Threshold hit, with value " + valueInMv);
    }
};
```
