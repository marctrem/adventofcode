(ns day03.core)


(defn part1 [input]
  "Solves part one of day three."
  (let [data (slurp input)
        initial-world {:houses-visited #{[0 0]}
                       :x              0
                       :y              0}]

    (-> (reduce (fn [coll item]
                  (as-> coll $

                        (case item
                          \^ (update-in $ [:y] inc)
                          \v (update-in $ [:y] dec)
                          \< (update-in $ [:x] dec)
                          \> (update-in $ [:x] inc)
                          )

                        (update-in $ [:houses-visited] #(conj % [($ :x) ($ :y)]))))

                initial-world
                data)

        (get :houses-visited)
        count
        println)))


(defn part2 [input]
  "Solves part one of day three."
  (let [data (slurp input)
        initial-world {:houses-visited #{[0 0]}
                       :santa          {:x 0 :y 0}
                       :robo-santa     {:x 0 :y 0}
                       :santa?         true}]               ; is it Santa or Robo-Santa?

    (-> (reduce (fn [coll item]
                  (let [current-actor (if (coll :santa?) :santa :robo-santa)]
                    (as-> coll $
                          (case item
                            \^ (update-in $ [current-actor :y] inc)
                            \v (update-in $ [current-actor :y] dec)
                            \< (update-in $ [current-actor :x] dec)
                            \> (update-in $ [current-actor :x] inc))

                          (update-in $ [:houses-visited] #(conj % [(-> $ current-actor :x) (-> $ current-actor :y)]))
                          (update-in $ [:santa?] not))))

                initial-world
                data)

        (get :houses-visited)
        count
        println)))


