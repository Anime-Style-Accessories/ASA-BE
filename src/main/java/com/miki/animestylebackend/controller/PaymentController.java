package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.config.Config;
import com.miki.animestylebackend.dto.PaymentResDTO;
import com.miki.animestylebackend.dto.TransactionStatusDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.miki.animestylebackend.config.Config.*;



@Controller
@RequestMapping("/api/payment")
public class PaymentController {
    @GetMapping("/create_payment")
    public ResponseEntity<?> createPayment(HttpServletRequest req, @RequestParam("amount") Long amount) throws UnsupportedEncodingException {
        String orderType = "other";
//        long amount = Integer.parseInt(req.getParameter("amount")) * 100;
//        String bankCode = req.getParameter("bankCode");

        amount *= 100;

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(req);

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        PaymentResDTO paymentResDTO = new PaymentResDTO();
        paymentResDTO.setStatus("ok");
        paymentResDTO.setMessage("success");
        paymentResDTO.setURL(paymentUrl);

//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));
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
