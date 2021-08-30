package br.com.texoit.premio.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Filme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long ano;
	
	private String titulo;
	
	@ManyToMany
	@JoinTable(name = "Filme_Produtor", joinColumns = @JoinColumn(name = "Filme_Id"), inverseJoinColumns = @JoinColumn(name = "Produtor_Id"))
	private Set<Produtor> produtores;	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getAno() {
		return ano;
	}
	
	public void setAno(Long ano) {
		this.ano = ano;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Set<Produtor> getProdutores() {
		return produtores;
	}

	public void adicionarProdutor(final Produtor produtor) {
		if (this.produtores == null) { 
			this.produtores = new HashSet<Produtor>();
		}
		this.produtores.add(produtor);
	}

}
