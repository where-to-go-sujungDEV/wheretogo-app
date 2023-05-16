package com.sjdev.wheretogo.ui.keyword

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.keyword.KeywordResult
import com.sjdev.wheretogo.data.remote.keyword.KeywordService
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class KeywordRemoveActivity : AppCompatActivity(){

    lateinit var exitBtn : ImageButton
    lateinit var keywordListRv : RecyclerView
    var keywordList : ArrayList<KeywordResult> = ArrayList()
    lateinit var keywordListRVAdapter: KeywordRVAdapter
    val isDeleteMode = true

    private val keywordService = KeywordService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword_remove)

        exitBtn = findViewById(R.id.exitBtn)
        keywordListRv = findViewById(R.id.keyword_remove_rv)

        if (intent.hasExtra("keywordList")) {
            keywordList = intent.getSerializableExtra("keywordList") as ArrayList<KeywordResult>

        }
        else {
            Toast.makeText(this, "키워드를 가져오는 과정에서 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        setAdapter()

        exitBtn.setOnClickListener {
            goKeywordHome()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        goKeywordHome()
    }

    private fun goKeywordHome() {
        val intent = Intent(this@KeywordRemoveActivity, KeywordActivity::class.java)
        setResult(RESULT_OK, intent)
        if (!isFinishing) finish()
    }


    fun setAdapter(){
        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.justifyContent = JustifyContent.CENTER
        keywordListRv.layoutManager = flexboxLayoutManager

        keywordListRVAdapter = KeywordRVAdapter(keywordList, this, isDeleteMode)

        keywordListRVAdapter.setOnItemClickListener { keyword, position ->
            var userIdx = getIdx()
            var msgBuilder: AlertDialog.Builder = AlertDialog.Builder(this@KeywordRemoveActivity)
                .setTitle("키워드 삭제")
                .setMessage("선택한 키워드를 삭제하시겠습니까?")
                .setPositiveButton("예") { p0, p1 ->
                    keywordService.deleteKeyword(
                        this@KeywordRemoveActivity,
                        userIdx,
                        keyword.content,
                        position
                    )
                }
                .setNegativeButton("아니오") { p0, p1 -> }

            var msgDlg: AlertDialog = msgBuilder.create()
            msgDlg.show()
        }

        keywordListRv.adapter = keywordListRVAdapter
        keywordListRVAdapter.notifyDataSetChanged()
    }

    private fun getIdx(): Int {
        val spf = this?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

    fun deleteKeyword(keyword: String, position:Int){
        Toast.makeText(this, "키워드가 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        keywordList.removeAt(position)
        keywordListRVAdapter.notifyItemRemoved(position)
    }

}