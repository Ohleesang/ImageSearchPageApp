![wireframe](https://github.com/Ohleesang/ImageSearchPageApp/assets/148442711/de5f6178-af66-45c9-85db-6ec52e1a581c)

[FigmaLink](https://www.figma.com/file/IR8yyToUoxLh21Mqz62Fv6/ImageSearchPageApp?type=design&node-id=0%3A1&mode=design&t=t7LsGqJoS4UNnFqB-1)

# A 타입
- [x] 검색어를 입력할 수 있도록 검색창을 구현합니다.
- [x] 검색어를 입력하고 검색 버튼을 누르면 검색된 이미지 리스트를 보여줍니다.
- [x] 검색 버튼을 누르면 키보드는 숨김 처리하도록 구현합니다.
- [x] API 검색 결과에서 thumbnail_url, display_sitename, datetime을 받아오도록 구현 합니다.
- [x] RecyclerView의 각 아이템 레이아웃을 썸네일 이미지, 사이트이름, 날짜 시간 으로 구현 합니다.
- [x] API 검색 결과를 RecyclerView에 표시하도록 구현합니다.
- [x] 날짜 시간은 "yyyy-MM-dd HH:mm:ss” 포멧으로 노출되도록 구현합니다.
- [x] 검색 결과는 최대 80개까지만 표시하도록 구현합니다.
- [x] 리스트에서 특정 이미지를 선택하면 특별한 표시를 보여주도록 구현합니다. (좋아요/별표/하트 등)
- [x] 선택된 이미지는 MainActivity의 ‘선택된 이미지 리스트 변수’에 저장합니다.
- [x] 마지막 검색어는 저장 되며, 앱 재시작시 마지막 검색어가 검색창 입력 필드에 자동으로 입력됩니다.

# B 타입
## 1.  A타입에서 달라지는 기능

- 공통
    - [x]  MainActivity의 하단 메뉴를 Bottom Navigation 또는 ViewPager+tablayout으로 변경
    - [x]  MVVM 패턴을 적용합니다. (ViewModel, LiveData)
    - [x]  검색 결과 화면은 보관함을 다녀와도 유지됩니다.
    - [ ]  보관한 이미지 리스트는 앱 재시작 후 다시 보여야 합니다.
    
- 첫 번째 fragment : 검색 결과
    - [ ]  검색은 키워드 하나에 이미지 검색과 동영상 검색을 동시에 사용, 두 검색 결과를 합친 리스트를 사용합니다.
    - [ ]  동영상 검색은 API는 (**[링크](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-video)**)의 thumbnail 필드를 사용합니다.
    - [ ]  두 검색 결과를 datetime 필드를 이용해 정렬하여 출력합니다. (최신부터 나타나도록)
    - [ ]  검색 결과 아이템에 [Image] 또는 [Video]를 표시합니다.
    - [ ]  검색 결과화면에서 마지막 결과로 스크롤시 다음 페이지가 자동 검색 되도록 구현합니다.(무한스크롤 기능)
    - [ ]  스크롤을 최상단으로 이동시키는 플로팅 액션 버튼을 추가합니다.
    - [ ]  아이템 선택시 SharedPreference에 저장합니다. (DB 사용 금지)
      
- 두 번째 fragment: 내 보관함
    - [ ]  SharedPreference에 저장된 리스트를 불러와 화면에 표시합니다.
    - [ ]  보관함에서 이미지 선택시 저장 리스트에서 삭제되며 화면에서도 삭제되도록 구현합니다.
