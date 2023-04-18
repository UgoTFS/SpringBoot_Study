package med.voll.api.pacientes;

import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(Long id, String name, String nome, String telefone, DadosEndereco endereco) {
  
}
