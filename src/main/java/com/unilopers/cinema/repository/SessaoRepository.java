package com.unilopers.cinema.repository;

import com.unilopers.cinema.model.Sessao;
import com.unilopers.cinema.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    boolean existsBySalaAndDataHora(Sala sala, LocalDateTime dataHora);
}
