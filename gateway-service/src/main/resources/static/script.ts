interface Book {
    id: number;
    title: string;
    author: string;
    category: string;
    stock: number;
    year?: number;
    publishedYear?: number;
    lent?: boolean;
}

// @ts-ignore
function fetchBooks(): void {
    fetch("http://localhost:8082/api/books")
        .then(res => {
            if (!res.ok) throw new Error("Kitaplar alınamadı");
            return res.json();
        })
        .then((books: Book[]) => {
            const container = document.querySelector(".book-container") || document.getElementById("book-list");
            if (!container) return;

            if (books.length === 0) {
                container.innerHTML = "<p>Hiç kitap bulunamadı.</p>";
                return;
            }

            books.forEach((book: Book) => {
                const div = document.createElement("div");
                div.className = "book-card";
                div.innerHTML = `
                    <h3>${book.title}</h3>
                    <p>Yazar: ${book.author}</p>
                    <p>Yayın Yılı: ${book.year || book.publishedYear || "-"}</p>
                    <p class="${book.lent ? 'borrowed' : 'available'}">
                        Durum: ${book.lent ? "Ödünçte" : "Müsait"}
                    </p>
                `;
                container.appendChild(div);
            });
        })
        .catch(err => {
            console.error("Kitaplar alınamadı:", err);
        });
}

// @ts-ignore
function searchBooks(query: string): void {
    fetch(`http://localhost:8082/api/books/search?q=${encodeURIComponent(query)}`)
        .then(res => {
            if (!res.ok) throw new Error("Arama yapılamadı");
            return res.json();
        })
        .then((books: Book[]) => {
            const container = document.getElementById("results");
            if (!container) return;

            if (books.length === 0) {
                container.innerHTML = "<p>Hiçbir sonuç bulunamadı.</p>";
                return;
            }

            books.forEach((book) => {
                const div = document.createElement("div");
                div.className = "book-card";
                div.innerHTML = `
                    <h3>${book.title}</h3>
                    <p>Yazar: ${book.author}</p>
                    <p>Yayın Yılı: ${book.year || book.publishedYear || "-"}</p>
                `;
                container.appendChild(div);
            });
        })
        .catch(err => {
            console.error("Arama hatası:", err);
            const container = document.getElementById("results");
            if (container) container.innerHTML = "<p>Bir hata oluştu.</p>";
        });
}

window.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const query = params.get("q");

    if (query) {
        searchBooks(query);
    } else {
        fetchBooks();
    }
});
