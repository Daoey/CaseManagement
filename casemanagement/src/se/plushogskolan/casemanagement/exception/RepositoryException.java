package se.plushogskolan.casemanagement.exception;

public class RepositoryException extends Exception {
    private static final long serialVersionUID = 5865255813765639681L;

    public RepositoryException(String message, Exception exception) {
        super(message, exception);
    }
}
