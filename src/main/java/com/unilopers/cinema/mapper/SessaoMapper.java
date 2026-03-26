package com.unilopers.cinema.mapper;

import com.unilopers.cinema.model.Sessao;
import com.unilopers.cinema.dto.response.SessaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessaoMapper {

    @Autowired
    private FilmeMapper filmeMapper;

    @Autowired
    private SalaMapper salaMapper;

    public SessaoDTO toDTO(Sessao sessao) {
        if (sessao == null) return null;

        return new SessaoDTO(
                sessao.getId(),
                filmeMapper.toSimpleDTO(sessao.getFilme()),
                salaMapper.toDTO(sessao.getSala()),
                sessao.getDataHora(),
                sessao.getPrecoBase(),
                sessao.getTipoExibicao(),
                sessao.getStatus(),
                sessao.getCreatedAt(),
                sessao.getUpdatedAt()
        );
    }

    public List<SessaoDTO> toDTOList(List<Sessao> sessoes) {
        return sessoes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}