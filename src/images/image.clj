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
