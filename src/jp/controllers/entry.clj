(ns jp.controllers.entry
  (:require [compojure.core :refer [defroutes GET]]))

(defn entry-page
  [req]
  "HI")

(defroutes entry-routes
  (GET "/:date" [] entry-page))
