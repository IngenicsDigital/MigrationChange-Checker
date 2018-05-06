package de.pixel.mcc.internal;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public enum HashAlgorithm {
    MD5 {
        @Override
        public String getHexHash(final InputStream input) throws IOException {
            return DigestUtils.md2Hex(input);
        }

        @Override
        public Pattern getPattern() {
            return Pattern.compile("[a-fA-F0-9]{32}");
        }
    },

    SHA2_256 {
        @Override
        public String getHexHash(final InputStream input) throws IOException {
            return DigestUtils.sha256Hex(input);
        }

        @Override
        public Pattern getPattern() {
            return Pattern.compile("[a-fA-F0-9]{64}");
        }
    },

    SHA2_512 {
        @Override
        public String getHexHash(final InputStream input) throws IOException {
            return DigestUtils.sha512Hex(input);
        }

        @Override
        public Pattern getPattern() {
            return Pattern.compile("[a-fA-F0-9]{128}");
        }
    };

    public abstract String getHexHash(final InputStream input) throws IOException;

    public abstract Pattern getPattern();
}
