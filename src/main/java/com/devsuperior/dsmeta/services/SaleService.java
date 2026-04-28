package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(@NonNull Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> getReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate effectiveMax = maxDate != null ? maxDate : today;
		LocalDate effectiveMin = minDate != null ? minDate : effectiveMax.minusYears(1L);
		String nameParam = (name != null && !name.isBlank()) ? name.trim() : null;

		Page<Sale> sales = repository.searchReport(effectiveMin, effectiveMax, nameParam, pageable);
		return sales.map(SaleMinDTO::new);
	}

	public List<SellerMinDTO> getSummary(LocalDate minDate, LocalDate maxDate) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate effectiveMax = maxDate != null ? maxDate : today;
		LocalDate effectiveMin = minDate != null ? minDate : effectiveMax.minusYears(1L);

		return repository.searchSummary(effectiveMin, effectiveMax);
	}
	

}
