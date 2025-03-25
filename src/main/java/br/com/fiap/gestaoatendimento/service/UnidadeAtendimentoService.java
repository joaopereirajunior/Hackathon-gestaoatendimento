package br.com.fiap.gestaoatendimento.service;

import br.com.fiap.gestaoatendimento.domain.UnidadeAtendimento;
import br.com.fiap.gestaoatendimento.repository.UnidadeAtdimentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeAtendimentoService {

    private final UnidadeAtdimentoRepository repository;

    public UnidadeAtendimentoService(UnidadeAtdimentoRepository unidadeRepository) {
        this.repository = unidadeRepository;
    }
    public UnidadeAtendimento salvar(UnidadeAtendimento unidade) {
        return repository.save(unidade);
    }

    public List<UnidadeAtendimento> listarTodas() {
        return repository.findAll();
    }

    public Optional<UnidadeAtendimento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }


}
