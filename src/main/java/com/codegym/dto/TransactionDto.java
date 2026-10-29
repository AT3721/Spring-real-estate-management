package com.codegym.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionDto {

    @NotBlank(message = "Mã giao dịch không được để trống")
    @Pattern(regexp = "^MGD-\\d{4}$", message = "Mã giao dịch phải đúng định dạng MGD-XXXX")
    private String id;

    @NotNull(message = "Vui lòng chọn khách hàng")
    private String customerId;

    // Email chỉ dùng để hiển thị (readonly/auto-fill từ Customer), không validate khi submit
    private String email;

    @NotBlank(message = "Vui lòng chọn loại dịch vụ")
    private String serviceType;

    @NotNull(message = "Ngày giao dịch không được để trống")
    private LocalDate transactionDate;

    @NotNull(message = "Đơn giá không được để trống")
    @Min(value = 500000, message = "Đơn giá phải lớn hơn 500.000 VNĐ")
    @NumberFormat(pattern = "#,###.##")
    private Double price;

    @NotNull(message = "Diện tích không được để trống")
    @Min(value = 20, message = "Diện tích phải lớn hơn 20 m2")
    @NumberFormat(pattern = "#,###.##")
    private Double area;
}
