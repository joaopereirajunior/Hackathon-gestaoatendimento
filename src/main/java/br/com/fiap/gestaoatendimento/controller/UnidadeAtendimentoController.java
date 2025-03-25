package br.com.fiap.gestaoatendimento.controller;

import br.com.fiap.gestaoatendimento.domain.UnidadeAtendimento;
import br.com.fiap.gestaoatendimento.service.UnidadeAtendimentoService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UnidadeAtendimento> criar(@RequestBody UnidadeAtendimento unidade) {
        return ResponseEntity.ok(service.salvar(unidade));
    }

    @GetMapping
    public ResponseEntity<List<UnidadeAtendimento>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeAtendimento> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<UnidadeAtendimento> atualizar(@PathVariable Long id, @RequestBody UnidadeAtendimento unidade) {
        unidade.setId(id);
        return ResponseEntity.ok(service.salvar(unidade));
    }

}
