(ns flatten-map.core
  (:require
    [clojure.tools.namespace.repl :refer [refresh]])
  (:use
    [clojure.pprint])
  (:gen-class))

(defn select-values [map ks]
  (reduce #(conj %1 (map %2)) [] ks))

(defn ks-grp [ks]
  (let [ks-grp (group-by #(clojure.string/includes? % "$") ks)
        flat-ks (ks-grp false)
        seq-ks (map #(let [[root-key rest-path] (clojure.string/split % #"\.\$\." 2)]
                       [root-key rest-path]) (ks-grp true))
        arr-ks (reduce (fn [m [k v]]
                         (update m k (fn [old] (conj old v)))) {} seq-ks)]
    {:flat-ks flat-ks :arr-ks arr-ks}))

(defn flatten-map
  ([v p-v m ks]
   (let [{:keys [flat-ks arr-ks]} (ks-grp ks)
         flat-data (select-values m flat-ks)
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
  (reduce #(concat %1 (flatten-map %2 ks)) [] docs))

(defn pprint-flatten-maps [rows ks]
  (print-table ks (pmap #(zipmap ks %) rows)))