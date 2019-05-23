package org.seraph.lib.di.vm

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * ViewModelKey
 * date：2019/4/18 17:47
 * author：xiongj
 * mail：417753393@qq.com
 **/
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
