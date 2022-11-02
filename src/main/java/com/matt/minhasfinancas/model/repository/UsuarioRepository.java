package com.matt.minhasfinancas.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matt.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

	
	boolean existsByEmail(String email);
	Optional<Usuario> findByEmail(String email); 
	
	
	
}
