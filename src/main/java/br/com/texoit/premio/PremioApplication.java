package br.com.texoit.premio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import br.com.texoit.premio.model.Filme;
import br.com.texoit.premio.model.Produtor;
import br.com.texoit.premio.repository.FilmeRepository;
import br.com.texoit.premio.repository.ProdutorRepository;

@SpringBootApplication
public class PremioApplication {
	
	@Autowired
	public FilmeRepository filmeRepository;
	
	@Autowired
	public ProdutorRepository produtorRepository;
	
	public static void main(String[] args) {	
		SpringApplication.run(PremioApplication.class, args);
	}
	
	@EventListener
	public void loadArquivoCsv(ContextRefreshedEvent event) {
		
		String caminhoArquivo = System.getProperty("user.home") + "/movielist.csv";
		System.out.println("caminhoArquivo = " + caminhoArquivo);
		
		BufferedReader conteudoCsv;
		String linha;
		Filme filme; 
		Produtor produtor; 
		
		try { 
			
			conteudoCsv = new BufferedReader(new FileReader(caminhoArquivo)); 
			 
			linha = conteudoCsv.readLine();
			
			while ((linha = conteudoCsv.readLine()) != null) {
				
				String[] campos = linha.split(";", 5);    
				
				if (campos.length == 5 && campos[4].trim().equalsIgnoreCase("yes") &&
					campos[0] != null && !campos[0].trim().isEmpty() && 
					campos[0].trim().length() >= 1 && campos[0].trim().length() <= 4 &&
					campos[0].trim().matches("[0-9]*") && 
					campos[1] != null && !campos[1].trim().isEmpty() &&
					campos[3] != null && !campos[3].trim().isEmpty()) { 
					
					filme = filmeRepository.findByAnoAndTitulo(Long.parseLong(campos[0].trim()), campos[1].trim());
					
					if (filme == null) {
					  
						filme = new Filme();
						filme.setAno(Long.parseLong(campos[0].trim()));
						filme.setTitulo(campos[1].trim());
											
						String[] camposProdutores = campos[3].split(" and |,");	
						
						if (camposProdutores != null && camposProdutores.length > 0) {
						
							for (String nomeProdutor : camposProdutores) {		 				
								
								if (nomeProdutor != null && !nomeProdutor.trim().isEmpty()) {	
									 
									nomeProdutor = nomeProdutor.trim(); 
									
									produtor = produtorRepository.findByNome(nomeProdutor);
									
									if (produtor == null) {
										
										produtor = new Produtor();
										produtor.setNome(nomeProdutor);	 
										produtorRepository.save(produtor); 
									
									}
									
									filme.adicionarProdutor(produtor);
								}
								
							}
							
						}
						
						filmeRepository.save(filme);  
						
					} 
					
				}
				
			}
			
			if (conteudoCsv != null) {
				conteudoCsv.close();
			} 
			
		} catch (FileNotFoundException e) {
			System.out.println("Erro FileNotFoundException");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Erro ArrayIndexOutOfBoundsException");
		} catch (IOException e) {  
			System.out.println("Erro IOException");
		}
		
	}

}
