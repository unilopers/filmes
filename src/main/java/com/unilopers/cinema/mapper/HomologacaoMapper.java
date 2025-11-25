package com.unilopers.cinema.mapper;

import com.unilopers.cinema.dto.response.HomologacaoDTO;
import com.unilopers.cinema.model.Homologacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HomologacaoMapper {

    @Autowired
    private FilmeMapper filmeMapper;

    @Autowired
    private SalaMapper salaMapper;

    public HomologacaoDTO toDTO(Homologacao homologacao) {
        if (homologacao == null) return null;

        return new HomologacaoDTO(
                homologacao.getId(),
                filmeMapper.toSimpleDTO(homologacao.getFilme()),
                salaMapper.toDTO(homologacao.getSala()),
                homologacao.getRequisitoTecnico(),
                homologacao.getStatusValidacao(),
                homologacao.getDataAnalise()
        );
    }

    public List<HomologacaoDTO> toDTOList(List<Homologacao> lista) {
        return lista.stream().map(this::toDTO).collect(Collectors.toList());
    }
}