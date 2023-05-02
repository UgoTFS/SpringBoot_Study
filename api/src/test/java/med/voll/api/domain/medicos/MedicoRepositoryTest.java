package med.voll.api.domain.medicos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.consultas.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.pacientes.DadosCadastroPaciente;
import med.voll.api.domain.pacientes.Paciente;

import static org.assertj.core.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {

  @Autowired
  private MedicoRepository repository;

  @Autowired
  private TestEntityManager em;

  @Test
  @DisplayName("Devolve null quando medico cadastrado não está disponível na data")
  void testEscolherMedicoLivreCenario1() {
    var proximaSegunda = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

    var medico = cadastrarMedico("Medico Teste", "medico.teste@voll.med", "123456", Especialidade.CARDIOLOGIA);
    var paciente = cadastrarPaciente("Paciente Teste", "paciente.teste@email.com", "12345678910");
    cadastrarConsulta(medico, paciente, proximaSegunda);

    var medicoLivre = repository.escolherMedicoLivre(Especialidade.CARDIOLOGIA, proximaSegunda);
    assertThat(medicoLivre).isNull();
  }

  @Test
  @DisplayName("Devolve medico quando medico cadastrado está disponível na data")
  void testEscolherMedicoLivreCenario2() {
    // given ou arrange
    var proximaSegunda = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

    // when ou act
    var medico = cadastrarMedico("Medico Teste", "medico.teste@voll.med", "123456", Especialidade.CARDIOLOGIA);

    var medicoLivre = repository.escolherMedicoLivre(Especialidade.CARDIOLOGIA, proximaSegunda);

    // then ou assert 
    assertThat(medicoLivre).isEqualTo(medico);
  }

  private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
    em.persist(new Consulta(null, medico, paciente, data, null));
  }

  private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
    em.persist(medico);
    return medico;
  }

  private Paciente cadastrarPaciente(String nome, String email, String cpf) {
    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
    em.persist(paciente);
    return paciente;
  }

  private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
    return new DadosCadastroMedico(
        nome,
        email,
        "61999999999",
        crm,
        especialidade,
        dadosEndereco());
  }

  private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
    return new DadosCadastroPaciente(
        nome,
        email,
        "61999999999",
        cpf,
        dadosEndereco());
  }

  private DadosEndereco dadosEndereco() {
    return new DadosEndereco(
        "rua xpto",
        "bairro",
        "00000000",
        "Brasilia",
        "DF",
        "01234567",
        "fantasia");
  }

}
