(ns jp.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [jp.controllers.index :as index]
            [jp.controllers.entry :as entry]
            [jp.controllers.search :as search]
            [compojure.core :refer [routes context]]
            [compojure.route :as route]))

(def app
  (routes
    #'index/index-routes
    #'search/search-routes
    (context "/entry" [] #'entry/entry-routes)
    (route/resources "/")))

(defn -main
  [port]
  (run-jetty (wrap-reload app) {:port (Integer. port)}))
