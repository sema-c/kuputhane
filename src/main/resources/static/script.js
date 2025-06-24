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
    var btn = document.getElementById('searchBtn');
    // @ts-ignore
    btn.addEventListener('click', function () {
        // @ts-ignore
        var query = document.getElementById('searchInput').value.trim();
        if (query) {
            var url = "https://annas-archive.org/search?q=".concat(encodeURIComponent(query));
            window.open(url, '_blank');
        }
    });
});
