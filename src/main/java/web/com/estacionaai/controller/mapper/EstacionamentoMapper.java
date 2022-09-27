package web.com.estacionaai.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


import web.com.estacionaai.controller.dto.EstacionamentoCreateDTO;
import web.com.estacionaai.controller.dto.EstacionamentoDTO;
import web.com.estacionaai.model.Estacionamento;

@Component
public class EstacionamentoMapper {

	 private static final ModelMapper MODEL_MAPPER = new ModelMapper();

	    public EstacionamentoDTO toEstacionamentoDTO(Estacionamento parking) {
	        return MODEL_MAPPER.map(parking, EstacionamentoDTO.class);
	    }

	    public List<EstacionamentoDTO> toParkingDTOList(List<Estacionamento> parkingList) {
	        return parkingList.stream().map(this::toEstacionamentoDTO).collect(Collectors.toList());
	    }

	    public Estacionamento toParking(EstacionamentoDTO dto) {
	        return MODEL_MAPPER.map(dto, Estacionamento.class);
	    }

	    public Estacionamento toParkingCreate(EstacionamentoCreateDTO dto) {
	        return MODEL_MAPPER.map(dto, Estacionamento.class);
	    }
	    
}
