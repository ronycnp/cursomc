package com.prony.cursomc.domain.enums;

public enum TipoCliente {
	
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private Integer id;
	private String descricao;
	
	private TipoCliente(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static  TipoCliente toEnum(Integer id) {
		
		if(id == null)
			return null;
		
		for(TipoCliente x : TipoCliente.values()) {
			if (id.equals(x.getId()))
				return x;
		}
		
		throw new IllegalArgumentException("id inválido: " + id);
	}
}
