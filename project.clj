(defproject adventofcode2015 "0.1.0-SNAPSHOT"
  :description "My go at Advent of Code 2015"
  :url "http://adventofcode.com/"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/math.combinatorics "0.1.1"]]
  :main ^:skip-aot day02.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
