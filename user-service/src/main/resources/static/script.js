// --- Giriş / Kayıt formu kontrolü
const form = document.getElementById("auth-form");
const formTitle = document.getElementById("form-title");
const toggleButton = document.getElementById("toggle-button");
const toggleText = document.getElementById("toggle-text");
const authMessage = document.getElementById("auth-message");
const registerFields = document.getElementById("register-fields");
const roleSelect = document.getElementById("role"); // ✅ role select eklendi

let isLogin = true;

// --- Giriş <-> Kayıt geçişi
toggleButton.addEventListener("click", () => {
    isLogin = !isLogin;
    formTitle.textContent = isLogin ? "Giriş Yap" : "Kayıt Ol";
    toggleText.textContent = isLogin ? "Hesabınız yok mu?" : "Zaten hesabınız var mı?";
    toggleButton.textContent = isLogin ? "Kayıt Ol" : "Giriş Yap";
    authMessage.textContent = "";
    registerFields.style.display = isLogin ? "none" : "flex";
    roleSelect.style.display = isLogin ? "none" : "block"; // ✅ rol select görünürlüğü
});

// --- Kayıt alanı doğrulama
function validateRegisterFields() {
    const firstName = document.getElementById("firstName").value.trim();
    const lastName = document.getElementById("lastName").value.trim();
    const phoneNumber = document.getElementById("phoneNumber").value.trim();
    const email = document.getElementById("email").value.trim();

    authMessage.textContent = "";

    const phonePattern = /^05\d{9}$/;
    if (!phonePattern.test(phoneNumber)) {
        showMessage("Telefon numarası 11 haneli olmalı ve 05 ile başlamalı.", false);
        return false;
    }

    if (!email.includes("@")) {
        showMessage("Geçerli bir email adresi giriniz.", false);
        return false;
    }

    if (firstName === "" || lastName === "") {
        showMessage("Ad ve soyad boş bırakılamaz.", false);
        return false;
    }

    return true;
}

// --- Geri bildirim mesajı
function showMessage(message, isSuccess) {
    authMessage.textContent = message;
    authMessage.style.color = isSuccess ? "lightgreen" : "#ff4d4d";
}

// --- Form gönderimi
form.addEventListener("submit", (e) => {
    e.preventDefault();

    if (!isLogin && !validateRegisterFields()) return;

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if (password.length < 6) {
        showMessage("Şifre en az 6 karakter olmalıdır.", false);
        return;
    }

    const endpoint = isLogin ? "/api/auth/login" : "/api/auth/register";

    let payload = { username, password };

    if (!isLogin) {
        payload = {
            ...payload,
            firstName: document.getElementById("firstName").value,
            lastName: document.getElementById("lastName").value,
            phoneNumber: document.getElementById("phoneNumber").value,
            email: document.getElementById("email").value,
            role: roleSelect.value // ✅ rol bilgisi eklendi
        };
    }

    fetch(endpoint, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
    })
        .then(async (res) => {
            if (!res.ok) {
                const errorText = await res.text();
                throw new Error(errorText || "Sunucu hatası oluştu.");
            }
            return res.json();
        })
        .then((response) => {
            showMessage(isLogin ? "Giriş başarılı" : "Kayıt başarılı", true);

            setTimeout(() => {
                // ✅ Rol bilgisine göre yönlendir
                if (response.role === 'USER') {
                    window.location.href = "/dashboard-user.html";
                } else if (response.role === 'LIBRARIAN') {
                    window.location.href = "/dashboard-librarian.html";
                } else {
                    window.location.href = "/index.html";
                }
            }, 1000);
        })
        .catch((err) => {
            showMessage(err.message, false);
        });
});


// --- Kitap arama butonu (Ana sayfa / search kısmı için)
document.addEventListener('DOMContentLoaded', function () {
    const btn = document.getElementById('searchBtn');
    const input = document.getElementById('searchInput');

    if (btn && input) {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            const query = input.value.trim();
            if (query === "") {
                window.location.href = "search.html";
            } else {
                window.location.href = `search.html?q=${encodeURIComponent(query)}`;
            }
        });
    }
});
