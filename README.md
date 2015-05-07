# clojure-objc-sample

Sample clojure-objc project.

## Usage

Edit Config.xcconfig with your local release path. (~/.clojure-objc/{version} if it was downloaded with the lein plugin)

Edit project.clj with your j2objc and clojure-objc locations.

     lein objcbuild auto

     Run xcode project!

![alt usage guide](https://github.com/galdolber/clojure-objc-sample/raw/master/ios.gif)

## License

Copyright © 2014 Gal Dolber

Distributed under the Eclipse Public License either version 1.0.
