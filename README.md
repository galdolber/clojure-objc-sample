# clojure-objc-sample

Sample clojure-objc project.

## Usage

Download j2objc and the last clojure-objc dist from: https://github.com/galdolber/clojure-objc
Add j2objc to PATH

Edit project.clj with your j2objc and clojure-objc locations.
Edit the sample xcode project Build Settings as well (header search path and library search path).

     lein compile
     lein objcbuild

     Run xcode project!

## License

Copyright © 2014 Gal Dolber

Distributed under the Eclipse Public License either version 1.0.
