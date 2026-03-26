package com.unilopers.cinema.service.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SessaoAsyncService {

    private static final Logger logger = LoggerFactory.getLogger(SessaoAsyncService.class);

    @Autowired
    private SessaoInternalService sessaoInternalService;

    @Async
    public void gerarAssentos(Long sessaoId, Integer capacidade) {
        long startTime = System.currentTimeMillis();
        logger.info("[ASYNC] Iniciando geração de {} assentos para sessão ID: {} | Thread: {}",
                capacidade, sessaoId, Thread.currentThread().getName());
        try {
            sessaoInternalService.executarGeracaoAssentos(sessaoId, capacidade);
            logger.info("[ASYNC] Geração concluída para sessão ID: {}. Tempo total: {}ms",
                    sessaoId, System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            logger.error("[ASYNC] Falha na geração de assentos para sessão ID: {}", sessaoId, e);
            sessaoInternalService.atualizarStatusErro(sessaoId);
        }
    }
}