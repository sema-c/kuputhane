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
