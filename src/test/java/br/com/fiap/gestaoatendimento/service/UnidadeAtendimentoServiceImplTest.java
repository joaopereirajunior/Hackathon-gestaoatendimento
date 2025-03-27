package br.com.fiap.gestaoatendimento.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.gestaoatendimento.model.UnidadeAtendimentoModel;
import br.com.fiap.gestaoatendimento.repository.UnidadeAtendimentoRepository;
import br.com.fiap.gestaoatendimento.service.impl.UnidadeAtendimentoServiceImpl;

class UnidadeAtendimentoServiceImplTest {

	@Mock
	private UnidadeAtendimentoRepository repository;

	private UnidadeAtendimentoServiceImpl unidadeServiceImpl;

	private AutoCloseable openMocks;

	@BeforeEach
	void setup() {
		openMocks = MockitoAnnotations.openMocks(this);
		unidadeServiceImpl = new UnidadeAtendimentoServiceImpl(repository);
	}

	@AfterEach
	void teardown() throws Exception {
		openMocks.close();
	}

	@Test
	void devePermitirListarUnidadesDeAtendimento() {
		// Arrange
		List<UnidadeAtendimentoModel> listaUnidadeAtendimento = gerarListaUnidadeAtendimento();
		when(repository.findAll()).thenReturn(listaUnidadeAtendimento);

		// Act
		List<UnidadeAtendimentoModel> listaObtida = unidadeServiceImpl.listarTodas();

		// Assert
		verify(repository, times(1)).findAll();
		assertThat(listaObtida)
				.hasSize(3)
				.allSatisfy(unidade -> assertThat(unidade).isNotNull().isInstanceOf(UnidadeAtendimentoModel.class));
	}

	@Test
	void devePermitirSalvarUmaUnidadeDeAtendimento() {
		// Arrange
		UnidadeAtendimentoModel unidade = gerarUmaUnidadeAtendimento();
		when(repository.save(any(UnidadeAtendimentoModel.class))).thenReturn(unidade);

		// Act
		UnidadeAtendimentoModel response = unidadeServiceImpl.salvar(unidade);

		// Assert
		verify(repository, times(1)).save(any(UnidadeAtendimentoModel.class));
		assertThat(response).isInstanceOf(UnidadeAtendimentoModel.class).isNotNull();
		assertThat(unidade.getNome()).isEqualTo(response.getNome());
		assertThat(unidade.getEndereco()).isEqualTo(response.getEndereco());
		assertThat(unidade.getCidade()).isEqualTo(response.getCidade());
		assertThat(unidade.getEstado()).isEqualTo(response.getEstado());
	}

	@Test
	void devePermitirExcluirUmaUnidadeDeAtendimento() {
		// Arrange:
		Long id = 1L; // ID da unidade a ser excluída
		doNothing().when(repository).deleteById(id); // Configura o comportamento de exclusão

		// Act
		unidadeServiceImpl.excluir(id);

		// Assert
		verify(repository, times(1)).deleteById(id);
	}

	@Test
	void devePermitirAtualizarUmaUnidadeDeAtendimento() {
		// Arrange
		UnidadeAtendimentoModel unidadeSemModificacao = gerarUmaUnidadeAtendimento();
		UnidadeAtendimentoModel unidadeModificada = gerarUmaUnidadeAtendimento();
		unidadeModificada.setNome("UBS 3 Marias");

		when(repository.findById(anyLong())).thenReturn(Optional.of(unidadeSemModificacao));
		when(repository.save(any(UnidadeAtendimentoModel.class))).thenReturn(unidadeModificada);

		// Act
		UnidadeAtendimentoModel unidadeObtida = unidadeServiceImpl.atualizar(1L, unidadeModificada);

		// Assert
		verify(repository, times(1)).findById(anyLong());
		verify(repository, times(1)).save(any(UnidadeAtendimentoModel.class));
		assertThat(unidadeObtida).isInstanceOf(UnidadeAtendimentoModel.class).isNotNull();
		assertThat(unidadeObtida.getId()).isEqualTo(unidadeSemModificacao.getId());
		assertThat(unidadeObtida.getNome()).isEqualTo(unidadeSemModificacao.getNome());
		assertThat(unidadeObtida.getEndereco()).isEqualTo(unidadeSemModificacao.getEndereco());
		assertThat(unidadeObtida.getCidade()).isEqualTo(unidadeSemModificacao.getCidade());
		assertThat(unidadeObtida.getEstado()).isEqualTo(unidadeSemModificacao.getEstado());
	}

	@Test
	void devePermitirBuscarUnidadeDeAtendimentoPorId() {
		// Arrange
		UnidadeAtendimentoModel unidade = gerarUmaUnidadeAtendimento();
		unidade.setId(1L);
		when(repository.findById(anyLong())).thenReturn(Optional.of(unidade));

		// Act
		UnidadeAtendimentoModel unidadeObtida = unidadeServiceImpl.buscarPorId(unidade.getId());

		// Assert
		verify(repository, times(1)).findById(anyLong());
		assertThat(unidadeObtida).isInstanceOf(UnidadeAtendimentoModel.class).isNotNull();
		assertThat(unidadeObtida.getId()).isEqualTo(unidade.getId());
		assertThat(unidadeObtida.getNome()).isEqualTo(unidade.getNome());
		assertThat(unidadeObtida.getEndereco()).isEqualTo(unidade.getEndereco());
		assertThat(unidadeObtida.getEstado()).isEqualTo(unidade.getEstado());
		assertThat(unidadeObtida.getCidade()).isEqualTo(unidade.getCidade());
	}

	private List<UnidadeAtendimentoModel> gerarListaUnidadeAtendimento() {

		return Arrays.asList(
				new UnidadeAtendimentoModel(
						"UBS teste",
						"Rua XPTO, 220",
						"Cotia",
						"São Paulo"),
				new UnidadeAtendimentoModel(
						"UBS teste 2",
						"Rua YX, 220",
						"Barueri",
						"São Paulo"),
				new UnidadeAtendimentoModel(
						"UBS teste 3",
						"Rua ABC, 220",
						"Osasco",
						"São Paulo"));
	}

	private UnidadeAtendimentoModel gerarUmaUnidadeAtendimento() {
		return new UnidadeAtendimentoModel(
				"UBS teste",
				"Rua XPTO, 220",
				"Cotia",
				"São Paulo");
	}

}
