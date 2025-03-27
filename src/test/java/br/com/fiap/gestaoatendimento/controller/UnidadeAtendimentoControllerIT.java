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
	void devePermitirAtualizarUmaUnidadeDeAtendimento() {

		// Arrange
		UnidadeAtendimentoModel unidadeAtendimento = repository.save(new UnidadeAtendimentoModel("UBS teste",
							"Rua XPTO, 220",
							"Barueri",
							"São Paulo"));
		unidadeAtendimento.setCidade("Cotia");

		// Arrange
		UnidadeAtendimentoRequestDTO request = gerarUmaUnidadeAtendimentoRequestDTO();
		
	 	// Act & Assert
	 	given().filter(new AllureRestAssured())
	 		.contentType(MediaType.APPLICATION_JSON_VALUE).body(request)
	 		.when().put("/unidades/{id}", unidadeAtendimento.getId())
	 		.then().statusCode(HttpStatus.OK.value())
	 		.body(matchesJsonSchemaInClasspath("./schemas/UnidadeAtendimentoSchema.json"));
	}

	@Test
	void devePermitirExcluirUmaUnidadeDeAtendimento() {

		// Arrange
		UnidadeAtendimentoModel unidadeAtendimento = repository.save(new UnidadeAtendimentoModel("UBS teste",
							"Rua XPTO, 220",
							"Barueri",
							"São Paulo"));
		
	 	// Act & Assert
	 	given().filter(new AllureRestAssured())
	 		.contentType(MediaType.APPLICATION_JSON_VALUE)
	 		.when().delete("/unidades/{id}", unidadeAtendimento.getId())
	 		.then().statusCode(HttpStatus.NO_CONTENT.value());
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
	
	@Test
	void devePermitirBuscarUnidadeDeAtendimentoPorId() {
		// Arrange
		UnidadeAtendimentoModel unidadeAtendimento = repository.save(new UnidadeAtendimentoModel("UBS teste",
							"Rua XPTO, 220",
							"Barueri",
							"São Paulo"));

		// Act & Assert
		given().filter(new AllureRestAssured())
	          .contentType(MediaType.APPLICATION_JSON_VALUE)
	          .when().get("/unidades/{id}", unidadeAtendimento.getId())
	          .then().statusCode(HttpStatus.OK.value())
	          .body(matchesJsonSchemaInClasspath("./schemas/UnidadeAtendimentoSchema.json"));
	}
	

	private UnidadeAtendimentoRequestDTO gerarUmaUnidadeAtendimentoRequestDTO() {
		return new UnidadeAtendimentoRequestDTO(
				"UBS teste",
				"Rua XPTO, 220",
				"Barueri",
				"São Paulo");
	}

}
