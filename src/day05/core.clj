(ns day05.core
  (:require [clojure.pprint]))


(defn part1 [input]
  "Solves part one of day five."
  (let [data (slurp input)
        data-sequence (clojure.string/split-lines data)
        cond3 (filter (fn [el] (nil? (re-find #"ab|cd|pq|xy" el))))
        cond2 (filter (fn [el] ((comp not nil?) (re-find #"([aeiou].*){3,}" el))))
        cond1 (filter (fn [el] ((comp not nil?) (re-find #"(.)\1" el))))]

    (count (sequence (comp cond1 cond2 cond3) data-sequence))))



(defn part2 [input]
  "Solves part two of day five."
  (let [data (slurp input)
        data-sequence (clojure.string/split-lines data)
        cond2 (filter (fn [el] ((comp not nil?) (re-find #"(.).\1" el))))
        cond1 (filter (fn [el] ((comp not nil?) (re-find #"(..).*\1" el))))]

    (count (sequence (comp cond1 cond2) data-sequence))))