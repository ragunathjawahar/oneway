One Way [![CircleCI](https://circleci.com/bb/ragunathjawahar/one-way/tree/master.svg?style=svg)](https://circleci.com/bb/ragunathjawahar/one-way/tree/master)
===================
One Way is a minimalistic MVI library that helps developers build robust Android apps.

Gradle [![Download](https://api.bintray.com/packages/ragunathjawahar/red-green-io/oneway/images/download.svg)](https://bintray.com/ragunathjawahar/red-green-io/oneway/_latestVersion)
---------------------
One Way is available on JCenter. If your project does not include `jcenter()` already, add it to your project's build script.

    allprojects {
      repositories {
        jcenter()
      }
    }

Then, include the dependencies in your module's build script.

    dependencies {
      implementation 'io.redgreen.oneway:core:(latest-version)'
      implementation 'io.redgreen.oneway:core-android:(latest-version)@aar'

      testImplementation 'io.redgreen.oneway:core-test:(latest-version)' // Recommended
    }

RxJava 2 and RxAndroid are not transitively resolved, hence they have to be included explicitly.

License
---------------------

    Copyright 2018 Ragunath Jawahar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
