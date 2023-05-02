package med.voll.api.domain.consultas.validacoes.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosCancelamentoConsulta;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoConsultas{
  
  @Autowired
  private ConsultaRepository repository;

  @Override
  public void cancelar(DadosCancelamentoConsulta dados){
    var consulta = repository.getReferenceById(dados.id());
    var agora = LocalDateTime.now();
    var diferencaHoras = Duration.between(agora, consulta.getData()).toHours();

    if(diferencaHoras < 24){
      throw new ValidacaoException("Não pode cancelar a consulta em menos de 24h");
    }
  }
}
