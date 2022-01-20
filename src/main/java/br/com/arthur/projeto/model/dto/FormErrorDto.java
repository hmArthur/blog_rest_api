package br.com.arthur.projeto.model.dto;

public class FormErrorDto {
	
	private String field;
	private String error;
	
	public FormErrorDto(String field, String error) {
		this.field = field;
		this.error = error;
	}

	public String getCampo() {
		return field;
	}

	public String getErro() {
		return error;
	}
}
