package br.com.texoit.premio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.texoit.premio.model.Produtor;

@Repository
public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
	
	Produtor findByNome(String nome);

}
