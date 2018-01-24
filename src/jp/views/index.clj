(ns jp.views.index
  (:use [hiccup.core]
        [hiccup.page])
  (:require [jp.views.default :as default]))

(defn make-date-link
  [date]
  (let [str-format (java.text.SimpleDateFormat. "yyyy-MM-dd")
        date-format (java.text.SimpleDateFormat. "EEE MMM dd yyyy")
        jdate (.parse str-format date)
        sdate (.format date-format jdate)]
    [:li [:pre [:a {:href (str "/entries/"
                           date)}
                sdate]]]))

(defn layout
  [dates]
  (default/layout
    "The Journal Project"
    [:p (str "A total of "
             (count dates)
             " entries found.")]
    [:ul
     (map #(make-date-link %)
          dates)]))
