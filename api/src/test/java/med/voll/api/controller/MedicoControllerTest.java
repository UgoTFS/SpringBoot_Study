package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medicos.DadosCadastroMedico;
import med.voll.api.domain.medicos.DadosDetalhamentoMedico;
import med.voll.api.domain.medicos.Especialidade;
import med.voll.api.domain.medicos.Medico;
import med.voll.api.domain.medicos.MedicoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MedicoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JacksonTester<DadosCadastroMedico> dadosCadastroRequest;

  @Autowired
  private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoResponse;

  @MockBean
  private MedicoRepository repository;

  @Test
  @DisplayName("Devolve erro http 400 para payload inválido")
  @WithMockUser
  void testCadastrarPayloadInvalido() throws Exception {
    var response = mockMvc
        .perform(post("/medicos"))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Devolve sucesso http 200 para payload válido")
  @WithMockUser
  void testCadastrarPayloadValido() throws Exception {

    var dadosCadastro = new DadosCadastroMedico("Medico Teste", "medico.teste@voll.med", "12345678910", "123456",
        Especialidade.ORTOPEDIA, dadosEndereco());
    when(repository.save(any())).thenReturn(new Medico(dadosCadastro));

    var response = mockMvc
        .perform(post("/medicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dadosCadastroRequest.write(dadosCadastro).getJson()))
        .andReturn().getResponse();

        var dadosDetalhamento = new DadosDetalhamentoMedico(
          null,
          dadosCadastro.nome(),
          dadosCadastro.email(),
          dadosCadastro.crm(),
          dadosCadastro.telefone(),
          dadosCadastro.especialidade(),
          new Endereco(dadosCadastro.endereco())
        );
        var jsonEsperado = dadosDetalhamentoResponse.write(dadosDetalhamento).getJson();

      assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
      assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
  }

  private DadosEndereco dadosEndereco() {
    return new DadosEndereco(
        "rua xpto",
        "bairro",
        "00000000",
        "Brasilia",
        "DF",
        "11111000",
        "fantasia");
  }

  @Test
  void testDetalhar() {

  }

  @Test
  void testExcluir() {

  }

  @Test
  void testListar() {

  }
}
