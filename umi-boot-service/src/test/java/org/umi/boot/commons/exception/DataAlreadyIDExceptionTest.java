package org.umi.boot.commons.exception;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.umi.boot.commons.info.enums.ShowType;

class DataAlreadyIDExceptionTest {

    private ShowType showType = ShowType.NOTIFICATION;

    private String traceId = IdUtil.randomUUID();

    private String host = "locahost";

    @Test
    void test1() {
        DataAlreadyIDException exception = Assertions.assertThrows(DataAlreadyIDException.class, () -> {
            throw new DataAlreadyIDException();
        });
        Assertions.assertEquals(exception.getMessage(), "该数据编号已存在，请勿重复添加");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), ShowType.WARN_MESSAGE);
        Assertions.assertNull(exception.getTraceId());
        Assertions.assertNull(exception.getHost());
    }

    @Test
    void test2() {
        DataAlreadyIDException exception = Assertions.assertThrows(DataAlreadyIDException.class, () -> {
            throw new DataAlreadyIDException(showType);
        });
        Assertions.assertEquals(exception.getMessage(), "该数据编号已存在，请勿重复添加");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), showType);
        Assertions.assertNull(exception.getTraceId());
        Assertions.assertNull(exception.getHost());
    }

    @Test
    void test3() {
        DataAlreadyIDException exception = Assertions.assertThrows(DataAlreadyIDException.class, () -> {
            throw new DataAlreadyIDException(traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), "该数据编号已存在，请勿重复添加");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), ShowType.WARN_MESSAGE);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }

    @Test
    void test4() {
        DataAlreadyIDException exception = Assertions.assertThrows(DataAlreadyIDException.class, () -> {
            throw new DataAlreadyIDException(showType, traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), "该数据编号已存在，请勿重复添加");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), showType);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }
}
