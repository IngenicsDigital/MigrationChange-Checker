package de.pixel.mcc;

import de.pixel.mcc.internal.HashAlgorithm;
import de.pixel.mcc.internal.VerifyException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public final class MigrationChangeChecker {

    private final Map<String, String> persistentHashes;
    private HashAlgorithm algorithmToUse;

    /**
     * Returns an fresh instance of the {@link MigrationChangeChecker}.
     * It has the {@link HashAlgorithm#SHA2_256} hash algorithm set as default.
     *
     * @return An instance of the {@link MigrationChangeChecker};
     */
    public static MigrationChangeChecker setup() {
        return new MigrationChangeChecker();
    }

    private MigrationChangeChecker() {
        this.persistentHashes = new HashMap<>();
        algorithmToUse = HashAlgorithm.SHA2_256;
    }

    /**
     * Adds an HashPair to the stored Hashes.
     * Each pair contains the filename and the corresponding hash string in hex.
     * If a filename is inserted more then one time the latest hash will be used!
     *
     * @param filename  The filename.
     * @param hashValue The precalculated hash.
     * @return The {@link MigrationChangeChecker} instance for chaining.
     */
    public MigrationChangeChecker withHashPair(final String filename, final String hashValue) {
        this.persistentHashes.put(filename, hashValue);
        return this;
    }

    /**
     * Sets the used hash algorithm.
     * If the algorithm is not set the default ({@link HashAlgorithm#SHA2_256}) is used.
     *
     * @param algorithm The algorithm which was used to make the hashes.
     * @return The {@link MigrationChangeChecker} instance for chaining.
     * @see HashAlgorithm
     */
    public MigrationChangeChecker withHashAlgorithm(final HashAlgorithm algorithm) {
        this.algorithmToUse = algorithm;
        return this;
    }

    /**
     * Checks the given file for a matching hash in the stored map.
     * This method will throw an {@link IOException} if anything file related goes wrong.
     *
     * @param path The path of the file to check.
     * @throws IOException If something is wrong with the file.
     */
    public void verifyFile(final Path path) throws IOException {
        final String computedHash = algorithmToUse.getHexHash(Files.newInputStream(path));
        final String storedHash = Optional.ofNullable(persistentHashes.get(path.getFileName().toString())).orElse("");
        final Pattern patternToCheck = algorithmToUse.getPattern();

        try {
            checkHashPattern(storedHash, patternToCheck);
            checkHashMatch(storedHash, computedHash);

        } catch (final VerifyException ex) {
            final String message = String.format("%s\nFile: \t'%s'\nStored hash: \t'%s'\nComputed hash: \t'%s'",
                    ex.getMessage(), path.getFileName().toString(), storedHash, computedHash);
            throw new AssertionError(message, ex);
        }
    }

    private static void checkHashPattern(final String hash, final Pattern pattern) {
        if (!pattern.matcher(hash).matches()) {
            throw new VerifyException(String.format("Hash '%s' does not match the expected pattern '%s'." +
                    " Make sure the hash is correctly inserted!", hash, pattern.pattern()));
        }
    }

    private static void checkHashMatch(final String hash, final String otherHash) {
        if (!hash.equalsIgnoreCase(otherHash)) {
            throw new VerifyException(String.format("Hash mismatch! '%s' != '%s'", hash, otherHash));
        }
    }
}
