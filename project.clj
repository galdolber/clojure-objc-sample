(defproject clojure-objc-sample "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-objcbuild "0.1.1"]]
  :objcbuild {:j2objc "path/to/j2objc"
              :clojure-objc "path/to/clojure-objc"}
  :aot :all
  :dependencies [[galdolber/clojure-objc "1.5.1"]])
