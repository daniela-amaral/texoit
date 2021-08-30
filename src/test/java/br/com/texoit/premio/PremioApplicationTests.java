package br.com.texoit.premio;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import br.com.texoit.premio.model.Filme;
import br.com.texoit.premio.model.Produtor;
import br.com.texoit.premio.repository.FilmeRepository;
import br.com.texoit.premio.repository.ProdutorRepository;

@SpringBootTest
@AutoConfigureMockMvc
class PremioApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	FilmeRepository filmeRepository;
	
	@Autowired
	ProdutorRepository produtorRepository;
	
	@Test
	void contextLoads() { }
	
	@Test
	void validaQueNenhumFilmeRetornaNotfound() throws Exception {
		
		filmeRepository.deleteAll();		
		
		mockMvc.perform(get("/premio")).andDo(print()).andExpect(status().isNotFound());
		
	}
	
	@Test
	void validaQueUmFilmeRetornaNotfound() throws Exception {
		
		filmeRepository.deleteAll();
		
		Produtor produtor = new Produtor();
		produtor.setNome("P1");
		
		Filme filme = new Filme();
		filme.setAno(2000L);
		filme.setTitulo("F1");
		filme.adicionarProdutor(produtor);
		
		produtorRepository.save(produtor);
		filmeRepository.save(filme);
		
		mockMvc.perform(get("/premio")).andDo(print()).andExpect(status().isNotFound());
		
	}
	
	@Test
	void validaQueDoisFilmesComProdutoresDiferentesRetornaNotfound() throws Exception {
		
		filmeRepository.deleteAll();
		
		Produtor produtor = new Produtor();
		produtor.setNome("P1");
		
		Filme filme = new Filme();
		filme.setAno(2000L);
		filme.setTitulo("F1");
		filme.adicionarProdutor(produtor);
		
		produtorRepository.save(produtor);
		filmeRepository.save(filme);
		
		Produtor produtor2 = new Produtor();
		produtor2.setNome("P2");
		
		Filme filme2 = new Filme();
		filme2.setAno(2000L);
		filme2.setTitulo("F2");
		filme2.adicionarProdutor(produtor2);
		
		produtorRepository.save(produtor2);
		filmeRepository.save(filme2);
		
		mockMvc.perform(get("/premio")).andDo(print()).andExpect(status().isNotFound());
		
	}
	
	@Test
	void validaQueDoisFilmesComMesmoProdutorRetornaMinMaxIguais() throws Exception {
		filmeRepository.deleteAll();
		
		Produtor produtor = new Produtor();
		produtor.setNome("P1");
		
		Filme filme = new Filme();
		filme.setAno(2000L);
		filme.setTitulo("F1");
		filme.adicionarProdutor(produtor);
		
		produtorRepository.save(produtor);
		filmeRepository.save(filme);
		
		Filme filme2 = new Filme();
		filme2.setAno(2010L);
		filme2.setTitulo("F2");
		filme2.adicionarProdutor(produtor);
		
		filmeRepository.save(filme2);
		
		mockMvc.perform(get("/premio"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.min").isArray())
					.andExpect(jsonPath("$.min", hasSize(1)))
					.andExpect(jsonPath("$.min[0].producer").value("P1"))
					.andExpect(jsonPath("$.min[0].interval").value(10L))
					.andExpect(jsonPath("$.min[0].previousWin").value(2000L))
					.andExpect(jsonPath("$.min[0].followingWin").value(2010L))
					.andExpect(jsonPath("$.max").isArray())
					.andExpect(jsonPath("$.max", hasSize(1)))
					.andExpect(jsonPath("$.max[0].producer").value("P1"))
					.andExpect(jsonPath("$.max[0].interval").value(10L))
					.andExpect(jsonPath("$.max[0].previousWin").value(2000L))
					.andExpect(jsonPath("$.max[0].followingWin").value(2010L));
		
	}
	
	@Test
	void validaQueDoisFilmesComUmProdutorIgualRetornaMinMaxIguais() throws Exception {
		filmeRepository.deleteAll();
		
		Produtor produtor1 = new Produtor();
		produtor1.setNome("P1");
		
		Produtor produtor2 = new Produtor();
		produtor2.setNome("P2");
		
		Produtor produtor3 = new Produtor();
		produtor3.setNome("P3");
		
		produtorRepository.save(produtor1);
		produtorRepository.save(produtor2);
		produtorRepository.save(produtor3);
		
		Filme filme = new Filme();
		filme.setAno(2010L);
		filme.setTitulo("F1");
		filme.adicionarProdutor(produtor1);
		filme.adicionarProdutor(produtor2);
			
		filmeRepository.save(filme);
		
		Filme filme2 = new Filme();
		filme2.setAno(2010L);
		filme2.setTitulo("F2");
		filme2.adicionarProdutor(produtor2);
		filme2.adicionarProdutor(produtor3);
		
		filmeRepository.save(filme2);
		
		mockMvc.perform(get("/premio"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.min").isArray())
					.andExpect(jsonPath("$.min", hasSize(1)))
					.andExpect(jsonPath("$.min[0].producer").value("P2"))
					.andExpect(jsonPath("$.min[0].interval").value(0L))
					.andExpect(jsonPath("$.min[0].previousWin").value(2010L))
					.andExpect(jsonPath("$.min[0].followingWin").value(2010L))
					.andExpect(jsonPath("$.max").isArray())
					.andExpect(jsonPath("$.max", hasSize(1)))
					.andExpect(jsonPath("$.max[0].producer").value("P2"))
					.andExpect(jsonPath("$.max[0].interval").value(0L))
					.andExpect(jsonPath("$.max[0].previousWin").value(2010L))
					.andExpect(jsonPath("$.max[0].followingWin").value(2010L));
		
	}
	
	@Test
	void validaQueTodosProdutoresComMesmoIntervaloRetornam() throws Exception {
		filmeRepository.deleteAll();
		
		Produtor produtor1 = new Produtor();
		produtor1.setNome("P1");
		
		Produtor produtor2 = new Produtor();
		produtor2.setNome("P2");
		
		produtorRepository.save(produtor1);
		produtorRepository.save(produtor2);
		
		Filme filme = new Filme();
		filme.setAno(1998L);
		filme.setTitulo("F1");
		filme.adicionarProdutor(produtor1);
			
		filmeRepository.save(filme);
		
		Filme filme2 = new Filme();
		filme2.setAno(2000L);
		filme2.setTitulo("F2");
		filme2.adicionarProdutor(produtor1);
		
		filmeRepository.save(filme2);
		
		Filme filme3 = new Filme();
		filme3.setAno(2002L);
		filme3.setTitulo("F3");
		filme3.adicionarProdutor(produtor2);
		
		filmeRepository.save(filme3);
		
		Filme filme4 = new Filme();
		filme4.setAno(2004L);
		filme4.setTitulo("F4");
		filme4.adicionarProdutor(produtor2);
		
		filmeRepository.save(filme4);
		
		Filme filme5 = new Filme();
		filme5.setAno(2008L);
		filme5.setTitulo("F5");
		filme5.adicionarProdutor(produtor1);
		
		filmeRepository.save(filme5);
		
		Filme filme6 = new Filme();
		filme6.setAno(2012L);
		filme6.setTitulo("F6");
		filme6.adicionarProdutor(produtor2);
		
		filmeRepository.save(filme6);
		
		mockMvc.perform(get("/premio"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.min").isArray())
					.andExpect(jsonPath("$.min", hasSize(2)))
					.andExpect(jsonPath("$.min[*].producer", containsInAnyOrder("P1", "P2")))
					.andExpect(jsonPath("$.min[*].interval", containsInAnyOrder(2, 2))) 
					.andExpect(jsonPath("$.min[*].previousWin", containsInAnyOrder(1998, 2002))) 
					.andExpect(jsonPath("$.min[*].followingWin", containsInAnyOrder(2000, 2004)))   									
					.andExpect(jsonPath("$.max").isArray())
					.andExpect(jsonPath("$.max", hasSize(2)))
					.andExpect(jsonPath("$.max[*].producer", containsInAnyOrder("P1", "P2")))
					.andExpect(jsonPath("$.max[*].interval", containsInAnyOrder(8, 8))) 
					.andExpect(jsonPath("$.max[*].previousWin", containsInAnyOrder(2000, 2004)))  
					.andExpect(jsonPath("$.max[*].followingWin", containsInAnyOrder(2008, 2012)));
		
	}
	
	@Test
	void validaProdutoresDiferentesComIntervalosDiferentes() throws Exception {
		filmeRepository.deleteAll();
		
		Produtor produtor1 = new Produtor();
		produtor1.setNome("P1");
		
		Produtor produtor2 = new Produtor();
		produtor2.setNome("P2");
		
		Produtor produtor3 = new Produtor();
		produtor3.setNome("P3");
		
		produtorRepository.save(produtor1);
		produtorRepository.save(produtor2);
		produtorRepository.save(produtor3);
		
		Filme filme = new Filme();
		filme.setAno(2010L);
		filme.setTitulo("F1");
		filme.adicionarProdutor(produtor1);
			
		filmeRepository.save(filme);
		
		Filme filme2 = new Filme();
		filme2.setAno(2015L);
		filme2.setTitulo("F2");
		filme2.adicionarProdutor(produtor1);
		filme2.adicionarProdutor(produtor2);
		filme2.adicionarProdutor(produtor3);
		
		filmeRepository.save(filme2);
		
		Filme filme3 = new Filme();
		filme3.setAno(2020L);
		filme3.setTitulo("F3");
		filme3.adicionarProdutor(produtor3);
		
		filmeRepository.save(filme3);
		
		Filme filme4 = new Filme();
		filme4.setAno(2021L);
		filme4.setTitulo("F4");
		filme4.adicionarProdutor(produtor2);
		filme4.adicionarProdutor(produtor3);
		
		filmeRepository.save(filme4);
		
		mockMvc.perform(get("/premio"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.min").isArray())
					.andExpect(jsonPath("$.min", hasSize(1)))
					.andExpect(jsonPath("$.min[0].producer").value("P3"))
					.andExpect(jsonPath("$.min[0].interval").value(1L))
					.andExpect(jsonPath("$.min[0].previousWin").value(2020L))
					.andExpect(jsonPath("$.min[0].followingWin").value(2021L))  									
					.andExpect(jsonPath("$.max").isArray())
					.andExpect(jsonPath("$.max", hasSize(1)))
					.andExpect(jsonPath("$.max[0].producer").value("P2"))
					.andExpect(jsonPath("$.max[0].interval").value(6L))
					.andExpect(jsonPath("$.max[0].previousWin").value(2015L))
					.andExpect(jsonPath("$.max[0].followingWin").value(2021L));
		
	}
	
	@Test
	void validaProdutorComIntervalosIguaisComFilmesDiferentes() throws Exception {
		filmeRepository.deleteAll();
		
		Produtor produtor1 = new Produtor();
		produtor1.setNome("P1");
		
		produtorRepository.save(produtor1);
		
		Filme filme = new Filme();
		filme.setAno(10L);
		filme.setTitulo("F1");
		filme.adicionarProdutor(produtor1);
			
		filmeRepository.save(filme);
		
		Filme filme2 = new Filme();
		filme2.setAno(20L);
		filme2.setTitulo("F2");
		filme2.adicionarProdutor(produtor1);
		
		filmeRepository.save(filme2);
		
		Filme filme3 = new Filme();
		filme3.setAno(30L);
		filme3.setTitulo("F3");
		filme3.adicionarProdutor(produtor1);
		
		filmeRepository.save(filme3);
		
		Filme filme4 = new Filme();
		filme4.setAno(50L);
		filme4.setTitulo("F4");
		filme4.adicionarProdutor(produtor1);
		
		filmeRepository.save(filme4);
		
		Filme filme5 = new Filme();
		filme5.setAno(70L);
		filme5.setTitulo("F5");
		filme5.adicionarProdutor(produtor1);
		
		filmeRepository.save(filme5);
		
		mockMvc.perform(get("/premio"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.min").isArray())
					.andExpect(jsonPath("$.min", hasSize(2)))
					.andExpect(jsonPath("$.min[*].producer", containsInAnyOrder("P1", "P1")))
					.andExpect(jsonPath("$.min[*].interval", containsInAnyOrder(10, 10))) 
					.andExpect(jsonPath("$.min[*].previousWin", containsInAnyOrder(10, 20))) 
					.andExpect(jsonPath("$.min[*].followingWin", containsInAnyOrder(20, 30)))   									
					.andExpect(jsonPath("$.max").isArray())
					.andExpect(jsonPath("$.max", hasSize(2)))
					.andExpect(jsonPath("$.max[*].producer", containsInAnyOrder("P1", "P1")))
					.andExpect(jsonPath("$.max[*].interval", containsInAnyOrder(20, 20))) 
					.andExpect(jsonPath("$.max[*].previousWin", containsInAnyOrder(30, 50)))  
					.andExpect(jsonPath("$.max[*].followingWin", containsInAnyOrder(50, 70)));
		
	}

}
