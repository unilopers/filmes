package com.unilopers.cinema.service.async;

import com.unilopers.cinema.model.Sala;
import com.unilopers.cinema.model.Sessao;
import com.unilopers.cinema.repository.SalaRepository;
import com.unilopers.cinema.repository.SessaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalaSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SalaSchedulerService.class);

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    /**
     * Nicholas Gabriel: Atualização automática de disponibilidade de Sala.
     * Roda a cada 1 minuto para verificar ocupação física baseada nas sessões.
     */
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void verificarOcupacaoDasSalas() {
        logger.info("[CRON-SALA] Iniciando verificação de disponibilidade...");
        
        LocalDateTime agora = LocalDateTime.now();
        List<Sala> todasAsSalas = salaRepository.findAll();
        List<Sessao> sessoesAtivas = sessaoRepository.findAll();

        for (Sala sala : todasAsSalas) {
            boolean estaOcupada = false;
            String filmeEmExibicao = "";

            for (Sessao sessao : sessoesAtivas) {
                if (sessao.getSala().getId().equals(sala.getId())) {
                    LocalDateTime inicio = sessao.getDataHora();
                    // Assume-se que Filme tem o campo duracaoMin
                    LocalDateTime fim = inicio.plusMinutes(sessao.getFilme().getDuracaoMin());

                    if (agora.isAfter(inicio) && agora.isBefore(fim)) {
                        estaOcupada = true;
                        filmeEmExibicao = sessao.getFilme().getTitulo();
                        break;
                    }
                }
            }

            // Atualiza apenas se houver mudança de estado para otimizar performance
            if (sala.getDisponivel() == estaOcupada) {
                sala.setDisponivel(!estaOcupada);
                salaRepository.save(sala);
                
                if (estaOcupada) {
                    logger.info("[CRON-SALA] STATUS ALTERADO: Sala '{}' ocupada pelo filme '{}'.", sala.getNome(), filmeEmExibicao);
                } else {
                    logger.info("[CRON-SALA] STATUS ALTERADO: Sala '{}' agora está livre.", sala.getNome());
                }
            }
        }
        
        logger.info("[CRON-SALA] Ciclo de verificação finalizado.");
    }
}