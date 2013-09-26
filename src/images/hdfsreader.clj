(ns images.hdfsreader
  (:import [com.splunk.hunk.input.entry TarEntryReader MapEntryReader]))

(def hdfs-uri (atom nil))

(defn get-entry-reader [path]
  (if (re-matches #".*\.map/data" path)
    (MapEntryReader.)
    (if (re-matches #".*\.tgz" path)
      (TarEntryReader.)
      (throw (Exception. (str "No entry reader for path: " path))))))

(defn get-image [hdfspath filename]
  (.getHdfsFileStream (get-entry-reader hdfspath)
                      @hdfs-uri
                      hdfspath
                      filename))
