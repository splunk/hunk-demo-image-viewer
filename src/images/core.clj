(ns images.core
  (:require [org.httpkit.server :as s]
            [images.hdfsreader :as hdfs]
            [images.handler :as h]))

(def port 5123)

(defn -main [hdfs-uri]
  (reset! hdfs/hdfs-uri hdfs-uri)
  (s/run-server h/app {:port port}))
