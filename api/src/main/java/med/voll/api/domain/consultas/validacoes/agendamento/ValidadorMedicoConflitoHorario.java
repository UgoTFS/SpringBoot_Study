package med.voll.api.domain.consultas.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;

@Component
public class ValidadorMedicoConflitoHorario implements ValidadorAgendamentoConsultas{
  
  @Autowired
  private ConsultaRepository consultaRepository;

  public void validar(DadosAgendamentoConsulta dados){
    var conflito = consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
    if(conflito){
      throw new ValidacaoException("Este médico já possui um atendimento neste horário");
    }
  }
}
