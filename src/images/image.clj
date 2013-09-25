(ns images.image
  (:import [org.imgscalr Scalr]
           [javax.imageio ImageIO]
           [java.io ByteArrayOutputStream]))

(def size 400)

(defn image-stream->byte-array [image-stream]
  (let [byte-output-stream (ByteArrayOutputStream.)]
    (do (-> (ImageIO/read image-stream)
            (Scalr/resize size (make-array java.awt.image.BufferedImageOp 0))
            (ImageIO/write "jpeg" byte-output-stream))
        (.toByteArray byte-output-stream))))
