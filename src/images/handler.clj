(ns images.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [images.hdfsreader :as reader]
            [images.image :as image]
            [clojure.string :as s]
            [ring.middleware.cors :as cors])
  (:import [org.apache.commons.codec.binary Base64]
           [org.apache.commons.io IOUtils]))

(defn content-subtype [filename]
  (-> filename (s/split #"\.") last s/lower-case))

(defroutes app-routes
  (GET "/" [] {:status 200 :body "Dat image search ya'll"})
  (GET "/image" [path filename]
       {:status 200
        :headers {"Content-Type" (str "image/" (content-subtype filename))
                  "Access-Control-Allow-Headers" "x-splunk-form-key, accept, origin"
                  "Access-Control-Allow-Methods" "GET"}
        :body (-> (try
                    (-> (reader/get-image path filename)
                        image/image-stream->byte-array)
                    (catch Exception e
                      (-> (clojure.java.io/resource "public/imsorry.jpg")
                          .openStream
                          IOUtils/toByteArray)))
                  Base64/encodeBase64
                  String.)})
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (cors/wrap-cors
        :access-control-allow-origin #".+")))
