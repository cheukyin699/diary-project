(ns jp.controllers.index
  (:require [compojure.core :refer [defroutes GET]]
            [jp.views.index :as index]
            [clojure.string :as string]))

(def default-dir "./entries")

(defn get-dates
  "Obtains all dates where things are entered"
  [dir]
  (->>
    (file-seq (clojure.java.io/file dir))
    rest
    (map #(-> (.getName %)
              (string/split #"\.")
              first))
    sort
    reverse))

(defn index-page
  [req]
  (index/layout (get-dates default-dir)))

(defroutes index-routes
  (GET "/" [] index-page))
