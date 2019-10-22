package org.seraph.bcy.di.module.act

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.seraph.bcy.ui.vm.BcyILVm
import org.seraph.lib.di.vm.ViewModelKey


@Module
abstract class BcyILVmModule {

    @Binds
    @IntoMap
    @ViewModelKey(BcyILVm::class)
    abstract fun bindBcyILModel(viewModel: BcyILVm): ViewModel


}