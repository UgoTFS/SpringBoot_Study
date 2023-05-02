package med.voll.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.consultas.AgendaDeConsultas;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.consultas.DadosDetalhamentoConsulta;
import med.voll.api.domain.medicos.Especialidade;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {

  @Autowired
  private MockMvc mock;

  @Autowired
  private JacksonTester<DadosAgendamentoConsulta> requestTester;

  @Autowired
  private JacksonTester<DadosDetalhamentoConsulta> responseTester;

  @MockBean
  private AgendaDeConsultas mockAgendaDeConsultas;

  @Test
  @DisplayName("Devolve erro http 400 para payload inválido")
  @WithMockUser
  void testAgendarPayloadInválido() throws Exception {
    var response = mock.perform(post("/consultas"))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Devolve sucesso http 200 para payload válido")
  @WithMockUser
  void testAgendarPayloadOk() throws Exception {
    var dataMock = LocalDateTime.now().plusHours(1);
    var especialidade = Especialidade.DERMATOLOGIA;

    var mockDadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(null, 2l, 5l, dataMock);

    when(mockAgendaDeConsultas.agendar(any())).thenReturn(mockDadosDetalhamentoConsulta);

    var response = mock.perform(post("/consultas")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestTester.write(new DadosAgendamentoConsulta(2l, 5l, dataMock, especialidade))
            .getJson()))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    var jsonEsperado = responseTester.write(
        new DadosDetalhamentoConsulta(null, 2l, 5l, dataMock)).getJson();

    assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
  }

  @Test
  void testCancelar() {

  }
}
