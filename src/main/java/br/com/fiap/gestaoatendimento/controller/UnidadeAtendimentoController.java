package br.com.fiap.gestaoatendimento.controller;

import br.com.fiap.gestaoatendimento.model.UnidadeAtendimentoModel;
import br.com.fiap.gestaoatendimento.model.UsuarioModel;
import br.com.fiap.gestaoatendimento.model.dto.UnidadeAtendimentoRequestDTO;
import br.com.fiap.gestaoatendimento.model.dto.UnidadeAtendimentoResponseDTO;
import br.com.fiap.gestaoatendimento.service.UnidadeAtendimentoService;
import br.com.fiap.gestaoatendimento.utils.JwtUtil;
import br.com.fiap.gestaoatendimento.utils.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades")
public class UnidadeAtendimentoController {
    private final UnidadeAtendimentoService service;
    private final JwtUtil jwtUtil = new JwtUtil();


    public UnidadeAtendimentoController(UnidadeAtendimentoService service) {
        this.service = service;
    }

    // Endpoint para autenticar e gerar token
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioModel user) {
        
        String token = jwtUtil.login(user);
        if(token != null && !token.isEmpty())
        {
            return ResponseEntity.ok(token);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos.");
        }
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
