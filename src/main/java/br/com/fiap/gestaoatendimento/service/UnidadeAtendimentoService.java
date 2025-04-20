package br.com.fiap.gestaoatendimento.service;

import br.com.fiap.gestaoatendimento.model.UnidadeAtendimentoModel;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UnidadeAtendimentoService {

    public UnidadeAtendimentoModel salvar(UnidadeAtendimentoModel unidade);

    public List<UnidadeAtendimentoModel> listarTodas();

    public UnidadeAtendimentoModel buscarPorId(Long id);

    public void excluir(Long id);

    public UnidadeAtendimentoModel atualizar(Long id, UnidadeAtendimentoModel unidade);
}
