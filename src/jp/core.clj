(ns jp.core
  (:require [ring.adapter.jetty :as jetty]
            [jp.controllers.index :as index]
            [compojure.core :refer [routes]]))

(def app
  (routes #'index/index-routes))

(defn -main
  [port]
  (jetty/run-jetty app {:port (Integer. port)}))
