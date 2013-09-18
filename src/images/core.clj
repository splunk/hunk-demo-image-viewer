(ns images.core
  (:require [org.httpkit.server :as s]
            [images.handler :as h]))

(def port 5123)

(defn -main [args]
  (s/run-server h/app {:port port}))
