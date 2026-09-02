package com.example.task03;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.UUID;

public class Task03MainTest {

    private void check(String file, SampleData expected) throws IOException, ClassNotFoundException {
        try (InputStream inputStream = new FileInputStream(file)) {
            SampleData result = Task03Main.deserialize(inputStream);
            Assertions.assertThat(result).as("Input: %s", file).isEqualTo(expected);
        }
    }

    @Test
    public void testExample1() throws IOException, ClassNotFoundException {
        check("src/main/resources/example1.bin", new SampleData(
                "f3104e12-0d3f-4b83-adbe-e8980d3309b6",
                234320322,
                new Date(1555012481477L)
        ));
    }

    @Test
    public void testExample2() throws IOException, ClassNotFoundException {
        check("src/main/resources/example2.bin", new SampleData(
                "28af9f14-d232-47c8-b5bc-7b7eeb19f13f",
                234230463,
                new Date(1555012391618L)
        ));
    }

    @Test
    public void test() throws IOException, ClassNotFoundException {
        for (int i = 0; i < 1000; i++) {
            SampleData data = new SampleData(
                    "" + UUID.randomUUID().toString(),
                    (int) System.currentTimeMillis(),
                    new Date()
            );

            byte[] buffer;
            try (
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
            ) {
                oos.writeObject(data);
                buffer = baos.toByteArray();
            }

            try (
                    ByteArrayInputStream bais = new ByteArrayInputStream(buffer)
            ) {
                SampleData result = Task03Main.deserialize(bais);
                Assertions.assertThat(result).isEqualTo(data);
            }
        }
    }
}
