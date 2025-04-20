package br.com.fiap.gestaoatendimento.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import br.com.fiap.gestaoatendimento.model.UnidadeAtendimentoModel;
import br.com.fiap.gestaoatendimento.repository.UnidadeAtendimentoRepository;

@AutoConfigureTestDatabase
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UnidadeAtendimentoServiceImplIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private UnidadeAtendimentoService service;
	
    @Autowired
    private UnidadeAtendimentoRepository repository;	
	
	@Test
	void devePermitirListarUnidadesDeAtendimento() {
		// Arrange
		repository.save(new UnidadeAtendimentoModel("UBS teste",
				"Rua XPTO, 220",
				"Barueri",
				"São Paulo"));
		repository.save(new UnidadeAtendimentoModel("UBS teste 2",
				"Rua ABC, 220",
				"Cotia",
				"São Paulo"));
		
		// Act
		List<UnidadeAtendimentoModel> listaUnidades = service.listarTodas();
		
		// Assert
		assertThat(listaUnidades)
	    .isNotEmpty()
	    .hasSizeGreaterThan(0)
	    .allSatisfy(unidade -> assertThat(unidade).isNotNull());
	}
	
	@Test
	void devePermitirSalvarUmaUnidadeDeAtendimento() {
		// Arrange
		UnidadeAtendimentoModel unidade = gerarUmaUnidadeAtendimento();
        
		// Act
		UnidadeAtendimentoModel response = service.salvar(unidade);
		
		// Assert
		assertThat(response).isInstanceOf(UnidadeAtendimentoModel.class).isNotNull();
		assertThat(response.getId()).isNotNull();
		assertThat(response.getNome()).isEqualTo(unidade.getNome());
		assertThat(response.getEndereco()).isEqualTo(unidade.getEndereco());
		assertThat(response.getEstado()).isEqualTo(unidade.getEstado());
		assertThat(response.getCidade()).isEqualTo(unidade.getCidade());
	}
	
	@Test
	void devePermitirBuscarUnidadeDeAtendimentoPorId() {
		// Arrange
		UnidadeAtendimentoModel unidade = gerarUmaUnidadeAtendimento();
		repository.save(unidade);

		// Act
		UnidadeAtendimentoModel response = service.buscarPorId(unidade.getId());
		
		// Assert
		assertThat(response).isInstanceOf(UnidadeAtendimentoModel.class).isNotNull();
		assertThat(response.getId()).isNotNull();
		assertThat(response.getNome()).isEqualTo(unidade.getNome());
		assertThat(response.getEndereco()).isEqualTo(unidade.getEndereco());
		assertThat(response.getEstado()).isEqualTo(unidade.getEstado());
		assertThat(response.getCidade()).isEqualTo(unidade.getCidade());
	}
	
	@Test
	void devePermitirExcluirUmaUnidadeDeAtendimento() {
		// Arrange
		UnidadeAtendimentoModel unidade = gerarUmaUnidadeAtendimento();
		repository.save(unidade);

		// Act
		service.excluir(unidade.getId());
	}

	@Test
	void devePermitirAtualizarUmaUnidadeDeAtendimento() {
		// Arrange
		UnidadeAtendimentoModel unidadeSemModificacao = gerarUmaUnidadeAtendimento();
		UnidadeAtendimentoModel unidadeModificado = repository.save(gerarUmaUnidadeAtendimento());
		unidadeModificado.setNome("UBS 3 Marias");
		Long id = unidadeModificado.getId();
		
		// Act
		UnidadeAtendimentoModel unidadeObtida = service.atualizar(id, unidadeModificado);
		
		// Assert
		assertThat(unidadeObtida).isInstanceOf(UnidadeAtendimentoModel.class).isNotNull();
		assertThat(unidadeObtida.getId()).isEqualTo(id);
		assertThat(unidadeObtida.getNome()).isNotEqualTo(unidadeSemModificacao.getNome());
		assertThat(unidadeObtida.getEndereco()).isEqualTo(unidadeSemModificacao.getEndereco());
		assertThat(unidadeObtida.getCidade()).isEqualTo(unidadeSemModificacao.getCidade());
		assertThat(unidadeObtida.getEstado()).isEqualTo(unidadeSemModificacao.getEstado());
	}
	
	private UnidadeAtendimentoModel gerarUmaUnidadeAtendimento() {
		return new UnidadeAtendimentoModel(
				"UBS teste",
				"Rua XPTO, 220",
				"Cotia",
				"São Paulo");
	}
}
