(ns images.main
  (:require [images.core :as core])
  (:gen-class))

(defn -main [& args] (core/-main (first args)))
