package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.VoucherDto;
import com.miki.animestylebackend.model.Voucher;
import org.springframework.stereotype.Service;

@Service
public class VoucherMapperImpl implements VoucherMapper{

    @Override
    public VoucherDto toDto(Voucher voucher) {
        return new VoucherDto(voucher.getId(), voucher.getCode(), voucher.getDiscount(), voucher.getExpirationDate(), voucher.getDescription(), voucher.getTitle(), voucher.getQuantity());
    }
}
