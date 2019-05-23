package org.seraph.demo.di.module.act

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.seraph.demo.ui.main.vm.MainVm
import org.seraph.lib.di.vm.ViewModelKey

/**
 * org.seraph.ktmvvm.di.module.activity
 * date：2019/4/18 17:33
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
abstract class MainVmModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainVm::class)
    abstract fun bindMainViewModel(viewModel: MainVm): ViewModel


}