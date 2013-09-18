(ns images.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [images.hdfsreader :as reader]))

(defroutes app-routes
  (GET "/" [] {:status 200 :body "Dat image search ya'll"})
  (GET "/image" [path filename] (reader/get-image path filename))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
