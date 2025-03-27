package br.com.fiap.gestaoatendimento.model.dto;

public record UnidadeAtendimentoRequestDTO(
        String nome,
        String endereco,
        String cidade,
        String estado) {
}