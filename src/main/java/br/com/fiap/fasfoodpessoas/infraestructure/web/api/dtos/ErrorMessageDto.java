package br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos;

public record ErrorMessageDto(String message) {
    public ErrorMessageDto(String message) {
        this.message = message;
    }
}
