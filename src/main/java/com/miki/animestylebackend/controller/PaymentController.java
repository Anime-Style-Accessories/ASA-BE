package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.config.Config;
import com.miki.animestylebackend.dto.PaymentResDTO;
import com.miki.animestylebackend.dto.TransactionStatusDTO;
import com.miki.animestylebackend.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.miki.animestylebackend.config.Config.*;



@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/create_payment")
    public ResponseEntity<?> createPayment(HttpServletRequest req, @RequestParam("amount") BigDecimal amount) throws UnsupportedEncodingException {
        PaymentResDTO paymentResDTO = paymentService.createPayment(req, amount);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);
    }

    @GetMapping("/vnpay_return")
    public ResponseEntity<?> transaction(@RequestParam(value = "vnpay_Amount") String amount,
                                         @RequestParam(value = "vnpay_BankCode") String bankCode,
                                         @RequestParam(value = "vnpay_OrderInfo") String orderInfo,
                                         @RequestParam(value = "vnpay_ResponseCode") String responseCode) {
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        if (responseCode.equals("00")) {
            //Kiem tra xem du lieu trong db co hop le hay khong va thong bao ket qua
            //Cap nhat lai trang thai don hang trong db
            //Tra lai cho VNPAY
            transactionStatusDTO.setStatus("ok");
            transactionStatusDTO.setMessage("success");
            transactionStatusDTO.setData("Thanh cong");
//                resp.getWriter().println("vnp_ResponseCode=00");
        } else {
            transactionStatusDTO.setStatus("fail");
            transactionStatusDTO.setMessage("fail");
            transactionStatusDTO.setData("Thanh toan that bai");
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDTO);
    }


}

