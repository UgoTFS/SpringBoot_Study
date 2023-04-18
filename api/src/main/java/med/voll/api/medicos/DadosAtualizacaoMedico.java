package med.voll.api.medicos;

import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(Long id, String nome, String telefone, DadosEndereco endereco) {
  
}
