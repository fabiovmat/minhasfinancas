package com.matt.minhasfinancas.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.matt.minhasfinancas.model.entity.Usuario;
import com.matt.minhasfinancas.model.repository.UsuarioRepository;
import com.matt.minhasfinancas.exception.RegraNegocioException;


@ActiveProfiles("test")
@SpringBootTest
public class UsuarioServiceTest {

	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void deveValidarEmailCadastrado() {
		
		
		//cenario
		repository.deleteAll();
		Usuario usuario = Usuario.builder().nome("matt").email("email@email.com").build();
		repository.save(usuario);
		
		//acao
		service.validarEmail("email@email.com");
		
		
		//test
		
				
		RegraNegocioException thrown = Assertions.assertThrows(RegraNegocioException.class,() -> {
			
		});
		
		
		Assertions.assertEquals("Ja existe um usuário com este email", thrown.getMessage());
		
	}
	
	
	
}
