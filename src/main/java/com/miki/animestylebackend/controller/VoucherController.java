package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.CreateVoucherRequest;
import com.miki.animestylebackend.dto.UpdateVoucherRequest;
import com.miki.animestylebackend.dto.VoucherData;
import com.miki.animestylebackend.dto.VoucherDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.page.SortOrder;
import com.miki.animestylebackend.model.Voucher;
import com.miki.animestylebackend.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vouchers")
public class VoucherController extends BaseController{

    private final VoucherService voucherService;

    @GetMapping("/getAll")
    public ResponseEntity<PageData<VoucherData>> getAllVoucher(@RequestParam(value = "name", required = false) String name,
                                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                              @RequestParam(value = "sortBy", required = false, defaultValue = "discount") String discount,
                                                              @RequestParam(value = "sort", required = false, defaultValue = "ASC") SortOrder.Direction sort) {
        List<Voucher> vouchers;
        if (name != null) {
            return ResponseEntity.ok(voucherService.getVoucherByCodeContaining(name, page, pageSize));
        } else {
            return ResponseEntity.ok(voucherService.getAllVouchers(page, pageSize));
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<VoucherDto> getVoucherById(@PathVariable UUID id) {
        return ResponseEntity.ok(voucherService.getVoucherById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<URI> createVoucher(@RequestBody CreateVoucherRequest voucher) {
        return ResponseEntity.created(voucherService.createVoucher(voucher)).build();
    }

    @GetMapping("/check_voucher")
    public ResponseEntity<Integer> getVoucherByCode(@RequestParam("voucherCode") String voucherCode) {
        Voucher voucher = voucherService.getVoucherByCode(voucherCode);
        if (voucher != null) {
            return ResponseEntity.ok(voucher.getDiscount());
        } else {
            return ResponseEntity.ok(0);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoucherDto> updateVoucher(@PathVariable UUID id, @RequestBody UpdateVoucherRequest updateVoucherRequest) {
        return ResponseEntity.ok(voucherService.updateVoucher(id, updateVoucherRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable UUID id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }
}