(ns clojure-api.schema
  "Contains custom resolvers and a function to provide the full schema."
  (:require
   [clojure.java.io :as io]
   [com.walmartlabs.lacinia.util :as util]
   [com.walmartlabs.lacinia.schema :as schema]
   [com.stuartsierra.component :as component]
   [clojure-api.db :as db]
   [clojure.edn :as edn]))

(defn product-by-art-nr
  [db]
  (fn [_ args _]
    (db/product-by-art-nr db (:art_nr args))))

(defn variant-by-variant-number
  [db]
  (fn [_ args _]
    (db/variant-by-variant-number db (:variant_number args))))

(defn all-products
  [db]
  (fn [_ _ _]
    (db/all_products db )))

(defn all-variants
  [db]
  (fn [_ _ _]
    (db/all_variants db)))

(defn product-variants
  [db]
  (fn [_ _ product]
    (db/list_variants_for_product db (:id product))))

(defn variant-product
  [db]
  (fn [_ _ variant]
    (db/product_for_variant db (:product_id variant))))

(defn resolver-map
  [component]
  (let [db (:db component)]
    {:query/product-by-art-nr (product-by-art-nr db)
     :query/variant-by-variant-number (variant-by-variant-number db)
     :query/all-products (all-products db)
     :query/all-variants (all-variants db)
     :Product/variants (product-variants db)
     :Variant/product (variant-product db)}))

(defn load-schema
  [component]
  (-> (io/resource "yoda-schema.edn")
      slurp
      edn/read-string
      (util/attach-resolvers (resolver-map component))
      schema/compile))

(defrecord SchemaProvider [schema]

  component/Lifecycle

  (start [this]
    (assoc this :schema (load-schema this)))

  (stop [this]
    (assoc this :schema nil)))

(defn new-schema-provider
  []
  {:schema-provider (-> {}
                        map->SchemaProvider
                        (component/using [:db]))})