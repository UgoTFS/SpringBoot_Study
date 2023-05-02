package med.voll.api.domain.consultas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.validacoes.agendamento.ValidadorAgendamentoConsultas;
import med.voll.api.domain.consultas.validacoes.cancelamento.ValidadorCancelamentoConsultas;
import med.voll.api.domain.medicos.Medico;
import med.voll.api.domain.medicos.MedicoRepository;
import med.voll.api.domain.pacientes.PacienteRepository;

@Service
public class AgendaDeConsultas {

  @Autowired
  private ConsultaRepository consultaRepository;

  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  private PacienteRepository pacienteRepository;

  @Autowired
  private List<ValidadorAgendamentoConsultas> validadores;

  @Autowired
  private List<ValidadorCancelamentoConsultas> validadoresCancelamento;

  public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
    if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
      throw new ValidacaoException("Id do medico informado não existe!");
    }
    if (!pacienteRepository.existsById(dados.idPaciente())) {
      throw new ValidacaoException("Id do paciente informado não existe!");
    }

    validadores.forEach(v -> v.validar(dados));

    var medico = escolherMedico(dados);
    var paciente = pacienteRepository.findById(dados.idPaciente()).get();

    if(medico == null ){
      throw new ValidacaoException("Não existe medico disponível para esta data!");
    }

    var consulta = new Consulta(null, medico, paciente, dados.data(), null);
    consultaRepository.save(consulta);
    return new DadosDetalhamentoConsulta(consulta);
  }

  private Medico escolherMedico(DadosAgendamentoConsulta dados) {
    if (dados.idMedico() != null) {
      return medicoRepository.getReferenceById(dados.idMedico());
    }

    if (dados.especialidade() == null) {
      throw new ValidacaoException("Especialidade é obrigatória quando não for escolhido um médico");
    }
    return medicoRepository.escolherMedicoLivre(dados.especialidade(), dados.data());
  }

  public void cancelar(DadosCancelamentoConsulta dados) {
    if (!consultaRepository.existsById(dados.id())) {
      throw new ValidacaoException("Não existe consulta com este id!");
    }

    validadoresCancelamento.forEach(v -> v.cancelar(dados));

    var consulta = consultaRepository.getReferenceById(dados.id());
    consulta.cancelar(dados.motivo());
  }

}
