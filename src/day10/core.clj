(ns day10.core
  (:require [clojure.string :as str]))


(defn step [input]
  (let [pb (partition-by identity input)]
    (reduce (fn [coll item] (-> coll
                                (conj (-> (count item)
                                          (+ (int \0))
                                          char))
                                (conj (first item))))
            [] pb)))


(defn generic [times input]
  (count (nth (iterate step (char-array input)) times)))


(def part1 (partial generic 40))
(def part2 (partial generic 50))