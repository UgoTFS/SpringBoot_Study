package med.voll.api.domain.consultas.validacoes.agendamento;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;

@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoConsultas{
  
  public void validar(DadosAgendamentoConsulta dados){
    var dataConsulta = dados.data();
    var dataAtual = LocalDateTime.now();

    var diferencaMinutos = java.time.Duration.between(dataAtual, dataConsulta).toMinutes();

    if(diferencaMinutos < 30){
      throw new ValidacaoException("Consulta deve ser agendada com antecedencia minima de 30 minutos");
    }
  }
}
