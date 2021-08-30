package br.com.texoit.premio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.texoit.premio.dto.IntervaloPremio;
import br.com.texoit.premio.model.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

	Filme findByAnoAndTitulo(Long ano, String titulo);
	
	@Query(value = "SELECT distinct new br.com.texoit.premio.dto.IntervaloPremio(p1.nome, f2.ano - f1.ano, f1.ano, f2.ano) "
			+ "FROM Filme f1 join f1.produtores p1, Filme f2 join f2.produtores p2 "
			+ "WHERE f1.id <> f2.id "
			+ "and f2.ano >= f1.ano "
			+ "and p1.id = p2.id "
			+ "and (f2.ano - f1.ano) = "
			+ "(SELECT min(f2.ano - f1.ano) "
			+ "FROM Filme f1 join f1.produtores p1, Filme f2 join f2.produtores p2 "
			+ "WHERE f1.id <> f2.id "
			+ "and f2.ano >= f1.ano "
			+ "and p1.id = p2.id) ")
	List<IntervaloPremio> listarMenorIntervaloPremio();
	
	@Query(value = "SELECT distinct new br.com.texoit.premio.dto.IntervaloPremio(p1.nome, f2.ano - f1.ano, f1.ano, f2.ano) "
			+ "FROM Filme f1 join f1.produtores p1, Filme f2 join f2.produtores p2 "
			+ "WHERE f1.id <> f2.id "
			+ "and f2.ano >= f1.ano "
			+ "and p1.id = p2.id "
			+ "and (f2.ano - f1.ano) = "
			+ "(SELECT max(f2.ano - f1.ano) "
			+ "FROM Filme f1 join f1.produtores p1, Filme f2 join f2.produtores p2 "
			+ "WHERE f1.id <> f2.id "
			+ "and f2.ano >= f1.ano "
			+ "and p1.id = p2.id "
			+ "and not exists (select 1 from Filme f3 join f3.produtores p3 where p3.id = p1.id and f3.ano > f1.ano and f3.ano < f2.ano )) "
			+ "and not exists (select 1 from Filme f3 join f3.produtores p3 where p3.id = p1.id and f3.ano > f1.ano and f3.ano < f2.ano ) ")
	List<IntervaloPremio> listarMaiorIntervaloPremio();

}
