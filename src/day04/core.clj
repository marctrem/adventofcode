(ns day04.core
  (:require [clojure.core.async :as async])
  (:import (org.apache.commons.codec.binary Hex)
           (java.security MessageDigest)
           (java.util.concurrent Executors)))


(defn generic [n-zero-prefix input]
  "Finds the smallest positive number that, once apended to the input, will produce a MD5 hash with n-zero-prefix leading zeros."

  (let [starting-bytes (take n-zero-prefix (repeat \0))]
    (loop [i 1]

      (let [d (MessageDigest/getInstance "MD5")
            message (str input i)
            m (.digest d (.getBytes message))]

        (when (-> i (mod 100000) (= 0))
          (println message))

        (if (->> m
                 Hex/encodeHex
                 (take 5)
                 (= starting-bytes))

          (println i)
          (recur (inc' i)))))))


(def part1 (partial generic 5))
(def part2 (partial generic 6))
