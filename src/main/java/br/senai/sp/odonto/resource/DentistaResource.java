package br.senai.sp.odonto.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.odonto.dto.DentistaDTO;
import br.senai.sp.odonto.model.Dentista;
import br.senai.sp.odonto.repository.DentistaRepository;

@RestController
@RequestMapping("/odonto")
@CrossOrigin
public class DentistaResource {
	
	@Autowired //responsável por instanciar a classe e os métodos
	private DentistaRepository dentistaRepository;
	
	@GetMapping("/dentistas")
	public List<Dentista> getDentistas() {
		
		
		return dentistaRepository.findAll();
		
	}
	
	@GetMapping("/dentistas/cro/{cro}")
	public List<Dentista> getDentistasCro(@PathVariable String cro) {
			
			return dentistaRepository.findByCro(cro);
			
		
	}
	
	@GetMapping("/dentistas/nome/{nome}")
	public List<Dentista> getDentistasNome(@PathVariable String nome) {
			
			return dentistaRepository.findByLikeNome(nome);
			
		
	}
	
	@PostMapping("/dentistas")
	@ResponseStatus(HttpStatus.CREATED)
	public Dentista gravar(@Valid @RequestBody Dentista d) {
		
		Dentista novoDentista =  dentistaRepository.save(d);
		
		return novoDentista;
		
	}
	
	@GetMapping("/dentista/{codigo}")
	public ResponseEntity<?> getDentistaById(@PathVariable Long codigo) {
		
		Optional<Dentista> dentistaConsultado = dentistaRepository.findById(codigo);
		DentistaDTO dentistaDTO = new DentistaDTO();
		Dentista d = (Dentista) dentistaConsultado.get();
		
		dentistaDTO.setEmail(d.getEmail());
		dentistaDTO.setNome(d.getNome());
		dentistaDTO.setTelefone(d.getTelefone());
		dentistaDTO.setCro(d.getCro());
		
		return  dentistaConsultado.isPresent() ?
				ResponseEntity.ok(dentistaConsultado.get()) :
					ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/dentista/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo){
		
		dentistaRepository.deleteById(codigo);
		
	}
	
	@PutMapping("/dentista/{codigo}") // codigo tem que ser igual no body
	public ResponseEntity<Dentista> atualizar(@PathVariable Long codigo, 
			@Valid @RequestBody Dentista dentistaBody){
		
		Dentista dentistaBanco = dentistaRepository.findById(codigo).get();
		
		BeanUtils.copyProperties(dentistaBody, dentistaBanco, "codigo", "email"); // (fonte, propriedade onde os valores mudam, "valor não herdado") 
		
		dentistaRepository.save(dentistaBanco);
		
		return ResponseEntity.ok(dentistaBanco);
		
	}

	
	
	
	
}
