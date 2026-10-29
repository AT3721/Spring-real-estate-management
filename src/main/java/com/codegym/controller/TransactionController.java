package com.codegym.controller;

import com.codegym.dto.TransactionDto;
import com.codegym.entity.Transaction;
import com.codegym.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Double.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.trim().isEmpty()) {
                    setValue(null);
                } else {
                    setValue(Double.parseDouble(text.replace(",", "")));
                }
            }
        });
    }

    // 1. Hiển thị danh sách + Tìm kiếm + Phân trang
    @GetMapping
    public String list(@RequestParam(defaultValue = "") String customerName,
                       @RequestParam(defaultValue = "") String serviceType,
                       @PageableDefault(size = 5) Pageable pageable,
                       Model model) {
        Page<Transaction> transactionPage = transactionService.search(customerName, serviceType, pageable);
        model.addAttribute("transactionPage", transactionPage);
        model.addAttribute("customerName", customerName);
        model.addAttribute("serviceType", serviceType);
        return "list";
    }

    // 2. Xem chi tiết giao dịch
    @GetMapping("/{id}")
    public String showDetail(@PathVariable String id, Model model) {
        Transaction transaction = transactionService.findById(id);
        if (transaction == null) {
            return "redirect:/transactions";
        }
        model.addAttribute("transaction", transaction);
        return "detail";
    }

    // 3. Form thêm mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("transactionDto", new TransactionDto());
        model.addAttribute("customers", transactionService.findAllCustomers());
        return "create";
    }

    // 4. Xử lý thêm mới
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("transactionDto") TransactionDto dto,
                         BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (transactionService.existsById(dto.getId())) {
            result.rejectValue("id", "error.transactionDto", "Mã giao dịch đã tồn tại");
        }
        if (result.hasErrors()) {
            model.addAttribute("customers", transactionService.findAllCustomers());
            return "create";
        }
        transactionService.save(dto);
        redirectAttributes.addFlashAttribute("success", "Thêm mới giao dịch thành công!");
        return "redirect:/transactions";
    }

    // 5. Form chỉnh sửa
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Transaction transaction = transactionService.findById(id);
        if (transaction == null) {
            return "redirect:/transactions";
        }

        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setCustomerId(transaction.getCustomer().getId());
        dto.setEmail(transaction.getCustomer().getEmail());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setServiceType(transaction.getServiceType());
        dto.setPrice(transaction.getPrice());
        dto.setArea(transaction.getArea());

        model.addAttribute("transactionDto", dto);
        model.addAttribute("customers", transactionService.findAllCustomers());
        return "edit";
    }

    // 6. Xử lý chỉnh sửa
    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id, @Valid @ModelAttribute("transactionDto") TransactionDto dto,
                         BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("customers", transactionService.findAllCustomers());
            return "edit";
        }
        transactionService.save(dto);
        redirectAttributes.addFlashAttribute("success", "Cập nhật giao dịch thành công!");
        return "redirect:/transactions";
    }

    // 7. Xử lý xóa
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        transactionService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Xóa giao dịch thành công!");
        return "redirect:/transactions";
    }
}
