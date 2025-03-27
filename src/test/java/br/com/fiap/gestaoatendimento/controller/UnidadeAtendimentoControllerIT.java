package br.com.fiap.gestaoatendimento.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import br.com.fiap.gestaoatendimento.model.UnidadeAtendimentoModel;
import br.com.fiap.gestaoatendimento.model.dto.UnidadeAtendimentoRequestDTO;
import br.com.fiap.gestaoatendimento.repository.UnidadeAtendimentoRepository;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

@AutoConfigureTestDatabase
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UnidadeAtendimentoControllerIT {

	@LocalServerPort
	private int port;
    
    @Autowired
    private UnidadeAtendimentoRepository repository;

	@BeforeEach
	public void setup() {
	     RestAssured.port = port;
	     RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	void devePermitirSalvarUmaUnidadeDeAtendimento() {
		// Arrange
		UnidadeAtendimentoRequestDTO request = gerarUmaUnidadeAtendimentoRequestDTO();

		// Act & Assert
		given().filter(new AllureRestAssured()).contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.when().post("/unidades")
			.then().statusCode(HttpStatus.CREATED.value())
			.body(matchesJsonSchemaInClasspath("./schemas/UnidadeAtendimentoSchema.json"))
			.body("$", hasKey("nome"))
			.body("$", hasKey("endereco"))
			.body("$", hasKey("cidade"))
			.body("$", hasKey("estado"))
			.body("id", greaterThan(0));
	}

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

		// Act & Assert
		given().filter(new AllureRestAssured())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/unidades")
			.then().statusCode(HttpStatus.OK.value())
			.body(matchesJsonSchemaInClasspath("./schemas/UnidadeAtendimentoSchema.json"));
	}
	
	// @Test
	// void devePermitirListarUmClientePorId() {
	// 	// Arrange
	// 	ClienteEntity clienteEntity =  clienteRepository.save(new ClienteEntity(1L, "João Silva", "355.347.740-70", "joao.silva@email.com",
	// 			"98765-4321", "Rua das Flores, 101", "04814-045", StatusCliente.ATIVO));

	// 	// Act & Assert
	// 	given().filter(new AllureRestAssured())
	//           .contentType(MediaType.APPLICATION_JSON_VALUE)
	//           .when().get("/api/clientes/{id}", clienteEntity.getId())
	//           .then().statusCode(HttpStatus.OK.value())
	//           .body(matchesJsonSchemaInClasspath("./schemas/ClienteSchema.json"));
	// }
	
	// @Test
	// void devePermitirListarClientes() {
	// 	// Arrange
	// 	clienteRepository.save(new ClienteEntity(1L, "João Silva", "355.347.740-70", "joao.silva@email.com", "98765-4321",
	// 			"Rua das Flores, 101", "03070-900", StatusCliente.ATIVO));
	// 	clienteRepository.save(new ClienteEntity(2L, "Maisa Santos", "935.782.990-30", "maria.santos@email.com", "92365-4521",
	// 			"Rua dos Ventos, 23", "08382-135", StatusCliente.ATIVO));

	// 	// Act & Assert
	// 	given().filter(new AllureRestAssured())
	// 		.contentType(MediaType.APPLICATION_JSON_VALUE)
	// 		.when().get("/api/clientes")
	// 		.then().statusCode(HttpStatus.OK.value())
	// 		.body(matchesJsonSchemaInClasspath("./schemas/ClienteSchema.json"));
	// }
	
	// @Test
	// void devePermitirAtualizarUmCliente() {
	// 	// Arrange
	// 	ClienteEntity clienteEntity =  clienteRepository.save(new ClienteEntity(null, "João Silva", "355.347.740-70", "joao.silva@email.com",
	// 			"98765-4321", "Rua das Flores, 101", "04326-000", StatusCliente.ATIVO));
	// 	clienteEntity.setEndereco("Rua do Teste, 123");
	// 	clienteEntity.setEmail("email@teste.com.br");
		
	// 	ClienteRequestDTO clienteAtualizadoRequest = new ClienteRequestDTO(clienteEntity.getNome(), clienteEntity.getDocumento(),
	// 			clienteEntity.getEmail(), clienteEntity.getTelefone(), clienteEntity.getEndereco(), clienteEntity.getCep());

	// 	// Act & Assert
	// 	given().filter(new AllureRestAssured())
	// 		.contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteAtualizadoRequest)
	// 		.when().put("/api/clientes/{id}", clienteEntity.getId())
	// 		.then().statusCode(HttpStatus.OK.value())
	// 		.body(matchesJsonSchemaInClasspath("./schemas/ClienteSchema.json"));
	// }
	
	// @Test
	// void devePermitirDesativarUmCliente() {
	// 	// Arrange
	// 	ClienteEntity clienteEntity =  clienteRepository.save(new ClienteEntity(null, "João Silva", "355.347.740-70", "joao.silva@email.com",
	// 			"98765-4321", "Rua das Flores, 101", "08031-086", StatusCliente.ATIVO));

	// 	// Act & Assert
	// 	given().filter(new AllureRestAssured())
    //     .contentType(MediaType.APPLICATION_JSON_VALUE)
    //     .when().patch("/api/clientes/desativar/{id}", clienteEntity.getId())
    //     .then().statusCode(HttpStatus.OK.value());
	// }
	
	// @Test
	// void devePermitirAtivarUmCliente() {
	// 	// Arrange
	// 	ClienteEntity clienteEntity =  clienteRepository.save(new ClienteEntity(null, "João Silva", "355.347.740-70", "joao.silva@email.com",
	// 			"98765-4321", "Rua das Flores, 101", "03389-060", StatusCliente.DESATIVADO));

	// 	// Act & Assert
	// 	given().filter(new AllureRestAssured())
    //     .contentType(MediaType.APPLICATION_JSON_VALUE)
    //     .when().patch("/api/clientes/ativar/{id}", clienteEntity.getId())
    //     .then().statusCode(HttpStatus.OK.value());
	// }

	private UnidadeAtendimentoRequestDTO gerarUmaUnidadeAtendimentoRequestDTO() {
		return new UnidadeAtendimentoRequestDTO(
				"UBS teste",
				"Rua XPTO, 220",
				"Barueri",
				"São Paulo");
	}

}
