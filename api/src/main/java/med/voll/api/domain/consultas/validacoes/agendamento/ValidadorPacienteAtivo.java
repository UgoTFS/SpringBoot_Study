package med.voll.api.domain.consultas.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.pacientes.PacienteRepository;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsultas {

  @Autowired
  private PacienteRepository pacienteRepository;

  public void validar(DadosAgendamentoConsulta dados) {
    var pacienteAtivo = pacienteRepository.findAtivoById(dados.idPaciente());
    if (!pacienteAtivo) {
      throw new ValidacaoException("Este paciente não está ativo");
    }
  }
}
