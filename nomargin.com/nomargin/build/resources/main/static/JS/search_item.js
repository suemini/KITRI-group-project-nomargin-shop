const searchForm = document.querySelector("#search-form");
const searchInput = document.querySelector("#search-input");
const searchResults = document.querySelector("#search-results");
const noResults = searchResults.querySelector(".no-results");
const results = searchResults.querySelector(".results");

function showNoResults() {
    if (noResults) {
        noResults.style.display = "block";
    }

    if (results) {
        results.style.display = "none";
    }
}

function showSearchResults(matchedProducts) {
    if (matchedProducts.length > 0) {
        if (noResults) {
            noResults.style.display = "none";
        }

        results.innerHTML = "";

        for (let i = 0; i < matchedProducts.length; i++) {
            const matchedProduct = matchedProducts[i];
            const productName = matchedProduct.querySelector(".product-name").textContent.trim();

            const productElement = document.createElement("div");
            productElement.classList.add("product");
            productElement.innerHTML = `<div class="product-name">${productName}</div>`;
            results.appendChild(productElement);
        }

        results.style.display = "block";
    } else {
        showNoResults();
    }
}

// JQuery 코드
$(function() {
    $('#search-form').on('submit', function(event) {
        var keyword = $('#keyword-input').val().trim();
        if (keyword === '') {
            event.preventDefault(); // 폼의 전송 이벤트를 막음
            alert('검색어를 입력해주세요.');
        }
    });
});






