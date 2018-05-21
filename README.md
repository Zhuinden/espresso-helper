# Espresso Helper

Do you want to write UI acceptance tests, but find Espresso demotivational with its long and verbose way of clicking a button?

``` java
Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
```

Fret not! With the help of Espresso-Helper, heavily borrowing assertions and matchers from agoda-com/Kakao, we can simplify this to the following:

``` kotlin
import com.zhuinden.espressohelper.*
import my.package.R.id.*

button.performClick()
```

The library is Kotlin-based, for extension functions.



## Using Espresso-Helper

In order to use Espresso Helper, you need to add jitpack to your project root gradle:

    buildscript {
        repositories {
            // ...
            maven { url "https://jitpack.io" }
        }
        // ...
    }
    allprojects {
        repositories {
            // ...
            maven { url "https://jitpack.io" }
        }
        // ...
    }


and add the compile dependency to your module level gradle.

    androidTestImplementation 'com.github.Zhuinden:espresso-helper:0.1.3'


## License

    Copyright 2018 Gabor Varadi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
