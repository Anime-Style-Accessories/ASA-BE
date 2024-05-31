package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.CreateVoucherRequest;
import com.miki.animestylebackend.dto.UpdateVoucherRequest;
import com.miki.animestylebackend.dto.VoucherData;
import com.miki.animestylebackend.dto.VoucherDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.exception.VoucherDuplicateException;
import com.miki.animestylebackend.exception.VoucherNotFoundException;
import com.miki.animestylebackend.exception.VoucherOutOfStockException;
import com.miki.animestylebackend.mapper.VoucherMapper;
import com.miki.animestylebackend.model.Voucher;
import com.miki.animestylebackend.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService{
    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;
    @Override
    public Voucher getVoucherByCode(String code) {
        return voucherRepository.findByCode(code);
    }

    @Override
    public PageData<VoucherData> getVoucherByCodeContaining(String name, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<VoucherData> vouchers = voucherRepository.findByCodeContaining(name, pageable).map(voucherMapper::toVoucherData);
        return new PageData<>(vouchers, "Voucher found successfully.");
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
        if (voucher.getQuantity() > 0) {
            voucher.setQuantity(voucher.getQuantity() - 1);
            voucherRepository.save(voucher);
        }
        else {
            voucher.setUsed(true);
            throw new VoucherOutOfStockException("Voucher is out of stock.");
        }
    }

    @Override
    public VoucherDto getVoucherById(UUID id) {
        return voucherRepository.findById(id).map(voucher -> voucherMapper.toDto(voucher, "Voucher found successfully."))
                .orElseThrow(() -> new VoucherNotFoundException("Voucher by id " + id + " was not found.)"));
    }



    @Override
    public PageData<VoucherData> getAllVouchers(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<VoucherData> voucherDtoPage = voucherRepository.findAll(pageable).map(voucherMapper::toVoucherData);
        return new PageData<>(voucherDtoPage, "Get all vouchers successfully.");
    }

    @Override
    public URI createVoucher(CreateVoucherRequest createVoucherRequest) {
        if(voucherRepository.existsByCode(createVoucherRequest.getCode())) {
            throw new VoucherDuplicateException("Voucher with code " + createVoucherRequest.getCode() + " already exists.");
        }
        Voucher voucher = new Voucher();
        voucher.setCode(createVoucherRequest.getCode());
        voucher.setTitle(createVoucherRequest.getTitle());
        voucher.setDescription(createVoucherRequest.getDescription());
        voucher.setDiscount(createVoucherRequest.getDiscount());
        voucher.setQuantity(createVoucherRequest.getQuantity());
        voucher.setUsed(false);
        voucherRepository.save(voucher);
        return URI.create("/api/v1/vouchers/" + voucher.getId());
    }

    @Override
    public VoucherDto updateVoucher(UUID uuid, UpdateVoucherRequest updateVoucherRequest) {
//        if(voucherRepository.existsByCode(updateVoucherRequest.getCode())) {
//            throw new VoucherDuplicateException("Voucher with code " + updateVoucherRequest.getCode() + " already exists.");
//        }
        Voucher voucher = voucherRepository.findById(uuid)
                .orElseThrow(() -> new VoucherNotFoundException("Voucher by id " + uuid + " was not found."));
        voucher.setCode(updateVoucherRequest.getCode());
        voucher.setTitle(updateVoucherRequest.getTitle());
        voucher.setDescription(updateVoucherRequest.getDescription());
        voucher.setDiscount(updateVoucherRequest.getDiscount());
        voucher.setQuantity(updateVoucherRequest.getQuantity());
        return voucherMapper.toDto(voucherRepository.save(voucher), "Voucher updated successfully.");
    }

    @Override
    public void deleteVoucher(UUID uuid) {
        Voucher voucher = voucherRepository.findById(uuid)
                .orElseThrow(() -> new VoucherNotFoundException("Voucher by id " + uuid + " was not found."));
        voucherRepository.delete(voucher);
    }
}
