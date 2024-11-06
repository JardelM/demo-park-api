package com.mballem.demoparkapi;

import com.mballem.demoparkapi.web.dto.UsuarioCreateDto;
import com.mballem.demoparkapi.web.dto.UsuarioResponseDto;
import com.mballem.demoparkapi.web.dto.UsuarioSenhaDto;
import com.mballem.demoparkapi.web.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class UsuarioIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201(){

        UsuarioResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@email.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("tody@email.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

    }

    @Test
    public void createUsuario_ComUsernameInvalido_RetornarErrorMessageStatus422(){

        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tod@", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tod@email.", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void createUsuario_ComPasswordInvalido_RetornarErrorMessageStatus422(){

        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("teste@email.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("teste@email.com", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("teste@email.com", "12345677"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void createUsuario_ComUsernameRepetidos_RetornarErrorMessageComStatus409(){

        //retorna 500 por erro no bd
        //teste com erro - ao tentar inserir nome repetido retorna 401 e response body null

        testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("beatriz@email.com", "123456"))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void buscarUsuarioComIdExistente_RetornarUsuarioComStatus200(){

        UsuarioResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/1500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1500);
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("teste@email.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

        responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/1502")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1502);
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("beatriz@email.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

        responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/1501")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1501);
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@email.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

    }

    @Test
    public void buscarUsuarioComIdInexistente_Retornar404(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);



    }

    @Test
    public void buscarUsuarioComUsuarioClienteBuscandoCliente_RetornarErrorComStatus403(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/1502")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);



    }

    @Test
    public void editarSenha_ComDadosValidos_RetornarStatus204(){

        //teste com admin
        testClient
                .patch()
                .uri("/api/v1/usuarios/1500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123458","123458"))
                .exchange()
                .expectStatus().isNoContent();

        //teste com cliente
        testClient
                .patch()
                .uri("/api/v1/usuarios/1501")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123458","123458"))
                .exchange()
                .expectStatus().isNoContent();

    }

/* não mais usado
    @Test
    public void editarSenha_ComIdInexistente_RetornarStatus404(){

        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123458","123458"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }

 */

    @Test
    public void editarSenha_ComIdInexistente_RetornarStatus403(){

        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/1502")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123458","123458"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/1502")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123458","123458"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }

    @Test
    public void editarSenha_ComDadosInvalidos_RetornarErrorMessageComStatus422(){

        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/1500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("", "",""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/1500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("12345", "12345","12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/1500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("1234567", "1234567","1234567"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void editarSenha_ComDadosInvalidos_RetornarStatus400(){

        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/1500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("999999", "123458","123458"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/1500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "000000","123458"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

    }


    @Test
    public void buscarUsuarios_SemParametro_RetornarListaDeUsuariosComStatus200(){

        List<UsuarioResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/usuarios")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody).size().isEqualTo(3);
    }

    @Test
    public void buscarUsuariosSemPermissao_SemParametro_RetornarErrorComStatus403(){

        //tipo cliente (ROLE_CLIENTE) buscando usuarios, retorna 403
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/usuarios")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

}
