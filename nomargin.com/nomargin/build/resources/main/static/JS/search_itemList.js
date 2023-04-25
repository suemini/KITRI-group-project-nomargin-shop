/* const searchForm = document.getElementById('search-form');
const searchInput = document.getElementById('search-input');
const itemList = document.querySelector('.item-table');
const items = [...itemList.querySelectorAll('.items')];

const searchResults = document.getElementById('search-results');
let message = document.getElementById('no-items-message');
if (!message) {
    message = document.createElement('p');
    message.textContent = '검색하신 상품이 존재하지 않습니다.';
    message.setAttribute('id', 'no-items-message');
}
message.style.display = 'none';
searchResults.appendChild(message);

searchForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const filter = searchInput.value.toLowerCase().trim();

    if (filter === '') {
        message.style.display = 'none';
        items.forEach((item) => {
            item.style.display = 'flex';
        });
    } else {
        const itemsToShow = items.filter((item) => {
            const itemName = item.querySelector('.item-name').textContent.toLowerCase();
            return itemName.includes(filter);
        });

        if (itemsToShow.length > 0) {
            message.style.display = 'none';
            items.forEach((item) => {
                item.style.display = 'none';
            });
            itemsToShow.forEach((item) => {
                item.style.display = 'table-row';
            });
        } else {
            alert('검색하신 상품이 존재하지 않습니다.');
            message.style.display = 'block';
        }
    }
});

const searchBtn = document.getElementById('search-btn');
searchBtn.addEventListener('click', (event) => {
    event.preventDefault();
    const searchTerm = searchInput.value.trim();
    const itemsToShow = items.filter((item) => {
        const itemName = item.querySelector('.item-name').textContent.toLowerCase();
        return itemName.includes(searchTerm);
    });
    if (searchTerm === '') {
        alert('검색어를 입력해주세요.');
    } else if (itemsToShow.length === 0) {
        alert('검색하신 상품이 존재하지 않습니다.');
    } else {
        searchForm.submit();
    }
}); */

$(function() {
    const searchForm = $('#search-form');
    const keywordInput = $('#keyword-input');
    const searchList = $('#search-list');
    const searchingFor = $('#searching-for');

    keywordInput.on('input', function() {
        const keyword = keywordInput.val().trim().toLowerCase();
        if (keyword === '') {
            searchList.empty();
            searchingFor.empty();
            alert('Please enter a search keyword.');
            return;
        }
        searchingFor.text(`Searching for "${keyword}"...`);
        $.ajax({
            url: '/search',
            method: 'GET',
            data: { keyword: keyword },
            success: function(response) {
                searchList.html(response);
                searchingFor.empty();
            },
            error: function(xhr) {
                console.error(xhr.responseText);
            }
        });
    });
});
