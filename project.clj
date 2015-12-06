(defproject adventofcode2015 "0.1.0-SNAPSHOT"
  :description "My go at Advent of Code 2015"
  :url "http://adventofcode.com/"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.2.374"]
                 [org.clojure/math.combinatorics "0.1.1"]
                 [commons-codec/commons-codec "1.10"]]
  :main ^:skip-aot day05.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
