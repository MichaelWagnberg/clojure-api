(ns clojure-api.db
  (:require [clojure.java.jdbc :as sql]))

(def db {:dbtype "postgresql"
         :dbname "yoda_dev"
         :host "localhost"
         :port 5432
         :user "postgres"})

(def db-spec {:dbtype "postgresql" :dbname "yoda_dev" :user "postgres"})

(def get-products
  (sql/query db-spec ["SELECT art_nr, cost_price, variants_count FROM products"]))

(def get-products-verbose
  (sql/query db-spec ["SELECT * FROM products"]))
