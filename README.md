보건복지부와 질병청으로부터 하루마다 업데이트 되는 정보를 크롤링 및 가공하여  구글 맵을 통하여 확진자의 동선을 다수 선택하여 확인할 수 있는 서비스. 그 외 여러 뉴스도 확인할수 있었고 지역별 확진자도 확인할 수 있었음.
"코로나19와 같은 국가위기사태를 이해하고 관리하는 방식이 디지털 기술로 인해 어떻게 변화하는지 관찰하는 연구"를 진행하는, 미시간 대학(Uni of Michigan) 소속 박사과정 연구원 분과 인터뷰를 진행하였고, 이 내용이 현재 논문제작에 사용되었으며 출판예정.
개발자 도구를 통해 로그를 분석하면서 앱 내의 처리하지 못한 에러를 직면,  잘못된 코드를 수정하면서 에러 핸들링의 중요성에 대해 절실히 알게 되었고, 어플의 강제 종료 비율을 일정 비율 개선하였음.

겪었던 문제점
1. 실제로 크롤링 url이 변경될 수도 있다는 가능성을 고려하지 않고 앱 내에 String 값으로 고정하였고, 이는 강제종료의 주 원인이 되었음. 해결 방법으로는 아래의 두 가지 방법으로 해결하였음.
- try-catch문으로 에러 핸들링
- Realtime-Firebase를 통해 url을 담는 변수를 실시간으로 DB에서 받아오게 함


2. main에서 애니메이션 넣을 때 초기에 설정하면 fragment 전환 시에 충돌이 일어날 수 있으니
- 애니메이션 설정하지 말고 바로 디비에서 불러와서 수정

도움이 되었던 링크들
https://duzi077.tistory.com/121
fragment google map

https://androidwave.com/bottom-sheet-behavior-in-android/
bottom sheet



https://icons8.com/icons/set/flu
png image good



alarm/sub/index.js :
when i changes the firebase db-fatality and db-diagnosis send the notification to users 
to notificate we have to deploy the index.js at nodejs
To connect nodejs and firebase, firebase provides a method.

alarm/sub/kor_ona_example.py :
when the govermentweb info was changed, the kor_ona_example.py do crawl the info and change firebase db automatically
so when the govermentweb info was changed, the alarm is sent to the user.

alarm/sub/main.py :
This is a daemon file that lets the file return to the background on the server.

alarm/sub/example-firebase.json :
this is the key to login our firebase

alarm/sub/package.json :
to run nodejs package 
