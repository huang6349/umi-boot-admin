package org.umi.boot.commons.exception;

import org.springframework.http.HttpStatus;
import org.umi.boot.commons.info.enums.ShowType;

public class DataNotAlreadyIDException extends InternalServerErrorException {

    private static final long serialVersionUID = -5050265227748557450L;

    private static final String errorCode = HttpStatus.CONFLICT.toString();

    private static final String message = "新增的数据不能存在编号";

    private static final ShowType showType = ShowType.WARN_MESSAGE;

    public DataNotAlreadyIDException() {
        super(message, errorCode, showType);
    }

    public DataNotAlreadyIDException(ShowType showType) {
        super(message, errorCode, showType);
    }

    public DataNotAlreadyIDException(String traceId, String host) {
        super(message, errorCode, showType, traceId, host);
    }

    public DataNotAlreadyIDException(ShowType showType, String traceId, String host) {
        super(message, errorCode, showType, traceId, host);
    }
}
