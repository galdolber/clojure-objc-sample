(ns clojure-objc-sample.core
  (:require [clojure-objc-sample.uikit :as uikit]))

(def red ($ ($ UIColor) :redColor))
(def blue ($ ($ UIColor) :blueColor))
(def white ($ ($ UIColor) :whiteColor))
(def black ($ ($ UIColor) :blackColor))
(def dark-text-color ($ ($ UIColor) :darkTextColor))
(def screen-bounds ($ ($ ($ UIScreen) :mainScreen) :bounds))
(def native ($ NativeFunctions))

(def demo
  (apply conj [:UIView :main {:setBackgroundColor red
                              :constraints ["H:|-[label0]-|"
                                            "H:|-[label1]-|"
                                            "H:|-[label2]-|"
                                            "V:|-[label0]-[label1]-[label2]"]}]
         (for [i (range 3)]
           [:UILabel (keyword (str "label" i)) {:setText (str "Label" i)}])))

(defn login-clicked [{:keys [login]}]
  ($ (uikit/top-controller) :pushViewController
     (uikit/controller "Demo!" demo)
     :animated true))

(defn user-changed [{:keys [pass]}]
  ($ pass :setText "password"))

(def login
  [:UIView :main
   {:setBackgroundColor blue
    :events {:WillShow
             (fn [{:keys [user]}]
               ($ user :becomeFirstResponder))}
    :constraints ["C:user.top=main.top 1.0 5"
                  "C:user.height=nil.nil 1.0 40"
                  "C:pass.top=user.bottom 1.0 5"
                  "C:pass.height=nil.nil 1.0 40"
                  "V:[pass]-[login(30)]"
                  "H:|-[user]-|"
                  "H:|-[pass]-|"
                  "H:|-[login]-|"]}
   [:CTextField :user
    {:setTextAlignment 1
     :setBackgroundColor white
     :events {:UITextFieldTextDidChangeNotification user-changed}
     :setTextColor dark-text-color}]
   [:CTextField :pass
    {:setTextAlignment 1
     :setBackgroundColor white
     :setTextColor dark-text-color}]
   [:UIButton :login
    {:setTitle:forState ["Login" 0]
     :setBackgroundColor red
     :gestures {:UITapGestureRecognizer login-clicked}}]])

(defn keyboard-will-hide [noti]
  ($ (uikit/top-view) :setFrame screen-bounds))

(defn keyboard-will-show [noti]
  (let [keyboard-frame (-> ($ noti :userInfo)
                           ($ :valueForKey "UIKeyboardFrameBeginUserInfoKey"))]
    ($ (uikit/top-view) :setFrame
       ($ native
          :cgrectmake ($ native :cgrectx screen-bounds)
          :y ($ native :cgrecty screen-bounds)
          :w ($ native :cgrectw screen-bounds)
          :h (- ($ native :cgrecth screen-bounds)
                ($ native :cgrecth keyboard-frame))))))

(defn nav-did-show-view [noti]
  (uikit/send-notification 
   (-> ($ noti :userInfo)
       ($ :valueForKey "UINavigationControllerNextVisibleViewController")
       ($ :view))
   :WillShow))

(defn main []
  (let [window ($ ($ ($ UIWindow) :alloc) :initWithFrame screen-bounds)
        navcontroller ($ ($ UINavigationController) :new)]
    ($ window :setBackgroundColor black)
    ($ window :makeKeyAndVisible)
    ($ window :setRootViewController navcontroller)

    (uikit/add-notification
     navcontroller nav-did-show-view
     :UINavigationControllerDidShowViewControllerNotification)

    ;(uikit/setup-keyboard keyboard-will-show keyboard-will-hide)

    ($ ($ navcontroller :navigationBar) :setTranslucent false)
    ($ navcontroller :pushViewController
       (uikit/controller "Login" login)
       :animated false)))
