package br.com.texoit.premio.dto;

public class IntervaloPremio {

	private String nomeProdutor;
	private Long intervalo;
	private Long vitoriaAnterior;
	private Long vitoriaPosterior;
	
	public IntervaloPremio(String nomeProdutor, Long intervalo, Long vitoriaAnterior, Long vitoriaPosterior) {
		this.nomeProdutor = nomeProdutor;
	    this.intervalo = intervalo;
	    this.vitoriaAnterior = vitoriaAnterior;
	    this.vitoriaPosterior = vitoriaPosterior;
	}

	public String getNomeProdutor() {
		return nomeProdutor;
	}

	public void setNomeProdutor(String nomeProdutor) {
		this.nomeProdutor = nomeProdutor;
	}

	public Long getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Long intervalo) {
		this.intervalo = intervalo;
	}

	public Long getVitoriaAnterior() {
		return vitoriaAnterior;
	}

	public void setVitoriaAnterior(Long vitoriaAnterior) {
		this.vitoriaAnterior = vitoriaAnterior;
	}

	public Long getVitoriaPosterior() {
		return vitoriaPosterior;
	}

	public void setVitoriaPosterior(Long vitoriaPosterior) {
		this.vitoriaPosterior = vitoriaPosterior;
	}
	
}
