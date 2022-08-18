//package com.example.wheretogo.ui.keyword
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.EditText
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.wheretogo.R
//import com.example.wheretogo.data.remote.keyword.KeywordResult
//import com.example.wheretogo.data.remote.keyword.KeywordService
//import com.example.wheretogo.databinding.FragmentKeywordBinding
//import org.w3c.dom.Text
//
//
//class KeywordFragment :Fragment() {
//    val TAG = "KeywordFragment"
//
//    lateinit var binding: FragmentKeywordBinding
//
//    lateinit var nickname : TextView
//
//    lateinit var tagList : RecyclerView
//    lateinit var tagListRVAdapter: KeywordRVAdapter
//    lateinit var keywordList : ArrayList<KeywordResult>
//
//    lateinit var addBtn : TextView
//    lateinit var deleteBtn : TextView
//    lateinit var addInput : EditText
//
//    var isDeleteMode : Boolean = false
//
//    val keywordService = KeywordService
//    val userIdx = getIdx()
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentKeywordBinding.inflate(inflater, container, false)
//        keywordService.service.getKeyword(userIdx)
//
//        //nickname =
//
//        addBtn = binding.root.findViewById(R.id.keyword_add_btn_tv)
//        deleteBtn = binding.root.findViewById(R.id.keyword_delete_btn_tv)
//        tagList = binding.root.findViewById(R.id.keyword_rv)
//        addInput = binding.root.findViewById(R.id.keyword_input)
//
//        deleteBtn.setOnClickListener(object: View.OnClickListener {
//            override fun onClick(p0: View?) {
//                isDeleteMode = true
//                setAdapter()
//
//            }
//        })
//
//
//
//        return binding.root
//    }
//
//    //유저 인덱스 가져오는 함수
//    private fun getIdx(): Int {
//        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
//        return spf!!.getInt("userIdx",-1)
//    }
//
//    fun getKeyword(results: ArrayList<KeywordResult>){
//        keywordList.clear()
//        keywordList.addAll(results)
//
//        setAdapter()
//    }
//
//    fun setAdapter(){
//        val gridLayoutManager = GridLayoutManager(context, 2)
//        gridLayoutManager.orientation=LinearLayoutManager.HORIZONTAL
//
//        tagListRVAdapter = KeywordRVAdapter(keywordList, requireContext(), isDeleteMode)
//        tagList.adapter = tagListRVAdapter
//        tagList.layoutManager = gridLayoutManager
//    }
//
//
//}
//
//
//
//
