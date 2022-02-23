(ns clojure-api.db
  (:require [clojure.java.jdbc :as sql]
            [com.stuartsierra.component :as component]
            [graphqlize.lacinia.core :as l]
            [com.walmartlabs.lacinia :as lacinia]
            [jdbc.pool.c3p0 :as pool]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.data.json :as json])
  (:import (com.mchange.v2.c3p0 ComboPooledDataSource)))

(def db {:dbtype "postgresql"
         :dbname "yoda_dev"
         :host "localhost"
         :port 5432
         :user "postgres"})

(def db-spec {:dbtype "postgresql" :dbname "yoda_dev" :user "postgres"})

(defn ^:private pooled-data-source
  [host dbname user password port]
  {:datasource
   (doto (ComboPooledDataSource.)
     (.setDriverClass "org.postgresql.Driver")
     (.setJdbcUrl (str "jdbc:postgresql://" host ":" port "/" dbname))
     (.setUser user)
     (.setPassword password))})

(defrecord ClojureApiDb [ds]

  component/Lifecycle

  (start [this]
    (assoc this
           :ds (pooled-data-source "localhost" "yoda_dev" "postgres" "" 5432)))

  (stop [this]
    (-> ds :datasource .close)
    (assoc this :ds nil)))

(defn new-db
  []
  {:db (map->ClojureApiDb {})})

;; (def get-products
;;   (sql/query db-spec ["SELECT art_nr, cost_price, variants_count FROM products"]))

(defn product-by-art-nr
  [component art_nr]
  (first
   (sql/query (:ds component)
               ["SELECT id, art_nr, cost_price, variants_count FROM products where art_nr = ?" art_nr])))

(defn product_for_variant
  [component product_id]
  (first
   (sql/query (:ds component)
              ["SELECT id, art_nr, cost_price, variants_count FROM products where id = ?" product_id])))

(defn all_products
  [component]
  (sql/query (:ds component)
             ["SELECT id, art_nr, cost_price, variants_count FROM products"]))

(defn all_variants
  [component]
  (sql/query (:ds component)
             ["SELECT id, color, size, product_id, article_number, variant_number, material_number FROM variants"]))

(defn variant-by-variant-number
  [component variant_number]
  (first
   (sql/query (:ds component)
              ["SELECT id, color, size, product_id, article_number, variant_number, material_number FROM variants where variant_number = ?" variant_number])))

(defn list_variants_for_product
  [component product-id]
  (sql/query (:ds component)
             ["SELECT id, color, size, product_id, article_number, variant_number, material_number FROM variants where product_id = ?" product-id]))