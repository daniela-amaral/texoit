package br.com.texoit.premio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.texoit.premio.dto.IntervaloPremio;
import br.com.texoit.premio.repository.FilmeRepository;
import br.com.texoit.premio.response.IntervaloPremioRs;
import br.com.texoit.premio.response.IntervaloPremioRsProdutor;

@RestController
public class PremioController {
	
	@Autowired
	private FilmeRepository filmeRepository;
	
	@GetMapping
	@RequestMapping(value="/premio", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<IntervaloPremioRs> intervaloPremio() {
		
		List<IntervaloPremio> menorIntervaloPremios = filmeRepository.listarMenorIntervaloPremio();
		
		IntervaloPremioRs intervaloPremioRs = new IntervaloPremioRs();
		IntervaloPremioRsProdutor intervaloPremioRsProdutor;
		
		for (IntervaloPremio intervaloPremio : menorIntervaloPremios) {
			intervaloPremioRsProdutor = new IntervaloPremioRsProdutor(intervaloPremio.getNomeProdutor(), intervaloPremio.getIntervalo(), intervaloPremio.getVitoriaAnterior(), intervaloPremio.getVitoriaPosterior());
			intervaloPremioRs.adicionarMinIntervaloPremioRsProdutor(intervaloPremioRsProdutor);
		}
		
		List<IntervaloPremio> maiorIntervaloPremios = filmeRepository.listarMaiorIntervaloPremio();
		
		for (IntervaloPremio intervaloPremio : maiorIntervaloPremios) {
			intervaloPremioRsProdutor = new IntervaloPremioRsProdutor(intervaloPremio.getNomeProdutor(), intervaloPremio.getIntervalo(), intervaloPremio.getVitoriaAnterior(), intervaloPremio.getVitoriaPosterior());
			intervaloPremioRs.adicionarMaxIntervaloPremioRsProdutor(intervaloPremioRsProdutor);
		}
		
		if (menorIntervaloPremios.isEmpty() && maiorIntervaloPremios.isEmpty()) {
			return new ResponseEntity<IntervaloPremioRs>(HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<IntervaloPremioRs>(intervaloPremioRs, HttpStatus.OK);
	}
	
}
