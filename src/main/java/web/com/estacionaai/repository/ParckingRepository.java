package web.com.estacionaai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.com.estacionaai.model.Estacionamento;

@Repository
public interface ParckingRepository extends JpaRepository<Estacionamento, String> {
	
	
}