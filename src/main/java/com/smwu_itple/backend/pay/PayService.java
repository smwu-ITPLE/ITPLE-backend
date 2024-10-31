package com.smwu_itple.backend.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PayService {
    @Autowired
    private PayRepository payRepository;

    public Pay savePay(Pay pay) {
        return payRepository.save(pay);
    }

    public Optional<Pay> getPayById(Long id) {
        return payRepository.findById(id);
    }

    public List<Pay> getAllPays() {
        return payRepository.findAll();
    }
}
