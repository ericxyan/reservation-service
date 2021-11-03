package com.example.reservationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@SpringBootApplication
public class ReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
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

	@Override
	public String toString() {
		return "Reservation{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}