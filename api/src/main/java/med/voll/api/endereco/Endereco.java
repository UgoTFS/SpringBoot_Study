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

}
