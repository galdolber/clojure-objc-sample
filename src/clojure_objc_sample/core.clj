(ns clojure-objc-sample.core
  (:require [clojure-objc-sample.uikit :as uikit]))

(def red ($ ($ UIColor) :redColor))
(def blue ($ ($ UIColor) :blueColor))
(def gray ($ ($ UIColor) :grayColor))
(def white ($ ($ UIColor) :whiteColor))
(def black ($ ($ UIColor) :blackColor))
(def dark-text-color ($ ($ UIColor) :darkTextColor))
(def screen-bounds ($ ($ ($ UIScreen) :mainScreen) :bounds))
(def native ($ NativeFunctions))

(def demo
  (let [n 5
        r (range n)
        id "label"]    
    [:UIScrollView :main
     {:setBackgroundColor white
      :constraints ["H:|[content]|"]
      :setAlwaysBounceVertical true}
     [:UIView :content
      {:constraints (conj (mapv #(str "H:|-[" id % "]-|") r)
                          (str "V:|" (reduce str (map #(str "-[" id % "]") r))))}
      (for [i r]
        [:UILabel (keyword (str id i)) {:setText (str "Label" i)}])]]))

(defn login-clicked [{:keys [login]}]
  (uikit/nav-push (uikit/controller "Demo!" demo) true))

(defn user-changed [{:keys [pass]}]
  ($ pass :setText "password"))

(def login
  [:UIView :main
   {:setBackgroundColor white
    :constraints ["V:|-[user(50)]-[pass(50)]-[login]"
                  "H:|-[user]-|"
                  "H:|-[pass]-|"
                  "H:|-[login]-|"]}
   [:UITextField :user
    {:setTextAlignment 1
     :setDelegate (nsproxy
                   ([:bool :textFieldShouldReturn :id field]
                      ($ field :resignFirstResponder) true))
     :setBackgroundColor gray
     :events {:UITextFieldTextDidChangeNotification user-changed}
     :setTextColor dark-text-color}]
   [:UITextField :pass
    {:setTextAlignment 1
     :setBackgroundColor gray
     :setTextColor dark-text-color}]
   [(uikit/button 1) :login
    {:setTitle:forState ["Login!" 0]
     :gestures {:UITapGestureRecognizer login-clicked}}]])

(defn main []
  (let [window ($ ($ ($ UIWindow) :alloc) :initWithFrame screen-bounds)
        navcontroller ($ ($ UINavigationController) :new)]
    ($ window :setBackgroundColor black)
    ($ window :setRootViewController navcontroller)
    ($ window :makeKeyAndVisible)
    ($ ($ navcontroller :navigationBar) :setTranslucent false)
    (uikit/nav-push (uikit/controller "Login" login))))
