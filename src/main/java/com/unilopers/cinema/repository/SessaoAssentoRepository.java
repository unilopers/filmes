package com.unilopers.cinema.repository;

import com.unilopers.cinema.model.Sessao;
import com.unilopers.cinema.model.SessaoAssento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoAssentoRepository extends JpaRepository<SessaoAssento, Long> {
    Optional<SessaoAssento> findBySessaoAndIdAssento(Sessao sessao, Integer idAssento);
    List<SessaoAssento> findBySessao(Sessao sessao);
    List<SessaoAssento> findBySessaoAndReservado(Sessao sessao, Boolean reservado);
    long countBySessao(Sessao sessao);
}