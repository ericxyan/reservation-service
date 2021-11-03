package com.example.reservationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class ReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}

}

@RestController
class ReservationRestController {
	private final ReservationRepository reservationRepository;

	@Autowired
	public ReservationRestController(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Reservation> reservations() {
		List<Reservation> all = this.reservationRepository.findAll();

		return all;
	}
}

@Component
class SampleDataCLR implements CommandLineRunner {
	private final ReservationRepository reservationRepository;

	@Autowired
	SampleDataCLR(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		Stream.of("Eric", "Heyhey", "Maymay", "Kat").forEach(name -> reservationRepository.save(new Reservation(name)));

		reservationRepository.findAll().forEach(System.out::println);
	}
}

interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Collection<Reservation> findByName(String name);
}

@Entity
class Reservation {
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	public Reservation() {
	}

	public Reservation(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Reservation{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}