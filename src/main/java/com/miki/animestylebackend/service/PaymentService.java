package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.PaymentResDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

public interface PaymentService {
    PaymentResDTO createPayment(HttpServletRequest req, BigDecimal amount) throws UnsupportedEncodingException;
}

