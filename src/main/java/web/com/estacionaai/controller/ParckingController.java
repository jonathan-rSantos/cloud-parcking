package web.com.estacionaai.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import web.com.estacionaai.controller.dto.EstacionamentoCreateDTO;
import web.com.estacionaai.controller.dto.EstacionamentoDTO;
import web.com.estacionaai.controller.mapper.EstacionamentoMapper;
import web.com.estacionaai.model.Estacionamento;
import web.com.estacionaai.service.ParckingService;

@RestController
@RequestMapping("/parking")
@Api(tags = "Parking Controller")
public class ParckingController {

	private final ParckingService parkingService;
	private final EstacionamentoMapper parkingMapper;

	public ParckingController(ParckingService parkingService, EstacionamentoMapper parkingMapper) {
		this.parkingService = parkingService;
		this.parkingMapper = parkingMapper;
	}

	@GetMapping
	@ApiOperation("Find all parkings")
	public ResponseEntity<List<EstacionamentoDTO>> findAll() {
		List<Estacionamento> parkingList = parkingService.findAll();
		List<EstacionamentoDTO> result = parkingMapper.toParkingDTOList(parkingList);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstacionamentoDTO> findById(@PathVariable String id) {
		Estacionamento parking = parkingService.findById(id);
		EstacionamentoDTO result = parkingMapper.toEstacionamentoDTO(parking);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable String id) {
		parkingService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<EstacionamentoDTO> create(@RequestBody EstacionamentoCreateDTO dto) {
		var parkingCreate = parkingMapper.toParkingCreate(dto);
		var parking = parkingService.create(parkingCreate);
		var result = parkingMapper.toEstacionamentoDTO(parking);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EstacionamentoDTO> update(@PathVariable String id,
			@RequestBody EstacionamentoCreateDTO parkingCreteDTO) {
		Estacionamento parkingUpdate = parkingMapper.toParkingCreate(parkingCreteDTO);
		Estacionamento parking = parkingService.update(id, parkingUpdate);
		return ResponseEntity.ok(parkingMapper.toEstacionamentoDTO(parking));
	}

	@PostMapping("/{id}/exit")
	public ResponseEntity<EstacionamentoDTO> checkOut(@PathVariable String id) {
		// TODO verificar se já não esta fechado e lançar exceção
		Estacionamento parking = parkingService.checkOut(id);
		return ResponseEntity.ok(parkingMapper.toEstacionamentoDTO(parking));
	}

}