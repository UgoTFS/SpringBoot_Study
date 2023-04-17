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

}
