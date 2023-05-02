package med.voll.api.domain.consultas.validacoes.cancelamento;

import med.voll.api.domain.consultas.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoConsultas {
  void cancelar(DadosCancelamentoConsulta dados);
}
