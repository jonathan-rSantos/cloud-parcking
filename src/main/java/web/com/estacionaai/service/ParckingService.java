package web.com.estacionaai.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.com.estacionaai.exception.ParkingNotFoundException;
import web.com.estacionaai.model.Estacionamento;
import web.com.estacionaai.repository.ParckingRepository;

@Service
public class ParckingService {

	 private final ParckingRepository parkingRepository;

	    public ParckingService(ParckingRepository parkingRepository) {
	        this.parkingRepository = parkingRepository;
	    }

	    @Transactional(readOnly = true)
	    public List<Estacionamento> findAll() {
	        return parkingRepository.findAll();
	    }

	    @Transactional(readOnly = true)
	    public Estacionamento findById(String id) {
	        return parkingRepository.findById(id).orElseThrow(
	                () -> new ParkingNotFoundException(id));
	    }

	    @Transactional
	    public Estacionamento create(Estacionamento parkingCreate) {
	        String uuid = getUUID();
	        parkingCreate.setId(uuid);
	        parkingCreate.setEntryDate(LocalDateTime.now());
	        parkingRepository.save(parkingCreate);
	        return parkingCreate;
	    }

	    @Transactional
	    public void delete(String id) {
	        findById(id);
	        parkingRepository.deleteById(id);
	    }

	    @Transactional
	    public Estacionamento update(String id, Estacionamento parkingCreate) {
	        Estacionamento parking = findById(id);
	        parking.setColor(parkingCreate.getColor());
	        parking.setState(parkingCreate.getState());
	        parking.setModel(parkingCreate.getModel());
	        parking.setLicense(parkingCreate.getLicense());
	        parkingRepository.save(parking);
	        return parking;
	    }

	    @Transactional
	    public Estacionamento checkOut(String id) {
	        Estacionamento parking = findById(id);
	        parking.setExitDate(LocalDateTime.now());
	        parking.setBill(ParckingCheckOut.getBill(parking));
	        return parkingRepository.save(parking);
	    }

	    private static String getUUID() {
	        return UUID.randomUUID().toString().replace("-", "");
	    }
}
