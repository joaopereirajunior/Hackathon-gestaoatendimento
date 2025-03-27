package br.com.fiap.gestaoatendimento.model.dto;

public record UnidadeAtendimentoResponseDTO(
        Long id,
        String nome,
        String endereco,
        String cidade,
        String estado) {
}