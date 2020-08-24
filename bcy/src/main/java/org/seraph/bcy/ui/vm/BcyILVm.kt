package org.seraph.bcy.ui.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import org.seraph.bcy.data.repository.DBRepository
import org.seraph.bcy.ui.b.MultiBean
import org.seraph.lib.ui.base.ABaseViewModel
import javax.inject.Inject


class BcyILVm @ViewModelInject constructor(
    application: Application
) : ABaseViewModel(application) {


    override fun start() {
    }



}
