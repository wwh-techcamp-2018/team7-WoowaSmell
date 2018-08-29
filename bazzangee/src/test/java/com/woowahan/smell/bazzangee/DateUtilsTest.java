package com.woowahan.smell.bazzangee;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;

public class DateUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(DateUtilsTest.class);
    @Test
    public void 숫자_앞에_0붙이기() {
//        log.info("{}", Integer.parseInt(DateUtils.addZero(9)));
//        log.info("{}", DateUtils.addZero(9));
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumIntegerDigits(2);
        log.info("{}", numberFormat.format(9));
    }
}
