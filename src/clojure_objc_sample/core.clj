(ns clojure-objc-sample.core)

(defn say-hi [name]
  ($ ($ ($ ($ UIAlertView) :alloc)
        :initWithTitle (str "Hello " name)
        :message "Hi! from clojure"
        :delegate nil
        :cancelButtonTitle "Cancelar"
        :otherButtonTitles nil) :show))
