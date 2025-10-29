package br.com.serratec.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.serratec.dto.ItemPedidoRequestDTO;
import br.com.serratec.dto.ItemPedidoResponseDTO;
import br.com.serratec.entity.ItemPedido;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
	ItemPedidoMapper INSTANCE = Mappers.getMapper(ItemPedidoMapper.class);
	
    ItemPedidoRequestDTO toDto(ItemPedido itemPedido);
    ItemPedido toEntity(ItemPedidoRequestDTO itemPedidoRequestDTO);

    ItemPedidoResponseDTO toResponseDto(ItemPedido itemPedido);
    ItemPedido toEntity(ItemPedidoResponseDTO itemPedidoResponseDTO);

}