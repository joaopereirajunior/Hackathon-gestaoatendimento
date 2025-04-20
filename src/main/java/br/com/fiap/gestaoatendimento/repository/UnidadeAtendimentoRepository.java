package br.com.fiap.gestaoatendimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.gestaoatendimento.model.UnidadeAtendimentoModel;

@Repository
public interface UnidadeAtendimentoRepository extends JpaRepository<UnidadeAtendimentoModel, Long> {

}
