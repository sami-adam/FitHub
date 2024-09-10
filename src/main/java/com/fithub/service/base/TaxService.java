package com.fithub.service.base;

import com.fithub.dto.base.TaxDTO;

import java.util.List;
import java.util.Map;

public interface TaxService {
    TaxDTO addTax(TaxDTO taxDTO);
    TaxDTO updateTax(Long id, TaxDTO taxDTO);
    Map<String, String> deleteTax(Long taxId);
    List<TaxDTO> getAllTaxes();
    TaxDTO getTaxById(Long taxId);
}
