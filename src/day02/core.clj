(ns day02.core
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str])
  (:gen-class))


(defn find-paper-cost-for-row [row]
  (let [sides [(* (nth row 0) (nth row 1))
               (* (nth row 0) (nth row 2))
               (* (nth row 1) (nth row 2))]]

    (+ (apply min sides)
       (* 2
          (reduce + sides)))))

(defn part1 [input]
  "Solves part one of day two."
  (let [data (slurp input)
        rows (str/split data #"\n")
        parsed-rows (map #(clojure.string/split % #"x") rows)
        conv-rows (map #(map (fn [x] (Integer/parseInt x)) %) parsed-rows)]

    (reduce + (map find-paper-cost-for-row conv-rows))))


(defn find-ribbon-cost-for-row [row]
  (let [sorted-sides (sort row)]

    (+ (* 2
          (apply + (take 2 sorted-sides)))
       (apply * row))))

(defn part2 [input]
  "Solves part two of day two."
  (let [data (slurp input)
        rows (str/split data #"\n")
        parsed-rows (map #(clojure.string/split % #"x") rows)
        conv-rows (map #(map (fn [x] (Integer/parseInt x)) %) parsed-rows)]

    (reduce + (map find-ribbon-cost-for-row conv-rows))))