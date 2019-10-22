package org.seraph.bcy.di.module.act

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.seraph.bcy.ui.vm.MainVm
import org.seraph.lib.di.vm.ViewModelKey


@Module
abstract class MainVmModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainVm::class)
    abstract fun bindMainViewModel(viewModel: MainVm): ViewModel


}