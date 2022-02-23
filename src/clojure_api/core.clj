(ns clojure-api.core
  (:require [ring.adapter.jetty :as ring-jetty]
            [reitit.ring :as ring]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [clojure-api.db :as db]
            [graphqlize.lacinia.core :as l]
            [com.walmartlabs.lacinia :as lacinia]
            [clojure.data.json :as json]
            [io.pedestal.http :as server]
            [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
            [com.walmartlabs.lacinia.schema :as schema]
            [clojure.java.jdbc :as sql])
  (:gen-class))

(def users (atom {}))

(defn string-handler [_]
  {:status 200
   :body "hello hello"})

(defn get-products [_]
  {:status 200
   :body "db/get-products"})

(def app
  (ring/ring-handler
   (ring/router
    ["/"
     ["products" {:get get-products}]
     ["" string-handler]]
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware]}})))

(defn start []
  (ring-jetty/run-jetty app {:port 3000
                             :join? false}))

(defn -main
  [& args]
  (start))

