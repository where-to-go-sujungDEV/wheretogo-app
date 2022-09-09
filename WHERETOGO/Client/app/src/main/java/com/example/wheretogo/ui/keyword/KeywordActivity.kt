package com.example.wheretogo.ui.keyword

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.keyword.KeywordResult
import com.example.wheretogo.data.remote.keyword.KeywordService
import com.example.wheretogo.databinding.ActivityChangeInfoBinding
import com.example.wheretogo.databinding.ActivityKeywordBinding
import com.example.wheretogo.ui.BaseActivity


class KeywordActivity : BaseActivity<ActivityKeywordBinding>(ActivityKeywordBinding::inflate) {

    //lateinit var binding: ActivityKeywordBinding


    lateinit var nickname : TextView

    lateinit var tagList : RecyclerView
    lateinit var tagListRVAdapter: KeywordRVAdapter
    var keywordList : ArrayList<KeywordResult> = ArrayList()

    lateinit var addBtn : TextView
    lateinit var deleteBtn : TextView
    lateinit var addInput : EditText

    var isDeleteMode = false
    var isAddMode = false

    val keywordService = KeywordService

    override fun initAfterBinding() {
        keywordService.service.getKeyword(getIdx())
        initClickListener()
        addBtn = binding.root.findViewById(R.id.keyword_add_btn_tv)
        deleteBtn = binding.root.findViewById(R.id.keyword_delete_btn_tv)
        tagList = binding.root.findViewById(R.id.keyword_rv)
        addInput = binding.root.findViewById(R.id.keyword_input)

        setAdapter()
    }

    private fun getIdx(): Int {
        val spf = this.getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

    private fun initClickListener(){
        binding.keywordDeleteBtnTv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(!isDeleteMode) {
                    isDeleteMode = true
                    setAdapter()
                } else{
                    isDeleteMode=false
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
                    keywordList.add(newKeyword)
                    tagListRVAdapter.notifyDataSetChanged()

                    return true
                }
                return false
            }
        })

    }


    fun setAdapter(){
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation= LinearLayoutManager.HORIZONTAL

        println("----------------------등록된 키워드 리스트 $keywordList")

        tagListRVAdapter = KeywordRVAdapter(keywordList, this, isDeleteMode)
        tagList.adapter = tagListRVAdapter
        tagList.layoutManager = gridLayoutManager
    }



    fun getKeyword(results: ArrayList<KeywordResult>){
        keywordList.clear()
        keywordList.addAll(results)

        setAdapter()
    }




}