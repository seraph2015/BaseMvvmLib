
# MvvmDemo
### mvvm模式框架demo  
#### 一.主要第三方框架使用：  
dagger2,okhttp3,retrofit2,arouter,Kotlin协程,glide4.0+,databinding...

#### 二.框架使用：(kotlin)  

##### 1.导入lib

`implementation project(':base_mvvm_lib')`


##### 2.开启databinding  

    apply plugin: 'kotlin-kapt'  
    ...
    android{
	    ...
	    dataBinding{
		    enabled = true 
	    }
    }
    ...
    dependencies{
	    ...
	    kapt "com.android.databinding:compiler:$databinding_version"
    }

##### 3.导入第三方依赖

##### dagger2依赖注入：

    dependencies{
		...
	    kapt "com.google.dagger:dagger-compiler:$dagger_version"  
	    kapt "com.google.dagger:dagger-android-processor:$dagger_version"
    }

##### glide图片加载：

    dependencies{
	    ...
	    kapt "com.github.bumptech.glide:compiler:$glide_version"
    }

##### ARouter路由：

    android{
     ...
	    defaultConfig{
	      ...
		    //ARouter 框架配置
		    kapt {
			    arguments {
				    arg("AROUTER_MODULE_NAME", project.getName())
			    }
		    }
	    }
    }
    
    
    dependencies{
	    ...
	    kapt "com.alibaba:arouter-compiler:$arouter_compiler_version"
    }

##### 4.初始化sdk
dagger2相关：

    @Singleton
    @Component(
        modules = [
            AndroidInjectionModule::class,
            AndroidSupportInjectionModule::class,
            ActivityBindingModule::class,
          ...
        ]
    )
    interface AppComponent : AndroidInjector<AppApplication> {
    
        //@BindsInstance使得component可以在构建时绑定实例Application
        @Component.Builder
        interface Builder {
    
            @BindsInstance
            fun application(application: Application): AppComponent.Builder
    
            fun build(): AppComponent
        }
    
    }


  界面注册绑定 (继承LibActivityBindingModule)

    @Module(includes = [LibActivityBindingModule::class])
    internal abstract class ActivityBindingModule {
    
        @ActivityScope
        @ContributesAndroidInjector(modules = [xxxmModule::class])
        abstract fun contributexxxActivityInjector(): xxxActivity
    }

注册对应界面的vm相关

    @Module
    abstract class xxxVmModule {
        @Binds
        @IntoMap
        @ViewModelKey(xxxVm::class)
        abstract fun bindMainViewModel(viewModel: xxxVm): ViewModel
    }

全局初始化第三方

    class AppApplication : DaggerApplication() {
    
        override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
            return DaggerAppComponent.builder()
                .application(this)
                .build()
        }
    
    
        override fun onCreate() {
            super.onCreate()
            //初始化第三方sdk
            initSDK()
        }
    
        private fun initSDK() {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            if (AppConfig.DEBUG) {
                // 打印日志
                ARouter.openLog()
                // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                ARouter.openDebug()
            }
            //阿里云路由框架
            ARouter.init(this)
            Utils.init(this)
        }
    
    }
