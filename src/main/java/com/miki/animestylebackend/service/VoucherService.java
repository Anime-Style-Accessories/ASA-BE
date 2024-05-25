package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.CreateVoucherRequest;
import com.miki.animestylebackend.dto.UpdateVoucherRequest;
import com.miki.animestylebackend.model.Voucher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherService {
    Voucher getVoucherByCode(String code);
    List<Voucher> getVoucherByCodeContaining(String name);
    Integer getVoucherValueByCode(String voucherCode);
    void useVoucher(Voucher voucher);
    Voucher getVoucherById(UUID id);
    List<Voucher> getAllVouchers();
    Voucher createVoucher(CreateVoucherRequest voucher);

    Voucher updateVoucher(UpdateVoucherRequest updateVoucherRequest);

    void deleteVoucher(Voucher voucher);
}
