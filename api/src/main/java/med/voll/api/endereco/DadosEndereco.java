package med.voll.api.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
  @NotBlank
  String logradouro,
  
  @NotBlank
  String bairro,
  
  String numero, 
  
  String complemento, 

  @NotBlank
  String uf, 

  @NotBlank
  @Pattern(regexp = "\\d{8}")
  String cep, 

  @NotBlank
  String cidade) {
  
}
