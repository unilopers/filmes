package com.unilopers.cinema.mapper;

import com.unilopers.cinema.model.Usuario;
import com.unilopers.cinema.dto.request.CreateUsuarioDTO;
import com.unilopers.cinema.dto.response.UsuarioDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt()
        );
    }

    public Usuario toEntity(CreateUsuarioDTO dto) {
        if (dto == null) return null;
        return new Usuario(dto.getNome(), dto.getEmail());
    }

    public void updateEntity(Usuario usuario, CreateUsuarioDTO dto) {
        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
    }

    public List<UsuarioDTO> toDTOList(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toDTO).collect(Collectors.toList());
    }
}