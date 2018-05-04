package io.github.poeschl.lib.migrationchangechecker;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldTest {

    @Test
    public void main() {
        //WHEN
        int a = 1;
        int b = 2;

        //THEN
        int sum = a + b;

        //VERIFY
        assertThat(sum).isEqualTo(3);
    }
}
