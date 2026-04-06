package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleMinDTO> getReport(LocalDate minDate, LocalDate maxDate, String name) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate effectiveMax = maxDate != null ? maxDate : today;
		LocalDate effectiveMin = minDate != null ? minDate : effectiveMax.minusYears(1L);
		String nameParam = (name != null && !name.isBlank()) ? name.trim() : null;

		List<Sale> sales = repository.searchReport(effectiveMin, effectiveMax, nameParam);
		return sales.stream().map(SaleMinDTO::new).toList();
	}

}
