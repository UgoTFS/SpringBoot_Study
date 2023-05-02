package med.voll.api.domain.consultas.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.medicos.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsultas {

  @Autowired
  private MedicoRepository medicoRepository;

  public void validar(DadosAgendamentoConsulta dados) {
    if (dados.idMedico() == null) {
      return;
    }
    var medicoAtivo = medicoRepository.findAtivoById(dados.idMedico());
    if (!medicoAtivo) {
      throw new ValidacaoException("Não existe este médico cadastrado");
    }
  }
}
