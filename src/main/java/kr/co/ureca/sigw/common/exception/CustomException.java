package kr.co.ureca.sigw.common.exception;

import kr.co.ureca.sigw.common.enumType.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{
    private final HttpStatus status;
    private final ErrorCode errorCode;
    private final String detail; // 명세화하지 않은 에러일 경우, 발생한 에러에 대한 원인을 전달하기 위한 데이터

    public CustomException(HttpStatus status, ErrorCode errorCode) {
        this.status = status;
        this.errorCode = errorCode;
        this.detail = "";
    }

    public CustomException(HttpStatus status, ErrorCode errorCode, String detail) {
        this.status = status;
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public CustomException( HttpStatus status, ErrorCode errorCode,  Throwable cause) {
        this.status = status;
        this.errorCode = errorCode;
        this.detail = cause.getMessage();
    }

    public CustomException(HttpStatus status, CustomException customException) {
        this.status = status;
        this.errorCode = customException.getErrorCode();
        this.detail = customException.getDetail();
    }

    public CustomException(HttpStatus status, Throwable cause) {
        this.status = status;
        this.errorCode = ErrorCode.UNKNOWN;
        this.detail = cause.getMessage();
    }

    // 예외의 원인만을 받아서 처리하는 생성자
    public CustomException(Exception exception) {
        if (exception.getClass() == CustomException.class) {
            CustomException customException = (CustomException) exception;
            this.status = customException.getStatus();
            this.errorCode = customException.getErrorCode();
            this.detail = customException.getMessage();
        } else {
            this.status = HttpStatus.BAD_REQUEST;
            this.errorCode = ErrorCode.UNKNOWN;
            this.detail = exception.getMessage();
        }
    }
}
