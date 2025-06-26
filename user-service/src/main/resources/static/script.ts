interface Book {
    id: number;
    title: string;
    author: string;
    category: string;
    stock: number;
}

fetch("/api/books")
    .then(response => {
        if (!response.ok) throw new Error("Network response was not ok");
        return response.json();
    })
    .then((data: Book[]) => {
        console.log("Kitaplar:", data);
        const list = document.getElementById("book-list");
        data.forEach(book => {
            const item = document.createElement("li");
            item.textContent = `${book.title} - ${book.author}`;
            list?.appendChild(item);
        });
    })
    .catch(error => console.error("Veri çekilemedi:", error));


window.addEventListener('DOMContentLoaded', () => {
    fetch('/api/books')
        .then(res => res.json())
        .then((books) => {
            const list = document.getElementById('book-list');
            books.forEach((book: any) => {
                const li = document.createElement('li');
                li.textContent = `${book.title} - ${book.author}`;
                list?.appendChild(li);
            });
        })
        .catch(err => console.error("Kitaplar alınamadı:", err));
});

document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const query = params.get("q");

    if (!query) {
        // @ts-ignore
        document.getElementById("results").innerHTML = "<p>Arama terimi girilmemiş.</p>";
        return;
    }

    fetch(`/api/books/search?q=${encodeURIComponent(query)}`)
        .then(res => {
            if (!res.ok) throw new Error("Arama yapılamadı");
            return res.json();
        })
        .then((books) => {
            const container = document.getElementById("results");
            if (books.length === 0) {
                // @ts-ignore
                container.innerHTML = "<p>Hiçbir sonuç bulunamadı.</p>";
                return;
            }
            books.forEach((book: { title: any; author: any; year: any; }) => {
                const div = document.createElement("div");
                div.className = "book-card";
                div.innerHTML = `
          <h3>${book.title}</h3>
          <p>Yazar: ${book.author}</p>
          <p>Yayın Yılı: ${book.year || "-"}</p>
        `;
                // @ts-ignore
                container.appendChild(div);
            });
        })
        .catch(err => {
            console.error(err);
            // @ts-ignore
            document.getElementById("results").innerHTML = "<p>Bir hata oluştu.</p>";
        });
});
