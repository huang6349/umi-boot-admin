package org.umi.boot.commons.exception;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.umi.boot.commons.info.enums.ShowType;

class DataAlreadyExistExceptionTest {

    private String errorMessage = "COMMONS_TEST_ERROR";

    private ShowType showType = ShowType.NOTIFICATION;

    private String traceId = IdUtil.randomUUID();

    private String host = "locahost";

    @Test
    void test1() {
        DataAlreadyExistException exception = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            throw new DataAlreadyExistException();
        });
        Assertions.assertEquals(exception.getMessage(), "该数据已存在，请勿重复添加");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), ShowType.WARN_MESSAGE);
        Assertions.assertNull(exception.getTraceId());
        Assertions.assertNull(exception.getHost());
    }

    @Test
    void test2() {
        DataAlreadyExistException exception = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            throw new DataAlreadyExistException(errorMessage);
        });
        Assertions.assertEquals(exception.getMessage(), errorMessage);
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), ShowType.WARN_MESSAGE);
        Assertions.assertNull(exception.getTraceId());
        Assertions.assertNull(exception.getHost());
    }

    @Test
    void test3() {
        DataAlreadyExistException exception = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            throw new DataAlreadyExistException(showType);
        });
        Assertions.assertEquals(exception.getMessage(), "该数据已存在，请勿重复添加");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), showType);
        Assertions.assertNull(exception.getTraceId());
        Assertions.assertNull(exception.getHost());
    }

    @Test
    void test4() {
        DataAlreadyExistException exception = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            throw new DataAlreadyExistException(errorMessage, showType);
        });
        Assertions.assertEquals(exception.getMessage(), errorMessage);
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), showType);
        Assertions.assertNull(exception.getTraceId());
        Assertions.assertNull(exception.getHost());
    }

    @Test
    void test5() {
        DataAlreadyExistException exception = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            throw new DataAlreadyExistException(traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), "该数据已存在，请勿重复添加");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), ShowType.WARN_MESSAGE);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }

    @Test
    void test6() {
        DataAlreadyExistException exception = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            throw new DataAlreadyExistException(errorMessage, traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), errorMessage);
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), ShowType.WARN_MESSAGE);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }

    @Test
    void test7() {
        DataAlreadyExistException exception = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            throw new DataAlreadyExistException(showType, traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), "该数据已存在，请勿重复添加");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), showType);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }

    @Test
    void test8() {
        DataAlreadyExistException exception = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            throw new DataAlreadyExistException(errorMessage, showType, traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), errorMessage);
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), showType);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }
}
