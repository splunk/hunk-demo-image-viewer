(defproject images "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [org.apache.hadoop/hadoop-core "1.1.2"]
                 [org.apache.commons/commons-compress "1.5"]
                 [midje "1.5.1"]
                 [http-kit "2.1.10"]
                 [ring-cors "0.1.0"]
                 [org.imgscalr/imgscalr-lib "4.2"]
                 [commons-codec "1.8"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler images.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}}
  :java-source-paths ["src/java"]
  :main images.main
  :jvm-opts  ["-Xmx2048M"])
