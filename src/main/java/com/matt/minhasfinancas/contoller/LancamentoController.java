package com.matt.minhasfinancas.contoller;


import com.matt.minhasfinancas.api.dto.LancamentoDTO;
import com.matt.minhasfinancas.exception.RegraNegocioException;
import com.matt.minhasfinancas.model.entity.Lancamento;
import com.matt.minhasfinancas.model.entity.Usuario;
import com.matt.minhasfinancas.model.enums.StatusLancamento;
import com.matt.minhasfinancas.model.enums.TipoLancamento;
import com.matt.minhasfinancas.service.LancamentoService;
import com.matt.minhasfinancas.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private LancamentoService service;

    private UsuarioService usuarioService;

    public LancamentoController(LancamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity buscar(
            @RequestParam(value = "descricao" , required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "usuario") Long idUsuario


    ){
    Lancamento lancamentoFiltro = new Lancamento();
    lancamentoFiltro.setDescricao(descricao);
    lancamentoFiltro.setMes(mes);
    lancamentoFiltro.setAno(ano);

    Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
        if (!usuario.isPresent()) {
            return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Usuário não encontrado na base.");
        } else {
            lancamentoFiltro.setUsuario(usuario.get());
        }
        List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
        return ResponseEntity.ok(lancamentos);

    }



    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {

        try {
            Lancamento entidade = converter(dto);
            entidade = service.salvar(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);

        } catch (RegraNegocioException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody LancamentoDTO dto) {
        return service.obterPorId(id).map(entity -> {

            try {
                Lancamento lancamento = converter(dto);
                lancamento.setId(entity.getId());
                service.atualizar(lancamento);
                return ResponseEntity.ok(lancamento);

            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());

            }
        }).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));
    }


    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return service.obterPorId(id).map(entidade -> {
            service.deletar(entidade);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));
    }


    private Lancamento converter(LancamentoDTO dto) {

        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());


        Usuario usuario = usuarioService.obterPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraNegocioException("Usuario não encontrado para o ID informado"));

        lancamento.setUsuario(usuario);
        lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        return lancamento;
    }

}