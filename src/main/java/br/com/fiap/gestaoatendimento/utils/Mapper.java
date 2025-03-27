package br.com.fiap.gestaoatendimento.utils;

import br.com.fiap.gestaoatendimento.model.UnidadeAtendimentoModel;
import br.com.fiap.gestaoatendimento.model.dto.UnidadeAtendimentoRequestDTO;
import br.com.fiap.gestaoatendimento.model.dto.UnidadeAtendimentoResponseDTO;

public class Mapper {
    
    public static UnidadeAtendimentoResponseDTO mapUnidadeAtendimentoModelParaUnidadeAtendimentoResponseDTO(UnidadeAtendimentoModel unidadeAtendimentoModel){

    	return new UnidadeAtendimentoResponseDTO(
            unidadeAtendimentoModel.getId(),
            unidadeAtendimentoModel.getNome(),
            unidadeAtendimentoModel.getEndereco(),
            unidadeAtendimentoModel.getCidade(),
            unidadeAtendimentoModel.getEstado()
        );
    }

    public static UnidadeAtendimentoModel mapUnidadeAtendimentoRequestDtoParaUnidadeAtendimentoModel(UnidadeAtendimentoRequestDTO unidadeAtendimentoRequestDTO){

        return new UnidadeAtendimentoModel(
            unidadeAtendimentoRequestDTO.nome(),
            unidadeAtendimentoRequestDTO.endereco(),
            unidadeAtendimentoRequestDTO.cidade(),
            unidadeAtendimentoRequestDTO.estado()
        );
    }
    
}
