package com.unilopers.cinema.service.async;

import com.unilopers.cinema.model.Ingresso;
import com.unilopers.cinema.repository.IngressoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngressoAsyncService {

    private static final Logger logger = LoggerFactory.getLogger(IngressoAsyncService.class);

    @Autowired
    private IngressoRepository ingressoRepository;

    @Async
    @Transactional
    public void processarConfirmacaoPagamento(Long ingressoId) {
        logger.info("Iniciando processamento assíncrono para o ingresso ID: {}", ingressoId);

        try {
            Thread.sleep(3000);

            Ingresso ingresso = ingressoRepository.findById(ingressoId)
                    .orElseThrow(() -> new RuntimeException("Ingresso não encontrado para o ID: " + ingressoId));

            ingresso.setStatus("CONFIRMADO");
            ingressoRepository.save(ingresso);

            logger.info("Pagamento confirmado e status atualizado para o ingresso ID: {}", ingressoId);

        } catch (InterruptedException e) {
            logger.error("Falha na interrupção do processamento do ingresso ID: {}", ingressoId, e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("Erro crítico ao processar confirmação de pagamento para o ingresso ID: {}", ingressoId, e);
        }
    }
}