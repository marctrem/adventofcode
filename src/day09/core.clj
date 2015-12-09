(ns day09.core
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str]))


(defn parse [data]

  (reduce (fn [akku item]
            (let [tokens (str/split item #" ")
                  src (nth tokens 0)
                  dest (nth tokens 2)
                  distance (Integer. (nth tokens 4))]

              (update-in akku [src] (fn [old] (if (nil? old)
                                                {dest distance}
                                                (assoc old dest distance))))))
          {} (str/split-lines data)))

(defn generic [goal-fn input]
  "Solves both parts of day nine."
  (let [data (slurp input)
        routes (parse data)
        cities (set (concat (keys routes) (flatten (map keys (vals routes)))))]

    (reduce goal-fn (map (partial reduce +) (map (fn [path]
                                                   (let [jumps (partition 2 1 path)]
                                                     (map (fn [[src dest]] (or (get-in routes [src dest])
                                                                               (get-in routes [dest src]))) jumps)))
                                                 (combo/permutations cities))))))

(def part1 (partial generic min))
(def part2 (partial generic max))