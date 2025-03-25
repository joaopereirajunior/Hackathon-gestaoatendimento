package br.com.fiap.gestaoatendimento.repository;

import br.com.fiap.gestaoatendimento.domain.UnidadeAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeAtdimentoRepository extends JpaRepository<UnidadeAtendimento, Long> {

}
