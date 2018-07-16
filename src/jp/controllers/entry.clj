(ns jp.controllers.entry
  (:require [compojure.core :refer [defroutes GET]]
            [jp.views.entry :as entry]))

(defn entry-page
  [date]
  (entry/layout date))

(defroutes entry-routes
  (GET "/:date" [date] (entry-page date)))
