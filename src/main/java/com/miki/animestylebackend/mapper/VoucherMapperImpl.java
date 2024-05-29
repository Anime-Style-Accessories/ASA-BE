package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.VoucherDto;
import com.miki.animestylebackend.dto.VoucherData;
import com.miki.animestylebackend.model.Voucher;
import org.springframework.stereotype.Service;

@Service
public class VoucherMapperImpl implements VoucherMapper {

    @Override
    public VoucherDto toDto(Voucher voucher, String message) {
        if (voucher == null) {
            return null;
        }

        VoucherData voucherData = new VoucherData();
        voucherData.setId(voucher.getId());
        voucherData.setCode(voucher.getCode());
        voucherData.setDiscount(voucher.getDiscount());
        voucherData.setExpirationDate(voucher.getExpirationDate());
        voucherData.setDescription(voucher.getDescription());
        voucherData.setTitle(voucher.getTitle());
        voucherData.setQuantity(voucher.getQuantity());

        VoucherDto voucherDto = new VoucherDto();
        voucherDto.setSuccess(true);
        voucherDto.setStatusCode(200);
        voucherDto.setMessage(message);
        voucherDto.setData(voucherData);

        return voucherDto;
    }

    @Override
    public VoucherData toVoucherData(Voucher voucher) {
        if (voucher == null) {
            return null;
        }

        VoucherData voucherData = new VoucherData();
        voucherData.setId(voucher.getId());
        voucherData.setCode(voucher.getCode());
        voucherData.setDiscount(voucher.getDiscount());
        voucherData.setExpirationDate(voucher.getExpirationDate());
        voucherData.setDescription(voucher.getDescription());
        voucherData.setTitle(voucher.getTitle());
        voucherData.setQuantity(voucher.getQuantity());

        return voucherData;
    }
}