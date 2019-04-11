# OnlineShops
This is an implementation of an online shops software product line with a simple text-based UI.
The [feature model](model.xml) has been created with *FeatureIDE*.

## Running
The project has been generated with *Eclipse Java Oxygen* and *FeatureIDE 3.5.4*.
There is also a dependency on *JUnit 4* for the unit tests.

Some example configurations are available in the [configs](configs) folder, and others can be generated with FeatureIDE's product generator.

The used product composition tool is the [Antenna](http://antenna.sourceforge.net/wtkpreprocess.php) proprocessor, which should comment/uncomment the code sorrounded with `// #if` and other such directives when a new configuration is selected.

```java
// #if Feature
System.out.println("Feature selected");
// #else
//@System.out.println("Feature not selected");
// #endif
```

## Tests
Work in progress...

### Unit tests
Work in progress...

Unit tests are located in the [assetTests package](src/assetTests), and should pass on any configuration.
