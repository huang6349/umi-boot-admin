package org.umi.boot.commons.exception;

import org.springframework.http.HttpStatus;
import org.umi.boot.commons.info.enums.ShowType;

public class BadRequestException extends InternalServerErrorException {

    private static final long serialVersionUID = -3076250638377926943L;

    private static final String errorCode = HttpStatus.BAD_REQUEST.toString();

    public BadRequestException(String message) {
        super(message, errorCode);
    }

    public BadRequestException(String message, ShowType showType) {
        super(message, errorCode, showType);
    }

    public BadRequestException(String message, String traceId, String host) {
        super(message, errorCode, traceId, host);
    }

    public BadRequestException(String message, ShowType showType, String traceId, String host) {
        super(message, errorCode, showType, traceId, host);
    }
}
