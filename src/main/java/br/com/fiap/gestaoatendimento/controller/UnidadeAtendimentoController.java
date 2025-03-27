package br.com.fiap.gestaoatendimento.controller;

import br.com.fiap.gestaoatendimento.model.UnidadeAtendimentoModel;
import br.com.fiap.gestaoatendimento.model.dto.UnidadeAtendimentoRequestDTO;
import br.com.fiap.gestaoatendimento.model.dto.UnidadeAtendimentoResponseDTO;
import br.com.fiap.gestaoatendimento.service.UnidadeAtendimentoService;
import br.com.fiap.gestaoatendimento.utils.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades")
public class UnidadeAtendimentoController {
    private final UnidadeAtendimentoService service;

    public UnidadeAtendimentoController(UnidadeAtendimentoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UnidadeAtendimentoResponseDTO criar(@RequestBody UnidadeAtendimentoRequestDTO unidade) {
        UnidadeAtendimentoModel model = Mapper.mapUnidadeAtendimentoRequestDtoParaUnidadeAtendimentoModel(unidade);
        model = service.salvar(model);
        return Mapper.mapUnidadeAtendimentoModelParaUnidadeAtendimentoResponseDTO(model);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UnidadeAtendimentoResponseDTO> listar() {
        var unidades = service.listarTodas();
        return unidades.stream().map(Mapper::mapUnidadeAtendimentoModelParaUnidadeAtendimentoResponseDTO).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UnidadeAtendimentoResponseDTO buscar(@PathVariable Long id) {
        UnidadeAtendimentoModel model = service.buscarPorId(id);
        return Mapper.mapUnidadeAtendimentoModelParaUnidadeAtendimentoResponseDTO(model);    
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UnidadeAtendimentoResponseDTO atualizar(@PathVariable Long id, @RequestBody UnidadeAtendimentoRequestDTO unidade) {
        UnidadeAtendimentoModel unidadeAtendimentoModel = Mapper.mapUnidadeAtendimentoRequestDtoParaUnidadeAtendimentoModel(unidade);
        UnidadeAtendimentoModel unidadeAtendimentoAtualizada = service.atualizar(id, unidadeAtendimentoModel);
        return Mapper.mapUnidadeAtendimentoModelParaUnidadeAtendimentoResponseDTO(unidadeAtendimentoAtualizada);
    }

}
