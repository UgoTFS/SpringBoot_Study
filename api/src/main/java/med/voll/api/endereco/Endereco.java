package med.voll.api.endereco;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

  private String logradouro;
  private String numero;
  private String bairro;
  private String cep;
  private String complemento;
  private String uf;
  private String cidade;
  
  public Endereco(DadosEndereco endereco) {
    this.logradouro = endereco.logradouro();
    this.numero = endereco.numero();
    this.bairro = endereco.bairro();
    this.cep = endereco.cep();
    this.complemento = endereco.complemento();
    this.uf = endereco.uf();
    this.cidade = endereco.cidade();

  }

  public void atualizarInformacoes(DadosEndereco endereco) {
    if (endereco.logradouro() != null) {
      this.logradouro = endereco.logradouro();
    }
    if (endereco.numero() != null) {
      this.numero = endereco.numero();
    }
    if (endereco.bairro() != null) {
      this.bairro = endereco.bairro();
    }
    if (endereco.cep() != null) {
      this.cep = endereco.cep();
    }
    if (endereco.complemento() != null) {
      this.complemento = endereco.complemento();
    }
    if (endereco.uf() != null) {
      this.uf = endereco.uf();
    }
    if (endereco.cidade() != null) {
      this.cidade = endereco.cidade();
    }
  }

}
