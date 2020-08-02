
(ns helios-banking.mercury
  (:require [crux.api :as crux]
            [helios-banking.earth :as earth]
            [helios-banking.pluto :as pluto]))

(pluto/easy-ingest earth/node
                   [{:crux.db/id :commodity/Pu
                      :common-name "Plutonium"
                      :type :element/metal
                      :density 19.816
                      :radioactive true}

                     {:crux.db/id :commodity/N
                      :common-name "Nitrogen"
                      :type :element/gas
                      :density 1.2506
                      :radioactive false}

                     {:crux.db/id :commodity/CH4
                      :common-name "Methane"
                      :type :molecule/gas
                      :density 0.717
                      :radioactive false}

                     {:crux.db/id :commodity/Au
                      :common-name "Gold"
                      :type :element/metal
                      :density 19.300
                      :radioactive false}

                     {:crux.db/id :commodity/C
                      :common-name "Carbon"
                      :type :element/non-metal
                      :density 2.267
                      :radioactive false}

                     {:crux.db/id :commodity/borax
                      :common-name "Borax"
                      :IUPAC-name "Sodium tetraborate decahydrate"
                      :other-names ["Borax decahydrate" "sodium borate"
                                    "sodium tetraborate" "disodium tetraborate"]
                      :type :mineral/solid
                      :appearance "white solid"
                      :density 1.73
                      :radioactive false}])

(crux/q (crux/db earth/node)
        '{:find [element]
          :where [[element :type :element/metal]]})

(=
 (crux/q (crux/db earth/node)
         '{:find [element]
           :where [[element :type :element/metal]]})

 (crux/q (crux/db earth/node)
         {:find '[element]
          :where '[[element :type :element/metal]]})

 (crux/q (crux/db earth/node)
         (quote
        {:find [element]
         :where [[element :type :element/metal]]})))

(crux/q (crux/db earth/node)
        '{:find [name]
          :where [[e :type :element/metal]
                  [e :common-name name]]})

(crux/q (crux/db earth/node)
        '{:find [name rho]
          :where [[e :density rho]
                  [e :common-name name]]})

(crux/q (crux/db earth/node)
        {:find '[name]
         :where '[[e :type t]
                  [e :common-name name]]
         :args [{'t :element/metal}]})

(defn filter-type
  [type]
  (crux/q (crux/db earth/node)
          {:find '[name]
           :where '[[e :type t]
                    [e :common-name name]]
           :args [{'t type}]}))


(defn filter-apperence
  [description]
  (crux/q (crux/db earth/node)
          {:find '[name IUPAC]
           :where '[[e :common-name name]
                    [e :IUPAC-name IUPAC]
                    [e :appearance appearence]]
           :args [{'appearence description}]}))


(filter-type :element/metal)


(filter-apperence "white solid")

(crux/submit-tx earth/node
                [[:crux.tx/put (assoc earth/manifest
                                      :badges ["SETUP" "PUT" "DATALOG-QUERIES"]
                                      :pilot-name "João")]])

(crux/entity (crux/db earth/node) :manifest)
