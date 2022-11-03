package com.matt.minhasfinancas.service.impl;


import com.matt.minhasfinancas.model.entity.Lancamento;
import com.matt.minhasfinancas.model.enums.StatusLancamento;
import com.matt.minhasfinancas.model.repository.LancamentoRepository;
import com.matt.minhasfinancas.service.LancamentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LancamentoServiveImpl implements LancamentoService {

    private LancamentoRepository repository;

    public LancamentoServiveImpl(LancamentoRepository lancamentoRepository){
        this.repository = repository;
    }

    @Override
    @Transactional
    public Lancamento Salvar(Lancamento lancamento) {
        return repository.save(lancamento);
    }

    @Override
    public Lancamento Atualizar(Lancamento lancamento) {
        return null;
    }

    @Override
    public void deletar(Lancamento lancamento) {

    }

    @Override
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        return null;
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {

    }
}
