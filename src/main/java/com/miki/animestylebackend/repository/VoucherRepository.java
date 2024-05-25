package com.miki.animestylebackend.repository;

import com.miki.animestylebackend.dto.CreateVoucherRequest;
import com.miki.animestylebackend.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, UUID> {

    Voucher findByCode(String code);
    List<Voucher> findByCodeContaining(String name);

    boolean existsByCode(String code);
}

