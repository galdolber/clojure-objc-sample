(ns sample.core
  (:require [uikit.core :as uikit]))

(def white ($ ($ UIColor) :whiteColor))

(defn on-tap [scope]
  (uikit/alert! "Clojure" "Hello World!!!"))

(def my-screen
  [:UIView :main
   {:setBackgroundColor white
    :constraints ["C:button.centerx=main.centerx"
                  "C:button.centery=main.centery"]}
   [(uikit/button 1) :button
    {:setTitle:forState ["Tap me!" 0]
     :gestures {:UITapGestureRecognizer #'on-tap}}]])

(defn main []
  (let [window ($ ($ ($ UIWindow) :alloc) :initWithFrame
                  ($ ($ ($ UIScreen) :mainScreen) :bounds))
        nav ($ ($ UINavigationController) :new)]
    ($ window :makeKeyAndVisible)
    ($ window :setRootViewController nav)
    ($ window :setBackgroundColor white)
    (reset! uikit/current-top-controller nav)
    ($ nav :setNavigationBarHidden true)
    (uikit/nav-push (uikit/controller "Screen title" my-screen))))
