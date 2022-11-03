package com.matt.minhasfinancas.service;

import com.matt.minhasfinancas.model.entity.Lancamento;
import com.matt.minhasfinancas.model.enums.StatusLancamento;

import java.util.List;

public interface LancamentoService {

    Lancamento Salvar(Lancamento lancamento);

    Lancamento Atualizar (Lancamento lancamento);

    void deletar (Lancamento lancamento);

    List<Lancamento> buscar(Lancamento lancamentoFiltro);

    void atualizarStatus(Lancamento lancamento, StatusLancamento status);

}
