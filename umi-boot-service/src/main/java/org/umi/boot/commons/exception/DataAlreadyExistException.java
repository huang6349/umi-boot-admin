package org.umi.boot.commons.exception;

import org.springframework.http.HttpStatus;
import org.umi.boot.commons.info.enums.ShowType;

public class DataAlreadyExistException extends InternalServerErrorException {

    private static final long serialVersionUID = 3703844674111252656L;

    private static final String errorCode = HttpStatus.CONFLICT.toString();

    private static final String message = "该数据已存在，请勿重复添加";

    private static final ShowType showType = ShowType.WARN_MESSAGE;

    public DataAlreadyExistException() {
        super(message, errorCode, showType);
    }

    public DataAlreadyExistException(String message) {
        super(message, errorCode, showType);
    }

    public DataAlreadyExistException(ShowType showType) {
        super(message, errorCode, showType);
    }

    public DataAlreadyExistException(String message, ShowType showType) {
        super(message, errorCode, showType);
    }

    public DataAlreadyExistException(String traceId, String host) {
        super(message, errorCode, showType, traceId, host);
    }

    public DataAlreadyExistException(String message, String traceId, String host) {
        super(message, errorCode, showType, traceId, host);
    }

    public DataAlreadyExistException(ShowType showType, String traceId, String host) {
        super(message, errorCode, showType, traceId, host);
    }

    public DataAlreadyExistException(String message, ShowType showType, String traceId, String host) {
        super(message, errorCode, showType, traceId, host);
    }
}
