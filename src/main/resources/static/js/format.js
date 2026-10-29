// Hàm tự động format dấu phẩy khi gõ và xử lý định dạng khoa học từ Java
function formatNumber(input) {
    let val = input.value;

    // Nếu Java trả về dạng khoa học (vd: 9.0E7), dịch ngược lại thành số thường
    if (val.toUpperCase().includes('E')) {
        val = Number(val).toLocaleString('en-US', {useGrouping: false, maximumFractionDigits: 10});
    }

    // Xóa tất cả các ký tự không phải số và dấu chấm
    val = val.replace(/[^0-9.]/g, '');

    let parts = val.split('.');

    // Thêm dấu phẩy vào phần nguyên
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',');

    // Ghép lại và gán vào ô input
    input.value = parts.join('.');
}

// Chạy format ngay khi trang vừa tải xong
document.addEventListener("DOMContentLoaded", function() {
    let priceInput = document.getElementById('price');
    let areaInput = document.getElementById('area');

    if(priceInput && priceInput.value) formatNumber(priceInput);
    if(areaInput && areaInput.value) formatNumber(areaInput);
});