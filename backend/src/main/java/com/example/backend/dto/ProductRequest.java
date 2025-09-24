package com.example.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm tối đa 255 ký tự")
    private String name;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 2000, message = "Mô tả tối đa 2000 ký tự")
    private String description;

    @NotBlank(message = "Hình ảnh không được để trống")
    private String images;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải >= 0")
    private Integer stock;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải > 0")
    private BigDecimal price;

    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;

    @NotNull(message = "Nhà cung cấp không được để trống")
    private Long supplierId;
}
