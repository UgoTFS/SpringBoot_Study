package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.medicos.DadosCadastroMedico;
import med.voll.api.medicos.Medico;
import med.voll.api.medicos.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

  @Autowired
  private MedicoRepository repository;

  @PostMapping
  public void cadastrar(@RequestBody DadosCadastroMedico dados){
    repository.save(new Medico(dados));
  }
  
}
