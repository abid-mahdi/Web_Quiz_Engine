package engine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class QuizForbiddenException extends RuntimeException {

    public QuizForbiddenException() { super("You do not have permission to delete this quiz."); }

}