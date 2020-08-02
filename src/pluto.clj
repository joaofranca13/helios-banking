(ns helios-banking.pluto
  (:require [crux.api :as crux]
            [helios-banking.earth :as earth]))


(crux/submit-tx earth/node
                [[:crux.tx/put
                  {:crux.db/id :commodity/Pu
                   :common-name "Plutonium"
                   :type :element/metal
                   :density 19.816
                   :radioactive true}]

                 [:crux.tx/put
                  {:crux.db/id :commodity/N
                   :common-name "Nitrogen"
                   :type :element/gas
                   :density 1.2506
                   :radioactive false}]

                 [:crux.tx/put
                  {:crux.db/id :commodity/CH4
                   :common-name "Methane"
                   :type :molecule/gas
                   :density 0.717
                   :radioactive false}]])


(crux/submit-tx earth/node
                [[:crux.tx/put
                  {:crux.db/id :stock/Pu
                   :commod :commodity/Pu
                   :weight-ton 21 }
                  #inst "2115-02-13T18"] ;; valid-time

                 [:crux.tx/put
                  {:crux.db/id :stock/Pu
                   :commod :commodity/Pu
                   :weight-ton 23 }
                  #inst "2115-02-14T18"]

                 [:crux.tx/put
                  {:crux.db/id :stock/Pu
                   :commod :commodity/Pu
                   :weight-ton 22.2 }
                  #inst "2115-02-15T18"]

                 [:crux.tx/put
                  {:crux.db/id :stock/Pu
                   :commod :commodity/Pu
                   :weight-ton 24 }
                  #inst "2115-02-18T18"]

                 [:crux.tx/put
                  {:crux.db/id :stock/Pu
                   :commod :commodity/Pu
                   :weight-ton 24.9 }
                  #inst "2115-02-19T18"]])

(crux/submit-tx earth/node
              [[:crux.tx/put
                {:crux.db/id :stock/N
                 :commod :commodity/N
                 :weight-ton 3 }
                #inst "2115-02-13T18"  ;; start valid-time
                #inst "2115-02-19T18"] ;; end valid-time

               [:crux.tx/put
                {:crux.db/id :stock/CH4
                 :commod :commodity/CH4
                 :weight-ton 92 }
                #inst "2115-02-15T18"
                #inst "2115-02-19T18"]])

(crux/entity (crux/db earth/node #inst "2115-02-14") :stock/Pu)

(crux/entity (crux/db earth/node #inst "2115-02-18") :stock/Pu)


(defn easy-ingest
  "Uses Crux put transaction to add a vector of documents to a specified node"
  [node docs]
  (crux/submit-tx node (mapv (fn
                               [doc]
                               [:crux.tx/put doc]) docs)))


(crux/submit-tx earth/node
                [[:crux.tx/put
                  (assoc earth/manifest :badges ["SETUP" "PUT"])]])


(crux/entity (crux/db earth/node) :manifest)
