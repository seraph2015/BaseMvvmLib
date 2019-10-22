package org.seraph.bcy.di.module.act

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.seraph.bcy.ui.vm.BcyShVm
import org.seraph.lib.di.vm.ViewModelKey


@Module
abstract class BcyShVmModule {

    @Binds
    @IntoMap
    @ViewModelKey(BcyShVm::class)
    abstract fun bindBcyShwModel(viewModel: BcyShVm): ViewModel


}