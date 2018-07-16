(ns jp.views.default
  (:use [hiccup.core]
        [hiccup.page]))

(defn layout
  "The default layout for any page"
  [title & content]
  (html5 [:head
          [:title title]
          (include-css "/css/default.css")]
         [:body
          [:div.container content]]))
