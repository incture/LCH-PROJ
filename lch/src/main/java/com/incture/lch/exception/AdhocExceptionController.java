package com.incture.lch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.incture.lch.dto.ResponseDto;

@RestControllerAdvice
public class AdhocExceptionController
{
	  @ExceptionHandler(value = FilterInvalidEntryException.class)
	   public ResponseDto exception(FilterInvalidEntryException exception) {
		  ResponseDto responseDto=new ResponseDto();
		  responseDto.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
		  responseDto.setCode(HttpStatus.NOT_FOUND.name());
		  responseDto.setMessage("Invalid Filter Value"+ exception.getFilter_feild());
		  
		  return responseDto;
		  
	   }
	  @ExceptionHandler(value = PageNumberNotFoundException.class)
	   public ResponseDto PageNumberException(PageNumberNotFoundException exception) {
		  ResponseDto responseDto=new ResponseDto();
		  responseDto.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
		  responseDto.setCode(HttpStatus.NOT_FOUND.name());
		  responseDto.setMessage("Number of Entries Should be Less than"+ exception.getTotal_entries());
		  
		  return responseDto;
		  
	   }
	   @ExceptionHandler(value = CarrierNotExistException.class)
	   public ResponseDto CarrierNotExistException(CarrierNotExistException exception) {
		  ResponseDto responseDto=new ResponseDto();
		  responseDto.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
		  responseDto.setCode(HttpStatus.NOT_FOUND.name());
		  responseDto.setMessage("Carrier doesnt exist with BPNumber"+ exception.getBpNumber());
		  
		  return responseDto;
		  
	   }
	   
	   @ExceptionHandler(value = SetCarrierDetailsException.class)
	   public ResponseDto SetCarrierDetailsException(SetCarrierDetailsException exception) {
		  ResponseDto responseDto=new ResponseDto();
		  responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
		  responseDto.setCode(HttpStatus.NOT_ACCEPTABLE.name());
		  responseDto.setMessage("Cannot add the Carrier details, Try Again");
		  
		  return responseDto;
		  
	   }


}
