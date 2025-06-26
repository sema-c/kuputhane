fetch("/api/books")
    .then(function (response) {
    if (!response.ok)
        throw new Error("Network response was not ok");
    return response.json();
})
    .then(function (data) {
    console.log("Kitaplar:", data);
    var list = document.getElementById("book-list");
    data.forEach(function (book) {
        var item = document.createElement("li");
        item.textContent = "".concat(book.title, " - ").concat(book.author);
        list === null || list === void 0 ? void 0 : list.appendChild(item);
    });
})
    .catch(function (error) { return console.error("Veri çekilemedi:", error); });
window.addEventListener('DOMContentLoaded', function () {
    fetch('/api/books')
        .then(function (res) { return res.json(); })
        .then(function (books) {
        var list = document.getElementById('book-list');
        books.forEach(function (book) {
            var li = document.createElement('li');
            li.textContent = "".concat(book.title, " - ").concat(book.author);
            list === null || list === void 0 ? void 0 : list.appendChild(li);
        });
    })
        .catch(function (err) { return console.error("Kitaplar alınamadı:", err); });
});
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

const form = document.getElementById("auth-form");
const formTitle = document.getElementById("form-title");
const toggleButton = document.getElementById("toggle-button");
const toggleText = document.getElementById("toggle-text");
const authMessage = document.getElementById("auth-message");
const registerFields = document.getElementById("register-fields");

let isLogin = true;

toggleButton.addEventListener("click", () => {
    isLogin = !isLogin;
    formTitle.textContent = isLogin ? "Giriş Yap" : "Kayıt Ol";
    toggleText.textContent = isLogin ? "Hesabınız yok mu?" : "Zaten hesabınız var mı?";
    toggleButton.textContent = isLogin ? "Kayıt Ol" : "Giriş Yap";
    authMessage.textContent = "";
    registerFields.style.display = isLogin ? "none" : "flex";
});

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

function showMessage(message, isSuccess) {
    authMessage.textContent = message;
    authMessage.style.color = isSuccess ? "lightgreen" : "#ff4d4d";
}

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
            email: document.getElementById("email").value
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
        .then(() => {
            showMessage(isLogin ? "Giriş başarılı" : "Kayıt başarılı", true);
            setTimeout(() => {
                window.location.href = "/index.html";
            }, 1000);
        })
        .catch((err) => {
            showMessage(err.message, false);
        });
});
