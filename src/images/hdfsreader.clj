(ns images.hdfsreader
  (:import images.java.HdfsReader))

(defn get-image [hdfspath filename]
  (HdfsReader/getHdfsFileStream "hdfs://localhost:9000" hdfspath filename))
