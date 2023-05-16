package com.sjdev.wheretogo.ui.keyword

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.keyword.KeywordResult
import com.sjdev.wheretogo.data.remote.keyword.KeywordService
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class KeywordAddActivity : AppCompatActivity() {

    lateinit var exitBtn : ImageButton
    lateinit var keywordInput : EditText
    lateinit var keywordRv : RecyclerView

    var keywordList : ArrayList<KeywordResult> = ArrayList()
    lateinit var keywordListRVAdapter: KeywordRVAdapter

    private val keywordService = KeywordService

    val isDeleteMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword_add)

        keywordRv = findViewById(R.id.keyword_add_rv)
        exitBtn = findViewById(R.id.exitBtn)
        keywordInput = findViewById(R.id.keyword_input)

        if (intent.hasExtra("keywordList")) {
            keywordList = intent.getSerializableExtra("keywordList") as ArrayList<KeywordResult>

        }
        else {
            Toast.makeText(this, "키워드를 가져오는 과정에서 오류가 발생했습니다.",Toast.LENGTH_SHORT).show()
            finish()
        }

        setAdapter()

//        엔터 키를 누를 경우 키워드가 입력된다.
        keywordInput.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if ((event.action == KeyEvent.ACTION_DOWN)&&(keyCode== KeyEvent.KEYCODE_ENTER)) {
                    var newKeyword: KeywordResult = KeywordResult(keywordInput.text.toString())

                    keywordService.setKeyword(this@KeywordAddActivity, getIdx(), newKeyword.content)

                    return true
                }
                return false
            }
        })

        exitBtn.setOnClickListener {
            goKeywordHome()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goKeywordHome()
    }



    private fun goKeywordHome() {
        val intent = Intent(this@KeywordAddActivity, KeywordActivity::class.java)
        setResult(RESULT_OK, intent)
        if (!isFinishing) finish()
    }

    fun setAdapter(){
        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.justifyContent = JustifyContent.CENTER
        keywordRv.layoutManager = flexboxLayoutManager

        keywordListRVAdapter = KeywordRVAdapter(keywordList, this, isDeleteMode)
        keywordRv.adapter = keywordListRVAdapter

        keywordListRVAdapter.notifyDataSetChanged()
    }

    fun setKeywordList(keyword:String) {
        Toast.makeText(this@KeywordAddActivity, "키워드를 성공적으로 등록했습니다.",Toast.LENGTH_SHORT).show()
        keywordList.add(KeywordResult(keyword.toString()))
        keywordListRVAdapter.notifyDataSetChanged()

        keywordInput.text = null
    }


    private fun getIdx(): Int {
        val spf = this.getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }
}