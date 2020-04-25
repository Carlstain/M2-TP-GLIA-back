package m2tp.serietemp.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NO_CONTENT, reason = "The resource is empty")
public class NoContentException extends RuntimeException{
}
