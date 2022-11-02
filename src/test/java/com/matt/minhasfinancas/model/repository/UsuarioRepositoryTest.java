package com.matt.minhasfinancas.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.matt.minhasfinancas.model.entity.Usuario;

@ActiveProfiles("test")
@SpringBootTest
public class UsuarioRepositoryTest {
	
	//teste de integracao
	@Autowired
	UsuarioRepository repository;
	
	
	
	
	@Test
	public void deveVerificarExistenciaDeUmEmail() {
		
		
		//cenario
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
		repository.save(usuario);
		
		//acao
		boolean result = repository.existsByEmail("usuario@email.com");
		
		
		
		//verificacao
		assertThat(result).isTrue();

}
	
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
		
		//cenario
		repository.deleteAll();
		
		//acao
		boolean result = repository.existsByEmail("usuario@email.com");
		
		//verificacao
		assertThat(result).isFalse();
		
		
		
	}
	
}