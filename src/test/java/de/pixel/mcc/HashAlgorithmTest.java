package de.pixel.mcc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class HashAlgorithmTest {

    @ParameterizedTest
    @MethodSource("hashDataProvider")
    void getHexHash(final HashAlgorithm algorithm, final String preComputedHash) throws IOException {
        //WHEN
        final String testContent = "test";
        final InputStream input = new ByteArrayInputStream(testContent.getBytes());

        //THEN
        final String hash = algorithm.getHexHash(input);

        //VERIFY
        assertThat(hash).isEqualToIgnoringCase(preComputedHash);
    }

    @ParameterizedTest
    @MethodSource("patternProvider")
    void getPattern(final HashAlgorithm algorithm, final String expectedPattern) {
        //WHEN

        //THEN
        final Pattern pattern = algorithm.getPattern();

        //VERIFY
        assertThat(pattern.pattern()).isEqualTo(expectedPattern);
    }

    private static Stream<Arguments> hashDataProvider() {
        return Stream.of(
                Arguments.of(HashAlgorithm.MD5, "098f6bcd4621d373cade4e832627b4f6"),
                Arguments.of(HashAlgorithm.SHA2_256, "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"),
                Arguments.of(HashAlgorithm.SHA2_512, "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67" +
                        "b143732c304cc5fa9ad8e6f57f50028a8ff")
        );
    }

    private static Stream<Arguments> patternProvider() {
        return Stream.of(
                Arguments.of(HashAlgorithm.MD5, "[a-fA-F0-9]{32}"),
                Arguments.of(HashAlgorithm.SHA2_256, "[a-fA-F0-9]{64}"),
                Arguments.of(HashAlgorithm.SHA2_512, "[a-fA-F0-9]{128}")
        );
    }
}
