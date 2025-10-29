package br.com.serratec.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.serratec.dto.PedidoRequestDTO;
import br.com.serratec.dto.PedidoResponseDTO;
import br.com.serratec.entity.Pedido;
import br.com.serratec.enums.StatusPedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    // RequestDTO -> entidade
    // REMOVIDO mapeamento de "status" porque o DTO não possui esse campo
    Pedido toEntity(PedidoRequestDTO dto);

    // Entidade -> ResponseDTO
    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "clienteNome", source = "cliente.nome")
    @Mapping(target = "status", source = "status") // StatusPedido -> String via método abaixo
    PedidoResponseDTO toResponseDto(Pedido pedido);

    // Conversor auxiliar String -> Enum (caso no futuro precisar)
    default StatusPedido map(String value) {
        if (value == null) return null;
        return StatusPedido.verificaStatusPedido(value);
    }

    // Conversor auxiliar Enum -> String (usado em toResponseDto)
    default String map(StatusPedido status) {
        return status == null ? null : status.name();
    }
}
