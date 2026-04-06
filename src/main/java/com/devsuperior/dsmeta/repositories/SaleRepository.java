package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT obj FROM Sale obj WHERE obj.date BETWEEN :minDate AND :maxDate "
			+ "AND (:name IS NULL OR LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%'))) "
			+ "ORDER BY obj.id ASC")
	List<Sale> searchReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate,
			@Param("name") String name);
}
