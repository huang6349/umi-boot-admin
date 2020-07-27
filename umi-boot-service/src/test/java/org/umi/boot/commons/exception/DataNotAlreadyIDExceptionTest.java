package org.umi.boot.commons.exception;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.umi.boot.commons.info.enums.ShowType;

class DataNotAlreadyIDExceptionTest {

    private ShowType showType = ShowType.NOTIFICATION;

    private String traceId = IdUtil.randomUUID();

    private String host = "locahost";

    @Test
    void test1() {
        DataNotAlreadyIDException exception = Assertions.assertThrows(DataNotAlreadyIDException.class, () -> {
            throw new DataNotAlreadyIDException();
        });
        Assertions.assertEquals(exception.getMessage(), "新增的数据不能存在编号");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), ShowType.WARN_MESSAGE);
        Assertions.assertNull(exception.getTraceId());
        Assertions.assertNull(exception.getHost());
    }

    @Test
    void test2() {
        DataNotAlreadyIDException exception = Assertions.assertThrows(DataNotAlreadyIDException.class, () -> {
            throw new DataNotAlreadyIDException(showType);
        });
        Assertions.assertEquals(exception.getMessage(), "新增的数据不能存在编号");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), showType);
        Assertions.assertNull(exception.getTraceId());
        Assertions.assertNull(exception.getHost());
    }

    @Test
    void test3() {
        DataNotAlreadyIDException exception = Assertions.assertThrows(DataNotAlreadyIDException.class, () -> {
            throw new DataNotAlreadyIDException(traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), "新增的数据不能存在编号");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), ShowType.WARN_MESSAGE);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }

    @Test
    void test4() {
        DataNotAlreadyIDException exception = Assertions.assertThrows(DataNotAlreadyIDException.class, () -> {
            throw new DataNotAlreadyIDException(showType, traceId, host);
        });
        Assertions.assertEquals(exception.getMessage(), "新增的数据不能存在编号");
        Assertions.assertEquals(exception.getErrorCode(), HttpStatus.CONFLICT.toString());
        Assertions.assertEquals(exception.getShowType(), showType);
        Assertions.assertEquals(exception.getTraceId(), traceId);
        Assertions.assertEquals(exception.getHost(), host);
    }
}
