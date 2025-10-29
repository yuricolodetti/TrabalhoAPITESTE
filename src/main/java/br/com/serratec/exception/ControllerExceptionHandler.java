package br.com.serratec.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> erros = new ArrayList<>();

		for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
			erros.add(erro.getField() + ":" + erro.getDefaultMessage());
		}

		ErrorResposta erroResposta = new ErrorResposta(status.value(), "Existem campos inválidos", LocalDateTime.now(),
				erros);

		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());

		ErrorResposta erroResposta = new ErrorResposta(status.value(), "Existem campos inválidos", LocalDateTime.now(),
				erros);

		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}

	@ExceptionHandler(UsuarioException.class)
	protected ResponseEntity<Object> handleUsuarioException(UsuarioException ex) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErrorResposta erroResposta = new ErrorResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Existem campos inválidos", LocalDateTime.now(), erros);

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erroResposta);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	protected ResponseEntity<Object>handleHttpClientErrorException(HttpClientErrorException ex){
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErrorResposta erroResposta = new ErrorResposta(HttpStatus.NOT_FOUND.value(),
				"Cep não encontrado", LocalDateTime.now(), erros);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResposta);
		
	}
}