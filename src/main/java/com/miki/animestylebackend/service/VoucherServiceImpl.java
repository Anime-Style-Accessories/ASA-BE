package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.CreateVoucherRequest;
import com.miki.animestylebackend.dto.UpdateVoucherRequest;
import com.miki.animestylebackend.exception.VoucherDuplicateException;
import com.miki.animestylebackend.exception.VoucherNotFoundException;
import com.miki.animestylebackend.model.Voucher;
import com.miki.animestylebackend.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService{
    private final VoucherRepository voucherRepository;

    @Override
    public Voucher getVoucherByCode(String code) {
        return voucherRepository.findByCode(code);
    }

    @Override
    public List<Voucher> getVoucherByCodeContaining(String name) {
        return voucherRepository.findByCodeContaining(name);
    }

    @Override
    public Integer getVoucherValueByCode(String voucherCode) {
        Voucher voucher = voucherRepository.findByCode(voucherCode);
        if(voucher!=null){
            return voucher.getDiscount();
        }
        else {
            return null;
        }
    }

    public void useVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    @Override
    public Voucher getVoucherById(UUID id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new VoucherNotFoundException("Voucher by id " + id + " was not found.)"));
    }



    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher createVoucher(CreateVoucherRequest createVoucherRequest) {
        if(voucherRepository.existsByCode(createVoucherRequest.getCode())) {
            throw new VoucherDuplicateException("Voucher with code " + createVoucherRequest.getCode() + " already exists.");
        }
        Voucher voucher = new Voucher();
        voucher.setCode(createVoucherRequest.getCode());
        voucher.setTitle(createVoucherRequest.getTitle());
        voucher.setDescription(createVoucherRequest.getDescription());
        voucher.setDiscount(createVoucherRequest.getDiscount());
        voucher.setQuantity(createVoucherRequest.getQuantity());

        return voucherRepository.save(voucher);

    }

    @Override
    public Voucher updateVoucher(UpdateVoucherRequest updateVoucherRequest) {
        if(voucherRepository.existsByCode(updateVoucherRequest.getCode())) {
            throw new VoucherDuplicateException("Voucher with code " + updateVoucherRequest.getCode() + " already exists.");
        }
        Voucher voucher = voucherRepository.findByCode(updateVoucherRequest.getOldCode());
        voucher.setCode(updateVoucherRequest.getCode());
        voucher.setTitle(updateVoucherRequest.getTitle());
        voucher.setDescription(updateVoucherRequest.getDescription());
        voucher.setDiscount(updateVoucherRequest.getDiscount());
        voucher.setQuantity(updateVoucherRequest.getQuantity());
        return voucherRepository.save(voucher);
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        voucherRepository.delete(voucher);
    }
}
