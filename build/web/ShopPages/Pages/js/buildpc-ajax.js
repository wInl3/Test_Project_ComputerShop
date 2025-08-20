const ctx = document.body.getAttribute("data-ctx") || '';
const modal = document.getElementById('customModal');
const modalTitle = document.getElementById('modalTitle');
const modalBody = document.getElementById('modalBody');
const deleteModal = document.getElementById('deleteModal');
const cancelDelete = document.getElementById('cancelDelete');
const confirmDelete = document.getElementById('confirmDelete');
const toast = document.getElementById('toast');

let deleteUrl = null;

// ===== [1] MỞ MODAL CHỌN LINH KIỆN =====
document.querySelectorAll('.open-component-modal').forEach(button => {
    button.addEventListener('click', () => {
        const componentId = button.dataset.componentId;
        const componentName = button.dataset.componentName;
        modal.style.display = 'flex';
        modalTitle.textContent = componentName;
        modalBody.innerHTML = "<p>Loading...</p>";

        fetch(`${ctx}/BuildPC?service=filter&componentID=${encodeURIComponent(componentId)}&ajax=true`)
            .then(res => res.text())
            .then(html => {
                modalBody.innerHTML = html;
                rebindSelectButtons(); // gán lại sự kiện
            })
            .catch(() => {
                modalBody.innerHTML = '<p style="color:red">Lỗi khi tải dữ liệu.</p>';
            });
    });
});

// ===== [2] GÁN SỰ KIỆN SAU KHI AJAX TẢI MODAL =====
function rebindSelectButtons() {
    modalBody.querySelectorAll('.select-product-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const categoryId = btn.getAttribute('data-category-id');
            const componentId = btn.getAttribute('data-component-id');

            fetch(`${ctx}/BuildPC?service=add&categoryID=${categoryId}`, {
                headers: { 'X-Requested-With': 'XMLHttpRequest' }
            })
            .then(res => res.text())
            .then(fragment => {
                const target = document.getElementById(`component-wrapper-${componentId}`);
                if (target) target.outerHTML = fragment;
                modal.style.display = "none";
                bindRemoveButtons(); // gán lại nút delete
            })
            .catch(() => alert('Lỗi khi thêm sản phẩm'));
        });
    });
}

// ===== [3] AJAX FILTER TRONG MODAL =====
modalBody.addEventListener('submit', function (e) {
    if (e.target && e.target.id === 'filterForm') {
        e.preventDefault();
        const form = e.target;
        const params = new URLSearchParams(new FormData(form)).toString();
        const componentID = form.querySelector('input[name="componentID"]').value;

        fetch(`${ctx}/BuildPC?service=filter&componentID=${componentID}&ajax=true&${params}`)
            .then(res => res.text())
            .then(html => {
                modalBody.innerHTML = html;
                rebindSelectButtons(); // gán lại sau filter
            });
    }
});

// ===== [4] MỞ MODAL XOÁ =====
function bindRemoveButtons() {
    document.querySelectorAll('.btn-remove').forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            deleteUrl = this.getAttribute('data-delete-url');
            deleteModal.style.display = 'flex';
        });
    });
}
bindRemoveButtons(); // gán lần đầu

// ===== [5] XOÁ LINH KIỆN =====
cancelDelete.onclick = () => {
    deleteModal.style.display = 'none';
    deleteUrl = null;
};

confirmDelete.onclick = () => {
    if (deleteUrl) {
        fetch(deleteUrl, {
            headers: { 'X-Requested-With': 'XMLHttpRequest' }
        }).then(response => {
            if (response.ok) {
                deleteModal.style.display = 'none';
                toast.style.display = 'block';
                setTimeout(() => {
                    toast.style.display = 'none';
                    location.reload(); // hoặc cập nhật lại component block
                }, 1000);
            } else {
                alert('Không thể xoá. Đã có lỗi xảy ra.');
            }
        }).catch(() => {
            alert('Không thể xoá. Lỗi mạng.');
        });
    }
};

// ===== [6] ĐÓNG MODAL KHI CLICK RA NGOÀI =====
window.onclick = function (e) {
    if (e.target === modal || e.target === deleteModal) {
        modal.style.display = "none";
        deleteModal.style.display = "none";
    }
};
