package br.com.fiap.gestaoatendimento.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.gestaoatendimento.model.UnidadeAtendimentoModel;
import br.com.fiap.gestaoatendimento.service.UnidadeAtendimentoService;

class UnidadeAtendimentoControllerTest {

	private MockMvc mockMvc;

	private AutoCloseable openMocks;

	@Mock
	private UnidadeAtendimentoService service;

	private UnidadeAtendimentoController controller;

	@BeforeEach
	void setup() {
		openMocks = MockitoAnnotations.openMocks(this);

		controller = new UnidadeAtendimentoController(service);

		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@AfterEach
	void teardown() throws Exception {
		openMocks.close();
	}

	@Test
	void devePermitirSalvarUmaUnidadeDeAtendimento() throws Exception {
		// Arrange
		UnidadeAtendimentoModel unidadeAtendimento = gerarUmaUnidadeAtendimento();

		when(service.salvar(any(UnidadeAtendimentoModel.class))).thenReturn(unidadeAtendimento);

		// Act & Assert
		mockMvc.perform(
				post("/unidades").contentType(MediaType.APPLICATION_JSON).content(asJsonString(unidadeAtendimento)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome").value("UBS teste"))
				.andExpect(jsonPath("$.endereco").value("Rua XPTO, 220"))
				.andExpect(jsonPath("$.cidade").value("Barueri"))
				.andExpect(jsonPath("$.estado").value("São Paulo"));
	}

	@Test
	void devePermitirListarUnidadesDeAtendimento() throws Exception {
		// Arrange
		List<UnidadeAtendimentoModel> unidades = Arrays.asList(
				new UnidadeAtendimentoModel(
						"UBS teste",
						"Rua XPTO, 220",
						"Barueri",
						"São Paulo"),
				new UnidadeAtendimentoModel(
						"UBS teste 2",
						"Rua ABC, 220",
						"Cotia",
						"São Paulo"));

		when(service.listarTodas()).thenReturn(unidades);

		// Act & Assert
		mockMvc.perform(get("/unidades"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].nome").value("UBS teste"))
				.andExpect(jsonPath("$[1].nome").value("UBS teste 2"))
				.andExpect(jsonPath("$[0].endereco").value("Rua XPTO, 220"))
				.andExpect(jsonPath("$[1].endereco").value("Rua ABC, 220"))
				.andExpect(jsonPath("$[0].cidade").value("Barueri"))
				.andExpect(jsonPath("$[1].cidade").value("Cotia"))
				.andExpect(jsonPath("$[0].estado").value("São Paulo"))
				.andExpect(jsonPath("$[1].estado").value("São Paulo"));
	}

	@Test
	void devePermitirBuscarUnidadeDeAtendimentoPorId() throws Exception {
		// Arrange
		Long id = 1L;
		UnidadeAtendimentoModel response = gerarUmaUnidadeAtendimento();
		when(service.buscarPorId(anyLong())).thenReturn(response);

		// Act & Assert
		mockMvc.perform(
				get("/unidades/{id}", id).contentType(MediaType.APPLICATION_JSON).content(asJsonString(response)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value("UBS teste"))
				.andExpect(jsonPath("$.endereco").value("Rua XPTO, 220"))
				.andExpect(jsonPath("$.cidade").value("Barueri"))
				.andExpect(jsonPath("$.estado").value("São Paulo"));

	}

	@Test
	void devePermitirAtualizarUmaUnidadeDeAtendimento() throws Exception {
		// Arrange
		Long id = 1L;
		UnidadeAtendimentoModel unidadeAtendimento = gerarUmaUnidadeAtendimento();
		unidadeAtendimento.setCidade("Osasco");

		when(service.atualizar(anyLong(), any(UnidadeAtendimentoModel.class))).thenReturn(unidadeAtendimento);

		// Act & Assert
		mockMvc.perform(put("/unidades/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(unidadeAtendimento)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value("UBS teste"))
				.andExpect(jsonPath("$.endereco").value("Rua XPTO, 220"))
				.andExpect(jsonPath("$.cidade").value("Osasco"))
				.andExpect(jsonPath("$.estado").value("São Paulo"));
		;
	}

	@Test
	void devePermitirExcluirUmaUnidadeDeAtendimento() throws Exception {
		// Arrange
		Long id = 1L;
		doNothing().when(service).excluir(id);

		// Act & Assert
		mockMvc.perform(patch("/unidades/{id}", id))
				.andExpect(status().is(405));
	}

	public static String asJsonString(final Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	private UnidadeAtendimentoModel gerarUmaUnidadeAtendimento() {
		return new UnidadeAtendimentoModel(
				"UBS teste",
				"Rua XPTO, 220",
				"Barueri",
				"São Paulo");
	}

}
