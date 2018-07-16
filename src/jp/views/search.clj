(ns jp.views.search
  (:use [hiccup.core]
        [hiccup.page])
  (:require [clojure.string :as s]
            [jp.views.default :as default]))

(defn make-anchor
  "Converts a header string into the corresponding anchor"
  [header]
  (s/join "_" (-> header
                  s/lower-case
                  ; Apparently, hiccup doesn't like playing by the github-styled
                  ; anchor-text conversion rules
                  ;(s/replace #"[^\w\d -]" "")
                  (s/split #" "))))

(defn add-emphasis
  [q text]
  (let [emp (partial format "<span class='search-term'>%s</span>")]
    (s/replace text q (emp q))))

(defn make-result-link
  [q day [headers body]]
  (let [href (str "/entry/" day
                  "#" (make-anchor (last headers)))
        h    (s/join " -> " headers)]
    [:li
     [:strong.search-heading
      (format "(%s) - " day)
      [:a {:href href} h]]
     [:br]
     [:p (add-emphasis q body)]]))

(defn make-result-links
  [q [day entries]]
  (map (partial make-result-link q day) entries))

(defn layout
  [q results options]
  (let [taken (:delta options)
        total (reduce + (map (comp count second) results))
        text  (if (zero? total)
                (format "No search results for '%s'" q)
                (format "Search results for '%s'" q))
        taken-text (format "%d results (%dms)" total taken)]
    (default/layout text
                    [:a {:href "/"} "Back to index"]
                    [:h1 text] [:span taken-text]
                    [:ul (map (partial make-result-links q)
                              (sort-by first results))])))

