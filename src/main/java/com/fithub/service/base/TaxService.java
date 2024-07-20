package com.fithub.service.base;

import com.fithub.dto.base.TaxDTO;

import java.util.List;
import java.util.Map;

public interface TaxService {
    public TaxDTO addTax(TaxDTO taxDTO);
    public TaxDTO updateTax(Long id, TaxDTO taxDTO);
    public Map<String, String> deleteTax(Long taxId);
    public List<TaxDTO> getAllTaxes();
}
