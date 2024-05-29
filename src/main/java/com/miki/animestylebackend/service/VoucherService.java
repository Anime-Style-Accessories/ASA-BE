package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.CreateVoucherRequest;
import com.miki.animestylebackend.dto.UpdateVoucherRequest;
import com.miki.animestylebackend.dto.VoucherData;
import com.miki.animestylebackend.dto.VoucherDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.Voucher;

import java.net.URI;
import java.util.UUID;

public interface VoucherService {
    Voucher getVoucherByCode(String code);
    PageData<VoucherData> getVoucherByCodeContaining(String name, int page, int pageSize);
    Integer getVoucherValueByCode(String voucherCode);
    void useVoucher(Voucher voucher);
    VoucherDto getVoucherById(UUID id);
    PageData<VoucherData> getAllVouchers(int page, int pageSize);
    URI createVoucher(CreateVoucherRequest voucher);

    VoucherDto updateVoucher(UUID uuid, UpdateVoucherRequest updateVoucherRequest);

    void deleteVoucher(UUID uuid);
}
