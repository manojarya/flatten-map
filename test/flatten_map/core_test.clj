(ns flatten-map.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [flatten-map.core :refer :all]))

(deftest test-nested-arrays
  (testing "multi-nested-array-paths-flattening"
    (let [ms (edn/read-string (slurp (io/resource "test1.edn")))
          ks ["k1" "k2" "k3.$.k31" "k3.$.k32" "k3.$.k33.$.k331"]
          rows (flatten-maps ms ks)]
    (pprint-flatten-maps rows ks)
    (is (= 5 (count rows))))))

(deftest test-leaf-node-arrays
  (testing "leaf-node-array-paths-flattening"
    (let [ms (edn/read-string (slurp (io/resource "test1.edn")))
          ks ["k1" "k2" "k4.$."]
          rows (flatten-maps ms ks)]
      (pprint-flatten-maps rows ks)
      (is (= 6 (count rows))))))
