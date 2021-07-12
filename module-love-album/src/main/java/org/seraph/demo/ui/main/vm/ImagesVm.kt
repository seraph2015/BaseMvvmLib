package org.seraph.demo.ui.main.vm

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.seraph.demo.R
import org.seraph.demo.ui.main.a.ImagesAdapter
import org.seraph.lib.ui.base.ABaseViewModel
import javax.inject.Inject

@HiltViewModel
class ImagesVm @Inject constructor(
    var adapter: ImagesAdapter
) : ABaseViewModel() {

    private val list = arrayListOf(
        R.mipmap.a01, R.mipmap.a02, R.mipmap.a03, R.mipmap.a04, R.mipmap.a05, R.mipmap.a06, R.mipmap.a07, R.mipmap.a08,
        R.mipmap.a09, R.mipmap.a10,R.mipmap.a11,R.mipmap.a12,R.mipmap.a13,R.mipmap.a14,R.mipmap.a15, R.mipmap.a16,
        R.mipmap.a17,R.mipmap.a18,R.mipmap.a19, R.mipmap.a20,R.mipmap.a21,R.mipmap.a22,R.mipmap.a23,R.mipmap.a24,
        R.mipmap.a25,R.mipmap.a26,R.mipmap.a27,R.mipmap.a28,R.mipmap.a29, R.mipmap.a30,R.mipmap.a31,R.mipmap.a32,
        R.mipmap.a33,R.mipmap.a34,R.mipmap.a35,R.mipmap.a36,R.mipmap.a37,R.mipmap.a38,R.mipmap.a39, R.mipmap.a40,
        R.mipmap.a41,R.mipmap.a42,R.mipmap.a43,R.mipmap.a44,R.mipmap.a45,R.mipmap.a46,R.mipmap.a47,R.mipmap.a48,
        R.mipmap.a49, R.mipmap.a50,R.mipmap.a51,R.mipmap.a52,R.mipmap.a53,R.mipmap.a54,R.mipmap.a55,R.mipmap.a56
    )

    val listData : MutableLiveData<ArrayList<Int>>  by lazy{
        MutableLiveData<ArrayList<Int>>()
    }


    override fun start(vararg any: Any?) {
        listData.value = list
    }

}

