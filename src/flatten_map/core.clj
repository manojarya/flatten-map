(ns flatten-map.core
  (:require
    [clojure.tools.namespace.repl :refer [refresh]])
  (:use
    [clojure.pprint])
  (:gen-class))

(defn select-values [m ks]
  (reduce #(conj %1 (m %2)) [] ks))

(defn ks-grp [ks]
  (let [ks-grp (group-by #(clojure.string/includes? % "$") ks)
        flat-ks (ks-grp false)
        seq-ks (map #(let [[root-key rest-path]
                           (clojure.string/split % #"\.\$\." 2)] [root-key rest-path])
                    (ks-grp true))
        arr-ks (reduce (fn [m [k v]]
                         (update m k (fn [old] (conj old v)))) {} seq-ks)]
    {:flat-ks flat-ks :arr-ks arr-ks}))

(defn flatten-map
  "Flatten a nested map m into rows for the given keys.
  Keys can have values for both a straight map look up or for collection values.
  Nested path is separated by dot (.) while collection paths are separated by dollar ($).
  Returns collection of maps as if a relational table format.
  e.g.
  user=> (flatten-map {\"k1\" 1 \"k2\" [ {\"k3\" 11 \"k4\" 21} {\"k3\" 12 \"k4\" 22}]} [\"k1\" \"k2.$.k3\"])
  returns [(1 11) (1 12)]"
  ([v p-v m ks]
   (let [{:keys [flat-ks arr-ks] :as grp } (ks-grp ks)
         flat-data (if (map? m) (select-values m flat-ks) (vector m))
         flat-data-merged (concat p-v flat-data)]
     (if (empty? arr-ks)
       (conj v flat-data-merged)
       (reduce (fn [v1 [doc-k ks1]]
                 (reduce (fn [v2 arr-doc]
                           (flatten-map v2 flat-data-merged arr-doc ks1))
                         v1 (m doc-k [{}])))
               v arr-ks))))
  ([m ks]
   (flatten-map [] [] m ks)))

(defn flatten-maps [docs ks]
  (reduce (fn [v d]
            (concat v (flatten-map d ks))) [] docs))

(defn pprint-flatten-maps [rows ks]
  (print-table ks (pmap #(zipmap ks %) rows)))
