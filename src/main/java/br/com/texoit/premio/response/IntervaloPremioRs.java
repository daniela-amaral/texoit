package br.com.texoit.premio.response;

import java.util.HashSet;
import java.util.Set;

public class IntervaloPremioRs {
	
	private Set<IntervaloPremioRsProdutor> min;
	private Set<IntervaloPremioRsProdutor> max;

	public Set<IntervaloPremioRsProdutor> getMin() {
		return min;
	}

	public void setMin(Set<IntervaloPremioRsProdutor> min) {
		this.min = min;
	}

	public Set<IntervaloPremioRsProdutor> getMax() {
		return max;
	}

	public void setMax(Set<IntervaloPremioRsProdutor> max) {
		this.max = max;
	}

	public void adicionarMinIntervaloPremioRsProdutor(final IntervaloPremioRsProdutor intervaloPremioRsProdutor) {
		if (this.min == null) {
			this.min = new HashSet<IntervaloPremioRsProdutor>();
		}
		this.min.add(intervaloPremioRsProdutor);
	}
	
	public void adicionarMaxIntervaloPremioRsProdutor(final IntervaloPremioRsProdutor intervaloPremioRsProdutor) {
		if (this.max == null) {
			this.max = new HashSet<IntervaloPremioRsProdutor>();
		}
		this.max.add(intervaloPremioRsProdutor);
	}
}
