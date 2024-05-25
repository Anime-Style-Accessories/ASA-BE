package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.CreateVoucherRequest;
import com.miki.animestylebackend.model.Voucher;
import com.miki.animestylebackend.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping("/getAll")
    public List<Voucher> getAllVoucher(@RequestParam(value = "name", required = false) String name) {
        List<Voucher> vouchers;
        if (name != null) {
            return voucherService.getVoucherByCodeContaining(name);
        } else {
            return voucherService.getAllVouchers();
        }
    }

    @GetMapping("/getById/{id}")
    public Voucher getVoucherById(@PathVariable UUID id) {
        return voucherService.getVoucherById(id);
    }

    @PostMapping("/create")
    public Voucher createVoucher(@RequestBody CreateVoucherRequest voucher) {
        return voucherService.createVoucher(voucher);
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

//    @PostMapping("/{code}/use")
//    public ResponseEntity<Void> useVoucher(@PathVariable("code") String code) {
//        Voucher voucher = voucherService.getVoucherByCode(code);
//        if (voucher != null && voucher.getStatus() == VoucherStatus.UNUSED) {
//            voucherService.useVoucher(voucher);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
}