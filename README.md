<div align="center" style="display:flex;">
       <img src="https://user-images.githubusercontent.com/57262833/86694398-8764df00-c046-11ea-8afb-5b8b7087dc1b.png">
</div>

<div align="center">
    🏃‍♀️ RUNNER'S HI_ANDRIOD  🏃‍♂️
<br> 러닝을 게임처럼, 러너스하이
</div>

---
우리는 '함께' 뛸 때 더 많은 시너지를 만들어냅니다.  
요즘 세대들은 러닝 크루에 소속되어 러닝을 즐기곤 하지만, Offline 러닝크루는 고정된 시간과 장소라는 한계점을 가지고 있습니다.  
우리는 **같이 달리고 싶지만, 달릴 수 없는 순간들이 존재한다는 점**에 집중하고자 합니다.  
우리의 서비스를 통해 시공간의 제약없이 다양한 사람들과 함께 달림으로써 건강한 라이프 스타일을 만들어나가길 바랍니다.   

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

### A-2. custom 확장 함수

:point_right: dpToPx.kt
- xml코드와 달리 kotlin source code에서는 px단위를 사용, dp를 px로 변환해주는 확장함수
```
fun Int.dpToPx(resources: Resources): Int = TypedValue
    .applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(), resources.displayMetrics).toInt()
```

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
