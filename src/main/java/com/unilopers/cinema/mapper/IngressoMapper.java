package com.unilopers.cinema.mapper;

import com.unilopers.cinema.model.Ingresso;
import com.unilopers.cinema.dto.response.IngressoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngressoMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private SessaoMapper sessaoMapper;

    @Autowired
    private TipoIngressoMapper tipoIngressoMapper;

    public IngressoDTO toDTO(Ingresso ingresso) {
        if (ingresso == null) return null;

        return new IngressoDTO(
                ingresso.getId(),
                usuarioMapper.toDTO(ingresso.getUsuario()),
                sessaoMapper.toDTO(ingresso.getSessao()),
                tipoIngressoMapper.toDTO(ingresso.getTipoIngresso()),
                ingresso.getIdAssento(),
                ingresso.getValorFinal(),
                ingresso.getStatus(),
                ingresso.getCreatedAt()
        );
    }

    public List<IngressoDTO> toDTOList(List<Ingresso> ingressos) {
        return ingressos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}