(ns images.test.hdfsreader
  (:use midje.sweet
        images.hdfsreader)
  (:import org.apache.commons.io.IOUtils))

(def image-path "/imagestest/test.tgz")
(def filename "IMG_1538.jpg")
(def verify-path (str "resources/testing/" filename))



(fact
  (let [image-stream (get-image image-path filename)
        expected-stream (ClassLoader/getSystemResource verify-path)]
    image-stream =not=> nil
    (IOUtils/contentEquals image-stream expected-stream) => true))
