package org.umi.boot.commons.exception;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.umi.boot.commons.info.enums.ShowType;

class InternalServerErrorExceptionTest {

    private String errorMessage = "COMMONS_TEST_ERROR";

    private String errorCode = "T500";

    private ShowType showType = ShowType.NOTIFICATION;

    private String traceId = IdUtil.randomUUID();

    private String host = "locahost";

    @Test
    void test1() {
        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            throw new InternalServerErrorException(errorMessage);
        });
        Assertions.assertEquals(exception.getMessage(), errorMessage);
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
        Assertions.assertEquals(exception.getShowType(), ShowType.ERROR_MESSAGE);
        Assertions.assertNull(exception.getTraceId());
        Assertions.assertNull(exception.getHost());
    }

    @Test
    void test2() {
        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            throw new InternalServerErrorException(errorMessage, errorCode);
        });
        Assertions.assertEquals(exception.getMessage(), errorMessage);
        Assertions.assertEquals(exception.getErrorCode(), errorCode);
        Assertions.assertEquals(exception.getShowType(), ShowType.ERROR_MESSAGE);
        Assertions.assertNull(exception.getTraceId());
        Assertions.assertNull(exception.getHost());
    }

    @Test
    void test3() {
        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            throw new InternalServerErrorException(errorMessage, errorCode, showType);
        });
        Assertions.assertEquals(exception.getMessage(), errorMessage);
        Assertions.assertEquals(exception.getErrorCode(), errorCode);
        Assertions.assertEquals(exception.getShowType(), showType);
        Assertions.assertNull(exception.getTraceId());
        Assertions.assertNull(exception.getHost());
    }

    @Test
    void test4() {
        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            throw new InternalServerErrorException(errorMessage, traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), errorMessage);
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
        Assertions.assertEquals(exception.getShowType(), ShowType.ERROR_MESSAGE);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }

    @Test
    void test5() {
        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            throw new InternalServerErrorException(errorMessage, errorCode, traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), errorMessage);
        Assertions.assertEquals(exception.getErrorCode(), errorCode);
        Assertions.assertEquals(exception.getShowType(), ShowType.ERROR_MESSAGE);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }

    @Test
    void test6() {
        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            throw new InternalServerErrorException(errorMessage, showType, traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), errorMessage);
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
        Assertions.assertEquals(exception.getShowType(), showType);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }

    @Test
    void test7() {
        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            throw new InternalServerErrorException(errorMessage, errorCode, showType, traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), errorMessage);
        Assertions.assertEquals(exception.getErrorCode(), errorCode);
        Assertions.assertEquals(exception.getShowType(), showType);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }
}
