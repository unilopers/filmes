package com.unilopers.cinema.mapper;

import com.unilopers.cinema.model.Sala;
import com.unilopers.cinema.dto.request.CreateSalaDTO;
import com.unilopers.cinema.dto.response.SalaDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalaMapper {

    public SalaDTO toDTO(Sala sala) {
        if (sala == null) return null;
        return new SalaDTO(
            sala.getId(), 
            sala.getNome(), 
            sala.getCapacidade(), 
            sala.getDisponivel()
        );
    }

    public Sala toEntity(CreateSalaDTO dto) {
        if (dto == null) return null;
        return new Sala(dto.getNome(), dto.getCapacidade());
    }

    public void updateEntity(Sala sala, CreateSalaDTO dto) {
        if (dto.getNome() != null) sala.setNome(dto.getNome());
        if (dto.getCapacidade() != null) sala.setCapacidade(dto.getCapacidade());
    }

    public List<SalaDTO> toDTOList(List<Sala> salas) {
        return salas.stream().map(this::toDTO).collect(Collectors.toList());
    }
}