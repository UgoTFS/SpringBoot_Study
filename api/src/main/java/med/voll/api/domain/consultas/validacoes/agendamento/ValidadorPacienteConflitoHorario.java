package med.voll.api.domain.consultas.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;

@Component
public class ValidadorPacienteConflitoHorario implements ValidadorAgendamentoConsultas{
  
  @Autowired
  private ConsultaRepository consultaRepository;

  public void validar(DadosAgendamentoConsulta dados){
    var primeiroHorario = dados.data().withHour(7);
    var ultimoHorario = dados.data().withHour(18);
    var conflito = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
    if(conflito){
      throw new ValidacaoException("Este paciente já possui um atendimento neste dia");
    }
  }
}
