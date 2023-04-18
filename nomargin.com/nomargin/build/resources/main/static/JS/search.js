function search() {
    var input = document.getElementById("search");
    var filter = input.value.toUpperCase();
    var tables = document.getElementsByClassName("item-table");
    var resultsDiv = document.getElementById("search-results");
    resultsDiv.innerHTML = "";

    // Loop through all tables and rows, and hide those who don't match the search query
    var hasResults = false;
    for (var i = 0; i < tables.length; i++) {
        var rows = tables[i].getElementsByTagName("tr");
        for (var j = 0; j < rows.length; j++) {
            var item = rows[j];
            var name = item.getElementsByClassName("item-name")[0];
            if (name) {
                if (name.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    item.style.display = "";
                    hasResults = true;
                } else {
                    item.style.display = "none";
                }
            }
        }
    }

    if (!hasResults) {
        var noResultsMsg = document.createElement("p");
        noResultsMsg.innerHTML = "No results found.";
        resultsDiv.appendChild(noResultsMsg);
    }
}
