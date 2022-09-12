package com.example.wheretogo.ui.keyword

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.keyword.*
import com.example.wheretogo.databinding.ActivityKeywordBinding
import com.example.wheretogo.ui.BaseActivity
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeywordActivity : BaseActivity<ActivityKeywordBinding>(ActivityKeywordBinding::inflate) {
    private var  userId = 0
    lateinit var nickname : TextView
    lateinit var tagList : RecyclerView
    lateinit var tagListRVAdapter: KeywordRVAdapter
    var keywordList : ArrayList<KeywordResult> = ArrayList()

    lateinit var addBtn : TextView
    lateinit var deleteBtn : TextView
    lateinit var addInput : EditText

    var isDeleteMode = false
    var isAddMode = false


    private val keywordService = getRetrofit().create(KeywordRetrofitInterface::class.java)

    override fun initAfterBinding() {
        nickname = binding.root.findViewById(R.id.keyword_nickname_tv)
        addBtn = binding.root.findViewById(R.id.keyword_add_btn_tv)
        deleteBtn = binding.root.findViewById(R.id.keyword_delete_btn_tv)
        tagList = binding.root.findViewById(R.id.keyword_rv)
        addInput = binding.root.findViewById(R.id.keyword_input)

        nickname.text = getName()

        initClickListener()
        getKeyword(getIdx())
    }


    private fun initClickListener(){
        binding.keywordDeleteBtnTv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(!isDeleteMode) {
                    isDeleteMode = true

                    deleteBtn.text = "종료하기"
                    addBtn.isEnabled=false

                    setAdapter()
                } else{
                    isDeleteMode=false
                    deleteBtn.text = "삭제하기"

                    addBtn.isEnabled=true

                    setAdapter()
                }
            }
        })

        binding.keywordAddBtnTv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(!isAddMode){
                    addInput.isEnabled = true
                    addInput.visibility = View.VISIBLE

                    addBtn.text = "종료하기"
                    deleteBtn.isEnabled = false
                    isAddMode = true

                    binding.keywordInput.hint= "등록할 키워드를 입력해주세요."
                }
                else{
                    addInput.isEnabled = false
                    addInput.visibility = View.INVISIBLE

                    addBtn.text = "추가하기"
                    deleteBtn.isEnabled = true
                    isAddMode = false
                }
            }
        })

        binding.keywordInput.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if ((event.action == KeyEvent.ACTION_DOWN)&&(keyCode==KeyEvent.KEYCODE_ENTER)) {
                    var newKeyword: KeywordResult = KeywordResult(addInput.text.toString())
                    setKeyword(getIdx(), newKeyword.content)
                    return true
                }
                return false
            }
        })

    }


    fun setAdapter(){
//        val gridLayoutManager = GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
//        gridLayoutManager.orientation= LinearLayoutManager.HORIZONTAL

        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW)
        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER)


        tagListRVAdapter = KeywordRVAdapter(keywordList, this, isDeleteMode)
        tagList.adapter = tagListRVAdapter
//        tagList.layoutManager = gridLayoutManager
        tagList.layoutManager = flexboxLayoutManager

        tagListRVAdapter.notifyDataSetChanged()
    }

    private fun getIdx(): Int {
        val spf = this.getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

    private fun getName(): String {
        val spf = this?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("nickname","USER")!!
    }


    fun getKeywordList(results: ArrayList<KeywordResult>){
        keywordList.clear()
        keywordList.addAll(results)

        setAdapter()
    }


    fun getKeyword(userID: Int){
        keywordService.getKeyword(userID).enqueue(object: Callback<KeywordResponse> {
            override fun onResponse(
                call: Call<KeywordResponse>,
                response: Response<KeywordResponse>
            ) {
                val resp = response.body()!!
                when(val code = resp.code){
                    200->{
                        getKeywordList(resp.results)
                    }
                    else ->{}

                }
            }

            override fun onFailure(call: Call<KeywordResponse>, t: Throwable) {
                Log.d("getKeyword/FAILURE", t.message.toString())
            }

        })
    }

    fun setKeyword(userID:Int, keyword:String) {
        keywordService.setKeyword(userID, keyword).enqueue(object : Callback<SetKeywordResponse> {
            override fun onResponse(call: Call<SetKeywordResponse>, response: Response<SetKeywordResponse>) {
                val resp = response.body()!!
                when(val code = resp.code){
                    201->{
                        Log.d("setKeyword/SUCCESS", resp.msg)

                        keywordList.add(KeywordResult(keyword.toString()))
                        binding.keywordInput.text = null
                        tagListRVAdapter.notifyDataSetChanged()

                    }
                    202->{
                        Log.d("setKeyword/ERROR", resp.msg)
                        Toast.makeText(this@KeywordActivity,resp.msg, Toast.LENGTH_SHORT).show()

                    }
                    500-> {
                        Log.d("setKeyword/ERROR", resp.msg)
                        Toast.makeText(this@KeywordActivity,"서버 오류가 발생했습니다.\n다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<SetKeywordResponse>, t: Throwable) {
                Log.d("setKeyword/FAILURE", t.message.toString())
            }
        })
    }




}