package com.matt.minhasfinancas.model.repository;


import com.matt.minhasfinancas.model.entity.Lancamento;
import com.matt.minhasfinancas.model.enums.StatusLancamento;
import com.matt.minhasfinancas.model.enums.TipoLancamento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class LancamentoRepositoryTest {


    @Autowired
    LancamentoRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveSalvarUmLancamento(){

        Lancamento lancamento = criarLancamento();

        lancamento = repository.save(lancamento);
        Assert.notNull(lancamento.getId());

    }

    public static Lancamento criarLancamento() {
        Lancamento lancamento = Lancamento.builder()
                .ano(2019)
                .mes(1).descricao("lancamento qualquer")
                .valor(BigDecimal.valueOf(10))
                .tipo(TipoLancamento.RECEITA)
                .status(StatusLancamento.PENDENTE)
                .dataCadastro(LocalDate.now())
                .build();
        return lancamento;
    }

    @Test
    public void deveDeletarUmLancamento(){

        Lancamento lancamento = criarEPersistirLancamento();

        lancamento = entityManager.find(Lancamento.class, lancamento.getId());

        repository.delete(lancamento);
        Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
        Assert.isNull(lancamentoInexistente);

    }

    private Lancamento criarEPersistirLancamento() {
        Lancamento lancamento = criarLancamento();
        entityManager.persist(lancamento);
        return lancamento;
    }

    @Test
    public void deveAtualizarUmLancamento(){
            Lancamento lancamento = criarEPersistirLancamento();
            lancamento.setAno(2018);
            lancamento.setDescricao("Teste Atualizar");
            lancamento.setStatus(StatusLancamento.CANCELADO);

            repository.save(lancamento);
            Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());
            Assert.isTrue(lancamentoAtualizado.getAno().equals(2018));
            Assert.isTrue(lancamentoAtualizado.getDescricao().equals("Teste Atualizar"));
            Assert.isTrue(lancamentoAtualizado.getStatus().equals(StatusLancamento.CANCELADO));




    }

    @Test
    public void deveBuscarLancamentoporID(){

        Lancamento lancamento = criarEPersistirLancamento();
        Optional<Lancamento> repositoryById = repository.findById(lancamento.getId());
        Assert.isTrue(repositoryById.isPresent());

    }

}
