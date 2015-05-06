(defproject clojure-objc-sample "0.1.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-objcbuild "0.1.5"]]
  ; uncomment to run on device
  :objcbuild {:archs [:i386 :x86_64 #_:armv7 #_:armv7s]}
  :aot :all
  :dependencies [[uikit "0.1.5"]
                 [galdolber/clojure-objc "1.7.0-beta2"]])
