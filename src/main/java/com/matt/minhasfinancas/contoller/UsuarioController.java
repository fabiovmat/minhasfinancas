package com.matt.minhasfinancas.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matt.minhasfinancas.api.dto.UsuarioDTO;
import com.matt.minhasfinancas.model.entity.Usuario;
import com.matt.minhasfinancas.service.UsuarioService;
import com.matt.minhasfinancas.service.exception.ErroAutenticacao;
import com.matt.minhasfinancas.service.exception.RegraNegocioException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	
	private UsuarioService service;
	
	public UsuarioController (UsuarioService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<Object> salvar(@RequestBody UsuarioDTO dto) {
		
	
		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha()).build();
	
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity<Object>(usuarioSalvo, HttpStatus.CREATED);
			
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity<Object> autenticar (@RequestBody UsuarioDTO dto) {
		
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			//return ResponseEntity.ok(usuarioAutenticado);
			return new ResponseEntity<Object>(usuarioAutenticado, HttpStatus.OK);
		} catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
	}
	
	
	
	
}
