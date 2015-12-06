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


(defn find-hash-range [starting-bytes input range-size sequence-number response]
  "Finds the smallest positive number that, once apended to the input, will produce a MD5 hash with n-zero-prefix leading zeros."

  (let [range-start (* range-size sequence-number)
        sequence-range (range range-start (+ range-start range-size))
        warmed-md (MessageDigest/getInstance "MD5")]
    (.update warmed-md (.getBytes input))

    (println range-start " - " (+ range-start range-size))
    (loop [range-to-try sequence-range]
      (when ((comp not empty?) range-to-try)
        (let [current-number (first range-to-try)
              d (.clone warmed-md)
              m (.digest d (.getBytes (str current-number)))]

          (if (->> m
                   Hex/encodeHex
                   (take 5)
                   (= starting-bytes))

            (swap! response (fn [old] (if (or (zero? old)
                                              (< current-number old))
                                        current-number
                                        old)))
            (recur (rest range-to-try))))))))


(defn generic-multi [n-zero-prefix input starting-point]
  "Finds the smallest positive number that, once apended to the input, will produce a MD5 hash with n-zero-prefix leading zeros."

  (let [starting-bytes (take n-zero-prefix (repeat \0))
        range-size 100000
        response (atom 0)
        sequence-number-provider (async/chan 20)
        nthreads (-> (Runtime/getRuntime) (.availableProcessors) (* 2))
        executors (Executors/newFixedThreadPool nthreads)
        generator-thread (async/thread (loop [i (quot starting-point range-size)]
                                         (if (zero? @response)
                                           (do (async/>!! sequence-number-provider i)
                                               (recur (inc' i)))
                                           (async/close! sequence-number-provider))))

        tasks (map (fn [t]
                     (fn []
                       (loop [sequence-number (async/<!! sequence-number-provider)]
                         (if ((comp not nil?) sequence-number)
                           (do
                             (find-hash-range starting-bytes input range-size sequence-number response)
                             (recur (async/<!! sequence-number-provider)))
                           (binding [*out* *err*]
                             (println "Exiting thread" t))))))
                   (range nthreads))]

    (doseq [future (.invokeAll executors tasks)]
      (.get future))

    (.shutdown executors)
    (println "ANSWER IS" @response)))

(def part1 (partial generic 5))
(def part2 (partial generic 6))
