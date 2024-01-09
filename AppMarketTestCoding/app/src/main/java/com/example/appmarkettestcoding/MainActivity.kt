package com.example.appmarkettestcoding


import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmarkettestcoding.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val channel_ID = "사과마켓"
    private val notification_ID = 1
    private lateinit var adapter: MyAdapter
    private lateinit var dataList: MutableList<MyItem>
    companion object {
        const val YOUR_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val dataList = mutableListOf<MyItem>()
        dataList.add(
            MyItem(
                R.drawable.sample_0,
                "선풍기",
                "산지 한달된 선풍기 팝니다",
                "이사가서 필요가 없어졌어요 급하게 내놓습니다",
                "대현동",
                "1,000원",
                "서울 서대문구 창천동",
                "13",
                "25",
                isLiked = false
            )
        )
        dataList.add(
            MyItem(
                R.drawable.sample_1,
                "김치냉장고",
                "김치냉장고",
                "이사로인해 내놔요",
                "안마담",
                "20,000원",
                "인천 계양구 귤현동",
                "8",
                "28",
                isLiked = false
            )
        )
        dataList.add(
            MyItem(
                R.drawable.sample_2,
                "샤넬카드지갑",
                "샤넬카드지갑",
                "고퀄지갑이구요\n사용감이 있어 싸게 내놓습니다",
                "코코유",
                "10,000원",
                "수성구 범어동",
                "23",
                "5",
                isLiked = false
            )
        )
        dataList.add(
            MyItem(
                R.drawable.sample_3,
                "금고",
                "금고",
                "금고 떼서 가져가야함\n대우월드마크센텀\n미국이주관계로 싸게 팝니다",
                "Nicole",
                "10,000원",
                "해운대구 우제2동",
                "14",
                "17",
                isLiked = false
            )
        )
        dataList.add(
            MyItem(
                R.drawable.sample_4,
                "갤럭시Z플립3",
                "갤럭시Z플립3 팝니다",
                "갤럭시Z플립3 그린 팝니다\n항시 케이스 씌어숴 썻고 필름 한장 챙겨드립니다\n화면에 살작 스크래치 난 거 말고 크게 이상은 없습니다",
                "절명",
                "150,000원",
                "연제구 연산제8동",
                "22",
                "9",
                isLiked = false
            )
        )
        dataList.add(
            MyItem(
                R.drawable.sample_5,
                "프라다 복조리팩",
                "프라다 복조리팩",
                "까임 오염없고 상태 깨끗합니다",
                "미니멀하게",
                "50,000원",
                "수원시 영통구 원천동",
                "25",
                "16",
                isLiked = false
            )
        )
        dataList.add(
            MyItem(
                R.drawable.sample_6,
                "별장",
                "울산 동해오션뷰 60평 복층 펜트하우스 1일 숙박권 펜션 힐링 숙소 별장",
                "울산 동해바다뷰 60평 복층 펜트하우스 1일 숙박권\n(에어컨이 없기에 낮은 가격으로 변경했으며 8월 초 가장 더운날 다녀가신 분 경우 시원했다고 잘 지내다 가셨습니다)\n1. 인원: 6명 기준입니다. 1인 10,000원 추가요금\n2. 장소: 북구 블루마시티, 32-33층\n3. 취사도구, 침구류, 세면도구, 드라이기 2개, 선풍기 4대 구비\n4. 예약방법: 예약금 50,000원 하시면 저희는 명함을 드리며 입실 오전 잔금 입금하시면 저희는 동.호수를 알려드리며 고객님은 예약자분 신분증 앞면 주민번호 뒷자리 가리시거나 지우시고 문자로 보내주시면 저희는 카드키를 우편함에 놓아 둡니다.\n5. 33층 옥상 야외 테라스 있음, 가스버너 있음\n6. 고기 굽기 가능\n7. 입실 오후 3시, 오전 11시 퇴실, 정리, 정돈 , 밸브 잠금 부탁드립니다.\n8. 층간소음 주의 부탁드립니다.\n9. 방3개, 화장실3개, 비데 3개\n10.저희 집안이 쓰는 별장입니다.",
                "굿리치",
                "150,000원",
                "남구 옥동",
                "142",
                "53",
                isLiked = false
            )
        )
        dataList.add(
            MyItem(
                R.drawable.sample_7,
                "샤넬 가방",
                "샤넬 탑핸드 가방",
                "샤넬 트랜디 CC 탑핸들 스몰 램스킨 블랙 금장 플랩백!\n \n색상 : 블랙\n사이즈:25.5cm * 17.5cm * 8cm\n구성 :본품, 더스트\n \n급하게 돈이 필요해서 팝니다 ㅠ ㅠ",
                "난쉽",
                "180,000원",
                "동래구 온천제2동",
                "31",
                "7",
                isLiked = false
            )
        )
        dataList.add(
            MyItem(
                R.drawable.sample_8,
                "4행정 엔진분무기",
                "4행정 엔진분무기 판매합니다",
                "3년전에 사서 한번 사용하고 그대로둔 상태입니다. 요즘 사용은 안해봤습니다. 그래서 저렴하게 내놓습니다. 중고라 반품은 어렵습니다",
                "알뜰한",
                "30,000원",
                "원주시 명륜2동",
                "7",
                "28",
                isLiked = false
            )
        )
        dataList.add(
            MyItem(
                R.drawable.sample_9,
                "셀린느 가방",
                "셀린느 버킷 가방",
                "22년 신세계 대전 구매입니당\n셀린느 버킷백\n구매해서 몇번사용했어요\n까짐 스크래치 없습니다.\n타지역에서 보내는거라 택배로 진행합니당!",
                "똑태현",
                "190,000원",
                "중구 동화동",
                "40",
                "6",
                isLiked = false
            )
        )


        adapter = MyAdapter(dataList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // 아이템 클릭 리스너 설정
        adapter.itemClick = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val myItem = dataList[position]
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("selected_item", myItem)
                startActivityForResult(intent, 1)
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            }
        }

        // 아이템 롱 클릭 리스너 설정
        adapter.itemLongClick = object : MyAdapter.ItemLongClick {
            override fun onLongClick(view: View, position: Int) {
                val myItem = dataList[position]
                showDeleteConfirmationDialog(position, myItem)
            }
        }

        // 알림 이미지 클릭 리스너 설정
        val ivAlarm = findViewById<ImageView>(R.id.iv_alram)
        ivAlarm.setOnClickListener {
            ivAlarm.setImageResource(R.drawable.clicked_image)
            ivAlarm.postDelayed({
                ivAlarm.setImageResource(R.drawable.original_image)
                createNotification()
            }, 300)
        }

        // RecyclerView 스크롤 시 FAB 숨김 처리
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var isFabVisible = true
        val floatingButton = findViewById<FloatingActionButton>(R.id.floating_button)
        floatingButton.setOnClickListener {
            scrollToTop()
        }

        recyclerView.viewTreeObserver.addOnGlobalLayoutListener {
            floatingButton.hide()
            isFabVisible = false
        }
    }

    // RecyclerView 맨 위로 스크롤
    private fun scrollToTop() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.smoothScrollToPosition(0)
    }

    // 삭제 다이얼로그 표시
    private fun showDeleteConfirmationDialog(position: Int, myItem: MyItem) {
        val dialogBuilder = AlertDialog.Builder(this)
        val productName = myItem.aProName

        dialogBuilder.setMessage("'$productName' 상품을 삭제하시겠습니까?")
            .setCancelable(false)
            .setPositiveButton("확인") { dialog, _ ->
                dataList.removeAt(position)
                adapter.notifyItemRemoved(position)
                Toast.makeText(this, "'$productName' 상품이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("삭제 확인")
        alert.show()
    }

    // 알림 생성
    private fun createNotification() {
        val builder = NotificationCompat.Builder(this, channel_ID)
            .setSmallIcon(R.drawable.im_alram)
            .setContentTitle("사과마켓")
            .setContentText("새로운 중고 물품이 들어왔습니다")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        builder.setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(notification_ID, builder.build())
        }
    }

    // onActivityResult() 오버라이드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == YOUR_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val isLiked = data?.getBooleanExtra("isLiked", false) ?: false
            val updatedPosition = data?.getIntExtra("updatedPosition", -1) ?: -1

            if (updatedPosition != -1) {
                val myItem = dataList[updatedPosition]
                myItem.isLiked = isLiked

                // isLiked 값에 따라 이미지 변경
                if (isLiked) {
                    myItem.aImage = R.drawable.im_heart
                } else {
                    myItem.aImage = R.drawable.im_heart_empty
                }

                adapter.notifyItemChanged(updatedPosition)
            }
        }
    }

    // 뒤로 가기 버튼 처리
    override fun onBackPressed() {
        showExitConfirmationDialog()
    }

    // 종료 다이얼로그 표시
    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("앱 종료")
            .setMessage("앱을 종료하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                finish()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }
}