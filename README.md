

<div align="center" style="display:flex;">
       <img src=https://user-images.githubusercontent.com/57262833/87799675-7c048580-c888-11ea-8bbb-ec6c4e5c769e.png alt="drawing" width="600"/> 
       <img src="https://user-images.githubusercontent.com/57262833/86694398-8764df00-c046-11ea-8afb-5b8b7087dc1b.png" width="300"/>
</div>

```
✨  기존 러닝 어플리케이션과의 확실한 차별화 요소를 적용했습니다.    

    사용자는 '실시간 경쟁'이라는 게이미피케이션 요소를 통해 지루한 러닝이 아닌, 게임처럼 유쾌한 러닝을 즐길 수 있습니다.
```

```
✨  '따로, 또 같이' 함께 하는 가치를  추구하고자 합니다.  

    어떤 단체에 소속되지 않아도, 굳이 친구와 약속을 잡고 만나지 않아도, 사용자는 언제 어디서든 나와 함께 뛸 상대를 찾아 같이 러닝을 즐길 수 있습니다.
```

```
✨  트렌디한 디자인적 요소를 통해 시각적인 즐거움을 얻을 수 있습니다.  

    특정 목표를 달성하면 개성있는 여러 가지 뱃지를 획득할 수 있음과 동시에 다양한 러너 랭킹을 통해 사용자의 즐거움을 도모하고자 합니다. 또, 누구나 사용하기 쉬운 UX 요소는 즐거움을 배로 향상시켜 줄거에요.
```

</br>
</br>

## :pig: A-1. ConstraintLayout 사용한 화면 개발

## :pig: Custom 확장 함수 
:point_right: dpToPx.kt
- xml코드와 달리 kotlin source code에서는 px단위를 사용, dp를 px로 변환해주는 확장함수
```
fun Int.dpToPx(resources: Resources): Int = TypedValue
    .applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(), resources.displayMetrics).toInt()
```
</br>

:point_right: logDebug.kt
- Log 쉽게 볼 수 있도록 해주는 확장함수
```
fun String.logDebug(any: Any) {
    Log.d(any::class.java.simpleName, this)
}

```
:point_right: newStartActivity.kt
- 액티비티 전환 확장함수
```
fun <T> Context.newStartActivity(toClass: Class<T>){
    val intent = Intent(this, toClass)
    startActivity(intent)
}

```



### :running: Kotlin Collection 확장함수 사용
- 서버와 위도, 경도 데이터를 소켓통신하기 위하여, 기존의 ArrayList를 JSONArray로 변환하는 함수
~~~
 fun getCoorsJSONArr(coordsArr: ArrayList<LatLng>): JSONArray {
        return JSONArray(coordsArr.map {
            try{
                JSONObject().apply {
                    this.put("latitude", it.latitude)
                    this.put("longitude", it.longitude)
                }
            }catch (e: JSONException){e.printStackTrace()}
        })
 }
~~~

## :pig: A-3. 프로젝트 설명
### :running:프로젝트 구조
<img src=https://user-images.githubusercontent.com/57262833/87794364-78213500-c881-11ea-88eb-a13454fbdf83.png alt="drawing" width="300"/> 

- 패키지 설명

| package | 설명 |  
|:---:|:---|
| `data` | 프로젝트 내에서 쓰는 데이터관련 클래스 | 
| `extension` | 확장함수관련 파일 |
| `feature` | 기능별 폴더들 |
| `network` | Retrofit을 사용한 http 통신시 사용하는 인터페이스 및 클래스 | 
| `socket` | Socket.io를 사용한 소켓 통신시 사용하는 클래스 및 서비스 |
| `util` | SharePrererence 및 기타 사용 클래스 | 


- feature내 하위 패키지 설명

| feature내 하위 패키지 | 해당 액티비티 및 프레그먼트 | 설명 | 
|:---:|:---|:---|
| `badgedetail` | BadgeDetailActivity | 뱃지 상세보기 화면 | 
| `finishrun` | FinishRunActivity | 러닝완료 후 화면 |
| `home` | HomeActivity, HomeFragment, BottomSheetFragment | 홈 화면 |
| `login` | LoginActivity | 로그인 화면 |
| `matchfail` | MatchFailActivity | 매칭 실패 화면 |
| `myprofile` | MyProfileFragment | 마이프로필 화면 |
| `onboard` | OnBoardActivity | 온보딩 화면 |
| `rank` | RankFragment | 랭킹 화면 |
| `recdetail` | RecDetailActivity | 기록상세보기 화면 |
| `record_badge` | BadgeFragment, RecBadgeFragment, RecFragment | 뱃지 모아보기 화면, 기록 모아보기 화면 |
| `result` | ResultActivity | 러닝 결과 화면 |
| `run` | GoalActivity, RivalActivity, MatchProcActivity, MatchSucActivity, RunActivity | 매칭상대선택 화면, 매칭 화면, 달리기 화면 |
| `runalone` | WaitMeActivity, GoalRunMeActivity, FinishRunActivity | 나와 달리기 화면, 더미와 달리기 화면, 나와 달리기 결과 화면 |
| `runcountdown` | CountDownActivity, StartRunActivity | 달리기전 카운트 다운 화면, 달리기 시작 알림 화면 |
| `rundummy` | MatchDummyActivity | 더미데이터와 달리기 매칭 화면 |
| `signup` | SignUpActivity | 회원가입 화면 | 
| `splash` | SplashActivity | 스플래시 화면 |


### :running: 핵심기능 및 구현화면 정리
#### 스플래쉬, 온보딩, 회원가입, 로그인 화면
- 스플래쉬 화면에서 SharedPreferences으로 자동로그인 구현
- 회원가입 화면에서 textWatcher으로 실시간으로 정규표현식 검사, 모든 조건 만족시에만 회원가입 버튼 활성화\

#### 홈화면, 매칭화면, 러닝화면- 다른사람과 경쟁, 나와 경쟁하기 :point_left: 핵심기능

- 홈화면에서 BottomSheetNavigation 사용
- 매칭화면에서 Socket.io를 이용한 실시간 통신으로 사용자와 매칭함
- 러닝화면에서 Socket.io를 이용하여 서버로 러닝 기록 전송, 매칭 상대방의 러닝 기록 받음
- 러닝화면에서 NaverMap API를 이용하여 사용자의 이동거리 기록
- 러닝화면에서 실시간으로 이동시간, 러닝거리, 페이스 보여줌
- 나와 경쟁하기 화면에서 TextToSpeeach와 Timer로 음성피드백 기능 구현

#### 기록화면, 기록 자세히보기 화면

- 기록화면에서 매칭상대방과 러닝 기록 비교 가능 및 승패 여부 보여줌
- 기록자세히 보기화면에서 NaverMap사용으로 러닝 경로 불러오기 

#### 뱃지화면, 마이페이지 화면


<br>
<br>




