package med.voll.api.domain.consultas.validacoes.agendamento;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoConsultas {

  public void validar(DadosAgendamentoConsulta dados){
    var dataConsulta = dados.data();

    var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    var muitoCedo = dataConsulta.getHour() < 7;
    var muitoTarde = dataConsulta.getHour() > 18;

    if(domingo || muitoCedo || muitoTarde){
      throw new ValidacaoException("Consulta fora do horário de funcionamento da clinica!");
    }
  }
  
}
