(defproject flatten-map "1.0.0"
  :description "flattening a nested map into rows"
  :url "https://github.com/manojarya/flatten-map"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.namespace "0.2.11"]
                 ]
  :main ^:skip-aot flatten-map.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
