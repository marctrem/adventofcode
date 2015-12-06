(ns day06.core
  (:require [clojure.pprint]
            [clojure.string :as str]
            [clojure.set :as set]))


(defn get-range [[x1 y1] [x2 y2]]
  (for [x (range x1 (inc x2))
        y (range y1 (inc y2))]
    [x y]))

(defn part1 [input]
  "Solves part one of day six."
  (let [data (slurp input)
        data-lines (str/split-lines data)
        line-tokens (map #(-> %
                              (str/replace #"turn " "")
                              (str/split #"\s"))
                         data-lines)]

    (reduce (fn [col item]
              (let [affected-cells (set (get-range (mapv #(Integer/parseInt %)
                                                         (-> (second item)
                                                             (str/split #",")))

                                                   (mapv #(Integer/parseInt %)
                                                         (-> (get item 3)
                                                             (str/split #",")))))

                    operation (case (first item)
                                "on" set/union
                                "off" set/difference
                                "toggle" (fn [col targets]
                                           (reduce #((if (contains? %1 %2)
                                                       disj conj) %1 %2)
                                                   col targets)))]

                (operation col affected-cells)))
            #{}
            line-tokens)))


(defn part2 [input]
  "Solves part two of day six."
  (let [data (slurp input)
        data-lines (str/split-lines data)
        line-tokens (map #(-> %
                              (str/replace #"turn " "")
                              (str/split #"\s"))
                         data-lines)]

    (reduce +
            (vals (reduce (fn [col item]
                            (let [affected-cells (get-range (mapv #(Integer/parseInt %)
                                                                  (-> (second item)
                                                                      (str/split #",")))

                                                            (mapv #(Integer/parseInt %)
                                                                  (-> (get item 3)
                                                                      (str/split #","))))

                                  operation (case (first item)
                                              "on" inc
                                              "off" (comp (partial max 0) dec)
                                              "toggle" (partial + 2))]

                              (reduce #(update-in %1 [%2] (fn [old]
                                                            (let [old (if (nil? old)
                                                                        0 old)]
                                                              (operation old))))
                                      col
                                      affected-cells)))
                          {}
                          line-tokens)))))