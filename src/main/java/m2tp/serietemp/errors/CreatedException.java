package m2tp.serietemp.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CREATED)
public class CreatedException extends RuntimeException{
}
