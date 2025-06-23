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
