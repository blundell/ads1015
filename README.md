# ADS1015 Analog to Digital Converter Driver

This driver supports ADS1015 peripherals using the I2C protocol.

See the [/library](/library) module for the implementation

See the [/demo](/demo) module for a working example

AndroidThings port of https://github.com/adafruit/Adafruit_ADS1X15

Matchign blog post http://blog.blundellapps.co.uk/tut-android-things-using-the-ads1015-analog-to-digital-converter-driver-library/

How to use the driver
---------------------

### Gradle dependency

To use the `ads1015` driver, simply add the line below to your project's `build.gradle`,
where `<version>` matches the last version of the driver available on [jcenter, see here for the version](https://bintray.com/blundell/maven/ads1015).

```
dependencies {
    compile 'com.blundell:driver-ads1015:<latest-version>'
}
```

### Sample usage

You can use the ADS1015 in two ways

As a reader:

```java
Ads1015.Factory factory = new Ads1015.Factory();
ads1015 = factory.newAds1015("I2C1", 0x48, Ads1015.Gain.TWO_THIRDS);
        
int valueInMv = ads1015.read(Ads1015.Channel.ONE)
Log.d("TUT", "Read value " + valueInMv);;


ads1015.close();
```

As a comparator:

```java
Ads1015.Factory factory = new Ads1015.Factory();
ads1015 = factory.newAds1015("I2C1", 0x48, Ads1015.Gain.TWO_THIRDS, "GPIO23");
ads1015.startComparator(Ads1015.Channel.ONE_AND_THREE, 2000, comparatorCallback);

private final Ads1015.ComparatorCallback comparatorCallback = new Ads1015.ComparatorCallback() {
    @Override
    public void onThresholdHit(float valueInMv) {
        Log.d("TUT", "Threshold hit, with value " + valueInMv);
    }
};

ads1015.close();
```

![](/ADS1015-connected.png)

### Demo

There is a demo app that you can run if you want to test the library without writing any code (just remember to wire in your analog sensors).

![](/demo.jpg)

