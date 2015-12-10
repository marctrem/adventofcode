(ns day10.core
  (:require [clojure.string :as str]))


(defn step [input]
  (let [pb (partition-by identity input)]
    (str/join "" (reduce (fn [coll item] (-> coll
                                             (conj (first (seq (char-array (str (count item))))))
                                             (conj (first item))))
                         [] pb))))


(defn generic [times input]
  (count (reduce (fn [s _] (step s)) input (range times))))


(def part1 (partial generic 40))
(def part2 (partial generic 50))