(ns day01.core)

(defn part1 [input]
  "Solves part one of day one."
  (let [data (slurp input)
        freq (frequencies data)]
    (- (freq \() (freq \)))))


(defn part2 [input]
  "Solves part two of day one."
  (let [data (slurp input)]
    (loop [in data floor 0]
      (if (= -1 floor)
        (println (- (count data) (count in)))
        (let [_ (println (first in) floor)
              next-floor ((case (first in)
                            \( +
                            \) -) floor 1)]
          (recur (rest in) next-floor))))))