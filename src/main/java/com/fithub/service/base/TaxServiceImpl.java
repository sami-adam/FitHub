package com.fithub.service.base;

import com.fithub.dto.base.TaxDTO;
import com.fithub.model.base.Tax;
import com.fithub.repository.base.TaxRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaxServiceImpl implements TaxService {
    private final TaxRepository taxRepository;
    private final ModelMapper mapper;

    public TaxServiceImpl(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public TaxDTO addTax(TaxDTO taxDTO) {
        taxRepository.save(mapper.map(taxDTO, Tax.class));
        return mapper.map(taxDTO, TaxDTO.class);
    }

    @Override
    public TaxDTO updateTax(Long id, TaxDTO taxDTO) {
        Tax tax = taxRepository.findById(id).orElseThrow();
        if(taxDTO.getName() != null) {
            tax.setName(taxDTO.getName());
        }
        if(taxDTO.getCode() != null) {
            tax.setCode(taxDTO.getCode());
        }
        if(taxDTO.getRate() != 0) {
            tax.setRate(taxDTO.getRate());
        }
        if(taxDTO.isActive() != tax.isActive()) {
            tax.setActive(taxDTO.isActive());
        }
        taxRepository.save(tax);
        return mapper.map(tax, TaxDTO.class);
    }

    @Override
    public Map<String, String> deleteTax(Long taxId) {
        taxRepository.deleteById(taxId);
        return Map.of("message", "Tax deleted successfully", "status", "success");
    }

    @Override
    public List<TaxDTO> getAllTaxes() {
        return taxRepository.findAll().stream().map(tax -> mapper.map(tax, TaxDTO.class)).toList();
    }
}
