package se.plushogskolan.casemanagement.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 620803407470045678L;

    public ServiceException(String message, Exception exception) {
        super(message, exception);
    }

    public ServiceException(String message) {
        super(message);
    }
}