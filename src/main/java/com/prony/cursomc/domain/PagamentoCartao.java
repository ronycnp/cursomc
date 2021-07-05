package com.prony.cursomc.domain;

import javax.persistence.Entity;

import com.prony.cursomc.domain.enums.EstadoPagamento;

@Entity
public class PagamentoCartao extends Pagamento{

	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;

	public PagamentoCartao() {
	}

	public PagamentoCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		//super(id, estado, pedido);
		this.setId(id);
		this.setEstado(estado);
		this.setPedido(pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
}
