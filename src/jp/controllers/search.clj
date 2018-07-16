(ns jp.controllers.search
  (:require [compojure.core :refer [defroutes GET]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]
            [markdown-to-hiccup.core :as m]
            [hiccup-find.core :as hf]
            [clojure.string :as s]
            [jp.views.search :as search]
            [jp.controllers.index :refer [default-dir get-date-paths]]))

(defn search-hiccup
  "Searches hiccup headers/body for query string"
  [headers body q]
  (let [header-text (flatten (map #(s/split (hf/hiccup-text %) #"\n") headers))
        body-text   (flatten (map #(s/split (hf/hiccup-text %) #"\n") body))]
    (cons
      ; Blank items will be filtered out later
      (if (some #(s/includes? % q) header-text) [header-text ""] [])
      (map #(if (s/includes? % q) [header-text %] []) body-text))))

(defn parse-hiccup
  "Parses hiccup into a key-value paired thingy"
  [hic]
  (let [heads   '(:h1 :h2 :h3 :h4 :h5 :h6)
        ; Groups headers and non-headers together for easy gathering
        grouped (partition-by #(.contains heads (first %)) hic)]
    (partition 2 grouped)))

(defn get-results
  "Returns a vector of search results from a file"
  [query file]
  (let [s   (slurp (.getAbsolutePath file))
        day (first (s/split (.getName file) #"\."))
        beg [:h1 {} "BEGINNING OF FILE"]
        hic (cons beg (drop 2 (m/component (m/md->hiccup s))))
        res (map #(filter
                    (complement empty?)
                    (search-hiccup (first %) (second %) query))
                 (parse-hiccup hic))]
    (cons day (filter (complement empty?) res))))

(defn index-page
  [{:keys [params] :as req}]
  (let [beg-time    (System/currentTimeMillis)
        query       (or (:q params) "")
        files       (get-date-paths default-dir)
        raw-results (concat (map (partial get-results query) files))
        results     (filter #(> (count %) 1) raw-results)
        end-time    (System/currentTimeMillis)]
    (search/layout query results {:delta (- end-time beg-time)})))

(defroutes search-routes
  (wrap-params (wrap-keyword-params (GET "/search" [] index-page))))
