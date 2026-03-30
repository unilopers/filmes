package com.unilopers.cinema.repository;

import com.unilopers.cinema.model.Ingresso;
import com.unilopers.cinema.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
    List<Ingresso> findByUsuario(Usuario usuario);
}