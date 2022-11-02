package com.matt.minhasfinancas.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter @Builder
public class UsuarioDTO {
	
	private String email;
	private String senha;
	private String nome; 

}
