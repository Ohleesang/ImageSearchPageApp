![wireframe](https://github.com/Ohleesang/ImageSearchPageApp/assets/148442711/de5f6178-af66-45c9-85db-6ec52e1a581c)

[FigmaLink](https://www.figma.com/file/IR8yyToUoxLh21Mqz62Fv6/ImageSearchPageApp?type=design&node-id=0%3A1&mode=design&t=t7LsGqJoS4UNnFqB-1)

# A 타입
- [ ] 검색어를 입력할 수 있도록 검색창을 구현합니다.
- [ ] 검색어를 입력하고 검색 버튼을 누르면 검색된 이미지 리스트를 보여줍니다.
- [ ] 검색 버튼을 누르면 키보드는 숨김 처리하도록 구현합니다.
- [ ] API 검색 결과에서 thumbnail_url, display_sitename, datetime을 받아오도록 구현 합니다.
- [ ] RecyclerView의 각 아이템 레이아웃을 썸네일 이미지, 사이트이름, 날짜 시간 으로 구현 합니다.
- [x] API 검색 결과를 RecyclerView에 표시하도록 구현합니다.
- [ ] 날짜 시간은 "yyyy-MM-dd HH:mm:ss” 포멧으로 노출되도록 구현합니다.
- [ ] 검색 결과는 최대 80개까지만 표시하도록 구현합니다.
- [x] 리스트에서 특정 이미지를 선택하면 특별한 표시를 보여주도록 구현합니다. (좋아요/별표/하트 등)
- [x] 선택된 이미지는 MainActivity의 ‘선택된 이미지 리스트 변수’에 저장합니다.
- [ ] 마지막 검색어는 저장 되며, 앱 재시작시 마지막 검색어가 검색창 입력 필드에 자동으로 입력됩니다.
