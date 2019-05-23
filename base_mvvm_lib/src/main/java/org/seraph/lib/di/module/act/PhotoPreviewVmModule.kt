package org.seraph.lib.di.module.act

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.seraph.lib.di.vm.ViewModelKey
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewVm

/**
 * 图片预览
 * date：2019/4/18 17:33
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
abstract class PhotoPreviewVmModule {

    @Binds
    @IntoMap
    @ViewModelKey(PhotoPreviewVm::class)
    abstract fun bindPhotoPreviewVM(viewModel: PhotoPreviewVm): ViewModel


}