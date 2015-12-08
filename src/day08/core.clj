(ns day08.core
  (:require [clojure.string :as str]))


(defn part1 [input]
  "Solves part one of day eight."
  (let [data (str/trim-newline (slurp input))
        data-sequence (clojure.string/split-lines data)
        count-all (reduce + (map count data-sequence))
        count-special (reduce + (map #(-> %
                                          (str/replace #"\\\\" "/")
                                          (str/replace #"\\x.." ".")
                                          (str/replace #"\\." ".")
                                          count
                                          (- 2)) data-sequence))]

    (println (- count-all count-special))))


(defn part2 [input]
  "Solves part two of day eight."
  (let [data (str/trim-newline (slurp input))
        data-sequence (clojure.string/split-lines data)
        count-all (reduce + (map count data-sequence))

        count-special (reduce + (map #(-> %
                                          (str/replace #"^\"" "...")
                                          (str/replace #"\"$" "...")
                                          (str/replace #"\\\"" "....")
                                          (str/replace #"\\" "..")
                                          count) data-sequence))]
    
    (println (- count-special count-all))))

