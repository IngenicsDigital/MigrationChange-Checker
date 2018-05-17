package de.pixel.mcc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ExampleTest {

    private static final String MIGRATION_SCRIPTS_URL = "src/test/resources";
    private static MigrationChangeChecker changeChecker;

    @BeforeAll
    private static void setUp() {
        changeChecker = MigrationChangeChecker.setup()
                .withHashAlgorithm(HashAlgorithm.MD5)
                .withHashPair("drop-tables.sql", "27ced4797d3267eb7eb64eacad7078f8");
//              ...
    }

    @ParameterizedTest
    @MethodSource("filesProvider")
    void checkMigrations(final Path file) throws IOException {
        changeChecker.verifyFile(file);
    }

    private static Stream filesProvider() throws IOException {
        final Path sqlScriptsFolder = Paths.get(MIGRATION_SCRIPTS_URL);
        return Files.walk(sqlScriptsFolder).filter(file -> Files.isRegularFile(file));
    }
}
