# clojure-objc-sample

Sample clojure-objc project.

## Usage

Download j2objc and the last clojure-objc dist(static lib and headers) from: https://github.com/galdolber/clojure-objc

Edit Config.xcconfig with your local paths.

Edit project.clj with your j2objc and clojure-objc locations.

     lein clean; lein compile; lein objcbuild

     Run xcode project!

![alt usage guide](https://github.com/galdolber/clojure-objc-sample/raw/master/ios.gif)

## License

Copyright © 2014 Gal Dolber

Distributed under the Eclipse Public License either version 1.0.
