package com.rxjava_retrofit.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.framework.http.http.RxHttp
import com.framework.http.interfac.SimpleResponseListener
import com.kotlin.mvp.bean.HomeBean
import com.rxjava_retrofit.HttpApi
import com.rxjava_retrofit.R
import com.rxjava_retrofit.adapter.HomePagerAdapter
import java.util.TreeMap

/**
 * @author: xiaxueyi
 * @date: 2022-12-30
 * @time: 10:47
 * @说明:
 */

class HomeFragment :Fragment(){
    private val TAG="HomeFragment";
    private var mRootView: View? = null

    private lateinit var mRecyclerView: RecyclerView

    private var mHomeAdapter: HomePagerAdapter? = null

    private var dataList: MutableList<HomeBean.Issue.Item> = mutableListOf()

    var parameter = TreeMap<String,Any>();


    private val linearLayoutManager by lazy {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView=mRootView?.findViewById(R.id.recycler_view)!!
        mHomeAdapter= mRootView?.let { HomePagerAdapter(it.context, dataList ) }
        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()



        parameter["id"] = "00700.HK"
        parameter["interval"] = "1m"
        parameter.put("period","1D")


        RxHttp.get()
            .setApiUrl(HttpApi.test)
            .setParameter(parameter)
            .build()
            .execute(object : SimpleResponseListener<HomeBean>() {
                override fun onSucceed(bean: HomeBean, method: String) {
                    super.onSucceed(bean, method)
                    Log.e(TAG,"输出的数据(onSuccess)${bean}")
                    dataList.addAll( bean.issueList[0].itemList)
                    mHomeAdapter?.notifyItemRangeChanged(0,bean.issueList[0].itemList.size)
                }

                override fun onCompleted() {
                    super.onCompleted()
                    Log.e(TAG,"输出的数据(onCompleted)")
                }

                override fun onError(exception: Throwable) {
                    super.onError(exception)
                    Log.e(TAG,"输出的数据(onError)${exception}")
                }
            })
    }
}