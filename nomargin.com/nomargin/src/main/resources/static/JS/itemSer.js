// const searchForm = document.getElementById("search-form");
// searchForm.addEventListener("submit", (event) => {
//     event.preventDefault();
//     const keyword = document.getElementById("search-input").value.trim();
//     if (keyword === "") {
//         alert("검색어를 입력해주세요.");
//     } else {
//         const form = document.createElement("form");
//         form.method = "get";
//         const input = document.createElement("input");
//         input.type = "hidden";
//         input.name = "keyword";
//         input.value = keyword;
//         form.appendChild(input);
//         document.body.appendChild(form);
//         form.submit();
//     }
//     const filter = keyword.toUpperCase();
//     const ul = document.getElementById("myUL");
//     const li = ul.getElementsByTagName("li");
//     let found = false;
//     for (let i = 0; i < li.length; i++) {
//         const a = li[i].getElementsByTagName("a")[0];
//         const txtValue = a.textContent || a.innerText;
//         if (txtValue.toUpperCase().indexOf(filter) > -1) {
//             li[i].style.display = "";
//             found = true;
//         } else {
//             li[i].style.display = "none";
//         }
//     }
//     if (!found) {
//         alert("검색어에 해당하는 상품이 없습니다.");
//     }
// });

// const searchForm = document.querySelector('#search-form');
// const searchInput = document.querySelector('#search-input');
//
// searchForm.addEventListener('submit', function(event) {
//     event.preventDefault(); // 기본 동작인 폼 제출을 막음
//
//     const keyword = searchInput.value.trim();
//     if (keyword) {
//         // 검색어가 입력되어 있는 경우, 검색 결과를 확인하고 처리
//         const url = `/form/itemList/search?keyword=${encodeURIComponent(keyword)}`;
//         // AJAX를 사용하여 검색 결과를 가져옴
//         fetch(url)
//             .then(response => response.json())
//             .then(data => {
//                 if (data.length > 0) {
//                     // 검색 결과가 있을 경우, 검색 결과 페이지로 이동
//                     location.href = url;
//                 } else {
//                     // 검색 결과가 없을 경우, 현재 페이지에 머무르게 함
//                     alert('검색 결과가 없습니다.');
//                 }
//             })
//             .catch(error => {
//                 console.error(error);
//                 alert('검색 결과를 가져오는 중 오류가 발생했습니다.');
//             });
//     } else {
//         // 검색어가 입력되어 있지 않은 경우, 경고창을 띄움
//         alert('검색어를 입력해주세요.');
//     }
// });

/* $(document).ready(function() {
    const searchForm = $('#search-form');
    const searchInput = $('#search-input');
    const itemList = $('.item-table');
    const items = itemList.find('.items');

    const searchResults = $('#search-results');
    let message = $('#no-items-message');
    if (!message.length) {
        message = $('<p id="no-items-message"></p>').hide();
        searchResults.append(message);
    }

    searchForm.submit(function(event) {
        event.preventDefault();
        const filter = searchInput.val().toLowerCase().trim();

        // AJAX를 이용하여 서버와 통신하여 검색 결과를 가져옵니다.
        $.ajax({
            type: "GET",
            url: "/search?filter=" + filter, // 수정된 부분
            success: function(response) {
                const results = response.results;
                if (results.length > 0) {
                    message.hide();
                    items.hide();
                    showSearchResults(results);
                } else {
                    message.show().text(`${filter}의 상품이 존재하지 않습니다.`);
                }
            },
            error: function(error) {
                console.log(error);
            }
        });

    });

    const searchBtn = $('#search-btn');
    searchBtn.click(function() {
        const searchTerm = searchInput.val().trim();
        const itemsToShow = items.filter(function() {
            const itemName = $(this).find('.item-name').text().toLowerCase();
            return itemName.includes(searchTerm);
        });
        if (searchTerm === '') {
            alert('검색어를 입력해주세요.');
        } else if (itemsToShow.length === 0) {
            alert(`${searchTerm}의 상품이 존재하지 않습니다.`);
        } else {
            const currentPath = window.location.pathname;
            const itemToDisplay = itemsToShow.filter(function() {
                const itemLink = $(this).find('.item-link').attr('href');
                return itemLink === currentPath;
            });
            if (itemToDisplay.length > 0) {
                items.hide();
                itemToDisplay.show();
            } else {
                // If no item matches the current path, show all the items that match the search term
                message.hide();
                items.hide();
                itemsToShow.show();
            }
        }
    });

    // 검색 결과를 화면에 출력하는 함수
    function showSearchResults(results) {
        const resultContainer = $('#search-results');

        // 검색 결과를 보여주기 전에 이전 검색 결과를 삭제합니다.
        resultContainer.empty();

        // 검색 결과를 하나씩 화면에 출력합니다.
        results.forEach(function(result) {
            const itemContainer = $('<div class="items"></div>');
            const itemLink = $('<a class="item-link"></a>').attr('href', result.link).click(function(event) {
                event.preventDefault();
                window.location.href = result.link;
            });
            const itemName = $('<h3 class="item-name"></h3>').text(result.name);
            const itemPrice = $('<p class="item-price"></p>').text(result.price);
            const itemDescription = $('<p class="item-description"></p>').text(result.description);

            itemLink.append(itemName);
            itemContainer.append(itemLink);
            itemContainer.append(itemPrice);
            itemContainer.append(itemDescription);
            resultContainer.append(itemContainer);
        });
    }
}); */

const searchForm = document.getElementById('search-form');
const searchInput = document.getElementById('search-input');

searchForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const keyword = searchInput.value.trim();
    console.log('ehdwkr')
    if (!keyword) {
        alert('검색어를 입력해주세요.');
        return;
    }

    fetch(`/form/itemList/search?filter=${encodeURIComponent(keyword)}`)
        .then((response) => {
            if (!response.ok) {
                throw new Error(response.statusText);
            }
            return response.json();
        })
        .then((data) => {
            const itemList = data.itemList;
            if (!itemList) {
                alert('상품을 찾을 수 없습니다.');
                return;
            }

            const itemContainer = document.getElementById('item-container');
            itemContainer.innerHTML = '';

            itemList.forEach((item) => {
                const itemElement = document.createElement('div');
                itemElement.classList.add('item');
                itemElement.innerHTML = `
        <div class="item-img">
          <img src="${item.imgName}" alt="${item.itemName}">
        </div>
        <div class="item-desc">
          <p class="item-name">${item.itemName}</p>
          <p class="item-price">${item.price}원</p>
        </div>
    `;
                itemContainer.appendChild(itemElement);
            });

        })
        .catch((error) => {
            console.error(error);
            alert('상품 검색 중 오류가 발생했습니다.');
        });
});








