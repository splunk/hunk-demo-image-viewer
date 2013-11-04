;; Copyright (C) 2013 Splunk Inc.
;;
;; Splunk Inc. licenses this file
;; to you under the Apache License, Version 2.0 (the
;; "License"); you may not use this file except in compliance
;; with the License.  You may obtain a copy of the License at
;;
;;     http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.
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
                      (.printStackTrace e)
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
