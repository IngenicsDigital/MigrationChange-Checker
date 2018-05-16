# MigrationChange-Checker 

<table>
  <tbody>
    <tr>
      <td align="center">
        <a href="https://travis-ci.org/PixelGmbH/MigrationChange-Checker"><img src="https://img.shields.io/travis/PixelGmbH/MigrationChange-Checker/master.svg?maxAge=3600"></a>
      </td>
      <td align="center">
        <a href="https://codeclimate.com/github/PixelGmbH/MigrationChange-Checker"><img src="https://img.shields.io/codeclimate/maintainability/PixelGmbH/MigrationChange-Checker.svg?maxAge=3600"></a>
      </td>
      <td align="center">
        <a href="https://codeclimate.com/github/PixelGmbH/MigrationChange-Checker"><img src="https://img.shields.io/codeclimate/coverage/PixelGmbH/MigrationChange-Checker.svg?maxAge=3600"></a>
      </td>
      <td align="center">
        <a href="https://bintray.com/PixelGmbH/maven/MigrationChange-Checker"><img src="https://img.shields.io/bintray/v/PixelGmbH/maven/MigrationChange-Checker.svg?maxAge=3600"></a>
      </td>
    </tr>
  </tbody>
</table>

Database migration scripts break a deployment in no time, that is common knowledge.
Changed by accident or through the IDE the verification at application startup stops it.

The MigrationChange-Checker(MCC) provides a possible solution for that issue. 
It will make tests fail early instead of breaking the deployment.

## Installation

MCC is available via jCenter.

To use it with gradle add following into the build.gradle:

```groovy
repositories {
    jcenter()
}

dependencies {
    //TODO
}

```

To use it in a maven project add this in the pom.xml:

```xml
</project>
    ...
    <repositories>
        <repository>
          <id>jcenter</id>
          <url>https://jcenter.bintray.com/</url>
        </repository>
    </repositories>
    
    <dependencies> 
        <dependency>
            <!--TODO-->
        </dependency>
    </dependencies>
</project>
```

## Usage

### General usage

MCC has a fluent way to interact. A basic usage is shown below:

```java
MigrationChangeChecker.setup()
    .withHashAlgorithm(HashAlgorithm.MD5)
    .withHashPair("migration.sql", "thisIsMyPrecalculatedMd5Hash1234")
    .verifyFile(Paths.get("path/to/migration.sql"));
``` 

With the optional `.withHashAlgorithm(...)` the used hash mechanism can be set.
MMC only supports `MD5` and `SHA-2` right now.
If none is set, `SHA-2` is used.

The ``.withHashPair(<filename>, <hash>)`` adds a new hash to the internal collection.
To calculated the hash use a tool of your liking or extract the hash is from the log of a failed verification.

To verify a file use`` .verifyFile(<path>)``.
It accepts a ``java.nio.file.Path`` as parameter and try to match a stored the checksum.

### Test example

To use the checker, a parameterized test class is recommended.
The example uses `junit5` but other java compatible testing frameworks work as well. 

The example lives in the test packages: [Example Test](https://github.com/PixelGmbH/MigrationChange-Checker/blob/master/src/test/java/de/pixel/mcc/ExampleTest.java)
