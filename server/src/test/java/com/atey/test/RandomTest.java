package com.atey.test;

import com.atey.service.IOrdersDetailService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
public class RandomTest {
private final IOrdersDetailService ordersDetailService;
    @Test
    public void test() {
        LocalDateTime localDateTime1 = LocalDateTime.now();
        long data1 = localDateTime1.toInstant(ZoneOffset.UTC).toEpochMilli();
        System.out.println(data1);

        LocalDateTime localDateTime2 = LocalDateTime.now().plusMinutes(15);
        long data2 = localDateTime2.toInstant(ZoneOffset.UTC).toEpochMilli();
        System.out.println(data2);
    }

    @Test
    public void test2(){
        ordersDetailService.chartCategory();
    }
}
