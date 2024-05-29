package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.VoucherData;
import com.miki.animestylebackend.dto.VoucherDto;
import com.miki.animestylebackend.model.Voucher;

public interface VoucherMapper {
    VoucherDto toDto(Voucher voucher, String message);

    VoucherData toVoucherData(Voucher voucher);
}
