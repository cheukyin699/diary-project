(ns jp.views.entry
  (:use [hiccup.core]
        [hiccup.page]
        [markdown.core])
  (:require [jp.views.default :as default]
            [jp.controllers.index :refer [default-dir]]))

(defn date-to-html
  [date]
  (let [path (str default-dir "/" date ".md")
        md   (try
               (slurp path)
               (catch Exception e (str "Error: " (.getMessage e))))]
    (md-to-html-string md :heading-anchors true)))

(defn layout
  [date]
  (default/layout
    (str "Entry for " date)
    [:a {:href "/"} "Back to index"]
    (date-to-html date)))
