package com.codegym.service;

import com.codegym.dto.TransactionDto;
import com.codegym.entity.Customer;
import com.codegym.entity.Transaction;
import com.codegym.repository.CustomerRepository;
import com.codegym.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepo;
    private final CustomerRepository customerRepo;

    public Page<Transaction> search(String customerName, String serviceType, Pageable pageable) {
        return transactionRepo.searchTransactions(customerName, serviceType, pageable);
    }

    public Transaction findById(String id) {
        return transactionRepo.findById(id).orElse(null);
    }

    public List<Customer> findAllCustomers() {
        return customerRepo.findAll();
    }

    public void save(TransactionDto dto) {
        Customer customer = customerRepo.findById(String.valueOf(dto.getCustomerId()))
                .orElseThrow(() -> new IllegalArgumentException("Khách hàng không tồn tại"));

        // Không cập nhật email của Customer - email là thuộc tính của Customer,
        // không bị ghi đè khi tạo/sửa giao dịch.

        Transaction t = new Transaction();
        t.setId(dto.getId());
        t.setCustomer(customer);
        t.setTransactionDate(dto.getTransactionDate());
        t.setServiceType(dto.getServiceType());
        t.setPrice(dto.getPrice());
        t.setArea(dto.getArea());

        transactionRepo.save(t);
    }

    public void delete(String id) {
        transactionRepo.deleteById(id);
    }

    public boolean existsById(String id) {
        return transactionRepo.existsById(id);
    }
}
