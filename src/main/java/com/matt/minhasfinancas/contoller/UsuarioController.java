package com.matt.minhasfinancas.contoller;

import com.matt.minhasfinancas.service.LancamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.matt.minhasfinancas.api.dto.UsuarioDTO;
import com.matt.minhasfinancas.model.entity.Usuario;
import com.matt.minhasfinancas.service.UsuarioService;
import com.matt.minhasfinancas.exception.ErroAutenticacao;
import com.matt.minhasfinancas.exception.RegraNegocioException;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	
	private final UsuarioService service;

	private final LancamentoService lancamentoService;
	

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

	@GetMapping("{id}/saldo")
	public ResponseEntity obterSaldo( @PathVariable("id") Long id ) {
		Optional<Usuario> usuario = service.obterPorId(id);

		if(!usuario.isPresent()) {
			return new ResponseEntity( HttpStatus.NOT_FOUND );
		}

		BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
		return ResponseEntity.ok(saldo);
	}


}
