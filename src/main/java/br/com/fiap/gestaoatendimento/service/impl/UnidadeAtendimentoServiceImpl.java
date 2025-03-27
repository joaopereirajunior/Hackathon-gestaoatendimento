package br.com.fiap.gestaoatendimento.service.impl;

import br.com.fiap.gestaoatendimento.model.UnidadeAtendimentoModel;
import br.com.fiap.gestaoatendimento.repository.UnidadeAtendimentoRepository;
import br.com.fiap.gestaoatendimento.service.UnidadeAtendimentoService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UnidadeAtendimentoServiceImpl implements UnidadeAtendimentoService {

    private final UnidadeAtendimentoRepository repository;

    public UnidadeAtendimentoServiceImpl(UnidadeAtendimentoRepository unidadeRepository) {
        this.repository = unidadeRepository;
    }

    public UnidadeAtendimentoModel salvar(UnidadeAtendimentoModel unidade) {
        return repository.save(unidade);
    }

    public UnidadeAtendimentoModel atualizar(Long id, UnidadeAtendimentoModel unidade) {
        UnidadeAtendimentoModel model = buscarPorId(id);
        
        if (unidade == null) {
            throw new EntityNotFoundException();
        }

        model.setNome(unidade.getNome());
        model.setEndereco(unidade.getEndereco());
        model.setCidade(unidade.getCidade());
        model.setEstado(unidade.getEstado());

        return repository.save(model);
    }

    public List<UnidadeAtendimentoModel> listarTodas() {
        return repository.findAll();
    }

    public UnidadeAtendimentoModel buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

}
