(ns clojure-objc-sample.uikit)

(defn add-notification [target handler n]
  ($ ($ ($ NSNotificationCenter) :defaultCenter)
     :addObserver handler
     :selector (sel "invokeWithId:")
     :name (name n)
     :object target))

(defn send-notification [target n]
  ($ ($ ($ NSNotificationCenter) :defaultCenter)
     :postNotificationName (name n)
     :object target))

(def constraint-regex #"C:(\w*)\.(\w*)(=|<=|>=)(\w*)\.(\w*) (-?\w*.\w*) (-?\w*.\w*)")

(def layout-constraints
  {:<= -1
   := 0
   :>= 1
   :left 1
   :right 2
   :top 3
   :bottom 4
   :leading 5
   :trailing 6
   :width 7
   :height 8
   :centerx 9
   :centery 10
   :baseline 11
   :nil 0})

(defn resolve-layout [c]
  (if (or (string? c) (keyword? c))
    (if-let [c (layout-constraints (if (string? c) (keyword c) c))]
      c
      (throw (Exception. (str "Constraint not found " c))))
    c))

(defn parse-constraint [^String c]
  (if (zero? (.indexOf c "C:"))
    (let [c (if (pos? (.indexOf c " ")) c (str c " 1.0 0.0"))]
      (let [[f1 p1 e f2 p2 m c] (vec (next (re-find constraint-regex c)))]
        [(str f1 "-" p1) f1 (resolve-layout p1) (resolve-layout e) f2
         (resolve-layout p2) (read-string m) (read-string c)]))
    c))

(defn autolayout [ui views c]
  (if (string? c)
    (let [c ($ ($ NSLayoutConstraint) :constraintsWithVisualFormat c
               :options 0 :metrics 0
               :views views)]
      ($ ui :addConstraints c)
      c)
    (let [[_ a b c d e f g] c
          toitemdecl (if (= d "nil") nil ($ views :objectForKey d))]
      (let [c ($ ($ NSLayoutConstraint)
                 :constraintWithItem ($ views :objectForKey a)
                 :attribute b
                 :relatedBy c
                 :toItem toitemdecl
                 :attribute e
                 :multiplier f
                 :constant g)]
        ($ ui :addConstraint c)
        c))))

(defmacro button
  ([type]
     `(let [b# ($ ($ UIButton) :buttonWithType ~type)]
        (:retain b#)
        b#))
  ([tag text b & body]
   `[(button 1) ~tag {:setTitle:forState (list ~text 0)
                      :gestures {$UITapGestureRecognizer
                                 {:setNumberOfTapsRequired 1
                                  :setNumberOfTouchesRequired 1
                                  :handler (fn [scope]
                                             (let [{:keys ~b} @scope]
                                               ~@body))}}}]))

(defn create-scope
  ([] (create-scope {}))
  ([m] (atom (assoc m :state (atom {})
                    :dealloc (atom [])))))

(defn set-property [scope view [k v]]
  (when-not (#{:constraints :events :gestures} k)
    (if (and (vector? v) (or (empty? v) (not (= -1 (.indexOf (name k) ":")))))
      (apply (partial (sel (name k)) view) v)
      ((sel (name k)) view v))))

(defn make-ui
  ([v] (make-ui (create-scope) v))
  ([scope [clazz tag props & children]]
     (let [view (if (keyword? clazz) ($ (objc-class (symbol (name clazz))) :new) clazz)
           views (if-let [views (:views @scope)]
                   views
                   (let [views ($ ($ NSMutableDictionary) :new)]
                     (swap! scope assoc :views views)
                      views))]
       
       ($ views :setValue view :forKey (name tag))
       (swap! scope assoc tag view)
       (swap! (:dealloc @scope) conj view)
       
        (doseq [p (if (map? props) props (partition 2 props))]
          (set-property scope view p))
        
        (doseq [c children]
          (let [s (make-ui scope c)]
            ($ s :setTranslatesAutoresizingMaskIntoConstraints false)
            ($ view :addSubview s)))
        
        (doseq [c (map parse-constraint (:constraints props))]
          (let [cname (keyword (first c))]
            (if (string? c)
              (autolayout view views c)
              (when-let [i (autolayout view views c)]
                (swap! scope assoc cname i)))))
        
        (doseq [[k v] (:gestures props)]
          (let [is-map (map? v)
                handler (if is-map (:handler v) v)
                selector (sel "invoke")
                handler #(handler @scope)
                g ($ ($ (objc-class (name k)) :alloc)
                     :initWithTarget handler :action selector)]
            ($ handler :retain)
            (swap! (:dealloc @scope) conj handler)
            (when is-map
              (doseq [p (dissoc v :handler)]
                (set-property scope g p)))
            ($ view :addGestureRecognizer g)))
        
        (doseq [[k v] (:events props)]
          (if (keyword? k)
            (let [kname (name k)]
              (let [handler #(v @scope)]
                ($ handler :retain)
                (swap! (:dealloc @scope) conj handler)
                ($ ($ ($ NSNotificationCenter) :defaultCenter)
                   :addObserver handler
                   :selector (sel "invoke")
                   :name kname
                   :object view)))
            (let [handler #(v @scope)]
              ($ handler :retain)
              (swap! (:dealloc @scope) conj handler)
              ($ view :addTarget handler :action (sel "invoke") :forControlEvents k))))
        view)))

(defn key-window []
  (-> ($ UIApplication)
      ($ :sharedApplication)
      ($ :keyWindow)))

(defn top-controller []
  ($ (key-window) :rootViewController))

(defn top-view []
  ($ (top-controller) :view))

(defn setup-keyboard
  "Setups global notifications for UIKeyboardWillShowNotification and UIKeyboardWillHideNotification"
  [keyboard-will-show keyboard-will-hide]

  ($ ($ ($ NSNotificationCenter) :defaultCenter)
     :addObserver keyboard-will-show
     :selector (sel "invokeWithId:")
     :name "UIKeyboardWillShowNotification"
     :object nil)

  ($ ($ ($ NSNotificationCenter) :defaultCenter)
     :addObserver keyboard-will-hide
     :selector (sel "invokeWithId:")
     :name "UIKeyboardWillHideNotification"
     :object nil))

(defn dealloc [scope]
  (doseq [v @(:dealloc @scope)]
    (send-notification v :Dealloc)
    ($ ($ ($ NSNotificationCenter) :defaultCenter) :removeObserver v)
    ($ v :release))
  ($ (:views @scope) :release)
  (reset! scope nil))

(defn controller [title view]
  (let [scope (create-scope)
        view (make-ui scope view)]
    (doto ($ ($ ($ UIKitController) :alloc) :initWithView view :withScope scope)
      ($ :setTitle title)
      ($ :setView view)
      ($ :autorelease))))

(defn nav-push
  ([controller] (nav-push controller false))
  ([controller animated] ($ (top-controller) :pushViewController controller :animated animated)))
