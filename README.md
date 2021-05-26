# MigrationChange-Checker

<table>
  <tbody>
    <tr>
      <td align="center">
        <a href="https://github.com/PixelGmbH/MigrationChange-Checker/actions">
        <img src="https://img.shields.io/github/workflow/status/PixelGmbH/MigrationChange-Checker/Master%20check"></a>
      </td>
      <td align="center">
        <a href="https://github.com/PixelGmbH/MigrationChange-Checker/releases"><img src="https://img.shields.io/github/release/PixelGmbH/MigrationChange-Checker.svg"></a>
      </td>
      <td align="center">
        <a href="https://github.com/PixelGmbH/MigrationChange-Checker/blob/master/LICENCE"><img src="https://img.shields.io/github/license/PixelGmbH/MigrationChange-Checker.svg?maxAge=3600"></a>
      </td>
    </tr>
  </tbody>
</table>

Database migration scripts break a deployment in no time, that is common knowledge. Changed by accident or through the IDE the verification
at application startup stops it.

The MigrationChange-Checker(MCC) provides a possible solution for that issue. It will make tests fail early instead of breaking the
deployment.

## Installation

MCC is available via Maven Central.

[![Latest version](https://img.shields.io/maven-central/v/de.pixel.mcc/MigrationChange-Checker)](https://search.maven.org/artifact/de.pixel.mcc/MigrationChange-Checker)

To use it with gradle add following into the build.gradle:

```groovy
repositories {
  mavenCentral()
}

dependencies {
  testCompile 'de.pixel.mcc:MigrationChange-Checker:<version>'
}

```

To use it in a maven project add this in the pom.xml:

```xml
<project>
    ...

    <dependencies>
        <dependency>
            <groupId>de.pixel.mcc</groupId>
            <artifactId>MigrationChange-Checker</artifactId>
            <version><!--version--></version>
            <type>pom</type>
            <scope>test</scope>
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

With the optional `.withHashAlgorithm(...)` the used hash mechanism can be set. MMC supports `MD5`,`SHA-256` and `SHA-512` right now. If
none is set, `SHA-256` is used.

The ``.withHashPair(<filename>, <hash>)`` adds a new hash to the internal collection. To calculate the hash use a tool of your liking or
extract the hash from the log of a failed verification.

To verify a file use `` .verifyFile(<path>)``. It accepts a ``java.nio.file.Path`` as parameter and try to match a stored checksum.

### Test example

To use the checker, a parameterized test class is recommended. The example uses `junit5` but other java compatible testing frameworks work
as well.

The example lives in the test
packages: [Example Test](https://github.com/PixelGmbH/MigrationChange-Checker/blob/master/src/test/java/de/pixel/mcc/ExampleTest.java)
