
# MvvmDemo
### mvvm模式框架demo  
#### 一.主要第三方框架使用：  
hilt(dagger2),okhttp3,retrofit2,arouter,Kotlin协程,glide4.0+,databinding...

#### 二.框架使用：(kotlin)  

##### 1.导入lib

`implementation project(':base_mvvm_lib')`


##### 2.开启databinding  

    apply plugin: 'kotlin-kapt'  
    ...
    android{
	    ...
	    buildFeatures{
              dataBinding = true
         }
    }
    ...
    dependencies{
	    ...
	    kapt "com.android.databinding:compiler:$databinding_version"
    }

##### 3.导入第三方依赖

##### hilt依赖注入：

    apply plugin: 'dagger.hilt.android.plugin'

    android{
           ...
          kotlinOptions {
                jvmTarget = '1.8'
            }
    }
    dependencies {
       ...
        classpath 'com.google.dagger:hilt-android-gradle-plugin:2.28.1-alpha'
    }

    dependencies{
		...
	      api "com.google.dagger:hilt-android:2.28-alpha"
          kapt 'com.google.dagger:hilt-android-compiler:2.28-alpha'
          api 'androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02'
          kapt 'androidx.hilt:hilt-compiler:1.0.0-alpha02'
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

    全局初始化第三方
    @HiltAndroidApp
    class AppApplication :Application() {

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
