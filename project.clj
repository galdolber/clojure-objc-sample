(defproject clojure-objc-sample "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-objcbuild "0.1.3"]]
  :objcbuild {:archs [:armv7]
              :j2objc "/Users/admin/projects/j2objc/dist"
              :clojure-objc "/Users/admin/github/clojurejvm/target"}
  :aot :all
  :objc-source-paths ["objc"]
  :dependencies [[galdolber/clojure-objc "1.5.1-1"]])
