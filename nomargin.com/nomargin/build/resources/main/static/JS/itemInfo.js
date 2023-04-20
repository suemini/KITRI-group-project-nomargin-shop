var tabs = document.querySelectorAll('#item-tab .nav-item'); // 탭 버튼들을 가져옵니다.

for (var i = 0; i < tabs.length; i++) { // 각 탭 버튼에 대해 반복문을 수행합니다.
    tabs[i].addEventListener('click', function(event) { // 각 탭 버튼에 클릭 이벤트를 추가합니다.
        event.preventDefault(); // 클릭 이벤트 기본 동작을 취소합니다. (해당 탭 버튼으로 이동하지 않도록 함)

        var target = this.getAttribute('href'); // 현재 클릭된 탭 버튼이 가리키는 탭 컨텐츠의 아이디(href)를 가져옵니다.
        var tabContent = document.querySelector(target); // 해당 탭 컨텐츠를 가져옵니다.
        var activeTab = document.querySelector('.nav-tabs .active'); // 현재 활성화된 탭 버튼을 가져옵니다.
        var activeTabContent = document.querySelector('.tab-content .active'); // 현재 활성화된 탭 컨텐츠를 가져옵니다.

        activeTab.classList.remove('active'); // 이전에 활성화된 탭 버튼에서 'active' 클래스를 제거합니다.
        this.classList.add('active'); // 현재 클릭된 탭 버튼에 'active' 클래스를 추가합니다.
        activeTabContent.classList.remove('active'); // 이전에 활성화된 탭 컨텐츠에서 'active' 클래스를 제거합니다.
        tabContent.classList.add('active'); // 현재 클릭된 탭 버튼이 가리키는 탭 컨텐츠에 'active' 클래스를 추가합니다.
    });
}


