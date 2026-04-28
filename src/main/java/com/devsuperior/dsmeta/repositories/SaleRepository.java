package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT obj FROM Sale obj WHERE obj.date BETWEEN :minDate AND :maxDate "
			+ "AND (:name IS NULL OR LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%')))")
	Page<Sale> searchReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate,
			@Param("name") String name, Pageable pageable);

	@Query("SELECT new com.devsuperior.dsmeta.dto.SellerMinDTO(s.seller.name, SUM(s.amount)) "
			+ "FROM Sale s "
			+ "WHERE s.date BETWEEN :minDate AND :maxDate "
			+ "GROUP BY s.seller.id, s.seller.name "
			+ "ORDER BY s.seller.name")
	List<SellerMinDTO> searchSummary(
			@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);

}
