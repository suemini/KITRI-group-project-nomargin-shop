const topButton = document.querySelector('#topButton');
topButton.onclick = () => {
    window.scrollTo({top:0, behavior: "smooth"});
}
