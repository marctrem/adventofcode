(ns day07.core
  (:require [clojure.string :as str]
            [clojure.core.async :as async]
            [reagi.core :as r]))

(defn clamp16 [in]
  (bit-and in 0xFFFF))

(def AND bit-and)
(def OR bit-or)
(def NOT (comp clamp16 bit-not))
(def RSHIFT bit-shift-right)
(def LSHIFT (comp clamp16 bit-shift-left))




(defn evaluate-expr [wires expr lvl]
  (println (clojure.string/join "" (repeat lvl " ")) expr)

  (let [spl-expr (str/split expr #" ")]

    (case (count spl-expr)
      1 (let [sym (get spl-expr 0)]
          (if (nil? (re-matches #"\d+" sym))
            (evaluate-expr wires (get wires sym) (inc lvl))
            (Integer. sym)))

      2 (eval (list (symbol (get spl-expr 0)) (evaluate-expr wires (get spl-expr 1) (inc lvl))))
      3 (eval (list (symbol (get spl-expr 1)) (evaluate-expr wires (get spl-expr 0) (inc lvl)) (evaluate-expr wires (get spl-expr 2) (inc lvl)))))))


(def part1
  (memoize (fn [wire input]
  "Solves part one of day seven."
  (let [data (slurp input)
        wires (atom {})
        data-chan (async/chan)
        arrow-split-chan (async/map #(str/split % #" -> ") [data-chan])]

    (async/onto-chan data-chan (str/split-lines data))

    (async/<!!
      (async/go-loop [] (let [valu (async/<! arrow-split-chan)]

                          (when ((comp not nil?) valu)
                            (swap! wires #(assoc % (last valu) (first valu)))
                            (recur)))))


    @wires

    (evaluate-expr @wires (get @wires wire) 0)))))