package org.umi.boot.commons.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.umi.boot.commons.info.enums.ShowType;

@Getter
@Setter
public class InternalServerErrorException extends RuntimeException {

    private static final long serialVersionUID = 5899807313916831250L;

    private String errorCode = HttpStatus.INTERNAL_SERVER_ERROR.toString();

    private ShowType showType = ShowType.ERROR_MESSAGE;

    private String traceId;

    private String host;

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public InternalServerErrorException(String message, String errorCode, ShowType showType) {
        super(message);
        this.errorCode = errorCode;
        this.showType = showType;
    }

    public InternalServerErrorException(String message, String traceId, String host) {
        super(message);
        this.traceId = traceId;
        this.host = host;
    }

    public InternalServerErrorException(String message, String errorCode, String traceId, String host) {
        super(message);
        this.errorCode = errorCode;
        this.traceId = traceId;
        this.host = host;
    }

    public InternalServerErrorException(String message, ShowType showType, String traceId, String host) {
        super(message);
        this.showType = showType;
        this.traceId = traceId;
        this.host = host;
    }

    public InternalServerErrorException(String message, String errorCode, ShowType showType, String traceId, String host) {
        super(message);
        this.errorCode = errorCode;
        this.showType = showType;
        this.traceId = traceId;
        this.host = host;
    }
}
