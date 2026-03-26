package com.unilopers.cinema.service.async;

import com.unilopers.cinema.model.Sessao;
import com.unilopers.cinema.model.SessaoAssento;
import com.unilopers.cinema.repository.SessaoAssentoRepository;
import com.unilopers.cinema.repository.SessaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessaoInternalService {

    private static final Logger logger = LoggerFactory.getLogger(SessaoInternalService.class);

    @Autowired
    private SessaoAssentoRepository sessaoAssentoRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executarGeracaoAssentos(Long sessaoId, Integer capacidade) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada: " + sessaoId));

        if (sessaoAssentoRepository.countBySessao(sessao) > 0) {
            logger.warn("[INTERNAL] Assentos já existem para sessão ID: {}. Operação ignorada.", sessaoId);
            return;
        }

        List<SessaoAssento> listaAssentos = new ArrayList<>();
        for (int i = 1; i <= capacidade; i++) {
            listaAssentos.add(new SessaoAssento(sessao, i, false));
        }

        sessaoAssentoRepository.saveAll(listaAssentos);

        sessao.setStatus("CONFIRMADA");
        sessaoRepository.save(sessao);

        logger.info("[INTERNAL] {} assentos persistidos para sessão ID: {}", capacidade, sessaoId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void atualizarStatusErro(Long sessaoId) {
        sessaoRepository.findById(sessaoId).ifPresent(s -> {
            s.setStatus("ERRO");
            sessaoRepository.save(s);
            logger.warn("[INTERNAL] Status da sessão ID: {} atualizado para ERRO.", sessaoId);
        });
    }
}