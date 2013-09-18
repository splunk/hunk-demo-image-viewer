(ns images.hdfsreader
  (:import images.java.HdfsReader))

(defn get-image [hdfspath filename]
  (HdfsReader/getHdfsFileStream hdfspath filename))
