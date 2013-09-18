(ns images.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [images.hdfsreader :as reader]
            [clojure.string :as s]
            [ring.middleware.cors :as cors]))

(defn content-subtype [filename]
  (-> filename (s/split #"\.") last s/lower-case))

(defroutes app-routes
  (GET "/" [] {:status 200 :body "Dat image search ya'll"})
  (GET "/image" [path filename]
       {:status 200
        :headers {"Content-Type" (str "image/" (content-subtype filename))}
        :body (reader/get-image path filename)})
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (cors/wrap-cors
        :access-control-allow-origin #".+")))
