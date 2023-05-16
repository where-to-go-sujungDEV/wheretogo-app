package com.sjdev.wheretogo.ui.keyword

import android.content.Intent
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.keyword.*
import com.sjdev.wheretogo.databinding.ActivityKeywordBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class KeywordActivity : BaseActivity<ActivityKeywordBinding>(ActivityKeywordBinding::inflate) {
    private var  userId = 0
    lateinit var nickname : TextView
    lateinit var keywordListRv : RecyclerView
    lateinit var keywordListRVAdapter: KeywordRVAdapter
    var keywordList : ArrayList<KeywordResult> = ArrayList()
    private val keywordService = KeywordService

    lateinit var addBtn : TextView
    lateinit var deleteBtn : TextView
    lateinit var addInput : EditText

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    val isDeleteMode = false

    override fun initAfterBinding() {
        nickname = binding.root.findViewById(R.id.keyword_nickname_tv)
        addBtn = binding.root.findViewById(R.id.keyword_add_btn_tv)
        deleteBtn = binding.root.findViewById(R.id.keyword_delete_btn_tv)
        keywordListRv = binding.root.findViewById(R.id.keyword_rv)
        addInput = binding.root.findViewById(R.id.keyword_input)

        nickname.text = getName()

        initClickListener()
        keywordService.getKeyword(this, getIdx())

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) { keywordService.getKeyword(this, getIdx()) }
        }

    }


    private fun initClickListener(){
//        삭제하기 버튼을 눌렀을 때.
        binding.keywordDeleteBtnTv.setOnClickListener {
            val removeIntent = Intent(this, KeywordRemoveActivity::class.java)
            removeIntent.putExtra("keywordList", keywordList)
            activityResultLauncher.launch(removeIntent)
        }

//        추가하기 버튼을 눌렀을 때.
        binding.keywordAddBtnTv.setOnClickListener {
            val addIntent = Intent(this, KeywordAddActivity::class.java)
            addIntent.putExtra("keywordList", keywordList)
            activityResultLauncher.launch(addIntent)
        }


    }


    fun setAdapter(){
//        val gridLayoutManager = GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
//        gridLayoutManager.orientation= LinearLayoutManager.HORIZONTAL

        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW)
        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER)


        keywordListRVAdapter = KeywordRVAdapter(keywordList, this, isDeleteMode)
        keywordListRv.adapter = keywordListRVAdapter
//        tagList.layoutManager = gridLayoutManager
        keywordListRv.layoutManager = flexboxLayoutManager


        keywordListRVAdapter.notifyDataSetChanged()
    }


    fun setKeywordList(keyword:String) {
        keywordList.add(KeywordResult(keyword.toString()))
        keywordListRVAdapter.notifyDataSetChanged()
    }

    fun getKeywordList(results: ArrayList<KeywordResult>){
        keywordList.clear()
        keywordList.addAll(results)

        setAdapter()
    }


    private fun getIdx(): Int {
        val spf = this.getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

    private fun getName(): String {
        val spf = this?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("nickname","USER")!!
    }





    /*
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

     */

    /*
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

     */
    }




